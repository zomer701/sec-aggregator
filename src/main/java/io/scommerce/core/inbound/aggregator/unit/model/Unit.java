package io.scommerce.core.inbound.aggregator.unit.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;

import java.util.UUID;

@Data
@AllArgsConstructor
@EqualsAndHashCode
public class Unit {

    @Id
    private UUID uuid;

    private String email;
}
