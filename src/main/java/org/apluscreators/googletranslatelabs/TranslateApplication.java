package org.apluscreators.googletranslatelabs;

import org.apluscreators.googletranslatelabs.xml.XmlParserReader;

import java.util.Arrays;
import java.util.List;

public class TranslateApplication {

    private static final List<String> languages = Arrays.asList(
            "ja","nb","nl","uk","hi","da",
            "ca","hr","et","hu","kk","lv",
            "in","ko","vi","th","sq"
    );


    public static void main(String[] args) {

        for (String language : languages) {
            XmlParserReader xmlParserReader = new XmlParserReader();
            xmlParserReader.executeXmlAndTranslationParser(language);
        }

    }
}
