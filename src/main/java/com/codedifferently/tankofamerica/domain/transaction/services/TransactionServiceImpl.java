package com.codedifferently.tankofamerica.domain.transaction.services;

import com.codedifferently.tankofamerica.domain.account.exceptions.AccountNotFoundException;
import com.codedifferently.tankofamerica.domain.account.models.Account;
import com.codedifferently.tankofamerica.domain.account.services.AccountService;
import com.codedifferently.tankofamerica.domain.transaction.exceptions.TransactionNotFoundException;
import com.codedifferently.tankofamerica.domain.transaction.models.Transaction;
import com.codedifferently.tankofamerica.domain.transaction.repos.TransactionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class TransactionServiceImpl implements TransactionService {

    private TransactionRepo transactionRepo;
    private AccountService accountService;

    @Autowired
    public TransactionServiceImpl(TransactionRepo transactionRepo, AccountService accountService) {
        this.transactionRepo = transactionRepo;
        this.accountService = accountService;
    }

    @Override
    public Transaction create(String accountId, Transaction transaction) throws AccountNotFoundException {
        Account account = accountService.getById(accountId);
        transaction.setAccount(account);
        return transactionRepo.save(transaction);
    }

    @Override
    public Transaction getById(Long id) throws TransactionNotFoundException {
        Optional<Transaction> optional = transactionRepo.findById(id);
        if (optional.isEmpty()) {
            throw new TransactionNotFoundException(String.format("Transaction with id: %s not found", id));
        }
        return optional.get();
    }

    @Override
    public String getAllFromAccount(String accountId) {
        return null;
    }

    @Override
    public String getAllWithdrawals(String accountId) {
        return null;
    }

    @Override
    public String getAllDeposits(String accountId) {
        return null;
    }

    @Override
    public Transaction getFirstTransaction(String accountId) {
        return null;
    }

    @Override
    public Transaction getMostRecentTransaction(String accountId) {
        return null;
    }

    public TransactionRepo getTransactionRepo() {
        return transactionRepo;
    }

    public void setTransactionRepo(TransactionRepo transactionRepo) {
        this.transactionRepo = transactionRepo;
    }

    public AccountService getAccountService() {
        return accountService;
    }

    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }
}