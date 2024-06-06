//package io.scommerce.core.inbound.aggregator.unit.facade;
//
//import io.scommerce.core.inbound.aggregator.unit.payload.UnitRequest;
//import io.scommerce.core.inbound.aggregator.unit.payload.UnitResponse;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;
//
//import java.util.UUID;
//
//public interface UnitFacade {
//    Mono<UnitResponse> getUnit(UUID uuid);
//
//    Mono<UnitResponse> create(UnitRequest unitRequest);
//    Mono<UnitResponse> update(UnitRequest unitRequest);
//
//    Mono<Void> delete(UUID uuid);
//
//    Flux<UnitResponse> getAll();
//
//    Mono<Page<UnitResponse>> getAll(Pageable pageable);
//}
