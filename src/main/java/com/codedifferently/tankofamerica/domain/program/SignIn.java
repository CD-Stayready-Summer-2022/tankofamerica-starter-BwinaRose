package com.codedifferently.tankofamerica.domain.program;

import com.codedifferently.tankofamerica.domain.user.controllers.UserController;
import com.codedifferently.tankofamerica.domain.user.exceptions.UserEmailNotValid;
import com.codedifferently.tankofamerica.domain.user.exceptions.UserPasswordNotValid;
import com.codedifferently.tankofamerica.domain.user.models.User;
import com.codedifferently.tankofamerica.domain.user.services.UserServiceImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class SignIn {
    private String email;
    private String password;
    private String firstName;
    private String lastName;

    public SignIn() {
    }

    public SignIn(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public List<User> signIn() throws IOException, UserEmailNotValid, UserPasswordNotValid {
        UserServiceImpl usi = new UserServiceImpl(UserServiceImpl.userRepo);
        UserController uc = new UserController(usi);
        StringBuilder builder = new StringBuilder();
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        System.out.println(".-------------.\n| Enter Email |\n'-------------'");
        email = input.readLine();
        System.out.println(".----------------.\n| Enter Password |\n'----------------'");
        password = input.readLine();
        String users = uc.getAllUsers();
        String[] userArr;
        userArr = users.split("\n");
