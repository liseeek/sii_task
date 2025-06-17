package com.example.siitaskintern.dto;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
public class FinancialReportDto {
    private String eventName;
    private BigDecimal amount;
    private String currency;

    public FinancialReportDto(String eventName, BigDecimal amount, String currency) {
        this.eventName = eventName;
        this.amount = amount;
        this.currency = currency;
    }
}
