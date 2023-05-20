package com.bank.digital_banking.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.swing.plaf.ColorUIResource;


@Data
@Entity
@DiscriminatorValue("SA")
@AllArgsConstructor
@NoArgsConstructor
public class SavingAccount extends BankAccount{

    private double interestRate;
}
