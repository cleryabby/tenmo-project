package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Balance;
import com.techelevator.tenmo.model.Transfer;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

public interface TransferDAO {

    int getAccountFrom(int transfer_id);
    int getAccountTo(int transfer_id);
    BigDecimal getAmount(int transfer_id);
    void getTransferStatusId(int transfer_id);
    public void transferFunds(Transfer transfer);
    public List<Transfer> getAllTransfers();
    String transferTypeDescription(int transferTypeId);
    String transferStatusDescription(int transferStatusId);

}
