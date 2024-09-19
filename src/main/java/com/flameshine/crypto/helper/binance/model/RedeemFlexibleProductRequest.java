package com.flameshine.crypto.helper.binance.model;

import java.math.BigDecimal;

public record RedeemFlexibleProductRequest(
    String productId,
    BigDecimal amount
) {}