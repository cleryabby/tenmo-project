package com.techelevator.tenmo.controller;


import com.techelevator.tenmo.dao.AccountDAO;
import com.techelevator.tenmo.dao.JdbcUserDao;
import com.techelevator.tenmo.dao.TransferDAO;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.exception.AccountNotFoundException;
import com.techelevator.tenmo.exception.InsufficientFundsException;
import com.techelevator.tenmo.exception.TransferNotFoundException;
import com.techelevator.tenmo.exception.UserNotFoundException;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Balance;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cassandra.CassandraProperties;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")

public class TenmoController {

    @Autowired
    AccountDAO dao;

    @Autowired
    UserDao userDao;

    @Autowired
    TransferDAO transferDAO;

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path = "/balance", method = RequestMethod.GET)
    public Balance getBalance(Principal principal) throws Exception {
        System.out.println(principal.getName());
        return dao.getBalance(principal.getName());
    }


    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path = "/users", method = RequestMethod.GET)
    public List<User> getAllUsers() throws Exception {

        return userDao.getAllUsers();

    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/users-transfers", method = RequestMethod.POST)
    public void transferFunds(@Valid @RequestBody Transfer transfer) throws InsufficientFundsException {


        if (transfer.getAmount().compareTo(dao.getAccountByAccountId(transfer.getAccountFrom()).getBalance()) < 0) {


            transferDAO.transferFunds(transfer);
            Account accountFrom = dao.getAccountByAccountId(transfer.getAccountFrom());
            Account accountTo = dao.getAccountByAccountId(transfer.getAccountTo());

            accountFrom.setBalance(accountFrom.getBalance().subtract(transfer.getAmount()));
            accountTo.setBalance(accountTo.getBalance().add(transfer.getAmount()));

            dao.updateBalance(accountFrom);
            dao.updateBalance(accountTo);

        } else {

            throw new InsufficientFundsException();

        }

    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path = "/accounts/{userId}", method = RequestMethod.GET)
    public Account getAccount(@PathVariable("userId") long userId) throws AccountNotFoundException {

        return dao.getAccount(userId);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path = "/transfers", method = RequestMethod.GET)
    public List<Transfer> getAllTransfers() throws Exception {

        return transferDAO.getAllTransfers();
    }

    //Commented out as was causing error in transferFunds
//    @RequestMapping(path = "/accounts/{accountId}", method = RequestMethod.GET)
//    public Account getAccountByAccountId(@PathVariable("accountId") int accountId) {

//        return dao.getAccountByAccountId(accountId);
//    }
   //

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path = "/users/{userId}", method = RequestMethod.GET)
    public User getUserByUserId(@PathVariable("userId") long userId) throws Exception{

        return userDao.getUserByUserId(userId);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path = "/{accountId}/user", method = RequestMethod.GET)
    public User getUserByAccountId(@PathVariable("accountId") int accountId) throws UserNotFoundException {

        return userDao.getUserByAccountId(accountId);
    }

    @RequestMapping(path = "/transfer-descriptions/{transferTypeId}", method = RequestMethod.GET)
    public String transferTypeDescription(@PathVariable("transferTypeId") int transferTypeId) {

        return transferDAO.transferTypeDescription(transferTypeId);
    }

    @RequestMapping(path = "/transfer-statuses/{transferStatusId}", method = RequestMethod.GET)
    public String transferStatusDescription(@PathVariable("transferStatusId") int transferStatusId) {

        return transferDAO.transferStatusDescription(transferStatusId);

    }


}
