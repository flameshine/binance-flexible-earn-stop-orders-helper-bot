package com.flameshine.crypto.helper.binance.stream.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.binance.connector.client.WebSocketStreamClient;
import com.binance.connector.client.impl.WebSocketStreamClientImpl;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;

import com.flameshine.crypto.helper.api.PriceTargetListener;
import com.flameshine.crypto.helper.binance.config.BinanceDataStreamConfig;
import com.flameshine.crypto.helper.binance.model.Order;
import com.flameshine.crypto.helper.binance.stream.BinanceTradeDataStreamer;

// TODO: load all orders from the database on startup

@ApplicationScoped
@Slf4j
public class BinanceTradeDataStreamerImpl implements BinanceTradeDataStreamer {

    private final PriceTargetListener listener;
    private final WebSocketStreamClient client;
    private final Map<Long, Map<Long, Integer>> streamedUserOrders;

    @Inject
    public BinanceTradeDataStreamerImpl(
        PriceTargetListener listener,
        BinanceDataStreamConfig config
    ) {
        this.listener = listener;
        this.client = new WebSocketStreamClientImpl(config.baseUrl());
        this.streamedUserOrders = new ConcurrentHashMap<>();
    }

    @Override
    public void stream(Order order) {

        var tradingPairKey = order.base() + order.quote();

        var streamId = client.tradeStream(
            tradingPairKey,
            event -> {

                log.debug("Received event: {}", event);

                var price = extractPrice(event);

                if (order.target().compareTo(price) <= 0) {
                    listener.onPriceReached(order.id());
                    remove(order);
                }
            }
        );

        streamedUserOrders.putIfAbsent(order.userId(), new HashMap<>());

        streamedUserOrders.get(order.userId())
            .put(order.id(), streamId);
    }

    private void remove(Order order) {

        var orderId = order.id();
        var userId = order.userId();

        if (!streamedUserOrders.containsKey(userId) || !streamedUserOrders.get(userId).containsKey(orderId)) {
            return;
        }

        var streamId = streamedUserOrders.get(userId)
            .get(orderId);

        log.info("Closing stream for order with id {}", orderId);

        client.closeConnection(streamId);

        streamedUserOrders.get(userId)
            .remove(orderId);
    }

    // TODO: handle potential errors

    private static BigDecimal extractPrice(String json) {
        var jsonObject = new JSONObject(json);
        return jsonObject.getBigDecimal("p");
    }
}