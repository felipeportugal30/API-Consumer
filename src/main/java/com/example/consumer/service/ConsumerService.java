package com.example.consumer.service;

import com.example.consumer.exception.ExternalApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ConsumerService {

    private final RestTemplate restTemplate;
    private final String API_KEY;

    public ConsumerService(RestTemplate restTemplate, @Value("${coinmarketcap.api.key}") String apiKey) {
        this.restTemplate = restTemplate;
        this.API_KEY = apiKey;
    }

    private static final Logger logger = LoggerFactory.getLogger(ConsumerService.class);

    public String getCryptoCoins() {
        try {
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

        } catch (RuntimeException e) {
            logger.error("Error calling CoinMarketCap API", e);
            throw new ExternalApiException("Error with external api: " + e);
        }
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
