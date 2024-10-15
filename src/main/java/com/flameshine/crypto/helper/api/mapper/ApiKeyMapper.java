package com.flameshine.crypto.helper.api.mapper;

import lombok.experimental.UtilityClass;

import com.flameshine.crypto.helper.api.entity.Account;
import com.flameshine.crypto.helper.binance.model.KeyPair;

@UtilityClass
class ApiKeyMapper {

    static KeyPair map(Account input) {
        return new KeyPair(input.getApiKey(), input.getSecretKey());
    }
}