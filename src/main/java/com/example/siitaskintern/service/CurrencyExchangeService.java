package com.example.siitaskintern.service;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
public class CurrencyExchangeService {

    private final Map<String, BigDecimal> rates = new HashMap<>();

    public CurrencyExchangeService() {
        rates.put("EUR_USD", new BigDecimal("1.15"));
        rates.put("PLN_EUR", new BigDecimal("0.23"));
        rates.put("USD_PLN", new BigDecimal("3.72"));
    }

    public BigDecimal convertCurrency(BigDecimal amount, String from, String to) {
        if (from.equals(to)) return amount;

        String key = from + "_" + to;
        BigDecimal rate = rates.get(key);

        return rate != null ? amount.multiply(rate) : amount;
    }
}
