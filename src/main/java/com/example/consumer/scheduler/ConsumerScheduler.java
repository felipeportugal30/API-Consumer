package com.example.consumer.scheduler;

import com.example.consumer.service.CryptoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ConsumerScheduler {

    private final CryptoService cryptoService;

    public ConsumerScheduler(CryptoService cryptoService) {
        this.cryptoService = cryptoService;
    }

    private static final Logger logger = LoggerFactory.getLogger(ConsumerScheduler.class);

    @Scheduled(fixedRateString = "${scheduler.crypto.fixedRate}")
    public void fetchConsumerDataPeriodically() {
        try {
            logger.info("[SCHEDULER] Calling Coin Market Cap API");
            cryptoService.fetchAndSaveCryptoData();
        } catch (Exception e) {
            System.err.println("Error at consume the api: " + e.getMessage());
        }
    }
}
