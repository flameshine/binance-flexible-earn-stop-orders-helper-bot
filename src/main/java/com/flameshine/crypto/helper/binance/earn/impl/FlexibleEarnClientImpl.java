package com.flameshine.crypto.helper.binance.earn.impl;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import com.binance.connector.client.exceptions.BinanceClientException;
import com.binance.connector.client.impl.SpotClientImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

import com.flameshine.crypto.helper.binance.config.ApiConfig;
import com.flameshine.crypto.helper.binance.earn.FlexibleEarnClient;
import com.flameshine.crypto.helper.api.model.Problem;
import com.flameshine.crypto.helper.binance.model.RedeemFlexibleProductRequest;
import com.flameshine.crypto.helper.binance.model.api.FlexibleEarnPositionListResponse;

// TODO: review this
// TODO: check if user has enough assets to redeem

@ApplicationScoped
@Slf4j
public class FlexibleEarnClientImpl implements FlexibleEarnClient {

    private final String url;
    private final ObjectMapper objectMapper;

    @Inject
    public FlexibleEarnClientImpl(ApiConfig config) {
        this.url = config.flexibleEarnUrl();
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public Optional<Problem> redeem(RedeemFlexibleProductRequest request) {

        var client = new SpotClientImpl(
            request.account().apiKey(),
            request.account().secretKey(),
            url
        );

        var simpleEarn = client.createSimpleEarn();

        String flexibleEarnPositionListResponseRaw;

        try {
            flexibleEarnPositionListResponseRaw = simpleEarn.flexibleProductPosition(new HashMap<>());
        } catch (BinanceClientException e) {
            log.error(e.getMessage(), e);
            return Optional.of(Problem.BINANCE_API_PROBLEM);
        }

        FlexibleEarnPositionListResponse response;

        try {
            response = objectMapper.readValue(flexibleEarnPositionListResponseRaw, FlexibleEarnPositionListResponse.class);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
            return Optional.of(Problem.BINANCE_API_PROBLEM);
        }

        log.info(response.toString());

        var productId = response.rows().stream()
            .filter(row -> request.asset().equals(row.asset()))
            .map(FlexibleEarnPositionListResponse.Row::productId)
            .findFirst();

        if (productId.isEmpty()) {
            return Optional.of(Problem.INVALID_PRODUCT_ID);
        }

        Map<String, Object> parameters = new LinkedHashMap<>();

        parameters.put("productId", productId.get());
        parameters.put("amount", request.amount());
        parameters.put("type", "FAST");

        try {
            var result = simpleEarn.redeemFlexibleProduct(parameters);
            log.info(result);
        } catch (BinanceClientException e) {
            return Optional.of(Problem.BINANCE_API_PROBLEM);
        }

        return Optional.empty();
    }
}