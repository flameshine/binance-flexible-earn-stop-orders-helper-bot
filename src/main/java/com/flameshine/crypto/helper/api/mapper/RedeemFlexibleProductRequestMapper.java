package com.flameshine.crypto.helper.api.mapper;

import lombok.experimental.UtilityClass;

import com.flameshine.crypto.helper.binance.model.RedeemFlexibleProductRequest;
import com.flameshine.crypto.helper.bot.entity.Order;

@UtilityClass
public class RedeemFlexibleProductRequestMapper {

    public static RedeemFlexibleProductRequest map(Order order) {
        var amount = order.getAmount().multiply(order.getPrice());
        return new RedeemFlexibleProductRequest(order.getQuote(), amount);
    }
}