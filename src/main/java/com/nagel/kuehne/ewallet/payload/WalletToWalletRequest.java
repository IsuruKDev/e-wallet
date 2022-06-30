package com.nagel.kuehne.ewallet.payload;

import com.nagel.kuehne.ewallet.model.Wallet;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class WalletToWalletRequest {

    private long creditWalletId;
    private long debitWalletId;
    private double transactionalAmount;
}
