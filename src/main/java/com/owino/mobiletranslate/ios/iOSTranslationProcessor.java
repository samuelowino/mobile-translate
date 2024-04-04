package com.owino.mobiletranslate.ios;

import com.owino.mobiletranslate.common.TranslationProcessor;
import com.owino.mobiletranslate.ios.translate.LocalizableFileProcessor;
import com.owino.mobiletranslate.ios.translate.impl.LocalizableFileProcessorImpl;
import java.io.File;
import java.util.List;
public class iOSTranslationProcessor implements TranslationProcessor {
    private LocalizableFileProcessor processor;
    public iOSTranslationProcessor() {
        processor = new LocalizableFileProcessorImpl();
    }
    @Override
    public void runTranslation(File outputFile, List<String> targetLanguages) throws Exception {
        for (String targetLanguage : targetLanguages) {
            var localizableTable = processor.extractLocalizableTableFromFile(outputFile);
            var translatedTable = processor.translateLocalizableTable(localizableTable, targetLanguage);
            processor.placeTranslatedTextInDestinationDir(translatedTable, targetLanguage);
        }
    }
}
