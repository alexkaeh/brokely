package com.techelevator.tenmo.services;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

public abstract class APIService {
    
    protected final String API_URL;
    protected RestTemplate restTemplate = new RestTemplate();

    protected String authToken = null;

    public APIService(String url) {
        this.API_URL = url;
    }

    /***************************************************************/

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    HttpEntity<Void> makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(headers);
    }

}
