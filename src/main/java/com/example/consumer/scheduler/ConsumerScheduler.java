package com.example.consumer.scheduler;

import com.example.consumer.service.ConsumerService;
import com.example.consumer.service.CryptoService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ConsumerScheduler {

    private final ConsumerService consumerService;
    private final CryptoService cryptoService;

    public ConsumerScheduler(ConsumerService consumerService, CryptoService cryptoService) {
        this.consumerService = consumerService;
        this.cryptoService = cryptoService;
    }

    @Scheduled(fixedRate = 300000)
    public void fetchConsumerDataPeriodically() {
        try {
            System.out.println(">> [SCHEDULER] Calling getCryptoCoins");
            cryptoService.fetchAndSaveCryptoData();
        } catch (Exception e) {
            System.err.println("Error at consume the api: " + e.getMessage());
        }
    }
}
