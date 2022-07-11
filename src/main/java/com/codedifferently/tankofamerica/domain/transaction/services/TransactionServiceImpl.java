package com.codedifferently.tankofamerica.domain.transaction.services;

import com.codedifferently.tankofamerica.domain.account.exceptions.AccountNotFoundException;
import com.codedifferently.tankofamerica.domain.account.models.Account;
import com.codedifferently.tankofamerica.domain.account.repos.AccountRepo;
import com.codedifferently.tankofamerica.domain.account.services.AccountService;
import com.codedifferently.tankofamerica.domain.transaction.exceptions.TransactionNotFoundException;
import com.codedifferently.tankofamerica.domain.transaction.models.Transaction;
import com.codedifferently.tankofamerica.domain.transaction.repos.TransactionRepo;
import com.codedifferently.tankofamerica.domain.user.models.User;
import com.codedifferently.tankofamerica.domain.user.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
    public Transaction createTransaction(UUID accountId, Transaction transaction) throws AccountNotFoundException {
        Account account = accountService.getById(String.valueOf(accountId));
        transaction.setAccount(account);
        return transactionRepo.save(transaction);
    }

    @Override
    public Transaction getTransactionById(UUID id) throws TransactionNotFoundException {
        Optional<Transaction> optional = transactionRepo.findById(id);
        if (optional.isEmpty()) {
            throw new TransactionNotFoundException(String.format("Transaction with id: %s not found", id));
        }
        return optional.get();
    }

}