package com.flameshine.crypto.helper.api.mapper;

import lombok.experimental.UtilityClass;

import com.flameshine.crypto.helper.api.entity.Account;
import com.flameshine.crypto.helper.binance.model.ApiKey;

@UtilityClass
class ApiKeyMapper {

    static ApiKey map(Account input) {
        return new ApiKey(input.getApiKey(), input.getSecretKey());
    }
}