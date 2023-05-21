package com.bank.digital_banking.services;

import com.bank.digital_banking.Exceptions.BalanceNotSufficientException;
import com.bank.digital_banking.Exceptions.BankAccountNotFoundException;
import com.bank.digital_banking.Exceptions.CustomerNotFoundException;
import com.bank.digital_banking.dtos.*;
import com.bank.digital_banking.entities.*;

import com.bank.digital_banking.enums.OperationType;
import com.bank.digital_banking.mappers.BankAccountMapperImp;
import com.bank.digital_banking.repositories.AccountOperationRepository;
import com.bank.digital_banking.repositories.BankAccountRepository;
import com.bank.digital_banking.repositories.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

// transactionall mean that all metods with (commit -rollback ) mode .
@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class BankAccountServiceImp implements BankAccountService{



  private CustomerRepository customerRepository;
  private BankAccountRepository bankAccountRepository;
  private AccountOperationRepository accountOperationRepository;

private BankAccountMapperImp BankAccountDtoMapper;

    @Override
    public CustomerDto saveCustomer(CustomerDto customerDto) {


        Customer customer=BankAccountDtoMapper.fromCustomerDTO(customerDto);

        log.info("Saving new Customer");
        Customer savedCustomer= customerRepository.save(customer);

        return BankAccountDtoMapper.fromCustomer(savedCustomer);
    }

  @Override
  public CurrentBankAccountDTO saveCurrentBankAccount(double initialBalance, double overDreaft, Long customerId) throws CustomerNotFoundException {

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

    return BankAccountDtoMapper.fromCurrentBankAccount(tmp);

  }

  @Override
  public SavingBankAccountDTO saveSavingBankAccount(double initialBlalnce, double interstRate, Long customerId) throws CustomerNotFoundException {
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

      return BankAccountDtoMapper.fromSavingBankAccount(tmp);
  }



    @Override
    public List<CustomerDto> listCustomer() {

        List<Customer> customers=customerRepository.findAll();
      List<CustomerDto> customerDtos= customers.stream().map(cust->BankAccountDtoMapper.fromCustomer(cust)).collect(Collectors.toList());
        return customerDtos;
    }

    @Override
    public BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFoundException {
        BankAccount bankAccount = bankAccountRepository.findById(accountId).orElseThrow(() -> new BankAccountNotFoundException("A"));

        if (bankAccount instanceof SavingAccount) {
            SavingAccount savingAccount = (SavingAccount) bankAccount;
            return BankAccountDtoMapper.fromSavingBankAccount(savingAccount);

        } else {
            CurrentAccount currentAccount = (CurrentAccount) bankAccount;
            return BankAccountDtoMapper.fromCurrentBankAccount(currentAccount);
        }

    }


    @Override
    public void debit(String accountId, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficientException {
     BankAccount bankAccount=bankAccountRepository.findById(accountId).orElseThrow(()->new BankAccountNotFoundException("Not found"));
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
        BankAccount bankAccount=bankAccountRepository.findById(accountId).orElseThrow(()->new BankAccountNotFoundException("Not found"));

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

    @Override
    public List<BankAccountDTO> bankAccountList() {

        List<BankAccount> bankAccounts = bankAccountRepository.findAll();

       List<BankAccountDTO> listBankAccount= bankAccounts.stream().map(bankAccount -> {

            if (bankAccount instanceof SavingAccount) {
                SavingAccount savingAccount = (SavingAccount) bankAccount;
                return BankAccountDtoMapper.fromSavingBankAccount(savingAccount);
            } else {
                CurrentAccount currentAccount = (CurrentAccount) bankAccount;
                return BankAccountDtoMapper.fromCurrentBankAccount(currentAccount);
            }
 }).collect(Collectors.toList());

   return listBankAccount; }


    @Override
public CustomerDto getCustomer(Long id) throws CustomerNotFoundException {

Customer customer= customerRepository.findById(id).orElseThrow(()->new CustomerNotFoundException("Customer Not Found"));


    return BankAccountDtoMapper.fromCustomer(customer);

    }

    @Override
    public CustomerDto updateCustomer(CustomerDto customerDto) {


        Customer customer=BankAccountDtoMapper.fromCustomerDTO(customerDto);

        log.info("Saving new Customer");
        Customer savedCustomer= customerRepository.save(customer);

        return BankAccountDtoMapper.fromCustomer(savedCustomer);
    }



@Override
@Transactional
public void deleteCustomer(Long customerId){

        customerRepository.deleteById(customerId);

}


    @Override
    public List<AccountOperationDTO> accountHistory(String accountId){
        List<AccountOperation> accountOperations = accountOperationRepository.findByBankAccountId(accountId);
        return accountOperations.stream().map(op->BankAccountDtoMapper.fromAccountOperation(op)).collect(Collectors.toList());
    }

    @Override
    public AccountHistoryDTO getAccountHistory(String accountId, int page, int size) throws BankAccountNotFoundException {
        BankAccount bankAccount=bankAccountRepository.findById(accountId).orElse(null);
        if(bankAccount==null) throw new BankAccountNotFoundException("Account not Found");
        Page<AccountOperation> accountOperations = accountOperationRepository.findByBankAccountIdOrderByOperationDateDesc(accountId, PageRequest.of(page, size));
        AccountHistoryDTO accountHistoryDTO=new AccountHistoryDTO();
        List<AccountOperationDTO> accountOperationDTOS = accountOperations.getContent().stream().map(op -> BankAccountDtoMapper.fromAccountOperation(op)).collect(Collectors.toList());
        accountHistoryDTO.setAccountOperationDTOList(accountOperationDTOS);
        accountHistoryDTO.setAccountId(bankAccount.getId());
        accountHistoryDTO.setBalance(bankAccount.getBalance());
        accountHistoryDTO.setCurrentPage(page);
        accountHistoryDTO.setPageSize(size);
        accountHistoryDTO.setTotalPages(accountOperations.getTotalPages());
        return accountHistoryDTO;
    }

}







