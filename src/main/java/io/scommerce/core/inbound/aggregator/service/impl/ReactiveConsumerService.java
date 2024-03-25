//package io.scommerce.core.inbound.aggregator.service.impl;
//
//import io.scommerce.core.inbound.aggregator.dto.CommandDTO;
//import lombok.extern.slf4j.Slf4j;
//import org.slf4j.LoggerFactory;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
//import org.springframework.stereotype.Service;
//import reactor.core.publisher.Flux;
//import org.apache.kafka.clients.consumer.ConsumerRecord;
//import java.util.logging.Logger;
//
//@Slf4j
//@Service
//public class ReactiveConsumerService implements CommandLineRunner {
//
//    private final ReactiveKafkaConsumerTemplate<String, CommandDTO> reactiveKafkaConsumerTemplate;
//
//    public ReactiveConsumerService(ReactiveKafkaConsumerTemplate<String, CommandDTO> reactiveKafkaConsumerTemplate) {
//        this.reactiveKafkaConsumerTemplate = reactiveKafkaConsumerTemplate;
//    }
//
//    public Flux<CommandDTO> consumeFakeConsumerDTO() {
//        return reactiveKafkaConsumerTemplate
//                .receiveAutoAck()
//                // .delayElements(Duration.ofSeconds(2L)) // BACKPRESSURE
//                .doOnNext(consumerRecord -> log.info("received key={}, value={} from topic={}, offset={}",
//                        consumerRecord.key(),
//                        consumerRecord.value(),
//                        consumerRecord.topic(),
//                        consumerRecord.offset())
//                )
//                .map(ConsumerRecord::value)
//                .doOnNext(fakeConsumerDTO -> log.info("successfully consumed {}={}", CommandDTO.class.getSimpleName(), fakeConsumerDTO))
//                .doOnError(throwable -> log.error("something bad happened while consuming : {}", throwable.getMessage()));
//    }
//
//    @Override
//    public void run(String... args) throws Exception {
//        consumeFakeConsumerDTO().subscribe();
//    }
//}
