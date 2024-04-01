package io.scommerce.core.inbound.aggregator.config;

import io.scommerce.core.inbound.shared.dto.CommandDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import reactor.kafka.receiver.ReceiverOptions;
import reactor.kafka.sender.SenderOptions;

import java.util.Collections;
import java.util.Map;

@Configuration
@Slf4j
public class ReactiveKafkaProducerConfig {

    @Bean
    public ReceiverOptions<String, CommandDTO> kafkaReceiverOptions(@Value("${topic.name.command}")String topic, KafkaProperties kafkaProperties) {
        ReceiverOptions<String, CommandDTO> basicReceiverOptions = ReceiverOptions.create(kafkaProperties.buildConsumerProperties(null));
        return basicReceiverOptions.subscription(Collections.singletonList(topic)).addAssignListener(partitions -> log.info("onPartitionsAssigned {}", partitions))
                .addRevokeListener(partitions -> log.info("onPartitionsRevoked {}", partitions));
    }

    @Bean
    public ReactiveKafkaConsumerTemplate<String, CommandDTO> reactiveKafkaConsumerTemplate(ReceiverOptions<String, CommandDTO> kafkaReceiverOptions) {
        return new ReactiveKafkaConsumerTemplate<>(kafkaReceiverOptions);
    }
    @Bean
    public ReactiveKafkaProducerTemplate<String, CommandDTO> reactiveKafkaProducerTemplate(
            KafkaProperties properties) {
        Map<String, Object> props = properties.buildProducerProperties(null);
        return new ReactiveKafkaProducerTemplate<>(SenderOptions.create(props));
    }

    @Bean
    public SenderOptions<String, CommandDTO> producerProps(KafkaProperties kafkaProperties) {
        return SenderOptions.create(kafkaProperties.buildProducerProperties(null));
    }
}
