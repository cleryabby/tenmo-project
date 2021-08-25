package com.techelevator.tenmo.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.BitSet;

import static org.junit.Assert.*;

public class TransferTest {
    Transfer transfer = new Transfer();

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void get_transfer_id_from_set_transfer_id() {

        transfer.setTransferId(123);
        assertEquals(123, transfer.getTransferId());

        transfer.setTransferId(11);
        assertEquals(11, transfer.getTransferId());

        transfer.setTransferId(3);
        assertEquals(3,transfer.getTransferId());

    }


    @Test
    public void get_transfer_type_id_from_set_transfer_type_id() {

        transfer.setTransferTypeId(789);
        assertEquals(789, transfer.getTransferTypeId());

        transfer.setTransferTypeId(5);
        assertEquals(5, transfer.getTransferTypeId());

        transfer.setTransferTypeId(1);
        assertEquals(1, transfer.getTransferTypeId());

    }


    @Test
    public void get_transfer_status_id_from_set_transfer_status_id() {

        transfer.setTransferStatusId(17);
        assertEquals(17, transfer.getTransferStatusId());

        transfer.setTransferStatusId(7);
        assertEquals(7, transfer.getTransferStatusId());

        transfer.setTransferStatusId(50);
        assertEquals(50, transfer.getTransferStatusId());

    }



    @Test
    public void getAccountFrom() {

        transfer.setAccountFrom(5005);
        assertEquals(5005, transfer.getAccountFrom());

        transfer.setAccountFrom(3);
        assertEquals(3, transfer.getAccountFrom());

        transfer.setAccountFrom(76);
        assertEquals(76, transfer.getAccountFrom());


    }



    @Test
    public void getAccountTo() {

        transfer.setAccountTo(7);
        assertEquals(7,transfer.getAccountTo());

        transfer.setAccountTo(1000);
        assertEquals(1000,transfer.getAccountTo());

        transfer.setAccountTo(5);
        assertEquals(5,transfer.getAccountTo());
    }



    @Test
    public void getAmount() {

        transfer.setAmount(new BigDecimal(1050));
        assertEquals(new BigDecimal(1050), transfer.getAmount());

        transfer.setAmount(new BigDecimal(10));
        assertEquals(new BigDecimal(10), transfer.getAmount());

        transfer.setAmount(new BigDecimal(150));
        assertEquals(new BigDecimal(150), transfer.getAmount());

    }


}