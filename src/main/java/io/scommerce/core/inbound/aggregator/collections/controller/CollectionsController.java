package io.scommerce.core.inbound.aggregator.collections.controller;

import io.scommerce.core.inbound.aggregator.collections.data.CollectionDTO;
import io.scommerce.core.inbound.aggregator.collections.facade.CollectionsFacade;
import io.scommerce.core.inbound.aggregator.collections.paylaod.CollectionResponseDto;
import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@RestController("/v1/collections")
@Slf4j
public class CollectionsController {

    @Resource
    private CollectionsFacade collectionsFacade;
    @PostMapping("create")
    public Mono<CollectionResponseDto> save(@RequestBody final CollectionDTO collection) {
        return collectionsFacade.create(collection);
    }
}
