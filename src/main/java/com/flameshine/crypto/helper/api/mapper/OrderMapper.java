package com.flameshine.crypto.helper.api.mapper;

import lombok.experimental.UtilityClass;

import com.flameshine.crypto.helper.binance.model.Order;

@UtilityClass
public class OrderMapper {

    public static Order map(com.flameshine.crypto.helper.bot.entity.Order order) {
        return new Order(
            order.id,
            order.getBase() + order.getQuote(),
            order.getTarget()
        );
    }
}