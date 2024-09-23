package com.flameshine.crypto.helper.binance.spot;

import com.flameshine.crypto.helper.binance.model.OrderCreationRequest;

public interface OrderCreator {
    void create(OrderCreationRequest request);
}