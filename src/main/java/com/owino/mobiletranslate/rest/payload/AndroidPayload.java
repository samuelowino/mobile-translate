package com.owino.mobiletranslate.rest.payload;

import java.util.List;
import java.util.Set;

/**
 * This  represents android request payload
 * @param workflow represents a service on the application
 * @param targetOS target operating system
 * @param distinctLanguages locales to xmlContent to be translated to
 * @param xmlContent payload to be processed
 */
public record AndroidPayload(String workflow,
                             String targetOS,
                             List<String> distinctLanguages,
                             List<XmlMessage> xmlContent)  implements TranslatePayload{
    @Override
    public String targetOS() {
        return "ANDROID";
    }
}
