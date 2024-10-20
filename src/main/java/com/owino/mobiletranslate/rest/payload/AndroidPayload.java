package com.owino.mobiletranslate.rest.payload;

import java.util.List;
import java.util.Set;

public record AndroidPayload(String workflow,
                             String targetOS,
                             List<String> distinctLanguages,
                             List<XmlMessage> xmlContent)  implements TranslatePayload{
    @Override
    public String targetOS() {
        return "ANDROID";
    }
}
