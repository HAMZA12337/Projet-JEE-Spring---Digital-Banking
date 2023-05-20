package com.bank.digital_banking.services;

import com.bank.digital_banking.Exceptions.BalanceNotSufficientException;
import com.bank.digital_banking.Exceptions.BankAccountNotFoundException;
import com.bank.digital_banking.Exceptions.CustomerNotFoundException;
import com.bank.digital_banking.entities.*;

import com.bank.digital_banking.enums.OperationType;
import com.bank.digital_banking.repositories.AccountOperationRepository;
import com.bank.digital_banking.repositories.BankAccountRepository;
import com.bank.digital_banking.repositories.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

// transactionall mean that all metods with (commit -rollback ) mode .
@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class BankAccountServiceImp implements BankAccountService{



  private CustomerRepository customerRepository;
  private BankAccountRepository bankAccountRepository;
  private AccountOperationRepository accountOperationRepository;



    @Override
    public Customer saveCustomer(Customer customer) {

        log.info("Saving new Customer");
      Customer savedCustomer=  customerRepository.save(customer);
        return savedCustomer;
    }

  @Override
  public CurrentAccount saveCurrentBankAccount(double initialBalance, double overDreaft, Long customerId) throws CustomerNotFoundException {

    Customer customer=customerRepository.findById(customerId).orElse(null);

    if(customer==null)
      throw new CustomerNotFoundException("Customer not found");

    CurrentAccount bankAccount=new CurrentAccount();

    bankAccount.setId(UUID.randomUUID().toString());
    bankAccount.setCreatedAt(new Date());
    bankAccount.setBalance(initialBalance);
    bankAccount.setCustomer(customer);
    bankAccount.setOverDraft(overDreaft);

   CurrentAccount tmp= bankAccountRepository.save(bankAccount);

    return tmp;

  }

  @Override
  public SavingAccount saveSavingBankAccount(double initialBlalnce, double interstRate, Long customerId) throws CustomerNotFoundException {
    Customer customer=customerRepository.findById(customerId).orElse(null);

    if(customer==null)
      throw new CustomerNotFoundException("Customer not found");

    SavingAccount bankAccount=new SavingAccount();

    bankAccount.setId(UUID.randomUUID().toString());
    bankAccount.setCreatedAt(new Date());
    bankAccount.setBalance(initialBlalnce);
    bankAccount.setCustomer(customer);
    bankAccount.setInterestRate(interstRate);

    SavingAccount  tmp= bankAccountRepository.save(bankAccount);

      return tmp;
  }



    @Override
    public List<Customer> listCustomer() {
        return customerRepository.findAll();
    }

    @Override
    public BankAccount getBankAccount(String accountId) throws BankAccountNotFoundException {
        BankAccount bankAccount=bankAccountRepository.findById(accountId).orElseThrow(()->new BankAccountNotFoundException("A"));
   return bankAccount; }

    @Override
    public void debit(String accountId, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficientException {
     BankAccount bankAccount=getBankAccount(accountId);
     if(bankAccount.getBalance()<amount)
         throw new BalanceNotSufficientException("Balance not Enough");
        AccountOperation accountOperation= new AccountOperation();
        accountOperation.setType(OperationType.DEBIT);
        accountOperation.setAmount(amount);
        accountOperation.setDescription(description);
        accountOperation.setOperationDate(new Date());
        accountOperation.setBankAccount(bankAccount);
        accountOperationRepository.save(accountOperation);

        bankAccount.setBalance(bankAccount.getBalance()-amount);
        bankAccountRepository.save(bankAccount);

    }

    @Override
    public void credit(String accountId, double amount, String description) throws BankAccountNotFoundException {
        BankAccount bankAccount=getBankAccount(accountId);

        AccountOperation accountOperation= new AccountOperation();
        accountOperation.setType(OperationType.CREDIT);
        accountOperation.setAmount(amount);
        accountOperation.setDescription(description);
        accountOperation.setOperationDate(new Date());
        accountOperation.setBankAccount(bankAccount);
        accountOperationRepository.save(accountOperation);

        bankAccount.setBalance(bankAccount.getBalance()+amount);
        bankAccountRepository.save(bankAccount);
    }

    @Override
    public void transfer(String accountIdSource, String accountIdDestination, double amount) throws BankAccountNotFoundException, BalanceNotSufficientException {

        debit(accountIdSource,amount,"Transfer to"+accountIdDestination);
         credit(accountIdDestination,amount,"transfer from "+accountIdSource);




    }
}
