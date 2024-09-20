package com.flameshine.crypto.helper.binance.streamer.impl;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;

import com.binance.connector.client.WebSocketStreamClient;
import com.binance.connector.client.impl.WebSocketStreamClientImpl;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;

import com.flameshine.crypto.helper.api.PriceTargetListener;
import com.flameshine.crypto.helper.binance.config.ApiConfig;
import com.flameshine.crypto.helper.binance.model.PriceAlert;
import com.flameshine.crypto.helper.binance.streamer.PriceDataStreamer;

// TODO: load all orders from the database on startup (failure recovery)

@ApplicationScoped
@Slf4j
public class PriceDataStreamerImpl implements PriceDataStreamer {

    private final PriceTargetListener listener;
    private final WebSocketStreamClient client;
    private final Map<Long, Integer> streamedAlerts;

    @Inject
    public PriceDataStreamerImpl(PriceTargetListener listener, ApiConfig config) {
        this.listener = listener;
        this.client = new WebSocketStreamClientImpl(config.streamUrl());
        this.streamedAlerts = new ConcurrentHashMap<>();
    }

    @Override
    public void stream(PriceAlert alert) {

        var streamId = client.tradeStream(
            alert.pair(),
            event -> {

                log.debug("Received event: {}", event);

                var trigger = getTrigger(alert.type(), alert.target());
                var price = extractPrice(event);

                if (price.isEmpty()) {
                    return;
                }

                if (trigger.test(price.get())) {
                    listener.onPriceReached(alert.id());
                    remove(alert);
                }
            }
        );

        streamedAlerts.putIfAbsent(alert.id(), streamId);
    }

    private void remove(PriceAlert alert) {

        var alertId = alert.id();

        if (!streamedAlerts.containsKey(alertId)) {
            return;
        }

        var streamId = streamedAlerts.get(alertId);

        log.debug("Closing stream for alert with id {}", alertId);

        client.closeConnection(streamId);
        streamedAlerts.remove(alertId);
    }

    private static Predicate<BigDecimal> getTrigger(PriceAlert.Type type, BigDecimal alertTarget) {
        return PriceAlert.Type.BUY == type
            ? price -> alertTarget.compareTo(price) >= 0
            : price -> alertTarget.compareTo(price) <= 0;
    }

    private static Optional<BigDecimal> extractPrice(String json) {
        try {
            var jsonObject = new JSONObject(json);
            return Optional.of(jsonObject.getBigDecimal("p"));
        } catch (JSONException e) {
            log.error("Failed to extract price from JSON: {}", json, e);
            return Optional.empty();
        }
    }
}