package com.flameshine.crypto.helper.api.mapper;

import lombok.experimental.UtilityClass;

import com.flameshine.crypto.helper.api.entity.Order;
import com.flameshine.crypto.helper.binance.model.OrderCreationRequest;

@UtilityClass
public class OrderCreationRequestMapper {

    public static OrderCreationRequest map(Order input) {
        var credentialPair = CredentialPairMapper.map(input.getAccount());
        var order = OrderMapper.map(input);
        return new OrderCreationRequest(credentialPair, order);
    }
}