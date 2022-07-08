package com.codedifferently.tankofamerica.domain.transaction.repos;

import com.codedifferently.tankofamerica.domain.transaction.models.Transaction;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface TransactionRepo extends CrudRepository<Transaction, Long> {
}