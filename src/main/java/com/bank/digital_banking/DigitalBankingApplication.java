package com.bank.digital_banking;

import com.bank.digital_banking.Exceptions.BalanceNotSufficientException;
import com.bank.digital_banking.Exceptions.BankAccountNotFoundException;
import com.bank.digital_banking.Exceptions.CustomerNotFoundException;
import com.bank.digital_banking.dtos.BankAccountDTO;
import com.bank.digital_banking.dtos.CurrentBankAccountDTO;
import com.bank.digital_banking.dtos.CustomerDto;
import com.bank.digital_banking.dtos.SavingBankAccountDTO;
import com.bank.digital_banking.entities.*;
import com.bank.digital_banking.enums.AccountStatus;
import com.bank.digital_banking.enums.OperationType;
import com.bank.digital_banking.repositories.AccountOperationRepository;
import com.bank.digital_banking.repositories.BankAccountRepository;
import com.bank.digital_banking.repositories.CustomerRepository;
import com.bank.digital_banking.services.BankAccountService;
import com.bank.digital_banking.services.BankService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
@EnableTransactionManagement
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
CommandLineRunner start(BankAccountService bankAccountService) {

    return args -> {

        Stream.of("hamza","hamid","karim").forEach(name->{

            CustomerDto customer= new CustomerDto ();

            customer.setName(name);
            customer.setEmail(name+"@gmail.com");

            bankAccountService.saveCustomer(customer);


            // get customers


            bankAccountService.listCustomer().forEach(tmp->{

                try {
                    bankAccountService.saveCurrentBankAccount(10000+Math.random() * 9000, 900, tmp.getId());
                    bankAccountService.saveSavingBankAccount(10000+Math.random() * 1000, 5.5, tmp.getId());

                    List<BankAccountDTO> bankAccounts = bankAccountService.bankAccountList();
                    for (BankAccountDTO bankAccount : bankAccounts) {
                        String accountId;
                        if(bankAccount instanceof SavingBankAccountDTO) {

                            accountId=((SavingBankAccountDTO)bankAccount).getId();
                        }else{
                            accountId=((CurrentBankAccountDTO)bankAccount).getId();
                        }

                        bankAccountService.credit(accountId, 100000+Math.random() * 100, "Credit");
                        bankAccountService.debit(accountId,Math.random()*1000+1000,"Debit");


                    }


                } catch (CustomerNotFoundException e) {
                    throw new RuntimeException(e);
                }
                catch (BankAccountNotFoundException |BalanceNotSufficientException e) {
                    throw new RuntimeException(e);
                }

            });


        });









    };
}



}
