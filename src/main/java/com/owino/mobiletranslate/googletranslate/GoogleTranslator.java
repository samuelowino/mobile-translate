package com.owino.mobiletranslate.googletranslate;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;
import java.util.Locale;
import java.util.logging.Logger;

public class GoogleTranslator {

    public static final Logger LOGGER = Logger.getLogger(GoogleTranslator.class.getSimpleName());

    private Translate translateClient;

    public GoogleTranslator() {
        translateClient = TranslateOptions.getDefaultInstance().getService();
    }

    public String getTranslatedText(String originalText, String targetLanguage) {
        Translation translation =
                translateClient.translate(
                        originalText,
                        Translate.TranslateOption.sourceLanguage("en"),
                        Translate.TranslateOption.targetLanguage(targetLanguage));

        java.lang.String translatedText = translation.getTranslatedText();

        LOGGER.info(String.format(Locale.getDefault(),"Text: %s%n", originalText));
        LOGGER.info(String.format("Translation: %s%n", translatedText));

        return translatedText;
    }
}
