package com.owino.mobiletranslate.rest.payload;

import java.util.List;
import java.util.Set;

public record IOSPayload(String workflow,
                         String targetOS,
                         List<String> distinctLanguages,
                         List<IOSMessage> iosContent) implements TranslatePayload {
    @Override
    public String targetOS() {
        return "IOS";
    }
}
