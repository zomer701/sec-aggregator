package io.scommerce.core.inbound.aggregator.unit.service.impl;

import io.scommerce.core.inbound.aggregator.unit.model.Unit;
import io.scommerce.core.inbound.aggregator.unit.repository.UnitRepository;
import io.scommerce.core.inbound.aggregator.unit.service.UnitService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@AllArgsConstructor
public class UnitServiceImpl implements UnitService {

    private UnitRepository unitRepository;


    public Mono<Unit> findById(UUID id) {
        return unitRepository.findById(id);
    }

    public Mono<Unit> save(Unit unit) {
        return unitRepository.save(unit);
    }

    public Mono<Void> deleteById(UUID id) {
        return unitRepository.deleteById(id);
    }

    public Flux<Unit> getAll(Pageable pageable) {
        return unitRepository.findAllBy(pageable);
    }

    @Override
    public Flux<Unit> getAll() {
        return unitRepository.findAll();
    }

}
