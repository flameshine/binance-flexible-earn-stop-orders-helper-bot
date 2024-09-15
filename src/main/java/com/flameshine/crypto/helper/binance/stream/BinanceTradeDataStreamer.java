package com.flameshine.crypto.helper.binance.stream;

import com.flameshine.crypto.helper.binance.model.Order;

public interface BinanceTradeDataStreamer {
    void stream(Order order);
}