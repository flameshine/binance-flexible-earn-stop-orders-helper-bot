package com.flameshine.crypto.helper.binance.model;

import java.math.BigDecimal;

public record Order(
    Long id,
    Long userId,
    String base,
    String quote,
    BigDecimal target
) {}