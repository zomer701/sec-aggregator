package io.scommerce.core.inbound.aggregator.collections.payload;

import io.scommerce.core.inbound.shared.constants.Constants;

public record CollectionRequest(String name, Constants.Platform platform) {
}
