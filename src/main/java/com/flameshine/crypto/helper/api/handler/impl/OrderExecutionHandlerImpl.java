package com.flameshine.crypto.helper.api.handler.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import com.flameshine.crypto.helper.api.entity.Order;
import com.flameshine.crypto.helper.api.handler.OrderExecutionHandler;
import com.flameshine.crypto.helper.api.mapper.OrderCreationRequestMapper;
import com.flameshine.crypto.helper.api.mapper.RedeemFlexibleProductRequestMapper;
import com.flameshine.crypto.helper.api.model.OrderExecutionResponse;
import com.flameshine.crypto.helper.binance.earn.FlexibleEarnClient;
import com.flameshine.crypto.helper.binance.spot.OrderCreator;

@ApplicationScoped
@RequiredArgsConstructor
public class OrderExecutionHandlerImpl implements OrderExecutionHandler {

    private final FlexibleEarnClient flexibleEarnClient;
    private final OrderCreator orderCreator;

    @Override
    @Transactional
    public OrderExecutionResponse handle(Long orderId) {

        var order = Order.findByIdOptional(orderId)
            .orElseThrow(() -> new RuntimeException("Order must be present at this stage"));

        order.delete();

        var redeemProblem = flexibleEarnClient.redeem(
            RedeemFlexibleProductRequestMapper.map(order)
        );

        if (redeemProblem.isPresent()) {
            return new OrderExecutionResponse(order, redeemProblem.get());
        }

        var orderProblem = orderCreator.create(
            OrderCreationRequestMapper.map(order)
        );

        return orderProblem.map(problem -> new OrderExecutionResponse(order, problem))
            .orElseGet(() -> new OrderExecutionResponse(order, null));
    }
}