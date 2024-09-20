package com.flameshine.crypto.helper.api.handler;

import com.flameshine.crypto.helper.api.model.OrderExecutionResponse;

public interface OrderExecutionHandler {
    OrderExecutionResponse handle(Long orderId);
}