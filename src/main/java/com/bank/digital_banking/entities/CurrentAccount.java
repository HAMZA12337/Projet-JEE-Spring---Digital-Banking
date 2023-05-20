package com.bank.digital_banking.entities;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@DiscriminatorValue("CA")
@AllArgsConstructor
@NoArgsConstructor
public class CurrentAccount extends BankAccount{

    private double overDraft;

}
