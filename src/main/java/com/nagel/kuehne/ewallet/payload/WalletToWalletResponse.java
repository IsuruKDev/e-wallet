package com.nagel.kuehne.ewallet.payload;

import com.nagel.kuehne.ewallet.model.Wallet;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
public class WalletToWalletResponse {

    private Wallet creditWallet;
    private Wallet debitWallet;
    private String message;
}
