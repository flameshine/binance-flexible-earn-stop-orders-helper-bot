package com.flameshine.crypto.helper.binance.spot;

import java.util.Optional;

import com.flameshine.crypto.helper.api.enums.Problem;
import com.flameshine.crypto.helper.binance.model.OrderCreationRequest;

public interface OrderCreator {
    Optional<Problem> create(OrderCreationRequest request);
}