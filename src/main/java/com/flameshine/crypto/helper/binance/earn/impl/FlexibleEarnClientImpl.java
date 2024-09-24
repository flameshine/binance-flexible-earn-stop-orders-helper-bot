package com.flameshine.crypto.helper.binance.earn.impl;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import com.binance.connector.client.exceptions.BinanceClientException;
import com.binance.connector.client.impl.SpotClientImpl;
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
            request.apiKey().apiKey(),
            request.apiKey().secretKey(),
            url
        );

        var simpleEarn = client.createSimpleEarn();

        var productId = Stablecoin.fromValueOptional(request.asset())
            .map(Stablecoin::getProductId);

        if (productId.isEmpty()) {

            var productIdFetcher = new ProductIdFetcher(simpleEarn, mapper);

            try {
                productId = productIdFetcher.fetch(request.asset());
            } catch (BinanceApiException e) {
                log.error(e.getMessage(), e);
                return Optional.of(Problem.BINANCE_API_PROBLEM);
            }
        }

        if (productId.isEmpty()) {
            return Optional.of(Problem.INSUFFICIENT_FUNDS);
        }

        Map<String, Object> parameters = new LinkedHashMap<>();

        parameters.put("productId", productId.get());
        parameters.put("amount", request.amount());
        parameters.put("type", "FAST");

        try {
            simpleEarn.redeemFlexibleProduct(parameters);
        } catch (BinanceClientException e) {
            log.error(e.getMessage(), e);
            return Optional.of(Problem.INSUFFICIENT_FUNDS);
        }

        return Optional.empty();
    }
}