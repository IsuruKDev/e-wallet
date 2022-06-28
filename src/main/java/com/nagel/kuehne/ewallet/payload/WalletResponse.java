package com.nagel.kuehne.ewallet.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data @AllArgsConstructor
public class WalletResponse {

    private long walletId;
    private double balance;
    private String message;
}
