package com.techelevator.dao;

import com.techelevator.Exception.AccountNotFoundException;
import com.techelevator.model.Account;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
@Component
public class JdbcAccountDao implements AccountDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcAccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public String getUsernameByAccountId(int accountId){
        String sql = "SELECT username FROM users u JOIN accounts a ON a.user_id = u.user_id WHERE account_id = ? " +
                "RETURNING username;";
        String username = null;

        username = jdbcTemplate.queryForObject(sql, String.class, accountId);

        if(username == null) throw new AccountNotFoundException();

        return username;
    }

    @Override
    public Account getAccountIdByUsername(String username) {

        Account account = null;
        String sql = "SELECT * FROM accounts a JOIN users u ON u.user_id = a.user_id WHERE username = ?; ";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, username);

        if (results.next()) {
                account = mapRowToAccount(results);
            } else throw new AccountNotFoundException();

        return account;
    }


    private Account mapRowToAccount(SqlRowSet rowSet) {
        Account account = new Account();

        account.setAccountId(rowSet.getInt("account_id"));
        account.setUserId(rowSet.getInt("user_id"));

        return account;
    }
}
