package com.bank.digital_banking.services;

import com.bank.digital_banking.entities.BankAccount;
import com.bank.digital_banking.entities.CurrentAccount;
import com.bank.digital_banking.entities.SavingAccount;
import com.bank.digital_banking.repositories.BankAccountRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class BankService {


    @Autowired
    BankAccountRepository bankAccountRepository;


public void consulter(){

    BankAccount bankAccount=bankAccountRepository.findById("77a93c1f-eddf-4670-83e2-5ba61c1e7ef9").orElse(null);

    System.out.println("*******************************");
    System.out.println(bankAccount.getId());
    System.out.println(bankAccount.getBalance());
    System.out.println(bankAccount.getStatus());
    System.out.println(bankAccount.getCreatedAt());
    System.out.println(bankAccount.getCustomer().getName());
    // to get type of "type column"
    System.out.println(bankAccount.getClass().getSimpleName());

    if(bankAccount instanceof CurrentAccount){

        System.out.println("Over Draft ==>"+((CurrentAccount)bankAccount).getOverDraft());

    }else if(bankAccount instanceof SavingAccount){
        System.out.println("Rate ==>"+((SavingAccount)bankAccount).getInterestRate());

    }

    // get history of this account (operations)

    bankAccount.getAccountOperationList().forEach(oper->{

        System.out.println("**************************");
        System.out.println(oper.getType());
        System.out.println(oper.getAmount());
        System.out.println(oper.getOperationDate());




    });


}




}
