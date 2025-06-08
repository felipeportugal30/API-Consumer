package com.example.consumer.service;

import com.example.consumer.dto.CryptoDataDto;
import com.example.consumer.exception.ExternalApiException;
import com.example.consumer.exception.ResourceNotFoundException;
import com.example.consumer.mapper.CryptoMapper;
import com.example.consumer.model.Crypto;
import com.example.consumer.repository.CryptoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CryptoServiceTest {

    @Mock
    private CryptoRepository cryptoRepository;

    @Mock
    private ConsumerService consumerService;

    @InjectMocks
    private CryptoService cryptoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveCrypto_shouldCallRepositoryOnce() {
        CryptoDataDto dto = new CryptoDataDto();
        dto.setName("Bitcoin");
        dto.setSymbol("BTC");
        dto.setPrice(new BigDecimal("10000.00"));
        dto.setMarketCap(new BigDecimal("100000000.00"));
        dto.setPercentChange24h(new BigDecimal("1.20"));

        cryptoService.saveCrypto(List.of(dto));

        verify(cryptoRepository, times(1)).saveAll(anyList());
    }

    @Test
    void testFindById_success() {
        Crypto crypto = new Crypto();
        crypto.setId(1L);

        when(cryptoRepository.findById(1L)).thenReturn(Optional.of(crypto));

        Crypto found = cryptoService.findById(1L);
        assertEquals(1L, found.getId());
    }

    @Test
    void testFindById_notFound() {
        when(cryptoRepository.findById(99L)).thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () -> cryptoService.findById(99L));
        assertTrue(ex.getMessage().contains("doesn't exist"));
    }

    @Test
    void testFindAll_shouldCallRepository() {
        when(cryptoRepository.findAll()).thenReturn(Collections.emptyList());

        List<Crypto> all = cryptoService.findAll();

        assertNotNull(all);
        verify(cryptoRepository).findAll();
    }

    @Test
    void testFetchAndSaveCryptoData_shouldCallConsumerAndRepository() {
        String fakeJson = "{\"data\":[{\"name\":\"Bitcoin\",\"symbol\":\"BTC\",\"price\":10000.0,\"marketCap\":100000000.0,\"percentChange24h\":1.2}]}";
        List<CryptoDataDto> dtos = List.of(new CryptoDataDto());

        when(consumerService.getCryptoCoins()).thenReturn(fakeJson);

        try (MockedStatic<CryptoMapper> mocked = mockStatic(CryptoMapper.class)) {
            mocked.when(() -> CryptoMapper.fromJson(fakeJson)).thenReturn(dtos);

            cryptoService.fetchAndSaveCryptoData();

            verify(consumerService).getCryptoCoins();
            verify(cryptoRepository).saveAll(anyList());
        }
    }


    @Test
    void testFindByName_success() {
        String name = "Bitcoin";
        Crypto mockCrypto = new Crypto();
        mockCrypto.setName(name);

        when(cryptoRepository.findByName(name)).thenReturn(mockCrypto);

        Crypto found = cryptoService.findByName(name);
        assertEquals(name, found.getName());
    }

    @Test
    void testFindByName_error() {
        when(cryptoRepository.findByName("Invalid")).thenThrow(new RuntimeException("Not found"));

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () -> cryptoService.findByName("Invalid"));
        assertTrue(ex.getMessage().contains("doesn't exist"));
    }

    @Test
    void fetchAndSaveCryptoData_shouldThrowException_whenApiFails() {
        when(consumerService.getCryptoCoins()).thenThrow(new ExternalApiException("API error"));

        assertThrows(ExternalApiException.class, () -> cryptoService.fetchAndSaveCryptoData());
    }

    private void mockStaticMapper(String json, List<CryptoDataDto> dtos) {
        try (MockedStatic<CryptoMapper> mocked = mockStatic(CryptoMapper.class)) {
            mocked.when(() -> CryptoMapper.fromJson(json)).thenReturn(dtos);
        }
    }
}
