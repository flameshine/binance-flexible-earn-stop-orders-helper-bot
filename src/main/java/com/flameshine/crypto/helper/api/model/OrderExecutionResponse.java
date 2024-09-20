package com.flameshine.crypto.helper.api.model;

import com.flameshine.crypto.helper.api.entity.Order;

public record OrderExecutionResponse(
    Order order,
    Problem problem
) {}