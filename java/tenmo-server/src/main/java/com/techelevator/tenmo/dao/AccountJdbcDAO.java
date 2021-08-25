package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Balance;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.math.BigDecimal;

@Component
public class AccountJdbcDAO implements AccountDAO {


    private JdbcTemplate jdbcTemplate;

    public AccountJdbcDAO(DataSource ds) {
        this.jdbcTemplate = new JdbcTemplate(ds);
    }

    @Override
    public Balance getBalance(String user) {

        Balance balance = new Balance();

        String sql = "SELECT * FROM accounts WHERE user_id = (SELECT user_id FROM users " +
                "WHERE username ILIKE ?)";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, user);

        while (results.next()) {

         Account account = mapRowToAccount(results);
         balance.setBalance(new BigDecimal(account.getBalance().toString()));
     }


        return balance;
    }

   public void updateBalance(Account account) {

       String sql =
                   "UPDATE accounts " +
                   "SET balance = ? " +
                   "WHERE account_id = ? ";


           jdbcTemplate.update(sql, account.getBalance(), account.getAccountId());

   }

    public Account getAccount(long userId) {

        Account newAccount = new Account();

        String sql = "SELECT account_id, user_id, balance " +
                "FROM accounts " +
                "WHERE user_id = ?";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);

        while (results.next()) {
            newAccount = mapRowToAccount(results);
        }

        return newAccount;
    }

    public Account getAccountByAccountId(int accountId) {

        Account newAccount = new Account();

        String sql = "SELECT account_id, user_id, balance " +
                "FROM accounts " +
                "WHERE account_id = ?";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, accountId);

        while (results.next()) {
            newAccount = mapRowToAccount(results);
        }

        return newAccount;
    }



    private Account mapRowToAccount(SqlRowSet results) {

        Account account = new Account();
        account.setAccountId(results.getInt("account_id"));
        account.setUserId(results.getInt("user_id"));
        account.setBalance(results.getBigDecimal("balance"));

        return account;

    }
}
