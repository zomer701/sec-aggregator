package io.scommerce.core.inbound.aggregator.products.data;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude. Include. NON_NULL)
public record ProductPageableData(List<Product> products, PageableData pageableData) {
}
