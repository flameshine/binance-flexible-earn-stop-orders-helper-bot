package com.flameshine.crypto.helper.binance.alert;

import com.flameshine.crypto.helper.binance.model.PriceAlert;

public interface PriceAlertHandler {
    void handle(PriceAlert priceAlert);
    void remove(Long alertId);
}