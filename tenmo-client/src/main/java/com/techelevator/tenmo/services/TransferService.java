package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.TransferDTO;
import com.techelevator.tenmo.model.UserDTO;
import com.techelevator.util.BasicLogger;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;

import java.util.List;

public class TransferService extends APIService {

    public TransferService() {
        super(URL.TRANSFER.getPath());
    }

    public TransferDTO[] getAllTransfers(){
       TransferDTO[] transfers = null;
        try {
            ResponseEntity<TransferDTO[]> response =
                    restTemplate.exchange(API_URL, HttpMethod.GET, makeAuthEntity(), TransferDTO[].class);
            transfers = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return transfers;
    }
}
