package com.flameshine.crypto.helper.api.mapper;

import lombok.experimental.UtilityClass;

import com.flameshine.crypto.helper.api.entity.Order;
import com.flameshine.crypto.helper.binance.model.RedeemFlexibleProductRequest;

@UtilityClass
public class RedeemFlexibleProductRequestMapper {

    public static RedeemFlexibleProductRequest map(Order input) {
        var credentialPair = CredentialPairMapper.map(input.getAccount());
        var amount = input.getQuantity().multiply(input.getPrice());
        return new RedeemFlexibleProductRequest(credentialPair, input.getBase(), amount);
    }
}