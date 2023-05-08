package com.bank.digital_banking.entities;

import com.bank.digital_banking.enums.AccountStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BankAccount {

    @Id
    private String id;
    private double balance ;
    private Date createdAt;

   private AccountStatus status;
   @ManyToOne
   private Customer customer;

   @OneToMany(mappedBy="bankAccount")
   private List<AccountOperation> accountOperationList ;



}
