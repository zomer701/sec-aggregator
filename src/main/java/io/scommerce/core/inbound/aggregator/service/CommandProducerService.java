package io.scommerce.core.inbound.aggregator.service;

import io.scommerce.core.inbound.aggregator.dto.CommandDTO;
import reactor.core.publisher.Mono;
import reactor.kafka.sender.SenderResult;

public interface CommandProducerService {
    Mono<SenderResult<Void>> send(CommandDTO command);
}
