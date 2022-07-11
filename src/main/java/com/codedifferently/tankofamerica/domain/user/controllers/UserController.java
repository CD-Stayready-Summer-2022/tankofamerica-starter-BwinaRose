package com.codedifferently.tankofamerica.domain.user.controllers;

import com.codedifferently.tankofamerica.domain.user.exceptions.UserNotFoundException;
import com.codedifferently.tankofamerica.domain.user.models.User;
import com.codedifferently.tankofamerica.domain.user.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.Optional;

@ShellComponent
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ShellMethod("Create a new User")
    public User createNewUser(@ShellOption({"-F", "--firstname"}) String firstName,
                              @ShellOption({"-L", "--lastname"})String lastName,
                              @ShellOption({"-E", "--email"})String email,
                              @ShellOption({"-P", "--password"})String password){
        User user = new User(firstName,lastName,email,password);
        user = userService.create(user);
        return user;
    }

    @ShellMethod("Get All Users")
    public String getAllUsers(){
        return userService.getAllUsers();
    }

    @ShellMethod("Get user by id")
    public User getUserById(@ShellOption({"-I", "--userid"}) Long userId) {
        User user = null;
        try {
            user = userService.getById(userId);
        } catch (UserNotFoundException e) {
            System.out.printf("User with id %d does not exist %n", userId);
        }
        return user;
    }



    @ShellMethod("Update user information")
    public String updateUser(@ShellOption({"-I", "--userid"}) Long userId,
                             @ShellOption({"-F", "--firstname"}) String firstName,
                             @ShellOption({"-L", "--lastname"})String lastName,
                             @ShellOption({"-E", "--email"})String email,
                             @ShellOption({"-P", "--password"})String password){
        User user = getUserById(userId);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password);
        userService.update(user);
        String msg = String.format("Updated user %s",user.toString());

        return msg;
    }
}