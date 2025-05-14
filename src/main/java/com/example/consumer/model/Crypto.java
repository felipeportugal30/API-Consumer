package com.example.consumer.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "cryptos")
public class Crypto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String symbol;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false, name = "market_cap")
    private Double marketCap;

    @Column(nullable = false, name = "percent_change_24h")
    private Double percentChange24h;

    @Column(nullable = false)
    private LocalDateTime timestamp;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getMarketCap() {
        return marketCap;
    }

    public void setMarketCap(Double marketCap) {
        this.marketCap = marketCap;
    }

    public Double getPercentChange24h() {
        return percentChange24h;
    }

    public void setPercentChange24h(Double percentChange24h) {
        this.percentChange24h = percentChange24h;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

}
