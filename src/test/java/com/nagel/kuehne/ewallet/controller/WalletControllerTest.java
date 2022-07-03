package com.nagel.kuehne.ewallet.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nagel.kuehne.ewallet.model.User;
import com.nagel.kuehne.ewallet.model.Wallet;
import com.nagel.kuehne.ewallet.payload.WalletRequest;
import com.nagel.kuehne.ewallet.repository.UserRepository;
import com.nagel.kuehne.ewallet.repository.WalletRepository;
import com.nagel.kuehne.ewallet.security.jwt.JwtUtils;
import com.nagel.kuehne.ewallet.service.WalletService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "testuser1", password = "Stl@123456",roles = {"USER","ADMIN"})
class WalletControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    WalletService walletService;

    @MockBean
    UserRepository userRepository;

    private User authUser;
    @BeforeEach
    void setUp() {


    }

    @AfterEach
    void tearDown() {
    }

    @Test @Disabled
    void createAWalletTest() throws Exception {

        WalletRequest walletRequest = WalletRequest
                .builder()
                .name("wallet_1")
                .amount(new BigDecimal(2500).setScale(2))
                .build();

        Wallet wallet = Wallet.builder()
                .balance(2500)
                .name("wallet_1")
                .build();

     //private static method can not be mocked
        //   Mockito.mockStatic()

        Mockito.when(walletService.saveWallet(Mockito.mock(Wallet.class))).thenReturn(wallet);

       // WalletService walletService = Mockito.mock(WalletService.class);
        //walletService.saveWallet(new Wallet());
        //Mockito.verify(walletService).saveWallet(Wallet.builder().build());

        mockMvc.perform(
                        post("/api/wallet/create")
                                .content(asJsonString(walletRequest))
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Wallet has been created successfully...")));
    }

    @Test
    void findAllWalletsTest() throws Exception {

       /* List<Wallet> wallets = new ArrayList<>();


        Mockito.when(userRepository.findById(1l)).thenReturn(Mockito.any( Optional.of(User.class)));
        Mockito.when(walletService.getAllWalletsByUser(Mockito.any(User.class))).thenReturn(wallets);

        mockMvc.perform(
                get("/api/wallet/all/1l"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
*/

    }

    @Test @Disabled
    void deposit() {
    }

    @Test @Disabled
    void withdraw() {
    }

    @Test @Disabled
    void walletToWalletTransaction() {
    }

    public static String asJsonString(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}