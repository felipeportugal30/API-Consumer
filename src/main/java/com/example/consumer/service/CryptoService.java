package com.example.consumer.service;

import com.example.consumer.dto.CryptoDataDto;
import com.example.consumer.model.Crypto;
import com.example.consumer.repository.CryptoRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CryptoService {

    @Autowired
    private CryptoRepository cryptoRepository;

    @Autowired
    private ConsumerService consumerService;

    public void fetchAndSaveCryptoData() {
        String json = consumerService.getCryptoCoins();
        List<CryptoDataDto> dtos = cleanCryptoData(json);
        saveCrypto(dtos);
    }

    public List<CryptoDataDto> cleanCryptoData(String jsonResponse) {
        ObjectMapper objectMapper = new ObjectMapper();
        List<CryptoDataDto> cryptoDataDtoList = new ArrayList<>();

        try {
            JsonNode rootNode = objectMapper.readTree(jsonResponse);
            JsonNode dataNode = rootNode.get("data");

            if (dataNode != null && dataNode.isArray()) {
                for (JsonNode coinNode : dataNode) {
                    JsonNode quoteNode = coinNode.get("quote").get("USD"); // Acessa o valor em USD

                    CryptoDataDto cryptoData = new CryptoDataDto();
                    cryptoData.setId(coinNode.get("id").asLong());
                    cryptoData.setName(coinNode.get("name").asText());
                    cryptoData.setSymbol(coinNode.get("symbol").asText());
                    cryptoData.setPrice(quoteNode.get("price").asDouble());
                    cryptoData.setMarketCap(quoteNode.get("market_cap").asDouble());
                    cryptoData.setPercentChange24h(quoteNode.get("percent_change_24h").asDouble());

                    cryptoDataDtoList.add(cryptoData);
                }
            }

            return cryptoDataDtoList;
        } catch (Exception e) {
            System.out.println("error:" + e.getMessage());
        }
        return cryptoDataDtoList;
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
        return cryptoRepository.findById(id).orElseThrow(() -> new RuntimeException("Crypto with ID " + id + " doesn't exist"));
    }

    public List<Crypto> findAll () {
        return cryptoRepository.findAll();
    }

    //this method probably won't be used
    public void deleteById (Long id) {
        cryptoRepository.deleteById(id);
    }
}
