package com.flameshine.crypto.helper.binance.model;

public record TradingPair(
    String base,
    String quote
) {
    public String toLowerCase() {
        return base.toLowerCase() + quote.toLowerCase();
    }

    public String toUpperCase() {
        return base.toUpperCase() + quote.toUpperCase();
    }
}