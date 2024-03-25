package io.scommerce.core.inbound.aggregator.controller;


import io.scommerce.core.inbound.aggregator.dto.CommandDTO;
import io.scommerce.core.inbound.aggregator.service.CommandProducerService;
import io.scommerce.core.inbound.aggregator.service.FileStorageService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Random;

@RestController
@RequestMapping("/v1")
@AllArgsConstructor
public class FileController {

    private final FileStorageService storageService;
    private final CommandProducerService commandProducerService;

    @PostMapping("/upload")
    public Mono<ResponseEntity<Void>> uploadFile(@RequestPart("file") Mono<FilePart> filePartMono) {
        return storageService.save(filePartMono)
                .flatMap(p -> commandProducerService.send(CommandDTO.builder()
                        .pk(new Random().nextLong(100000)).name(p)
                        .build()))
                .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK)))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
