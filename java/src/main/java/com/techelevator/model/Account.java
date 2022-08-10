package com.techelevator.model;

import com.techelevator.dao.AccountDao;

public class Account {

    private int accountId;
    private int userId;

    public Account() {}

    public Account(int accountId, int userId) {
        this.accountId = accountId;
        this.userId = userId;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountId=" + accountId +
                ", userId=" + userId +
                '}';
    }
}
