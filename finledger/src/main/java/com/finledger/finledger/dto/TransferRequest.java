package com.finledger.finledger.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class TransferRequest {

    private UUID fromAccountId;
    private UUID toAccountId;
    private BigDecimal amount;
}