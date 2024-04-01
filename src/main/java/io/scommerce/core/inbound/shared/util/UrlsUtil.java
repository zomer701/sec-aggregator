package io.scommerce.core.inbound.shared.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class UrlsUtil {

    public static String azureBucketUrl(final String prefix, String fileName) {
        return String.format("%s/%s", prefix, fileName);
    }

}
