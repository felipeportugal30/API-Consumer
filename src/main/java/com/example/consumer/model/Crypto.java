package com.example.consumer.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
@ToString
@EqualsAndHashCode
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
    private BigDecimal price;

    @Column(nullable = false, name = "market_cap")
    private BigDecimal marketCap;

    @Column(nullable = false, name = "percent_change_24h")
    private BigDecimal percentChange24h;

    @Column(nullable = false)
    private LocalDateTime timestamp;


}
