package io.scommerce.core.inbound.aggregator.products.data;

import lombok.Data;

@Data
public class Product {

    private String code;
    private String ean;
    private String baseId;
    private String name;
    private String variants;
    private String categories;
}
