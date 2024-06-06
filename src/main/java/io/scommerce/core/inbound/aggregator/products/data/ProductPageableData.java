package io.scommerce.core.inbound.aggregator.products.data;

import java.util.List;

public record ProductPageableData(List<Product> products, PageableData pageableData) {
}
