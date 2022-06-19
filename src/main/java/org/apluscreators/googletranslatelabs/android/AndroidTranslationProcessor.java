package org.apluscreators.googletranslatelabs.android;

import java.io.File;
import java.util.List;
import org.apluscreators.googletranslatelabs.android.xml.XmlParserReader;
import org.apluscreators.googletranslatelabs.common.TranslationProcessor;

public class AndroidTranslationProcessor implements TranslationProcessor {
    public void runTranslation(File outputFile, List<String> targetLanguages){
        new XmlParserReader().executeXmlAndTranslationParser(outputFile, targetLanguages);
    }
}
