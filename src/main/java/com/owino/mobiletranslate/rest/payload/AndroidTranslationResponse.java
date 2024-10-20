package com.owino.mobiletranslate.rest.payload;

import java.util.Map;

public record AndroidTranslationResponse(String workflow, String targetOS, Map<String,Map<String,String>> translations) {
}
