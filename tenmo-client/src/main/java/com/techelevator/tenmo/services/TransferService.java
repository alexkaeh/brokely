package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.TransferDto;
import com.techelevator.util.BasicLogger;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;

public class TransferService extends ApiService {

    public TransferService() {
        super(Url.TRANSFER.getPath());
    }

    public TransferDto[] getAllTransfers(){
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
}
