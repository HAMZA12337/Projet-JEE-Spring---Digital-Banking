package com.bank.digital_banking.dtos;

import com.bank.digital_banking.enums.AccountStatus;
import lombok.Data;

import java.util.Date;


@Data
public class SavingBankAccountDTO extends  BankAccountDTO{

    private String id;
    private double balance ;
    private Date createdAt;
    private double interestRate;
    private CustomerDto customerDto;
    private AccountStatus status;
}
