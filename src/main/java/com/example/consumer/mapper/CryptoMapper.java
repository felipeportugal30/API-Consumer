package com.example.consumer.mapper;

import com.example.consumer.dto.CryptoDataDto;
import com.example.consumer.exception.ExternalApiException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CryptoMapper {

    private static final Logger logger = LoggerFactory.getLogger(CryptoMapper.class);

    public static List<CryptoDataDto> fromJson(String jsonResponse) {
        ObjectMapper objectMapper = new ObjectMapper();
        List<CryptoDataDto> cryptoDataDtoList = new ArrayList<>();

        try {
            JsonNode rootNode = objectMapper.readTree(jsonResponse);
            JsonNode dataNode = rootNode.get("data");

            if (dataNode != null && dataNode.isArray()) {
                for (JsonNode coinNode : dataNode) {
                    JsonNode quoteNode = coinNode.get("quote").path("USD");

                    CryptoDataDto cryptoData = new CryptoDataDto();
                    cryptoData.setId(coinNode.get("id").asLong());
                    cryptoData.setName(coinNode.get("name").asText());
                    cryptoData.setSymbol(coinNode.get("symbol").asText());
                    cryptoData.setPrice(quoteNode.get("price").decimalValue());
                    cryptoData.setMarketCap(quoteNode.get("market_cap").decimalValue());
                    cryptoData.setPercentChange24h(quoteNode.get("percent_change_24h").decimalValue());

                    cryptoDataDtoList.add(cryptoData);
                }
            }

        } catch (Exception e) {
            logger.error("Failed to map crypto data", e);
            throw new ExternalApiException("Invalid response from external API");
        }

        return cryptoDataDtoList;
    }
}
