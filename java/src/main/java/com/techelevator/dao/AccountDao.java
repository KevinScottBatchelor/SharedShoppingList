package com.techelevator.dao;

import com.techelevator.model.Account;

public interface AccountDao {
    String getUsernameByAccountId(int accountId);

    Account getAccountIdByUsername(String username);
}
