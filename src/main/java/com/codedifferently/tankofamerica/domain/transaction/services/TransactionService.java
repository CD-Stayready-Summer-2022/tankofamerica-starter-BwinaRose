package com.codedifferently.tankofamerica.domain.transaction.services;

import com.codedifferently.tankofamerica.domain.account.exceptions.AccountNotFoundException;
import com.codedifferently.tankofamerica.domain.transaction.exceptions.TransactionNotFoundException;
import com.codedifferently.tankofamerica.domain.transaction.models.Transaction;

import java.util.UUID;

public interface TransactionService {
    Transaction create(String accountId, Transaction transaction) throws AccountNotFoundException;
    Transaction getById(Long id) throws TransactionNotFoundException;
    String getAllFromAccount(String accountId);
    String getAllWithdrawals(String accountId);
    String getAllDeposits(String accountId);
    Transaction getFirstTransaction(String accountId);
    Transaction getMostRecentTransaction(String accountId);
}