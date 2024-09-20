package com.flameshine.crypto.helper.api.mapper;

import lombok.experimental.UtilityClass;

import com.flameshine.crypto.helper.binance.model.Account;

@UtilityClass
class AccountMapper {

    static Account map(com.flameshine.crypto.helper.api.entity.Account account) {
        return new Account(account.getApiKey(), account.getSecretKey());
    }
}