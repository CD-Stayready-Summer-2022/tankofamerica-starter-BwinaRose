package com.codedifferently.tankofamerica.domain.transaction.services;

import com.codedifferently.tankofamerica.domain.account.exceptions.AccountNotFoundException;
import com.codedifferently.tankofamerica.domain.transaction.exceptions.TransactionNotFoundException;
import com.codedifferently.tankofamerica.domain.transaction.models.Transaction;

import java.util.UUID;

public interface TransactionService {
    Transaction createTransaction(UUID accountID, Transaction transaction) throws AccountNotFoundException;
    Transaction getTransactionById(UUID accountID) throws TransactionNotFoundException;
    //String getAllTransactions(UUID accountID) throws AccountNotFoundException;
}