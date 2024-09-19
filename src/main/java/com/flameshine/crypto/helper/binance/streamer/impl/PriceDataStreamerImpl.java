package com.flameshine.crypto.helper.binance.streamer.impl;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.binance.connector.client.WebSocketStreamClient;
import com.binance.connector.client.impl.WebSocketStreamClientImpl;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;

import com.flameshine.crypto.helper.api.PriceTargetListener;
import com.flameshine.crypto.helper.binance.config.ApiConfig;
import com.flameshine.crypto.helper.binance.model.PriceAlert;
import com.flameshine.crypto.helper.binance.streamer.PriceDataStreamer;

// TODO: load all orders from the database on startup

@ApplicationScoped
@Slf4j
public class PriceDataStreamerImpl implements PriceDataStreamer {

    private final PriceTargetListener listener;
    private final WebSocketStreamClient client;
    private final Map<Long, Integer> streamedOrders;

    @Inject
    public PriceDataStreamerImpl(
        PriceTargetListener listener,
        ApiConfig config
    ) {
        this.listener = listener;
        this.client = new WebSocketStreamClientImpl(config.streamUrl());
        this.streamedOrders = new ConcurrentHashMap<>();
    }

    @Override
    public void stream(PriceAlert priceAlert) {

        var streamId = client.tradeStream(
            priceAlert.pair(),
            event -> {

                log.debug("Received event: {}", event);

                var price = extractPrice(event);

                if (priceAlert.target().compareTo(price) >= 0) {
                    listener.onPriceReached(priceAlert.id());
                    remove(priceAlert);
                }
            }
        );

        streamedOrders.putIfAbsent(priceAlert.id(), streamId);
    }

    private void remove(PriceAlert priceAlert) {

        var orderId = priceAlert.id();

        if (!streamedOrders.containsKey(orderId)) {
            return;
        }

        var streamId = streamedOrders.get(orderId);

        log.debug("Closing stream for order with id {}", orderId);

        client.closeConnection(streamId);

        streamedOrders.remove(orderId);
    }

    // TODO: handle potential errors

    private static BigDecimal extractPrice(String json) {
        var jsonObject = new JSONObject(json);
        return jsonObject.getBigDecimal("p");
    }
}