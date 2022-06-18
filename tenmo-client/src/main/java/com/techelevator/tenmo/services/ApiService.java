package com.techelevator.tenmo.services;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

public abstract class ApiService {
    
    protected final String API_URL;
    protected RestTemplate restTemplate = new RestTemplate();

    private static String authToken = null;

    public ApiService(String url) {
        this.API_URL = url;
    }

    /***************************************************************/

    public static void setAuthToken(String authToken) {
        ApiService.authToken = authToken;
    }

    protected HttpEntity<Void> makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(headers);
    }

    protected HttpEntity<Object> makeDtoEntity(Object obj) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(obj, headers);
    }

}
