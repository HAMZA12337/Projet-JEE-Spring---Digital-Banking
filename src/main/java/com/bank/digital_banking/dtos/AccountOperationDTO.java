package com.bank.digital_banking.dtos;

import com.bank.digital_banking.entities.BankAccount;
import com.bank.digital_banking.enums.OperationType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;



@Data

public class AccountOperationDTO {

    private Long id ;
    private Date operationDate;
    private OperationType type ;
    private double amount ;
    private String description ;

}
