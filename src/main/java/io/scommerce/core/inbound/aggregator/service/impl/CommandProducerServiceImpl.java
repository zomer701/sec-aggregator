package io.scommerce.core.inbound.aggregator.service.impl;

import io.scommerce.core.inbound.shared.dto.CommandDTO;
import io.scommerce.core.inbound.aggregator.service.CommandProducerService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.kafka.sender.SenderResult;

@Service
@Slf4j
public class CommandProducerServiceImpl implements CommandProducerService {

    @Value("${topic.name.command}")
    private String topicNameCommand;

    @Resource
    private ReactiveKafkaProducerTemplate<String, CommandDTO> reactiveKafkaProducerTemplate;

    @Override
    public Mono<SenderResult<Void>> send(CommandDTO command) {
        log.info("send to topic={}, {}={},", topicNameCommand, CommandDTO.class.getSimpleName(), command);
        return reactiveKafkaProducerTemplate.send(topicNameCommand, command)
                .doOnSuccess(senderResult -> log.info("sent {} offset : {}", command, senderResult.recordMetadata().offset()))
                .doOnError(throwable -> log.error("Error while sending message to destination topic : {}", throwable.getMessage()));
    }
}
