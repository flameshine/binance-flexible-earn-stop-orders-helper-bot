package com.flameshine.crypto.helper.api.mapper;

import lombok.experimental.UtilityClass;

import com.flameshine.crypto.helper.binance.model.OrderCreationRequest;
import com.flameshine.crypto.helper.binance.model.TradingPair;

@UtilityClass
class OrderMapper {

    static OrderCreationRequest.Order map(com.flameshine.crypto.helper.api.entity.Order input) {
        var pair = new TradingPair(input.getBase(), input.getQuote());
        return new OrderCreationRequest.Order(
            pair,
            input.getType(),
            input.getQuantity(),
            input.getPrice()
        );
    }
}