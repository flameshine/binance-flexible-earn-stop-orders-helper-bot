package com.flameshine.crypto.helper.api.mapper;

import lombok.experimental.UtilityClass;

import com.flameshine.crypto.helper.binance.model.Order;

@UtilityClass
public class OrderMapper {

    public static Order map(com.flameshine.crypto.helper.bot.entity.Order order) {

        var pair = com.flameshine.crypto.helper.bot.entity.Order.Type.BUY == order.getType()
            ? order.getBase() + order.getQuote()
            : order.getQuote() + order.getBase();

        return new Order(
            order.id,
            pair,
            order.getTarget()
        );
    }
}