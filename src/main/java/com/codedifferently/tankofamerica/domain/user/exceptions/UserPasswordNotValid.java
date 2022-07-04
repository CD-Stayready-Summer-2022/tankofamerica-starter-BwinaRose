package com.codedifferently.tankofamerica.domain.user.exceptions;

public class UserPasswordNotValid extends Exception{
    public UserPasswordNotValid(String message) {
        super(message);
    }
}
