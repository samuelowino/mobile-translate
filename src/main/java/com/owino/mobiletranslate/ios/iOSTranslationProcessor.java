package com.owino.mobiletranslate.ios;

import java.io.File;
import java.util.List;
import com.owino.mobiletranslate.common.TranslationProcessor;

public class iOSTranslationProcessor implements TranslationProcessor {
    private TranslationProcessor translationProcessor;

    public iOSTranslationProcessor() {
        translationProcessor = new iOSTranslationProcessor();
    }

    @Override
    public void runTranslation(File outputFile, List<String> targetLanguages) {
    }
}
