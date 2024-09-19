package com.flameshine.crypto.helper.binance.streamer;

import com.flameshine.crypto.helper.binance.model.PriceAlert;

public interface PriceDataStreamer {
    void stream(PriceAlert priceAlert);
}