package com.owino.mobiletranslate.rest.payload;

import java.util.Set;

public record IOSPayload(String workflow,
                         String targetOS,
                         Set<String> distinctLanguages,
                         Set<IOSMessage> iosContent) implements TranslatePayload {
    @Override
    public String targetOS() {
        return "IOS";
    }
}
