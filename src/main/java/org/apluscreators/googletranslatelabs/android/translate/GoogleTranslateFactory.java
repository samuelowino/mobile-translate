package org.apluscreators.googletranslatelabs.android.translate;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.Translate.TranslateOption;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;
import org.apluscreators.googletranslatelabs.android.model.Resources;
import org.apluscreators.googletranslatelabs.android.model.String;

public class GoogleTranslateFactory {

    Translate translateClient;
    Resources resources;

    public GoogleTranslateFactory(Resources resources) {
        translateClient = TranslateOptions.getDefaultInstance().getService();
        this.resources = resources;
    }

    public Resources getTranslatedResources(java.lang.String targetLanguage) {
        return translateResources(targetLanguage);
    }

    private Resources translateResources(java.lang.String targetLanguage) {

        Resources translatedResources = new Resources();
        String[] translatedStringsArray = new String[resources.getStrings().length];

        org.apluscreators.googletranslatelabs.android.model.String[] strings = resources.getStrings();

        for (int i = 0; i < strings.length; i++) {
            String translatedStringEntry = new String();

            java.lang.String name = strings[i].getName();
            java.lang.String untranslatedContent = strings[i].getContent();
            java.lang.String translatedContent = getTranslatedText(untranslatedContent, targetLanguage);

            //todo translate

            translatedStringEntry.setName(name);
            translatedStringEntry.setContent(translatedContent);

            translatedStringsArray[i] = translatedStringEntry;
        }

        translatedResources.setStrings(translatedStringsArray);

        return translatedResources;

    }

    private java.lang.String getTranslatedText(java.lang.String originalText, java.lang.String targetLanguage) {

        Translation translation =
                translateClient.translate(
                        originalText,
                        TranslateOption.sourceLanguage("en"),
                        TranslateOption.targetLanguage(targetLanguage));

        java.lang.String translatedText = translation.getTranslatedText();

        System.out.printf("Text: %s%n", originalText);
        System.out.printf("Translation: %s%n", translatedText);

        return translatedText;
    }

}


