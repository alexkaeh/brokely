/**
 * RestTemplate methods for accessing the transfer table in our database. The workhorse of our client app.
 */
package com.techelevator.tenmo.services;

import com.techelevator.tenmo.info.Url;
import com.techelevator.tenmo.model.TransferDto;
import com.techelevator.util.BasicLogger;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;

import org.springframework.web.client.RestClientResponseException;

import java.math.BigDecimal;

public class TransferService extends ApiService {

    //Super call to ApiService constructor, setting the API_URL field to our own specific URL from Url.
    public TransferService() {
        super(Url.TRANSFER.toString());
    }


    public TransferDto getTransferById(int transferId) {
        TransferDto transfer = null;

        try {
            HttpEntity<TransferDto> response = restTemplate.exchange(Url.TRANSFER.toString() + transferId, HttpMethod.GET, makeAuthEntity(), TransferDto.class);
            transfer = response.getBody();
        } catch (RestClientException e) {
            BasicLogger.log(e.getMessage());
        }

        return transfer;
    }

    public TransferDto[] getAllTransfers() {
        TransferDto[] transfers = null;
        try {
            ResponseEntity<TransferDto[]> response =
                    restTemplate.exchange(API_URL, HttpMethod.GET, makeAuthEntity(), TransferDto[].class);
            transfers = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return transfers;
    }

    public TransferDto[] getPendingTransfers() {
        TransferDto[] transfers = null;
        try {
            ResponseEntity<TransferDto[]> response =
                    restTemplate.exchange(Url.PENDING.toString(), HttpMethod.GET, makeAuthEntity(), TransferDto[].class);
            transfers = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return transfers;
    }

    public BigDecimal sendMoney(int userToId, BigDecimal amount) {
        TransferDto transfer = new TransferDto();
        transfer.setOtherUserInRequestId(userToId);
        transfer.setAmount(amount);
        BigDecimal balance;
        try {
            HttpEntity<BigDecimal> response = restTemplate.exchange(Url.SEND.toString(), HttpMethod.POST, makeDtoEntity(transfer), BigDecimal.class);
            balance = response.getBody();
            return balance;
        } catch (RestClientException e) {
            BasicLogger.log(e.getMessage());
            return null;
        }
    }

    public Boolean requestMoney(int userFromId, BigDecimal amount) {
        TransferDto transfer = new TransferDto();
        transfer.setOtherUserInRequestId(userFromId);
        transfer.setAmount(amount);

        try {
            HttpEntity<Boolean> response = restTemplate.exchange(Url.REQUEST.toString(), HttpMethod.POST, makeDtoEntity(transfer), Boolean.class);
            return true;
        } catch (RestClientException e) {
            BasicLogger.log(e.getMessage());
            return false;
        }
    }

    public BigDecimal approveRequest(int transferId) {
        BigDecimal returnedBalance = null;
        try {
            HttpEntity<BigDecimal> response = restTemplate.exchange(Url.APPROVE.toString() + transferId, HttpMethod.PUT, makeAuthEntity(), BigDecimal.class);
            returnedBalance = response.getBody();
        } catch (RestClientException e) {
            BasicLogger.log(e.getMessage());
        }
        return returnedBalance;
    }

    public boolean rejectRequest(int transferId) {
        try {
            HttpEntity<Boolean> response = restTemplate.exchange(Url.REJECT.toString() + transferId, HttpMethod.PUT, makeAuthEntity(), Boolean.class);
            return Boolean.TRUE.equals(response.getBody());
        } catch (RestClientException | NullPointerException e) {
            BasicLogger.log(e.getMessage());
            return false;
        }
    }

}
