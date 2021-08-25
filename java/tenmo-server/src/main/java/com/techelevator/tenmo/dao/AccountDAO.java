package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Balance;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;

import java.math.BigDecimal;
import java.util.List;

public interface AccountDAO {

    Balance getBalance(String user);

    public void updateBalance(Account account);

    public Account getAccount(long userId);

    public Account getAccountByAccountId(int accountId);


}
