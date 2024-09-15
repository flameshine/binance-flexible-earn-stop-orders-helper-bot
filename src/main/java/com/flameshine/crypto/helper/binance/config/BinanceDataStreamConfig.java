package com.flameshine.crypto.helper.binance.config;

import io.smallrye.config.ConfigMapping;

@ConfigMapping(prefix = "binance.stream")
public interface BinanceDataStreamConfig {
    String baseUrl();
}