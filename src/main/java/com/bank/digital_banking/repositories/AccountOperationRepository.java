package com.bank.digital_banking.repositories;

import com.bank.digital_banking.entities.AccountOperation;
import com.bank.digital_banking.entities.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountOperationRepository extends JpaRepository<AccountOperation,Long> {
}
