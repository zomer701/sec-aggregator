//package io.scommerce.core.inbound.aggregator.unit.controller;
//
//import io.scommerce.core.inbound.aggregator.unit.facade.UnitFacade;
//import io.scommerce.core.inbound.aggregator.unit.payload.UnitRequest;
//import io.scommerce.core.inbound.aggregator.unit.payload.UnitResponse;
//import jakarta.validation.Valid;
//import lombok.AllArgsConstructor;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.http.HttpStatus;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.*;
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;
//
//import java.util.UUID;
//
//@RestController
//@AllArgsConstructor
//@RequestMapping("/api/v1/unit")
//@Validated
//public class UnitController {
//
//    private UnitFacade unitFacade;
//
//    @GetMapping("/{uuid}")
//    @ResponseStatus(HttpStatus.OK)
//    public Mono<UnitResponse> getByUUID(@PathVariable("uuid") UUID uuid) {
//        return unitFacade.getUnit(uuid);
//    }
//
//    @GetMapping
//    @ResponseStatus(HttpStatus.OK)
//    public Flux<UnitResponse> getAll() {
//        return unitFacade.getAll();
//    }
//
//    @GetMapping("/pages")
//    @ResponseStatus(HttpStatus.OK)
//    public Mono<Page<UnitResponse>> getAll(Pageable pageable) {
//        return unitFacade.getAll(pageable);
//    }
//
//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    public Mono<UnitResponse> create(@RequestBody @Valid UnitRequest unitRequest) {
//        return unitFacade.create(unitRequest);
//    }
//
//    @PutMapping("/{uuid}")
//    @ResponseStatus(HttpStatus.OK)
//    public Mono<UnitResponse> update(@PathVariable("uuid") UUID uuid, @RequestBody @Valid UnitRequest unitRequest) {
//        return unitFacade.update(unitRequest.withId(uuid));
//    }
//
//    @DeleteMapping("/{uuid}")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public Mono<Void> deleteTutorial(@PathVariable("uuid") UUID uuid) {
//        return unitFacade.delete(uuid);
//    }
//}
