package com.example.consumer.controller;

import com.example.consumer.model.Crypto;
import com.example.consumer.repository.CryptoRepository;
import com.example.consumer.service.ConsumerService;
import com.example.consumer.service.CryptoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/consumer/api")
public class ConsumerController {

    @Autowired
    private ConsumerService consumerService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CryptoService cryptoService;

    @Autowired
    private CryptoRepository cryptoRepository;

    @GetMapping("/cryptocoins")
    public List<Crypto> getCryptocoins() {
        cryptoService.fetchAndSaveCryptoData();
        return cryptoService.findAll();
    }

    @GetMapping("/all")
    public List<Crypto> getAllSavedCryptos() {
        return cryptoService.findAll();
    }
}
