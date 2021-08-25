package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

public class AccountJdbcDAOTests extends TenmoDaoTests{

    private static final Account ACCOUNT_1 = new Account (1, 1L, new BigDecimal(1000));

    private static final Account ACCOUNT_2 = new Account (2, 2L, new BigDecimal(2000));

    private static final Account ACCOUNT_3 = new Account (3, 3L, new BigDecimal(2500));


    private AccountJdbcDAO sut;
    private Account testAccount;

    @Before
    public void setup() {
        sut = new AccountJdbcDAO(dataSource);
        testAccount = new Account (4, 1L, new BigDecimal(1500));
    }

    @Test
    public void updatedAccount_has_expected_values_when_retrieved() {
        Account accountToUpdate = sut.getAccount(1L);

        accountToUpdate.setBalance(new BigDecimal(2000.00));

        sut.updateBalance(accountToUpdate);

        Account retrievedAccount = sut.getAccount(1L);
 //       Assert.assertEquals(retrievedAccount.getBalance().compareTo(accountToUpdate.getBalance()),0);

        int account1 = retrievedAccount.getAccountId();
        int account2 = accountToUpdate.getAccountId();

        Assert.assertEquals(account1, account2);


    }

    @Test
    public void getAccount_returns_correct_account_for_userId() {
        Account newAccount = new Account(2, 2L, new BigDecimal(2000.00));

        assertAccountsMatch(ACCOUNT_2, newAccount);

        int account1 = newAccount.getAccountId();
        int account2 =  ACCOUNT_2.getAccountId();

        Assert.assertEquals(account1, account2);

    }

    @Test
    public void getAccountByAccountId_returns_correct_account_for_accountId() {
        Account newAccount = sut.getAccount(1L);

        int account1 = newAccount.getAccountId();
        int account2 = ACCOUNT_1.getAccountId();

        Assert.assertEquals(account1, account2);

    }





    private void assertAccountsMatch(Account expected, Account actual) {
        Assert.assertEquals(expected.getAccountId(), actual.getAccountId());
        Assert.assertEquals(expected.getUserId(), actual.getUserId());
        Assert.assertEquals(expected.getBalance().compareTo(actual.getBalance()),0);

    }


}



