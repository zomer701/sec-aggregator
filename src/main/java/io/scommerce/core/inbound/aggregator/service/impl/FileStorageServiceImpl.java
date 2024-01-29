package io.scommerce.core.inbound.aggregator.service.impl;

import io.scommerce.core.inbound.aggregator.data.FileData;
import io.scommerce.core.inbound.aggregator.service.FileStorageService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@Service
public class FileStorageServiceImpl implements FileStorageService {

    private final Path root = Paths.get("uploads");

    @PostConstruct
    public void init() {
        try {
            Files.createDirectories(root);
        } catch (IOException e) {
            log.error("File storage init", e);
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }

    @Override
    public Mono<String> save(Mono<FilePart> filePartMono) {
        return filePartMono.doOnNext(fp -> log.info("Receiving File:" + fp.filename()))
                .flatMap(filePart -> {
                    var filename = filePart.filename();
            return filePart.transferTo(root.resolve(filename)).then(Mono.just(filename));
        });
    }

    @Override
    public Mono<FileData> getStatus(String filename) {
        return null;
    }
}
