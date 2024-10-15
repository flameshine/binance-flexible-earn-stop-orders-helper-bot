package com.flameshine.crypto.helper.binance.model;

import java.math.BigDecimal;

public record RedeemFlexibleProductRequest(
    CredentialPair credentialPair,
    String asset,
    BigDecimal amount
) {}