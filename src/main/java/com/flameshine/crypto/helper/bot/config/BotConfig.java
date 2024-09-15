package com.flameshine.crypto.helper.bot.config;

import io.smallrye.config.ConfigMapping;

@ConfigMapping(prefix = "bot")
public interface BotConfig {
    String token();
    String username();
}