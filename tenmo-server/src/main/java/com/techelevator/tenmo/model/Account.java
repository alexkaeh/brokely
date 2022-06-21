package com.techelevator.tenmo.model;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "account_id")
    private int accountId;
    //    private int userId;
    private BigDecimal balance;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    public Account(int accountId, BigDecimal balance, User user) {
        this.accountId = accountId;
        this.balance = balance;
        this.user = user;
    }

    public Account() {
    }

    public int getAccountId() {
        return this.accountId;
    }

    public BigDecimal getBalance() {
        return this.balance;
    }

    public User getUser() {
        return this.user;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
