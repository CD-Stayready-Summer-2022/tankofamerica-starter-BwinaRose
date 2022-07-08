package com.codedifferently.tankofamerica.domain.account.models;

import com.codedifferently.tankofamerica.domain.transaction.exceptions.NonSufficientFundsException;
import com.codedifferently.tankofamerica.domain.transaction.models.Transaction;
import com.codedifferently.tankofamerica.domain.user.models.User;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name="accounts")
public class Account {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name= "uuid2", strategy = "uuid2")
    @Type(type="uuid-char")
    private UUID id;
    private String name;
    private Double balance;

    @ManyToOne()
    private User owner;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="account")
    private Set<Transaction> transactions;

    public Account() {
    }

    public Account(String name) {
        this.name = name;
        this.balance = 0.0;
    }

    public Account(String name, User owner) {
        this.name = name;
        this.balance = 0.0;
        this.owner = owner;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(Set<Transaction> transactions) {
        this.transactions = transactions;
    }

    public void updateBalance(Double amount) throws NonSufficientFundsException {
        Double newBalance = balance + amount;
        if (newBalance < 0) {
            throw new NonSufficientFundsException("Nonsufficient Funds!");
        } else {
            balance = newBalance;
        }
    }

    public String toString() {
        return String.format("Account for %s named %s with id %s and balance $%.2f", owner.getFirstName(), name, id.toString(), balance);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(id, account.id) && Objects.equals(name, account.name) && Objects.equals(balance, account.balance) && Objects.equals(owner, account.owner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, balance, owner);
    }
}