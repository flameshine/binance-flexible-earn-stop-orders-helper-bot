package com.flameshine.crypto.helper.api.mapper;

import org.junit.jupiter.api.Test;

import com.flameshine.crypto.helper.api.entity.Account;
import com.flameshine.crypto.helper.binance.model.CredentialPair;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CredentialPairMapperTest {

    @Test
    void map() {

        var input = Account.builder()
            .apiKey(TestFixtures.API_KEY)
            .secretKey(TestFixtures.SECRET_KEY)
            .build();

        var expected = new CredentialPair(
            TestFixtures.API_KEY,
            TestFixtures.SECRET_KEY
        );

        var actual = CredentialPairMapper.map(input);

        assertEquals(expected, actual);
    }
}