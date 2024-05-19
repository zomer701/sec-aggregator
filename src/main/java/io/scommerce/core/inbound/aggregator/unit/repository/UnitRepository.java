package io.scommerce.core.inbound.aggregator.unit.repository;

import io.scommerce.core.inbound.aggregator.unit.model.Unit;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Repository
public interface UnitRepository extends R2dbcRepository<Unit, UUID> {
    Flux<Unit> findAllBy(Pageable pageable);
}
