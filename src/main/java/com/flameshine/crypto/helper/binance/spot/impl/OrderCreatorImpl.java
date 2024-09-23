package com.flameshine.crypto.helper.binance.spot.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import com.flameshine.crypto.helper.binance.config.ApiConfig;
import com.flameshine.crypto.helper.binance.model.OrderCreationRequest;
import com.flameshine.crypto.helper.binance.spot.OrderCreator;

@ApplicationScoped
public class OrderCreatorImpl implements OrderCreator {

    private final String url;
    private final ObjectMapper mapper;

    @Inject
    public OrderCreatorImpl(ApiConfig config) {
        this.url = config.flexibleEarnUrl();
        this.mapper = new ObjectMapper();
    }

    @Override
    public void create(OrderCreationRequest request) {
        // TODO: implement
    }
}