package com.codedifferently.tankofamerica.domain.account.controllers;

import com.codedifferently.tankofamerica.domain.account.exceptions.AccountNotFoundException;
import com.codedifferently.tankofamerica.domain.account.models.Account;
import com.codedifferently.tankofamerica.domain.account.services.AccountService;
import com.codedifferently.tankofamerica.domain.user.exceptions.UserNotFoundException;
import com.codedifferently.tankofamerica.domain.user.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class AccountController {

    private AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @ShellMethod("Create a new account")
    public String createNewAccount(@ShellOption({"-U","--user"}) Long id,
                                   @ShellOption({"-N", "--name"}) String name) {
        try {
            Account account = new Account(name);
            account = accountService.create(id, account);
            return account.toString();
        } catch (UserNotFoundException e) {
            return "The User Id is invalid";
        }
    }

    @ShellMethod("Get account by id")
    public Account getAccountById(@ShellOption({"-I", "--accountid"}) String accountId) {
        Account account = null;
        try {
            account = accountService.getById(accountId);
        } catch (AccountNotFoundException | IllegalArgumentException ex) {
            System.out.printf(ex.getMessage() + "\n");
        }
        return account;
    }

    @ShellMethod("Get user accounts")
    public String userAccounts(@ShellOption({"-U", "--user"}) Long id) {
        try {
            String accounts = accountService.getAllFromUser(id);
            return accounts;
        } catch (UserNotFoundException e) {
            return "The user Id is invalid";
        }
    }
}