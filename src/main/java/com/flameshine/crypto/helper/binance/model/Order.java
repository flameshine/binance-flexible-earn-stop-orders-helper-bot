package com.flameshine.crypto.helper.binance.model;

import java.math.BigDecimal;

public record Order(
    Long id,
    String pair,
    BigDecimal target
) {}