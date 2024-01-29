package io.scommerce.core.inbound.aggregator.controller;


import io.scommerce.core.inbound.aggregator.service.FileStorageService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1")
@AllArgsConstructor
public class FileController {

    private FileStorageService storageService;

    @PostMapping("/upload")
    public Mono<Void> uploadFile(@RequestPart("file") Mono<FilePart> filePartMono) {
        return storageService.save(filePartMono).map(
                (filename) -> ResponseEntity.ok()).then();
    }
}
