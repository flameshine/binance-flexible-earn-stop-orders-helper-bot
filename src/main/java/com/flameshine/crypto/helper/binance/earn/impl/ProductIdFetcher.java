package com.flameshine.crypto.helper.binance.earn.impl;

import java.util.HashMap;
import java.util.Optional;

import com.binance.connector.client.exceptions.BinanceClientException;
import com.binance.connector.client.impl.spot.SimpleEarn;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.flameshine.crypto.helper.binance.exception.BinanceApiException;
import com.flameshine.crypto.helper.binance.model.api.FlexibleEarnPositionListResponse;

public class ProductIdFetcher {

    private final SimpleEarn simpleEarn;
    private final ObjectMapper mapper;

    public ProductIdFetcher(SimpleEarn simpleEarn, ObjectMapper mapper) {
        this.simpleEarn = simpleEarn;
        this.mapper = mapper;
    }

    public Optional<String> fetch(String asset) throws BinanceApiException {

        String flexibleEarnPositionListResponseRaw;

        try {
            flexibleEarnPositionListResponseRaw = simpleEarn.flexibleProductPosition(new HashMap<>());
        } catch (BinanceClientException e) {
            throw new BinanceApiException("Unable to list Flexible Earn positions", e);
        }

        FlexibleEarnPositionListResponse response;

        try {
            response = mapper.readValue(flexibleEarnPositionListResponseRaw, FlexibleEarnPositionListResponse.class);
        } catch (JsonProcessingException e) {
            throw new BinanceApiException("Unable to parse Flexible Earn position list response", e);
        }

        return response.rows().stream()
            .filter(row -> asset.equals(row.asset()))
            .map(FlexibleEarnPositionListResponse.Row::productId)
            .findFirst();
    }
}