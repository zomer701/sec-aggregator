package io.scommerce.core.inbound.aggregator.products.data;

public record PageableData(String nextToken, long total, int pageSize) {
}
