package com.nagel.kuehne.ewallet.payload;

import lombok.Data;

@Data
public class WithdrawRequest {

    private long walletId;
    private double withdrawAmount;
}
