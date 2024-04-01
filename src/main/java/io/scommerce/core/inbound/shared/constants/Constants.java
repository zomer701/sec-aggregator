package io.scommerce.core.inbound.shared.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Constants {

    public enum ProcessorChannels {
        NATIVE_FACEBOOK_REQUIRED_PRODUCTS,
        NATIVE_FACEBOOK_US_CHECKOUT_PRODUCTS,
    }
}
