package com.codedifferently.tankofamerica.domain.program;

import com.codedifferently.tankofamerica.domain.user.controllers.UserController;
import com.codedifferently.tankofamerica.domain.user.exceptions.UserEmailNotValid;
import com.codedifferently.tankofamerica.domain.user.models.User;
import com.codedifferently.tankofamerica.domain.user.services.UserServiceImpl;
import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.jline.reader.LineReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SignUp {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String emailChecked;

    public SignUp() {
    }

    public SignUp(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public User signUp() throws IOException,UserEmailNotValid {
        UserServiceImpl usi = new UserServiceImpl(UserServiceImpl.userRepo);
        UserController uc = new UserController(usi);
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("What is your first name?");
        firstName = input.readLine();
        System.out.println("What is your last name?");
        lastName = input.readLine();
        try {
            System.out.println("What is your email?");
            email = input.readLine();
            if ("admin".equals(email)) { //how to check if table has the email already?
                emailChecked = email;
            }
        } catch (Exception UserEmailNotValid) {
            UserEmailNotValid.printStackTrace();
        }
        System.out.println("Enter a password you would like to use:");
        password = input.readLine();

        User user = uc.createNewUser(firstName, lastName, emailChecked, password);
        System.out.println(user.toString());
        return user;
    }
}

