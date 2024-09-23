package com.flameshine.crypto.helper.binance.model;

import java.math.BigDecimal;

import com.flameshine.crypto.helper.api.enums.OrderType;

public record OrderCreationRequest(
    ApiKey apiKey,
    Order order
) {
    public record Order(
        TradingPair pair,
        OrderType type,
        BigDecimal quantity,
        BigDecimal price
    ) {}
}