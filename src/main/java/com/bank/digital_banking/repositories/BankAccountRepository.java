package com.bank.digital_banking.repositories;

import com.bank.digital_banking.entities.BankAccount;
import com.bank.digital_banking.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount,String> {
}
