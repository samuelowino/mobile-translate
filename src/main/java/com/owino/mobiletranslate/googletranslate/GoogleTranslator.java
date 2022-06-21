package com.owino.mobiletranslate.googletranslate;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.logging.Logger;

public class GoogleTranslator {

    public static final Logger LOGGER = Logger.getLogger(GoogleTranslator.class.getSimpleName());

    private Translate translateClient;

    public GoogleTranslator() {
        translateClient = TranslateOptions.getDefaultInstance().getService();
    }

    public byte[] getTranslatedBytes(String originalText, String targetLanguage) {
        Translation translation =
                translateClient.translate(
                        originalText,
                        Translate.TranslateOption.sourceLanguage("en"),
                        Translate.TranslateOption.targetLanguage(targetLanguage));

        var translatedBytes = translation.getTranslatedText().getBytes(StandardCharsets.UTF_16);

        var translatedString = new String(translatedBytes, StandardCharsets.UTF_16).replace("&quot;", "\"");

        translatedBytes = translatedString.getBytes(StandardCharsets.UTF_16);

        LOGGER.info(String.format(Locale.getDefault(),"Text: %s%n", originalText));
        LOGGER.info(String.format("Translation: %s%n", translatedString));

        return translatedBytes;
    }
}
