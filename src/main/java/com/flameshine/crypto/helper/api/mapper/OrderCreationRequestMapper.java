package com.flameshine.crypto.helper.api.mapper;

import lombok.experimental.UtilityClass;

import com.flameshine.crypto.helper.binance.model.OrderCreationRequest;
import com.flameshine.crypto.helper.binance.model.TradingPair;

@UtilityClass
public class OrderCreationRequestMapper {

    public static OrderCreationRequest map(com.flameshine.crypto.helper.api.entity.Order input) {
        var apiKey = ApiKeyMapper.map(input.getAccount());
        var order = OrderMapper.map(input);
        return new OrderCreationRequest(apiKey, order);
    }
}