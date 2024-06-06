package io.scommerce.core.inbound.aggregator.products.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude. Include. NON_NULL)
public class Product {

    private String code;
    private String ean;
    private String baseId;
    private String name;
    private String variants;
    private String categories;
    private int stock;
}
