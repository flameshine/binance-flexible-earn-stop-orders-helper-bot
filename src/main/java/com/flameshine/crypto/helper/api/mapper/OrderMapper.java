package com.flameshine.crypto.helper.api.mapper;

import lombok.experimental.UtilityClass;

import com.flameshine.crypto.helper.binance.model.Order;

@UtilityClass
public class OrderMapper {

    public static Order map(com.flameshine.crypto.helper.bot.entity.Order order) {
        return new Order(
            order.getKey().getTelegramUserId(),
            order.getBase(),
            order.getQuote(),
            order.getTarget()
        );
    }
}