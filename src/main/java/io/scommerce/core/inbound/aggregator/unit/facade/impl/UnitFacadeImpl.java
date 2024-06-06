//package io.scommerce.core.inbound.aggregator.unit.facade.impl;
//
//import io.scommerce.core.inbound.aggregator.unit.facade.UnitFacade;
//import io.scommerce.core.inbound.aggregator.unit.mapper.UnitMapper;
//import io.scommerce.core.inbound.aggregator.unit.payload.UnitRequest;
//import io.scommerce.core.inbound.aggregator.unit.payload.UnitResponse;
//import io.scommerce.core.inbound.aggregator.unit.service.UnitService;
//import lombok.AllArgsConstructor;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Service;
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;
//
//import java.util.UUID;
//
//@Service
//@AllArgsConstructor
//public class UnitFacadeImpl implements UnitFacade {
//
//    private UnitService unitService;
//
//    private UnitMapper unitMapper;
//
//    @Override
//    public Mono<UnitResponse> getUnit(UUID uuid) {
//         return unitService.findById(uuid)
//                 .map(unitMapper::unitToUnitResponse);
//    }
//
//    @Override
//    public Mono<UnitResponse> update(UnitRequest unitRequest) {
//         return Mono.just(unitRequest)
//                 .map(unitMapper::unitResponseToUnit)
//                 .flatMap(unitService::save)
//                 .map(unitMapper::unitToUnitResponse);
//    }
//
//    @Override
//    public Mono<UnitResponse> create(UnitRequest unitRequest) {
//        return Mono.just(unitRequest)
//                .map(unitMapper::unitResponseToUnit)
//                .flatMap(unitService::save)
//                .map(unitMapper::unitToUnitResponse);
//    }
//
//    @Override
//    public Mono<Void> delete(UUID uuid) {
//        return unitService.deleteById(uuid);
//    }
//
//    @Override
//    public Flux<UnitResponse> getAll() {
//        return unitService.getAll()
//                .map(unitMapper::unitToUnitResponse);
//    }
//
//    public Mono<Page<UnitResponse>> getAll(Pageable pageable) {
//        return unitService.getAll(pageable)
//                .map(unitMapper::unitToUnitResponse)
//                .collectList()
//                .zipWith(unitService.getAll(pageable).count())
//                .map(p -> new PageImpl<>(p.getT1(), pageable, p.getT2()));
//    }
//}
