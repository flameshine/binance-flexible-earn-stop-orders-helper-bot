package com.flameshine.crypto.helper.binance.enums;

import java.util.Arrays;
import java.util.Optional;

import lombok.Getter;

/**
 * Binance API doesn't allow to list stable coins placed in Flexible Earn for some reason.
 * Therefore, we need to use hard-coded values to be able to use defined product IDs.
 */

@Getter
public enum Stablecoin {

    USDT("USDT001"),
    USDC("USDC001"),
    FDUSD("FDUSD001");

    private final String productId;

    Stablecoin(String productId) {
        this.productId = productId;
    }

    public static Optional<Stablecoin> fromValueOptional(String value) {
        return Arrays.stream(Stablecoin.values())
            .filter(stablecoin -> stablecoin.name().equalsIgnoreCase(value))
            .findFirst();
    }
}