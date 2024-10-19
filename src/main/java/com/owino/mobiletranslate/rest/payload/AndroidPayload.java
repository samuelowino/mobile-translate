package com.owino.mobiletranslate.rest.payload;

import java.util.Set;

public record AndroidPayload(String workflow,
                             String targetOS,
                             Set<String> distinctLanguages,
                             Set<XmlMessage> xmlContent)  implements TranslatePayload{
    @Override
    public String targetOS() {
        return "ANDROID";
    }
}
