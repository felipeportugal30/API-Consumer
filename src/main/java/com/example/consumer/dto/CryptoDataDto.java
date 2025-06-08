package com.example.consumer.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public final class CryptoDataDto {

    private long id;
    private String name;
    private String symbol;
    private BigDecimal price;
    private BigDecimal marketCap;
    private BigDecimal percentChange24h;

}
