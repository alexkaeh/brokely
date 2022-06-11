package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.UserDTO;
import com.techelevator.util.BasicLogger;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

public class UserService extends APIService {

    public UserService(String api_url) {
        super(api_url, Path.USER.getPath());
    }

    public UserDTO[] getUsers() {
        UserDTO[] users = null;
        try {
            ResponseEntity<UserDTO[]> response =
                    restTemplate.exchange(SERVICE_URL, HttpMethod.GET, makeAuthEntity(), UserDTO[].class);
            users = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return users;
    }

}
