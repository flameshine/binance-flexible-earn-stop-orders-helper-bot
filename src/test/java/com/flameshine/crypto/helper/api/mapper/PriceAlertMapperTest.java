package com.flameshine.crypto.helper.api.mapper;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import com.flameshine.crypto.helper.api.entity.Order;
import com.flameshine.crypto.helper.api.enums.OrderType;
import com.flameshine.crypto.helper.binance.model.PriceAlert;
import com.flameshine.crypto.helper.binance.model.TradingPair;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PriceAlertMapperTest {

    @Test
    void map() {

        var input = Order.builder()
            .base(TestFixtures.BASE)
            .quote(TestFixtures.QUOTE)
            .type(OrderType.BUY)
            .price(BigDecimal.TEN)
            .build();

        var expected = new PriceAlert(
            null,
            new TradingPair(TestFixtures.BASE, TestFixtures.QUOTE),
            OrderType.BUY,
            BigDecimal.TEN
        );

        var actual = PriceAlertMapper.map(input);

        assertEquals(expected, actual);
    }
}