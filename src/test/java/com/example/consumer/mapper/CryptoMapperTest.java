package com.example.consumer.mapper;

import com.example.consumer.dto.CryptoDataDto;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CryptoMapperTest {

    @Test
    void testValidJsonConversion() {
        String json = """
        {
          "data": [
            {
              "id": 1,
              "name": "Bitcoin",
              "symbol": "BTC",
              "quote": {
                "USD": {
                  "price": 30000.0,
                  "market_cap": 600000000,
                  "percent_change_24h": 2.5
                }
              }
            }
          ]
        }
        """;

        List<CryptoDataDto> result = CryptoMapper.fromJson(json);
        assertEquals(1, result.size());
        CryptoDataDto dto = result.get(0);
        assertEquals("Bitcoin", dto.getName());
        assertEquals("BTC", dto.getSymbol());
        assertEquals(BigDecimal.valueOf(30000.0), dto.getPrice());
    }
}
