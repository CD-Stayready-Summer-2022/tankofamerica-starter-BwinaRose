package com.codedifferently.tankofamerica.domain.transaction.controllers;

import com.codedifferently.tankofamerica.domain.account.exceptions.AccountNotFoundException;
import com.codedifferently.tankofamerica.domain.account.models.Account;
import com.codedifferently.tankofamerica.domain.account.services.AccountService;
import com.codedifferently.tankofamerica.domain.transaction.exceptions.NonSufficientFundsException;
import com.codedifferently.tankofamerica.domain.transaction.exceptions.TransactionNotFoundException;
import com.codedifferently.tankofamerica.domain.transaction.models.Transaction;
import com.codedifferently.tankofamerica.domain.transaction.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.UUID;

@ShellComponent
public class TransactionController {
    private TransactionService transactionService;
    private AccountService accountService;

    @Autowired
    public TransactionController(TransactionService transactionService, AccountService accountService) {
        this.transactionService = transactionService;
        this.accountService = accountService;
    }

    @ShellMethod("Make a money deposit")
    public String makeDeposit(@ShellOption({"-I", "--accountid"}) UUID accountId,
                          @ShellOption({"A", "--amount"}) Double amount) throws AccountNotFoundException {
        try {
            Account account = accountService.getById(String.valueOf(accountId));
            if (account == null){
                throw new AccountNotFoundException("User account not found");
            }
            account.updateBalance(amount);
            account = accountService.update(account);
            Transaction transaction = new Transaction(amount, account);
            transactionService.createTransaction(accountId, transaction);
            return transaction.toString();
        } catch (AccountNotFoundException | NonSufficientFundsException e) {
            return e.getMessage();
        }
    }

    @ShellMethod("Make a withdrawl")
    public String makeWithdrawal(@ShellOption({"-I", "--accountid"}) String accountId,
                             @ShellOption({"-A", "--amount"}) Double amount) throws AccountNotFoundException {
        try {
            Account account = accountService.getById(accountId);
            if (account == null){
                throw new AccountNotFoundException("User account not found");
            }
            amount = (-1) * amount;
            account.updateBalance(amount);
            if (account.getBalance() <= -1){
                throw new NonSufficientFundsException("Nonsufficient Funds!");
            }
            account = accountService.update(account);
            Transaction transaction = new Transaction(amount, account);
            UUID id = UUID.fromString(accountId);
            transactionService.createTransaction(id, transaction);
            return transaction.toString();
        } catch (AccountNotFoundException | NonSufficientFundsException e) {
            return e.getMessage();
        }
    }

    @ShellMethod("Find a transaction by id")
    public Transaction getTransactionById(@ShellOption({"-I", "--transactionId"}) UUID transactionId) {
        Transaction transaction = null;
        try {
            transaction = transactionService.getTransactionById(transactionId);
        } catch (TransactionNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return transaction;
    }

    @ShellMethod("Transfer money between accounts")
    public String makeTransfer(@ShellOption({"-F", "--accountid"}) String accountFrom,
                               @ShellOption({"-T", "--accountid"}) String accountTo,
                               @ShellOption({"-A", "--amount"}) Double amount) throws AccountNotFoundException, NonSufficientFundsException {
        makeWithdrawal(accountFrom,amount);
        makeDeposit(UUID.fromString(accountTo),amount);
        String transfer = String.format("Transfered %s from %s to %s",amount,accountFrom,accountTo);
        return transfer;
    }
}