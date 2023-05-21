package com.bank.digital_banking.mappers;

import com.bank.digital_banking.dtos.*;
import com.bank.digital_banking.entities.*;
import org.hibernate.property.access.internal.PropertyAccessCompositeUserTypeImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.beans.beancontext.BeanContextServiceAvailableEvent;


@Service
// MapStruct
public class BankAccountMapperImp {

    public CustomerDto fromCustomer(Customer customer){

        CustomerDto customerDto= new CustomerDto();

        // instead of a lot of setters we use this propoerty
        BeanUtils.copyProperties(customer,customerDto);

//        customerDto.setId(customer.getId());
//        customerDto.setEmail(customer.getEmail());
//       customerDto.setName(customer.getName());


    return customerDto;}

    public Customer fromCustomerDTO(CustomerDto customerDTO){
Customer customer= new Customer();
BeanUtils.copyProperties(customerDTO,customer);
        return customer ;
    }

    // convert savingAccout to SavingAccountDTO
    public SavingBankAccountDTO fromSavingBankAccount(SavingAccount savingAccount){

        SavingBankAccountDTO savingAccountDTO= new SavingBankAccountDTO();

        BeanUtils.copyProperties(savingAccount,savingAccountDTO);
        savingAccountDTO.setCustomerDto(fromCustomer(savingAccount.getCustomer()));
        savingAccountDTO.setType(savingAccount.getClass().getSimpleName());
     return savingAccountDTO; }

    // Convert SavingAccountDto to SavingAccount
    public SavingAccount fromSavingBankAccountDTO(SavingBankAccountDTO savingBankAccountDTO){

        SavingAccount savingAccount= new SavingAccount();

        BeanUtils.copyProperties(savingBankAccountDTO,savingAccount);
     savingAccount.setCustomer(fromCustomerDTO(savingBankAccountDTO.getCustomerDto()));

       return savingAccount;
    }

    // for CurrentAccount to CurrentAccountDTO

    public CurrentBankAccountDTO fromCurrentBankAccount(CurrentAccount currentAccount){

        CurrentBankAccountDTO currentBankAccountDTO= new CurrentBankAccountDTO();

        BeanUtils.copyProperties(currentAccount,currentBankAccountDTO);

        currentBankAccountDTO.setCustomerDto(fromCustomer(currentAccount.getCustomer()));
        currentBankAccountDTO.setType(currentAccount.getClass().getSimpleName());
    return currentBankAccountDTO;}


    public CurrentAccount fromCurrentAccountDTO(CurrentBankAccountDTO currentBankAccountDTO){

        CurrentAccount currentAccount= new CurrentAccount();
        BeanUtils.copyProperties(currentBankAccountDTO,currentAccount);

        currentAccount.setCustomer(fromCustomerDTO(currentBankAccountDTO.getCustomerDto()));

    return currentAccount;
    }

// convert bank account to bank accout dto


    public AccountOperationDTO fromAccountOperation(AccountOperation accountOperation){

        AccountOperationDTO accountOperationDTO=new AccountOperationDTO();
        BeanUtils.copyProperties(accountOperation,accountOperationDTO);
      return accountOperationDTO;}


}
