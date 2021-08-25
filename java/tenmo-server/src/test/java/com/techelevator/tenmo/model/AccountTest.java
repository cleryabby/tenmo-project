package com.techelevator.tenmo.model;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class AccountTest {
    Account account = new Account();


    @Test
    public void get_account_id_from_set_account_id() {

        account.setAccountId(123);

        assertEquals(123, account.getAccountId());

        account.setAccountId(456);
        assertEquals(456, account.getAccountId());

        account.setAccountId(789);
        assertEquals(789, account.getAccountId());


    }

    @Test
     public void get_user_id_from_set_user_id() {

        account.setUserId(5005);
        assertEquals(5005, account.getUserId());

        account.setUserId(4555);
        assertEquals(4555, account.getUserId());

    }

    @Test
    public void get_balance_from_set_balance() {

        account.setBalance(new BigDecimal(80));
        assertEquals(new BigDecimal(80), account.getBalance());

        account.setBalance(new BigDecimal(3));
        assertEquals(new BigDecimal(3), account.getBalance());



    }
}