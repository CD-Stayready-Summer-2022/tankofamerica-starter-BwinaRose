package com.codedifferently.tankofamerica.domain.program;

import com.codedifferently.tankofamerica.domain.user.exceptions.UserEmailNotValid;
import com.codedifferently.tankofamerica.domain.user.exceptions.UserPasswordNotValid;
import com.codedifferently.tankofamerica.domain.user.models.User;
import com.codedifferently.tankofamerica.domain.user.repos.UserRepo;
import com.codedifferently.tankofamerica.domain.user.services.UserServiceImpl;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@ShellComponent
public class Startup {
    @ShellMethod("Start program")
    public String startup() throws IOException, UserEmailNotValid, UserPasswordNotValid {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("\n\n        Welcome to \n      Tank of America\n\n" +
                "          .--._____,\n" +
                "       .-='=='==-, \"\n" +
                "      (O_o_o_o_o_O)    \n\n\n");
        System.out.println("      .----------------.\n" +
                "      | Sign-Up type 0 |\n" +
                ".------------. .-------------.\n" +
                "      | Sign-In type 1 |\n" +
                "      .----------------.");
        Integer userChoice = Integer.parseInt(input.readLine());
        switch (userChoice){
            case 0:
                SignUp signup = new SignUp();
                User user = signup.signUp();
                break;
            case 1:
                SignIn signin = new SignIn();
                String output = String.valueOf(signin.signIn());
                break;
        }
        return null;
    }


}
