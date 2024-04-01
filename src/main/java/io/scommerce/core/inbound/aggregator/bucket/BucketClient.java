package io.scommerce.core.inbound.aggregator.bucket;

import io.scommerce.core.inbound.shared.constants.Constants;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Mono;

public interface BucketClient {
    Mono<Void> upload(FilePart file,  Constants.ProcessorChannels channel);
}
