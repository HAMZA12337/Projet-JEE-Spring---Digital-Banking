package com.bank.digital_banking.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.swing.plaf.ColorUIResource;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class SavingAccount extends BankAccount{

    private boolean interestRate;
}
