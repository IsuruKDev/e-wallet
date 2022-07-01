package com.nagel.kuehne.ewallet.repository;

import com.nagel.kuehne.ewallet.model.Wallet;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class WalletRepositoryTest {

    @Autowired
    WalletRepository walletRepository;

    @BeforeEach
    void setUp() {
        walletRepository.deleteAll();
    }

    @AfterEach
    void tearDown() {
        walletRepository.deleteAll();
    }

    @Test
    void findByWalletIdTest() {

        Wallet wallet = Wallet.builder()
                .name("testWallet")
                .balance(2500.00)
                .build();

        walletRepository.save(wallet);

        Wallet savedInstance = walletRepository.findByWalletId(1).get();

        Assertions.assertThat(savedInstance.getWalletId()).isEqualTo(1L);

    }


}