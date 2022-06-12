package com.techelevator.tenmo.services;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

public abstract class APIService {
    
    protected final String API_URL;
    protected RestTemplate restTemplate = new RestTemplate();

    private static String authToken = null;

    public APIService(String url) {
        this.API_URL = url;
    }

    /***************************************************************/

    public static void setAuthToken(String authToken) {
        APIService.authToken = authToken;
    }

    HttpEntity<Void> makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(headers);
    }

}
