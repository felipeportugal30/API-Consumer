package com.example.consumer.service;

import com.example.consumer.exception.ExternalApiException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ConsumerServiceTest {

    @Mock
    private RestTemplate restTemplate;

    private ConsumerService consumerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        consumerService = new ConsumerService(restTemplate, "fake-api-key");
    }

    @Test
    void getCryptoCoins_success() {
        String expected = "{\"data\":[]}";
        ResponseEntity<String> response =
                new ResponseEntity<>(expected, HttpStatus.OK);

        when(restTemplate.exchange(
                anyString(), eq(HttpMethod.GET), any(HttpEntity.class), eq(String.class)))
                .thenReturn(response);

        String result = consumerService.getCryptoCoins();

        assertEquals(expected, result);
        verify(restTemplate).exchange(anyString(), eq(HttpMethod.GET), any(), eq(String.class));
    }

    @Test
    void getCryptoCoins_error() {
        when(restTemplate.exchange(
                anyString(), eq(HttpMethod.GET), any(HttpEntity.class), eq(String.class)))
                .thenThrow(new RuntimeException("timeout"));

        assertThrows(ExternalApiException.class, consumerService::getCryptoCoins);
        verify(restTemplate).exchange(anyString(), eq(HttpMethod.GET), any(), eq(String.class));
    }
}
