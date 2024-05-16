package io.scommerce.core.inbound.aggregator.collections.facade.impl;

import io.scommerce.core.inbound.aggregator.collections.data.CollectionDTO;
import io.scommerce.core.inbound.aggregator.collections.facade.CollectionsFacade;
import io.scommerce.core.inbound.aggregator.collections.paylaod.CollectionResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class CollectionsFacadeImpl implements CollectionsFacade {
    @Override
    public Mono<CollectionResponseDto> create(CollectionDTO collection) {
        return null;
    }
}
