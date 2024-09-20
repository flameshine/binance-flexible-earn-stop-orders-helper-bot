package com.flameshine.crypto.helper.api.handler.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import com.flameshine.crypto.helper.api.entity.Order;
import com.flameshine.crypto.helper.api.handler.OrderExecutionHandler;
import com.flameshine.crypto.helper.api.mapper.RedeemFlexibleProductRequestMapper;
import com.flameshine.crypto.helper.api.model.OrderExecutionResponse;
import com.flameshine.crypto.helper.binance.earn.FlexibleEarnClient;

@ApplicationScoped
public class OrderExecutionHandlerImpl implements OrderExecutionHandler {

    private final FlexibleEarnClient flexibleEarnClient;

    @Inject
    public OrderExecutionHandlerImpl(FlexibleEarnClient flexibleEarnClient) {
        this.flexibleEarnClient = flexibleEarnClient;
    }

    @Override
    @Transactional
    public OrderExecutionResponse handle(Long orderId) {

        // TODO: fix runtime exception thrown here

        var order = Order.findByIdOptional(orderId)
            .orElseThrow(() -> new RuntimeException("Order must be present at this stage"));

        order.delete();

        var problem = flexibleEarnClient.redeem(
            RedeemFlexibleProductRequestMapper.map(order)
        );

        // TODO: place stop-order here

        return problem.map(value -> new OrderExecutionResponse(order, value))
            .orElseGet(() -> new OrderExecutionResponse(order, null));
    }
}