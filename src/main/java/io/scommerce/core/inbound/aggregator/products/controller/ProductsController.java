package io.scommerce.core.inbound.aggregator.products.controller;

import io.scommerce.core.inbound.aggregator.products.data.ProductPageableData;
import io.scommerce.core.inbound.aggregator.products.facade.ProductsFacade;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@RestController
@RequestMapping("/v1/products")
@Slf4j
public class ProductsController {

    private ProductsFacade productsFacade;

    @GetMapping
    public Mono<ProductPageableData> getProducts(@RequestParam(required = false) String token,
                                                 @RequestParam(required = false, defaultValue = "100") int pageSize,
                                                 @RequestParam(required = false) String search) {
        return productsFacade.getProducts(token, pageSize, search);
    }

    @GetMapping("/base")
    public Mono<ProductPageableData> getBaseProducts(@RequestParam(required = false) String token,
                                                 @RequestParam(required = false, defaultValue = "100") int pageSize,
                                                 @RequestParam(required = false) String search) {
        return productsFacade.getBaseProducts(token, pageSize, search);
    }
}
