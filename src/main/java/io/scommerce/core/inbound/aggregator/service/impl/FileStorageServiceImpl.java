package io.scommerce.core.inbound.aggregator.service.impl;

import io.scommerce.core.inbound.aggregator.bucket.BucketClient;
import io.scommerce.core.inbound.aggregator.data.FileData;
import io.scommerce.core.inbound.aggregator.service.FileStorageService;
import io.scommerce.core.inbound.shared.constants.Constants;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
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

    @Resource
    private BucketClient azureBucketClient;

    @Override
    public Mono<String> save(Mono<FilePart> filePartMono, Constants.ProcessorChannels channel) {
        return filePartMono.doOnNext(fp -> log.info(String.format("Receiving File: %s | channel %s", fp.filename(), channel)))
                .flatMap(filePart -> azureBucketClient.upload(filePart, channel))
                .then(filePartMono.map(FilePart::filename));
    }

    @Override
    public Mono<FileData> getStatus(String filename) {
        return null;
    }
}
