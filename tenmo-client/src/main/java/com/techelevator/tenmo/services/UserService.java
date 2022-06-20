/**
 * RestTemplate methods for accessing the User table in our database
 */
package com.techelevator.tenmo.services;

import com.techelevator.tenmo.info.Url;
import com.techelevator.tenmo.model.UserDto;
import com.techelevator.util.BasicLogger;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;

public class UserService extends ApiService {

    //Super call to ApiService, setting the API_URL field to our own specific URL from Url.
    public UserService() {
        super(Url.USER.toString());
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
