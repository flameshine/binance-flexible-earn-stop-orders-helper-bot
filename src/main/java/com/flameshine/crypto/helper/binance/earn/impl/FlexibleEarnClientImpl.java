package com.flameshine.crypto.helper.binance.earn.impl;

import com.google.common.base.Preconditions;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.MediaType;

import com.flameshine.crypto.helper.binance.config.ApiConfig;
import com.flameshine.crypto.helper.binance.earn.FlexibleEarnClient;
import com.flameshine.crypto.helper.binance.model.RedeemFlexibleProductRequest;
import com.flameshine.crypto.helper.binance.model.RedeemFlexibleProductResponse;

// TODO: use SpotClient::redeem
// TODO: review this
// TODO: check if user has enough assets to redeem

@ApplicationScoped
public class FlexibleEarnClientImpl implements FlexibleEarnClient {

    private final Client client;
    private final String url;

    @Inject
    public FlexibleEarnClientImpl(ApiConfig config) {
        this.client = ClientBuilder.newClient();
        this.url = config.flexibleEarnUrl();
    }

    @Override
    public boolean redeem(RedeemFlexibleProductRequest request) {

        var builder = client.target(url)
            .request(MediaType.APPLICATION_JSON);

        RedeemFlexibleProductResponse response;

        try (var result = builder.post(Entity.json(request))) {

            Preconditions.checkState(
                200 == result.getStatus(),
                "Received unsuccessful HTTP response status: %s",
                result.getStatus()
            );

            response = result.readEntity(RedeemFlexibleProductResponse.class);
        }

        return response.success();
    }
}