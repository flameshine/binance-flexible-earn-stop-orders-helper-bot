package com.flameshine.crypto.helper.binance.model;

import java.math.BigDecimal;

public record RedeemFlexibleProductRequest(
    Account account,
    String asset,
    BigDecimal amount
) {}