package com.flameshine.crypto.helper.api.mapper;

import lombok.experimental.UtilityClass;

import com.flameshine.crypto.helper.api.entity.Order;
import com.flameshine.crypto.helper.binance.model.PriceAlert;
import com.flameshine.crypto.helper.binance.model.TradingPair;

@UtilityClass
public class PriceAlertMapper {

    public static PriceAlert map(Order input) {
        var pair = new TradingPair(input.getBase(), input.getQuote());
        return new PriceAlert(
            input.id,
            pair,
            input.getType(),
            input.getPrice()
        );
    }
}