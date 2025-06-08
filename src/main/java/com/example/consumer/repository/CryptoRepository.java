package com.example.consumer.repository;

import com.example.consumer.model.Crypto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CryptoRepository extends JpaRepository<Crypto, Long> {
    Crypto findByName(String name);
}
