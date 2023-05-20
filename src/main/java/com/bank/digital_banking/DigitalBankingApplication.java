package com.bank.digital_banking;

import com.bank.digital_banking.entities.*;
import com.bank.digital_banking.enums.AccountStatus;
import com.bank.digital_banking.enums.OperationType;
import com.bank.digital_banking.repositories.AccountOperationRepository;
import com.bank.digital_banking.repositories.BankAccountRepository;
import com.bank.digital_banking.repositories.CustomerRepository;
import com.bank.digital_banking.services.BankService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class DigitalBankingApplication {

    public static void main(String[] args) {
        SpringApplication.run(DigitalBankingApplication.class, args);
    }


//@Bean
//    CommandLineRunner start(CustomerRepository customerRepository,
//                            BankAccountRepository bankAccountRepository,
//                            AccountOperationRepository accountOperationRepository){
//
//        return args -> {
//            // add Cuustomers
//
//   Stream.of("Hassan","Yassine","Aicha").forEach(name->{
//          Customer customer= new Customer();
//          customer.setName(name);
//          customer.setEmail(name+"@gmail.com");
//          customerRepository.save(customer);
//   });
//
////  add Current Account
//
//            customerRepository.findAll().forEach(tmp->{
//                CurrentAccount currentAccount = new CurrentAccount();
//                currentAccount.setId(UUID.randomUUID().toString());
//                currentAccount.setBalance(Math.random()*1000);
//                currentAccount.setCreatedAt(new Date());
//                currentAccount.setStatus(AccountStatus.CREATED);
//                currentAccount.setCustomer(tmp);
//                currentAccount.setOverDraft(9000);
//                bankAccountRepository.save(currentAccount);
//
//
//                // Saving Account
//                SavingAccount savingAccount = new SavingAccount();
//                savingAccount.setId(UUID.randomUUID().toString());
//                savingAccount .setBalance(Math.random()*1000);
//                savingAccount .setCreatedAt(new Date());
//                savingAccount .setStatus(AccountStatus.CREATED);
//                savingAccount .setCustomer(tmp);
//                savingAccount.setInterestRate(5.5);
//
//                bankAccountRepository.save(savingAccount);
//  });
//
//
//            bankAccountRepository.findAll().forEach(acc->{
//                for(int i=0;i<10;i++){
//                    AccountOperation accountOperation= new AccountOperation();
//                    accountOperation.setOperationDate(new Date());
//                    accountOperation.setAmount(Math.random()*1200);
//                    accountOperation.setType(Math.random()>0.5? OperationType.CREDIT:OperationType.DEBIT);
//                    accountOperation.setBankAccount(acc);
//                    accountOperationRepository.save(accountOperation);
//
//                }
//            });
//
//     };
//
//
//    }

@Bean
CommandLineRunner start(BankService bankService) {

    return args -> {
  bankService.consulter();
    };
}



}
