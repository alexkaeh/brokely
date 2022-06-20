/**
 * This abstract class is extended by all classes in the service package that need authentication, giving them
 * access the methods relating to HTTP headers, auth tokens, and API URLs.
 */
package com.techelevator.tenmo.services;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

public abstract class ApiService {
    
    protected final String API_URL;
    protected RestTemplate restTemplate = new RestTemplate();

    private static String authToken = null;

    /* Each service extending this class should make a super call to this constructor in its own constructor,
    passing its own specific URL from the list of URLS found in the Url enum found in the info package. */
    public ApiService(String url) {
        this.API_URL = url;
    }


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
