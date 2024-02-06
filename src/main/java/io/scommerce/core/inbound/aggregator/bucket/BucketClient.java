package io.scommerce.core.inbound.aggregator.bucket;

import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Mono;

public interface BucketClient {
    Mono<Void> upload(FilePart file);
}
