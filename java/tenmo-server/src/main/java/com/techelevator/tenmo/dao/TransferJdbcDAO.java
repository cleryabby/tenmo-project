package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class TransferJdbcDAO implements TransferDAO {


    private Account account;

    AccountDAO dao;

    private JdbcTemplate jdbcTemplate;

    public TransferJdbcDAO (DataSource ds) {
        this.jdbcTemplate = new JdbcTemplate(ds);

    }



    @Override
    public int getAccountFrom(int transfer_id) {

  /*    METHOD NOT USED
        Account account = new Account();
        String sql = "SELECT * FROM transfers " +
                "JOIN accounts ON transfers.account_from=accounts.account_id" +
                "JOIN users ON accounts.user_id=users.user_id"+
                "WHERE transfer_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, transfer_id);
        while (results.next()) {
            Transfer transfer = mapRowToTransfer(results);
            account.setAccountId(transfer.getAccountFrom());
        }
        return account.getAccountId();*/
        return 0;
    }

    @Override
    public int getAccountTo(int transfer_id) {
       /* METHOD NOT USED
        Account account = new Account();
        String sql = "SELECT * FROM transfers " +
                "JOIN accounts ON transfers.account_from=accounts.account_id" +
                "JOIN users ON accounts.user_id=users.user_id"+
                "WHERE transfer_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, transfer_id);
        while (results.next()) {
            Transfer transfer = mapRowToTransfer(results);
            account.setAccountId(transfer.getAccountTo());
        }
        return account.getAccountId();*/
        return 0;
    }

    @Override
    public BigDecimal getAmount(int transfer_id) {
     /* METHOD NOT USED
        Transfer transfer = new Transfer();
        String sql = "SELECT * FROM transfers " +
                "JOIN accounts ON transfers.account_from=accounts.account_id" +
                "JOIN users ON accounts.user_id=users.user_id"+
                "WHERE transfer_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, transfer_id);
        while (results.next()) {
            transfer = mapRowToTransfer(results);
            transfer.setAmount(transfer.getAmount());
        }

        return transfer.getAmount();*/
        return new BigDecimal(0);
    }

    @Override
    public void getTransferStatusId(int transfer_id) {
       /* METHOD NOT USED
        Transfer transfer = new Transfer();
        String sql = "SELECT * FROM transfers " +
                "JOIN transfer_statuses ON transfers.transfer_status_id" +
                "=transfer_statuses.transfer_status_id"+
                "WHERE transfer_id=?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, transfer_id);

        while (results.next()) {
            transfer = mapRowToTransfer(results);
            transfer.setTransferStatusId(transfer.getTransferStatusId());

        }
*/
    }

    public void transferFunds(Transfer transfer) {

        String sql =  "INSERT INTO transfers (transfer_type_id, transfer_status_id, " +
                "account_from, account_to, amount)" +
                 "VALUES (?, ?, ?, ?, ?)";


            jdbcTemplate.update(sql, transfer.getTransferTypeId(),
                    transfer.getTransferStatusId(), transfer.getAccountFrom(), transfer.getAccountTo(),
                    transfer.getAmount());

    }

   public List<Transfer> getAllTransfers() {

        List<Transfer> transfers = new ArrayList<>();

        String sql = "SELECT transfer_id, transfer_type_id, transfer_status_id, " +
                "account_from, account_to, amount FROM transfers" ;
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);

         while(results.next()) {
             Transfer transfer = mapRowToTransfer(results);
             transfers.add(transfer);

         }
         return transfers;
    }

    public String transferTypeDescription(int transferTypeId) {

        String sql = "SELECT transfer_type_desc " +
                "FROM transfer_types " +
                "WHERE transfer_type_id = ?";
        String desc = jdbcTemplate.queryForObject(sql, String.class, transferTypeId);

         return desc;
    }

    public String transferStatusDescription(int transferStatusId) {

        String sql = "SELECT transfer_status_desc " +
                "FROM transfer_statuses " +
                "WHERE transfer_status_id = ?";
        String desc = jdbcTemplate.queryForObject(sql, String.class, transferStatusId);

        return desc;
    }



    private Transfer mapRowToTransfer(SqlRowSet results){
        Transfer transfer = new Transfer();
        transfer.setTransferId(results.getInt("transfer_id"));
        transfer.setTransferTypeId(results.getInt("transfer_type_id"));
        transfer.setTransferStatusId(results.getInt("transfer_status_id"));
        transfer.setAccountFrom(results.getInt("account_from"));
        transfer.setAccountTo(results.getInt("account_to"));
        transfer.setAmount(results.getBigDecimal("amount"));

        return transfer;
    }

}
