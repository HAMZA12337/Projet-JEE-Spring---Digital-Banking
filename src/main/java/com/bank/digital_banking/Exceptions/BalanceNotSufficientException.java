package com.bank.digital_banking.Exceptions;

public class BalanceNotSufficientException extends Exception {
    public BalanceNotSufficientException(String balanceNotEnough){

    super(balanceNotEnough);

    }
}
