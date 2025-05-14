package com.example.consumer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ConsumerService {

    @Autowired
    private RestTemplate restTemplate;

    //api key, if is necessary
    private final String API_KEY = "c12eb97e-9587-4377-93b2-2a74735f9eda";

    public String getCryptoCoins() {

        //this link return the number 10 in list (limit=10), you can adjust this value
        String url = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest?limit=10&convert=USD";
        HttpHeaders headers = new HttpHeaders();

        headers.set("X-CMC_PRO_API_KEY", API_KEY);
        headers.set("Accept", "application/json");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                String.class
        );
        return response.getBody();
    }
    /*
    public String getBitcoin() {

        String url = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest?limit=1&convert=USD";
        HttpHeaders headers = new HttpHeaders();

        headers.set("X-CMC_PRO_API_KEY", API_KEY);
        headers.set("Accept", "application/json");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                String.class
        );
        return response.getBody();
    }
    */
}
