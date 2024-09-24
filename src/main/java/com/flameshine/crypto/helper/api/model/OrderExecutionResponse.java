package com.flameshine.crypto.helper.api.model;

import com.flameshine.crypto.helper.api.entity.Order;
import com.flameshine.crypto.helper.api.enums.Problem;

public record OrderExecutionResponse(
    Order order,
    Problem problem
) {}