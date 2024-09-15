package com.flameshine.crypto.helper.binance;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.binance.connector.client.WebSocketStreamClient;
import com.binance.connector.client.impl.WebSocketStreamClientImpl;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import com.flameshine.crypto.helper.api.PriceTargetListener;
import com.flameshine.crypto.helper.binance.config.BinanceDataStreamConfig;
import com.flameshine.crypto.helper.binance.model.Order;

// TODO: finalize

@ApplicationScoped
public class BinanceOrderPriceStreamer {

    private final PriceTargetListener listener;
    private final Map<Long, Map<Order, Integer>> userConnections;
    private final WebSocketStreamClient client;

    @Inject
    public BinanceOrderPriceStreamer(PriceTargetListener listener, BinanceDataStreamConfig config) {
        this.listener = listener;
        this.userConnections = new ConcurrentHashMap<>();
        this.client = new WebSocketStreamClientImpl(config.baseUrl());
    }
    
    public void monitorOrder(Order order) {

        var tradingPairKey = buildTradingPairKey(order.base(), order.quote());

        var streamId = client.tradeStream(
            tradingPairKey,
            event -> {
                listener.onPriceReached();
                cancelOrder(order);
            }
        );

        userConnections.putIfAbsent(order.userId(), new HashMap<>());

        userConnections.get(order.userId())
            .put(order, streamId);
    }

    private void cancelOrder(Order order) {

        var userId = order.userId();

        if (!userConnections.containsKey(userId) && !userConnections.get(userId).containsKey(order)) {
            return;
        }

        var streamId = userConnections.get(userId)
            .get(order);

        client.closeConnection(streamId);

        userConnections.get(userId)
            .remove(order);
    }

    private static String buildTradingPairKey(String base, String quote) {
        return String.format("%s%s", base.toLowerCase(), quote.toLowerCase());
    }
}