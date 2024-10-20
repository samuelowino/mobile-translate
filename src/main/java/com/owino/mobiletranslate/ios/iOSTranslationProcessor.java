package com.owino.mobiletranslate.ios;

import com.owino.mobiletranslate.common.TranslationProcessor;
import com.owino.mobiletranslate.ios.translate.LocalizableFileProcessor;
import com.owino.mobiletranslate.ios.translate.impl.LocalizableFileProcessorImpl;
import com.owino.mobiletranslate.rest.payload.IOSMessage;
import com.owino.mobiletranslate.rest.payload.TranslationResponse;

import java.io.File;
import java.util.*;

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
    public TranslationResponse runTranslation(List<IOSMessage> input, List<String> targetLanguages){
        Map<String,Map<String,String>> allTranslations =new HashMap<>();
        for(String targetLanguage: targetLanguages){
            Map<String,String > localeTranslated=new HashMap<>();
            List<IOSMessage> languageTranslations = new ArrayList<>();
            languageTranslations=processor.translateLocalizable(input,targetLanguage);
            for (IOSMessage x : languageTranslations) {
                localeTranslated.put(x.key(), x.content());
            }
            allTranslations.put(targetLanguage,localeTranslated);
        }
        return  new TranslationResponse(
                "TRANSLATION",
                "IOS",
                allTranslations
        );

    }
}
