package io.scommerce.core.inbound.aggregator.unit.service;

import io.scommerce.core.inbound.aggregator.unit.model.Unit;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface UnitService {
    Mono<Unit> findById(UUID id);

    Mono<Unit> save(Unit unit);

    Mono<Void> deleteById(UUID id);

    Flux<Unit> getAll(Pageable pageable);

    Flux<Unit> getAll();
}
