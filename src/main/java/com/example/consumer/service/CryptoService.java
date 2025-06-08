package com.example.consumer.service;

import com.example.consumer.dto.CryptoDataDto;
import com.example.consumer.exception.ResourceNotFoundException;
import com.example.consumer.mapper.CryptoMapper;
import com.example.consumer.model.Crypto;
import com.example.consumer.repository.CryptoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CryptoService {

    private final CryptoRepository cryptoRepository;
    private final ConsumerService consumerService;

    public CryptoService(CryptoRepository cryptoRepository, ConsumerService consumerService){
        this.cryptoRepository = cryptoRepository;
        this.consumerService = consumerService;
    }

    private static final Logger logger = LoggerFactory.getLogger(CryptoService.class);

    public void fetchAndSaveCryptoData() {
        String json = consumerService.getCryptoCoins();
        List<CryptoDataDto> dtos = CryptoMapper.fromJson(json);
        saveCrypto(dtos);
    }

    public void saveCrypto(List<CryptoDataDto> listCryptoDataDto) {
        //best performance
        List<Crypto> listCryptos = new ArrayList<>();

        for (CryptoDataDto dto : listCryptoDataDto) {
            Crypto crypto = new Crypto();

            crypto.setName(dto.getName());
            crypto.setSymbol(dto.getSymbol());
            crypto.setPrice(dto.getPrice());
            crypto.setMarketCap(dto.getMarketCap());
            crypto.setPercentChange24h(dto.getPercentChange24h());
            crypto.setTimestamp(LocalDateTime.now());

            listCryptos.add(crypto);
        }
        cryptoRepository.saveAll(listCryptos);
    }

    public Crypto findById (Long id) {
        return cryptoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Crypto with ID " + id + " doesn't exist"));
    }

    public Crypto findByName (String name) {

        try{
            return cryptoRepository.findByName(name);
        } catch (RuntimeException e) {
            throw new ResourceNotFoundException("Crypto with name " + name + " doesn't exist");
        }

    }

    public List<Crypto> findAll () {
        return cryptoRepository.findAll();
    }
}
