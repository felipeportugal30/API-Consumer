package com.example.consumer.controller;

import com.example.consumer.model.Crypto;
import com.example.consumer.service.CryptoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/consumer/api")
public class ConsumerController {

    private final CryptoService cryptoService;

    public ConsumerController(CryptoService cryptoService) {
        this.cryptoService = cryptoService;
    }

    @GetMapping("/find-all")
    public List<Crypto> getAllSavedCryptos() {
        return cryptoService.findAll();
    }

    @GetMapping("/find-by-name/{name}")
    public Crypto getCryptoByName(@PathVariable String name) {
        return cryptoService.findByName(name);
    }
}
