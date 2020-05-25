package com.redhat.app.kogito.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Account {
    private Double balance;
    private String status;
    private String accountId;    
    private Double overdraft;
    private Customer<Account> customer;

    public Account(Double balance, String accountId) {
        super();
        this.balance = balance;
        this.accountId = accountId;
    }
    public void withdraw(int money) {
        balance -= money;
    }

    
}