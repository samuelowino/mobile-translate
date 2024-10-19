package com.owino.mobiletranslate.rest.payload;

public sealed interface TranslatePayload permits AndroidPayload,IOSPayload {
    String targetOS();
}
