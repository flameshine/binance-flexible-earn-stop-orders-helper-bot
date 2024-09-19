package com.flameshine.crypto.helper.api.mapper;

import lombok.experimental.UtilityClass;

import com.flameshine.crypto.helper.binance.model.PriceAlert;

@UtilityClass
public class PriceAlertMapper {

    public static PriceAlert map(com.flameshine.crypto.helper.bot.entity.Order order) {
        return new PriceAlert(
            order.id,
            order.getBase() + order.getQuote(),
            order.getPrice()
        );
    }
}