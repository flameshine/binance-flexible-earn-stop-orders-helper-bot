package com.flameshine.crypto.helper.binance.model.api;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public record FlexibleEarnPositionListResponse(
    @JsonProperty("rows") List<Row> rows
) {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Row(
        @JsonProperty("totalAmount") String totalAmount,
        @JsonProperty("asset") String asset,
        @JsonProperty("productId") String productId
    ) {}
}