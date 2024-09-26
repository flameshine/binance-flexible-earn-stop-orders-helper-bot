package com.flameshine.crypto.helper.binance.alert.impl;

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
import com.flameshine.crypto.helper.api.enums.OrderType;
import com.flameshine.crypto.helper.binance.config.ApiConfig;
import com.flameshine.crypto.helper.binance.model.PriceAlert;
import com.flameshine.crypto.helper.binance.alert.PriceAlertHandler;

// TODO: load all orders from the database on startup (disaster recovery)
// TODO: investigate if it makes sense to process alert slightly earlier before the target price is reached

@ApplicationScoped
@Slf4j
public class PriceAlertHandlerImpl implements PriceAlertHandler {

    private final PriceTargetListener listener;
    private final WebSocketStreamClient client;
    private final Map<Long, Integer> streamedAlerts;

    @Inject
    public PriceAlertHandlerImpl(PriceTargetListener listener, ApiConfig config) {
        this.listener = listener;
        this.client = new WebSocketStreamClientImpl(config.streamUrl());
        this.streamedAlerts = new ConcurrentHashMap<>();
    }

    @Override
    public void handle(PriceAlert alert) {

        var alertId = alert.id();

        var streamId = client.tradeStream(
            alert.pair().toLowerCase(),
            event -> {

                if (!streamedAlerts.containsKey(alertId)) {
                    log.debug("Alert with id {} has been already removed. Skipping event.", alertId);
                    return;
                }

                log.debug("Processing event: {}", event);

                var trigger = getTrigger(alert.type(), alert.target());
                var price = extractPrice(event);

                if (price.isEmpty()) {
                    return;
                }

                if (trigger.test(price.get())) {
                    listener.onPriceReached(alertId);
                    remove(alertId);
                }
            }
        );

        streamedAlerts.putIfAbsent(alertId, streamId);
    }

    @Override
    public void remove(Long alertId) {

        if (!streamedAlerts.containsKey(alertId)) {
            return;
        }

        var streamId = streamedAlerts.get(alertId);

        log.debug("Closing stream for alert with id {}", alertId);

        client.closeConnection(streamId);
        streamedAlerts.remove(alertId);
    }

    private static Predicate<BigDecimal> getTrigger(OrderType type, BigDecimal alertTarget) {
        return OrderType.BUY == type
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