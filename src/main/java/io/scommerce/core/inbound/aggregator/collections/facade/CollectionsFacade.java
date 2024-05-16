package io.scommerce.core.inbound.aggregator.collections.facade;

import io.scommerce.core.inbound.aggregator.collections.data.CollectionDTO;
import io.scommerce.core.inbound.aggregator.collections.paylaod.CollectionResponseDto;
import reactor.core.publisher.Mono;

public interface CollectionsFacade {
    Mono<CollectionResponseDto> create(CollectionDTO collection);
}
