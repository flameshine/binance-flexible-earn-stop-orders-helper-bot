package com.flameshine.crypto.helper.binance.model;

import java.math.BigDecimal;

public record RedeemFlexibleProductRequest(
    KeyPair keyPair,
    String asset,
    BigDecimal amount
) {}