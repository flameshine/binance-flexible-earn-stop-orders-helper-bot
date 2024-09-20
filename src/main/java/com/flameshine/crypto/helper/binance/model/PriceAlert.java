package com.flameshine.crypto.helper.binance.model;

import java.math.BigDecimal;

public record PriceAlert(
    Long id,
    String pair,
    Type type,
    BigDecimal target
) {

    public enum Type {
        BUY,
        SELL
    }
}