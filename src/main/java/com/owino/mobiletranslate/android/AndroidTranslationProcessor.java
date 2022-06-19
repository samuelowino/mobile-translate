package com.owino.mobiletranslate.android;

import java.io.File;
import java.util.List;
import com.owino.mobiletranslate.android.xml.XmlParserReader;
import com.owino.mobiletranslate.common.TranslationProcessor;

public class AndroidTranslationProcessor implements TranslationProcessor {
    public void runTranslation(File outputFile, List<String> targetLanguages){
        new XmlParserReader().executeXmlAndTranslationParser(outputFile, targetLanguages);
    }
}
