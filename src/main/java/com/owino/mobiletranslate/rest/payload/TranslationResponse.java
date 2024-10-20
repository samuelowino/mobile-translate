package com.owino.mobiletranslate.rest.payload;

import java.util.Map;

/**
 * This record represents our response payload
 *
 * @param workflow service you requested
 * @param targetOS operating system you targeted
 * @param translations request body sent back
 */
public record TranslationResponse(String workflow, String targetOS, Map<String,Map<String,String>> translations) {
}
