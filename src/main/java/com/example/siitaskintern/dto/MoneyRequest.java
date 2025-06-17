package com.example.siitaskintern.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class MoneyRequest {
    private BigDecimal amount;
    private String currency;
}
