package com.example.consumer.repository;

import com.example.consumer.model.Crypto;
import org.springframework.data.jpa.repository.JpaRepository;

//With this interface you have some methods ready to use, like: save, findById, findAll and deleteById
public interface CryptoRepository extends JpaRepository<Crypto, Long> {
}
