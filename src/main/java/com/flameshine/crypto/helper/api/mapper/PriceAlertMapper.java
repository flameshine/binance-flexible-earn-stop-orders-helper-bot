package com.flameshine.crypto.helper.api.mapper;

import lombok.experimental.UtilityClass;

import com.flameshine.crypto.helper.api.entity.Order;
import com.flameshine.crypto.helper.binance.model.PriceAlert;

@UtilityClass
public class PriceAlertMapper {

    public static PriceAlert map(Order order) {

        var type = Order.Type.BUY == order.getType()
            ? PriceAlert.Type.BUY
            : PriceAlert.Type.SELL;

        return new PriceAlert(
            order.id,
            order.getBase() + order.getQuote(),
            type,
            order.getPrice()
        );
    }
}