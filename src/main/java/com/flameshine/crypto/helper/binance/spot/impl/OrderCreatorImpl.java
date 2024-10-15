package com.flameshine.crypto.helper.binance.spot.impl;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import com.binance.connector.client.exceptions.BinanceClientException;
import com.binance.connector.client.impl.SpotClientImpl;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

import com.flameshine.crypto.helper.api.enums.Problem;
import com.flameshine.crypto.helper.binance.config.ApiConfig;
import com.flameshine.crypto.helper.binance.model.OrderCreationRequest;
import com.flameshine.crypto.helper.binance.spot.OrderCreator;

@ApplicationScoped
@Slf4j
public class OrderCreatorImpl implements OrderCreator {

    private final String url;

    @Inject
    public OrderCreatorImpl(ApiConfig config) {
        this.url = config.flexibleEarnUrl();
    }

    @Override
    public Optional<Problem> create(OrderCreationRequest request) {

        var client = new SpotClientImpl(
            request.credentialPair().apiKey(),
            request.credentialPair().secretKey(),
            url
        );

        var order = request.order();

        Map<String, Object> parameters = new LinkedHashMap<>();

        parameters.put("symbol", order.pair().toUpperCase());
        parameters.put("side", order.type().toString());
        parameters.put("type", "LIMIT");
        parameters.put("timeInForce", "GTC");
        parameters.put("quantity", order.quantity());
        parameters.put("price", order.price());

        try {
            client.createTrade()
                .newOrder(parameters);
        } catch (BinanceClientException e) {
            log.error(e.getMessage(), e);
            return Optional.of(Problem.BINANCE_API_PROBLEM);
        }

        return Optional.empty();
    }
}