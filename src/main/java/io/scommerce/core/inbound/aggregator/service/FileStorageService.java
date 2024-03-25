package io.scommerce.core.inbound.aggregator.service;

import io.scommerce.core.inbound.aggregator.data.FileData;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Mono;

public interface FileStorageService {
    Mono<String> save(Mono<FilePart> filePartMono);

    Mono<FileData> getStatus(String filename);
}
