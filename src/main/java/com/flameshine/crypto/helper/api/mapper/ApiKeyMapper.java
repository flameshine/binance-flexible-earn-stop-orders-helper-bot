package com.flameshine.crypto.helper.api.mapper;

import lombok.experimental.UtilityClass;

import com.flameshine.crypto.helper.api.entity.Account;
import com.flameshine.crypto.helper.binance.model.CredentialPair;

@UtilityClass
class ApiKeyMapper {

    static CredentialPair map(Account input) {
        return new CredentialPair(input.getApiKey(), input.getSecretKey());
    }
}