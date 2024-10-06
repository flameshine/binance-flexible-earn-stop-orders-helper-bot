package com.flameshine.crypto.helper.binance.config;

import io.smallrye.config.ConfigMapping;

@ConfigMapping(prefix = "binance")
public interface ApiConfig {
    String streamUrl();
    String flexibleEarnUrl();
}