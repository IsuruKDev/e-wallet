package com.nagel.kuehne.ewallet.service;

import com.nagel.kuehne.ewallet.model.Wallet;
import com.nagel.kuehne.ewallet.repository.WalletRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class WalletServiceTest {

    @Mock
    WalletRepository walletRepository;

    WalletService walletService;

    @BeforeEach
    void setUp() {
        walletService = new WalletService(walletRepository);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void saveWalletTest() {

        Wallet wallet = Wallet
                .builder()
                .name("testWallet")
                .balance(2500.00)
                .build();

        //walletService.saveWallet(wallet);

        ArgumentCaptor<Wallet> walletArgumentCaptor = ArgumentCaptor.forClass(Wallet.class);
        Mockito.verify(walletRepository).save(walletArgumentCaptor.capture());

        Wallet captureWallet = walletArgumentCaptor.getValue();
        Assertions.assertThat(captureWallet).isEqualTo(wallet);


    }

    @Test @Disabled
    void findWalletById() {
    }

    @Test @Disabled
    void getAllWalletsByUser() {
    }

    @Test @Disabled
    void deposite() {
    }

    @Test @Disabled
    void withdraw() {
    }

    @Test @Disabled
    void walletToWalletTransaction() {
    }
}