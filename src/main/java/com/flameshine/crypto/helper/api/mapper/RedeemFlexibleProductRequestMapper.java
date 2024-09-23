package com.flameshine.crypto.helper.api.mapper;

import lombok.experimental.UtilityClass;

import com.flameshine.crypto.helper.binance.model.RedeemFlexibleProductRequest;
import com.flameshine.crypto.helper.api.entity.Order;

@UtilityClass
public class RedeemFlexibleProductRequestMapper {

    public static RedeemFlexibleProductRequest map(Order input) {
        var account = ApiKeyMapper.map(input.getAccount());
        var amount = input.getQuantity().multiply(input.getPrice());
        return new RedeemFlexibleProductRequest(account, input.getQuote(), amount);
    }
}