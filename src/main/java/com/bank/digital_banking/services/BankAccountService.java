package com.bank.digital_banking.services;

import com.bank.digital_banking.Exceptions.BalanceNotSufficientException;
import com.bank.digital_banking.Exceptions.BankAccountNotFoundException;
import com.bank.digital_banking.Exceptions.CustomerNotFoundException;
import com.bank.digital_banking.dtos.*;
import com.bank.digital_banking.entities.BankAccount;
import com.bank.digital_banking.entities.CurrentAccount;
import com.bank.digital_banking.entities.Customer;
import com.bank.digital_banking.entities.SavingAccount;

import java.util.List;

public interface BankAccountService {

    public CustomerDto saveCustomer(CustomerDto customer);
    CurrentBankAccountDTO saveCurrentBankAccount(double initialBlalnce, double overDreaft, Long customerId) throws CustomerNotFoundException;
    SavingBankAccountDTO saveSavingBankAccount(double initialBlalnce, double interstRate, Long customerId) throws CustomerNotFoundException;
    List<CustomerDto> listCustomer();
    BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFoundException;
   void debit(String accountId,double amount ,String description) throws BankAccountNotFoundException, BalanceNotSufficientException;
    void credit(String accountId,double amount ,String description) throws BankAccountNotFoundException, BalanceNotSufficientException;
   void transfer(String accountIdSource,String accountIdDestination,double amount) throws BankAccountNotFoundException, BalanceNotSufficientException;


    List<BankAccountDTO> bankAccountList();

    CustomerDto getCustomer(Long id) throws CustomerNotFoundException;

    CustomerDto updateCustomer(CustomerDto customerDto);

    void deleteCustomer(Long customerId);

    List<AccountOperationDTO> accountHistory(String accountId);

    AccountHistoryDTO  getAccountHistory(String accountId, int page, int size) throws BankAccountNotFoundException;
}
