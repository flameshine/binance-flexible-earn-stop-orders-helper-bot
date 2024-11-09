package com.flameshine.crypto.helper.api.mapper;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import com.flameshine.crypto.helper.api.entity.Account;
import com.flameshine.crypto.helper.api.entity.Order;
import com.flameshine.crypto.helper.binance.model.CredentialPair;
import com.flameshine.crypto.helper.binance.model.RedeemFlexibleProductRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RedeemFlexibleProductRequestMapperTest {

    @Test
    void map() {

        var account = Account.builder()
            .apiKey(TestFixtures.API_KEY)
            .secretKey(TestFixtures.SECRET_KEY)
            .build();

        var input = Order.builder()
            .base(TestFixtures.BASE)
            .quantity(BigDecimal.ONE)
            .price(BigDecimal.TEN)
            .account(account)
            .build();

        var expectedCredentialPair = new CredentialPair(
            TestFixtures.API_KEY,
            TestFixtures.SECRET_KEY
        );

        var expected = new RedeemFlexibleProductRequest(
            expectedCredentialPair,
            TestFixtures.BASE,
            BigDecimal.TEN
        );

        var actual = RedeemFlexibleProductRequestMapper.map(input);

        assertEquals(expected, actual);
    }
}