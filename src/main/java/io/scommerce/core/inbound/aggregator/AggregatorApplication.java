package io.scommerce.core.inbound.aggregator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@Slf4j
public class AggregatorApplication {
	public static void main(String[] args) {
		SpringApplication.run(AggregatorApplication.class, args);
	}
}
