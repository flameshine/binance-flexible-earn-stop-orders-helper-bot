package com.flameshine.crypto.helper.api.mapper;

import lombok.experimental.UtilityClass;

import com.flameshine.crypto.helper.binance.model.RedeemFlexibleProductRequest;
import com.flameshine.crypto.helper.api.entity.Order;

@UtilityClass
public class RedeemFlexibleProductRequestMapper {

    public static RedeemFlexibleProductRequest map(Order order) {
        var account = AccountMapper.map(order.getAccount());
        var amount = order.getAmount().multiply(order.getPrice());
        return new RedeemFlexibleProductRequest(account, order.getQuote(), amount);
    }
}