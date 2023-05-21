package com.bank.digital_banking.repositories;

import com.bank.digital_banking.entities.AccountOperation;
import com.bank.digital_banking.entities.BankAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountOperationRepository extends JpaRepository<AccountOperation,Long> {

public List<AccountOperation> findByBankAccountId(String accountId);
   Page<AccountOperation> findByBankAccountIdOrderByOperationDateDesc(String accoundId, Pageable pageable);


}
