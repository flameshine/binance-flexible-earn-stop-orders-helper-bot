package com.flameshine.crypto.helper.api.mapper;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import com.flameshine.crypto.helper.api.entity.Order;
import com.flameshine.crypto.helper.api.enums.OrderType;
import com.flameshine.crypto.helper.binance.model.OrderCreationRequest;
import com.flameshine.crypto.helper.binance.model.TradingPair;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OrderMapperTest {

    @Test
    void map() {

        var input = Order.builder()
            .base(TestFixtures.BASE)
            .quote(TestFixtures.QUOTE)
            .type(OrderType.BUY)
            .price(BigDecimal.TEN)
            .quantity(BigDecimal.ONE)
            .build();

        var expected = new OrderCreationRequest.Order(
            new TradingPair(TestFixtures.BASE, TestFixtures.QUOTE),
            OrderType.BUY,
            BigDecimal.ONE,
            BigDecimal.TEN
        );

        var actual = OrderMapper.map(input);

        assertEquals(expected, actual);
    }
}