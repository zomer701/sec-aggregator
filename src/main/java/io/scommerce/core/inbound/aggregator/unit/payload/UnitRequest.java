package io.scommerce.core.inbound.aggregator.unit.payload;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UnitRequest(UUID uuid, @NotNull(message = "email cannot be null") String email) {
    public UnitRequest withId(UUID uuid) {
        return new UnitRequest(uuid, email());
    }
}
