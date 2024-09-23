package com.flameshine.crypto.helper.binance.model;

import java.math.BigDecimal;

import com.flameshine.crypto.helper.api.enums.OrderType;

public record PriceAlert(
    Long id,
    TradingPair pair,
    OrderType type,
    BigDecimal target
) {}