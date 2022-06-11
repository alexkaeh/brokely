package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.UserDTO;
import com.techelevator.util.BasicLogger;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;

public class UserService extends APIService {

//    protected final String URL;
//    protected RestTemplate restTemplate = new RestTemplate();
//
//    protected String authToken = null;

    public UserService() {
        super(URL.USER.getPath());
    }

    public UserDTO[] getUsers() {
        UserDTO[] users = null;
        try {
            ResponseEntity<UserDTO[]> response =
                    restTemplate.exchange(API_URL, HttpMethod.GET, makeAuthEntity(), UserDTO[].class);
            users = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return users;
    }

}
