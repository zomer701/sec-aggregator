package io.scommerce.core.inbound.aggregator;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Inbound", version = "1.0", description = "Inbound APIs v1.0"))
public class AggregatorApplication {
	public static void main(String[] args) {
		SpringApplication.run(AggregatorApplication.class, args);
	}

}
