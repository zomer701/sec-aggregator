package io.scommerce.core.inbound.aggregator.products.data;

import lombok.Data;

import java.util.List;

@Data
public class BaseProduct {

    private String id;
    private List<Product> variants;
}
