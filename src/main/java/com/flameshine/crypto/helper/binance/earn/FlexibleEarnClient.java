package com.flameshine.crypto.helper.binance.earn;

import java.util.Optional;

import com.flameshine.crypto.helper.api.model.Problem;
import com.flameshine.crypto.helper.binance.model.RedeemFlexibleProductRequest;

public interface FlexibleEarnClient {
    Optional<Problem> redeem(RedeemFlexibleProductRequest request);
}