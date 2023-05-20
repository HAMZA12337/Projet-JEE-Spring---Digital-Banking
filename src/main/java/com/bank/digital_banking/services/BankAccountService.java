package com.bank.digital_banking.services;

import com.bank.digital_banking.Exceptions.BalanceNotSufficientException;
import com.bank.digital_banking.Exceptions.BankAccountNotFoundException;
import com.bank.digital_banking.Exceptions.CustomerNotFoundException;
import com.bank.digital_banking.entities.BankAccount;
import com.bank.digital_banking.entities.CurrentAccount;
import com.bank.digital_banking.entities.Customer;
import com.bank.digital_banking.entities.SavingAccount;

import java.util.List;

public interface BankAccountService {

    public Customer saveCustomer(Customer customer);
    CurrentAccount saveCurrentBankAccount(double initialBlalnce, double overDreaft, Long customerId) throws CustomerNotFoundException;
    SavingAccount saveSavingBankAccount(double initialBlalnce, double interstRate, Long customerId) throws CustomerNotFoundException;
    List<Customer> listCustomer();
    BankAccount getBankAccount(String accountId) throws BankAccountNotFoundException;
   void debit(String accountId,double amount ,String description) throws BankAccountNotFoundException, BalanceNotSufficientException;
    void credit(String accountId,double amount ,String description) throws BankAccountNotFoundException, BalanceNotSufficientException;
   void transfer(String accountIdSource,String accountIdDestination,double amount) throws BankAccountNotFoundException, BalanceNotSufficientException;






}
