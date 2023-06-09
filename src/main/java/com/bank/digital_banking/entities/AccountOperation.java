package com.bank.digital_banking.entities;

import com.bank.digital_banking.enums.OperationType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountOperation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    private Date operationDate;

    @Enumerated(EnumType.STRING)
    private OperationType type ;
    private double amount ;
    @ManyToOne
    private BankAccount bankAccount ;

    private String description ;

}
