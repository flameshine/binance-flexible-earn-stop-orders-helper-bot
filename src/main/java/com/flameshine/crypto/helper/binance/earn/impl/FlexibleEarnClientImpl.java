package com.flameshine.crypto.helper.binance.earn.impl;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import com.binance.connector.client.exceptions.BinanceClientException;
import com.binance.connector.client.impl.SpotClientImpl;
import com.binance.connector.client.impl.spot.SimpleEarn;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

import com.flameshine.crypto.helper.api.enums.Problem;
import com.flameshine.crypto.helper.binance.config.ApiConfig;
import com.flameshine.crypto.helper.binance.earn.FlexibleEarnClient;
import com.flameshine.crypto.helper.binance.enums.Stablecoin;
import com.flameshine.crypto.helper.binance.exception.BinanceApiException;
import com.flameshine.crypto.helper.binance.model.RedeemFlexibleProductRequest;

@ApplicationScoped
@Slf4j
public class FlexibleEarnClientImpl implements FlexibleEarnClient {

    private final String url;
    private final ObjectMapper mapper;

    @Inject
    public FlexibleEarnClientImpl(ApiConfig config) {
        this.url = config.flexibleEarnUrl();
        this.mapper = new ObjectMapper();
    }

    @Override
    public Optional<Problem> redeem(RedeemFlexibleProductRequest request) {

        var client = new SpotClientImpl(
            request.credentialPair().apiKey(),
            request.credentialPair().secretKey(),
            url
        );

        var simpleEarn = client.createSimpleEarn();
        var productId = getProductId(request.asset(), simpleEarn);

        if (productId.isEmpty()) {
            return Optional.of(Problem.INSUFFICIENT_FUNDS);
        }

        var parameters = buildRedemptionParameters(productId.get(), request.amount());

        try {
            simpleEarn.redeemFlexibleProduct(parameters);
        } catch (BinanceClientException e) {
            log.error(e.getMessage(), e);
            return Optional.of(Problem.INSUFFICIENT_FUNDS);
        }

        return Optional.empty();
    }

    private Optional<String> getProductId(String asset, SimpleEarn simpleEarn) {

        var productId = Stablecoin.fromValueOptional(asset)
            .map(Stablecoin::getProductId);

        if (productId.isEmpty()) {

            var productIdFetcher = new ProductIdFetcher(simpleEarn, mapper);

            try {
                return productIdFetcher.fetch(asset);
            } catch (BinanceApiException e) {
                log.error(e.getMessage(), e);
                return Optional.empty();
            }
        }

        return productId;
    }

    private static Map<String, Object> buildRedemptionParameters(String productId, BigDecimal amount) {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("productId", productId);
        result.put("amount", amount);
        result.put("type", "FAST");
        return Collections.unmodifiableMap(result);
    }
}