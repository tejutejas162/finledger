package com.finledger.finledger.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class AccountRequest {

    private String customerName;
    private BigDecimal initialBalance;
}
