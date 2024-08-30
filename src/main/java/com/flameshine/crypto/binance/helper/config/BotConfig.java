package com.flameshine.crypto.binance.helper.config;

import io.smallrye.config.ConfigMapping;

@ConfigMapping(prefix = "bot")
public interface BotConfig {
    String token();
    String username();
}