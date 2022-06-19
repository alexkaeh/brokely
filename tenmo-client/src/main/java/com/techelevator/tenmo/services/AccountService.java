package com.techelevator.tenmo.services;

import com.techelevator.tenmo.info.Url;
import com.techelevator.tenmo.model.Account;
import com.techelevator.util.BasicLogger;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;

import java.math.BigDecimal;

public class AccountService extends ApiService {


    public AccountService() {
        super(Url.ACCOUNT.toString());
    }

    //CHANGED TO BigDecimal
    public BigDecimal getBalance(){
        BigDecimal balance = null;
        try {
            ResponseEntity<BigDecimal> response = restTemplate.exchange(Url.BALANCE.toString(), HttpMethod.GET, makeAuthEntity(), BigDecimal.class);
            balance = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        //assert balance != null;
        return balance;
    }

    public Account getCurrentAccount() {
        Account acc = null;
        try {
            ResponseEntity<Account> response = restTemplate.exchange(API_URL, HttpMethod.GET, makeAuthEntity(), Account.class);
            acc = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return acc;
    }
}
