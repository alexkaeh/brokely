package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.UserDto;
import com.techelevator.util.BasicLogger;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;

public class UserService extends ApiService {

//    protected final String URL;
//    protected RestTemplate restTemplate = new RestTemplate();
//
//    protected String authToken = null;

    public UserService() {
        super(Url.USER.getPath());
    }

    public UserDto[] getUsers() {
        UserDto[] users = null;
        try {
            ResponseEntity<UserDto[]> response =
                    restTemplate.exchange(API_URL, HttpMethod.GET, makeAuthEntity(), UserDto[].class);
            users = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return users;
    }

}
