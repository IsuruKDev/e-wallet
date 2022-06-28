package com.nagel.kuehne.ewallet.payload;

import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;

@Data
public class DepositeRequest {

    private long walletId;
    private double depositeAmount;
}
