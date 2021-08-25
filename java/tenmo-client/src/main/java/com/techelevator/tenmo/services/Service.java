package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import com.techelevator.view.ConsoleService;
import okhttp3.Response;
import org.springframework.http.*;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import javax.security.auth.login.AccountNotFoundException;
import java.beans.AppletInitializer;
import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Exchanger;


public class Service {
    private RestTemplate restTemplate = new RestTemplate();
    private static final String API_BASE_URL = "http://localhost:8080/";
    private AuthenticatedUser currentUser;
    private ConsoleService console;
    private AuthenticationService authenticationService;


    public User[] getAllUsers(String token) {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setBearerAuth(token);
        HttpEntity entity = new HttpEntity(httpHeaders);
        ResponseEntity<User[]> response = restTemplate.exchange(API_BASE_URL + "users", HttpMethod.GET, entity,
                User[].class);

        return response.getBody();
    }

   public Account getAccount(long userId, String token) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);

        HttpEntity entity = new HttpEntity(headers);



        ResponseEntity<Account> accountEntity = restTemplate.exchange(API_BASE_URL + "accounts/" + userId,
                HttpMethod.GET, entity, Account.class);

        return accountEntity.getBody();


    }

    public Transfer[] getAllTransfers(String token) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
        HttpEntity entity = new HttpEntity(headers);

        ResponseEntity<Transfer[]> responseEntity = restTemplate.exchange(API_BASE_URL + "transfers",
                HttpMethod.GET, entity, Transfer[].class);

        return responseEntity.getBody();
    }

 /*   public Account getAccountByAccountId(int accountId, String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
        HttpEntity entity = new HttpEntity(headers);

        ResponseEntity<Account> response = restTemplate.exchange(API_BASE_URL + "accounts/" + accountId,
                HttpMethod.GET, entity, Account.class);

        return response.getBody();
    }*/

    public User getUserByUserId(long userId, String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
        HttpEntity entity = new HttpEntity(headers);

        ResponseEntity<User> response = restTemplate.exchange(API_BASE_URL + "users/" + userId,
                HttpMethod.GET, entity, User.class);

        return response.getBody();
    }

    public User getUserByAccountId(int accountId, String token) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
        HttpEntity entity = new HttpEntity(headers);


        ResponseEntity<User> response = restTemplate.exchange(API_BASE_URL + accountId + "/user",
                    HttpMethod.GET, entity, User.class);


        return response.getBody();
    }

    public String transferTypeDescription(int transferTypeId, String token) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
        HttpEntity entity = new HttpEntity(headers);

        ResponseEntity<String> response = restTemplate.exchange(API_BASE_URL + "transfer-descriptions/" +
                transferTypeId, HttpMethod.GET, entity, String.class);

        return response.getBody();
    }

    public String transferStatusDescription(int transferStatusId, String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
        HttpEntity entity = new HttpEntity(headers);

        ResponseEntity<String> response = restTemplate.exchange(API_BASE_URL + "" +
                "transfer-statuses/" + transferStatusId, HttpMethod.GET, entity, String.class);

        return response.getBody();
    }


}
