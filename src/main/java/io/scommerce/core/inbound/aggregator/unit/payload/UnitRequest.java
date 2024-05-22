package io.scommerce.core.inbound.aggregator.unit.payload;

import jakarta.validation.constraints.NotEmpty;

import java.util.UUID;

public record UnitRequest(UUID uuid, @NotEmpty(message = "email cannot be null") String email) {
    public UnitRequest withId(UUID uuid) {
        return new UnitRequest(uuid, email());
    }
}
