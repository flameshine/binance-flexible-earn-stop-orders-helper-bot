package com.flameshine.crypto.helper.api.mapper;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import com.flameshine.crypto.helper.api.entity.Account;
import com.flameshine.crypto.helper.api.entity.Order;
import com.flameshine.crypto.helper.api.enums.OrderType;
import com.flameshine.crypto.helper.binance.model.CredentialPair;
import com.flameshine.crypto.helper.binance.model.OrderCreationRequest;
import com.flameshine.crypto.helper.binance.model.TradingPair;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OrderCreationRequestMapperTest {

    @Test
    void map() {

        var account = Account.builder()
            .apiKey(TestFixtures.API_KEY)
            .secretKey(TestFixtures.SECRET_KEY)
            .build();

        var input = Order.builder()
            .base(TestFixtures.BASE)
            .quote(TestFixtures.QUOTE)
            .type(OrderType.BUY)
            .price(BigDecimal.TEN)
            .quantity(BigDecimal.ONE)
            .account(account)
            .build();

        var expectedCredentialPair = new CredentialPair(
            TestFixtures.API_KEY,
            TestFixtures.SECRET_KEY
        );

        var expectedOrder = new OrderCreationRequest.Order(
            new TradingPair(TestFixtures.BASE, TestFixtures.QUOTE),
            OrderType.BUY,
            BigDecimal.ONE,
            BigDecimal.TEN
        );

        var expected = new OrderCreationRequest(expectedCredentialPair, expectedOrder);
        var actual = OrderCreationRequestMapper.map(input);

        assertEquals(expected, actual);
    }
}