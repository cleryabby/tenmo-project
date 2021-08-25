package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class TransferJdbcDAOTests extends TenmoDaoTests {

    private static final Transfer TRANSFER_1 = new Transfer(1, 2, 2, 1,
            2, new BigDecimal(10));

    private static final Transfer TRANSFER_2 = new Transfer(2, 2, 2, 3,
            2, new BigDecimal(15));

    private static final Transfer TRANSFER_3 = new Transfer(3, 2, 2, 2,
            1, new BigDecimal(20));

    private TransferJdbcDAO sut;
    private Transfer testTransfer;

    @Before
    public void setup() {
        sut = new TransferJdbcDAO(dataSource);
        testTransfer = new Transfer (4, 2, 2, 2, 3,
                new BigDecimal(45));

    }

    @Test
    public void transferFunds_increases_entries_on_transfer_table() {
        sut.transferFunds(testTransfer);
        List<Transfer> transferList = sut.getAllTransfers();
        Assert.assertEquals(4, transferList.size());

    }

    @Test
    public void getAllTransfers_returns_correct_number_of_transfers() {
        List<Transfer> transfersList = sut.getAllTransfers();

        Assert.assertEquals(3, transfersList.size());
    }

    @Test
    public void transferTypeDescription_returns_correct_description_using_typeID() {
        String desc = sut.transferTypeDescription(TRANSFER_3.getTransferTypeId());
        String desc2 = "Send";

        Assert.assertEquals(desc2, desc);

    }

    @Test
    public void transferStatusDesc_returns_correct_desc_for_statusId() {
        String desc1 = sut.transferStatusDescription(TRANSFER_2.getTransferStatusId());
        String desc2 = "Approved";

        Assert.assertEquals(desc2, desc1);

    }

    private void assertTransfersMatch(Transfer expected, Transfer actual) {
        Assert.assertEquals(expected.getTransferId(), actual.getTransferId());
        Assert.assertEquals(expected.getTransferTypeId(), actual.getTransferTypeId());
        Assert.assertEquals(expected.getTransferStatusId(), actual.getTransferStatusId());
        Assert.assertEquals(expected.getAccountFrom(), actual.getAccountFrom());
        Assert.assertEquals(expected.getAccountTo(), actual.getAccountTo());
        Assert.assertEquals(expected.getAmount().compareTo(actual.getAmount()),0);

    }







}
