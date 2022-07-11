package com.codedifferently.tankofamerica.domain.program;

import com.codedifferently.tankofamerica.domain.user.controllers.UserController;
import com.codedifferently.tankofamerica.domain.user.exceptions.UserEmailNotValid;
import com.codedifferently.tankofamerica.domain.user.exceptions.UserNotFoundException;
import com.codedifferently.tankofamerica.domain.user.models.User;
import com.codedifferently.tankofamerica.domain.user.services.UserServiceImpl;
import org.hibernate.exception.ConstraintViolationException;

import javax.ejb.EJBTransactionRolledbackException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SignUp {
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    public SignUp() {
    }

    public SignUp(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public User signUp() throws IOException,UserEmailNotValid,ConstraintViolationException {
        UserServiceImpl usi = new UserServiceImpl(UserServiceImpl.userRepo);
        UserController uc = new UserController(usi);
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        User user = null;
        boolean isOkay = false;
        System.out.println("What is your first name?");
        firstName = input.readLine();
        System.out.println("What is your last name?");
        lastName = input.readLine();
        System.out.println("Enter a password you would like to use:");
        password = input.readLine();
        System.out.println("What is your email?");
        email = input.readLine(); //i can't catch constraint exception so how to get around this..
        user = uc.createNewUser(firstName, lastName, email, password);
        System.out.println(user.toString());
        return user;
    }
}

