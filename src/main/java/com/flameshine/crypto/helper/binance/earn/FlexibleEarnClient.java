package com.flameshine.crypto.helper.binance.earn;

import com.flameshine.crypto.helper.binance.model.RedeemFlexibleProductRequest;

public interface FlexibleEarnClient {
    boolean redeem(RedeemFlexibleProductRequest request);
}