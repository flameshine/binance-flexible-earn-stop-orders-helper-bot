package com.flameshine.crypto.helper.binance.model;

import java.math.BigDecimal;

public record RedeemFlexibleProductRequest(
    ApiKey apiKey,
    String asset,
    BigDecimal amount
) {}