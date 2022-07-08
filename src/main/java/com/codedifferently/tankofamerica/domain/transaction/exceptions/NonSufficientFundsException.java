package com.codedifferently.tankofamerica.domain.transaction.exceptions;

public class NonSufficientFundsException extends Exception{
    public NonSufficientFundsException(String message) {
        super(message);
    }
}
