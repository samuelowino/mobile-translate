package org.apluscreators.googletranslatelabs;

import org.apluscreators.googletranslatelabs.xml.XmlParserReader;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TranslateApplication {

    private static final List<String> languages = Arrays.asList(
            "ja", "nb", "nl", "uk", "hi", "da",
            "ca", "hr", "et", "hu", "kk", "lv",
            "in", "ko", "vi", "th", "sq", "ar",
            "be", "fil", "bg", "bs", "cs", "el",
            "es", "it", "zh", "vi", "th", "pt",
            "pl", "no", "nn", "nl", "nb", "is",
            "fr", "et", "fi", "ms", "ne", "fa",
            "af", "am", "hy", "az", "bn", "my",
            "et", "fi", "gl", "ka", "iw", "hi",
            "hu", "is", "id", "kn", "km", "ky",
            "lo", "lv", "mk", "ms", "ml", "mr",
            "mn", "ne", "no", "fa", "pl", "pa",
            "ro", "rm", "ru", "sr", "si", "sk",
            "sl", "es", "sw", "sv", "ta", "tr",
            "zu"
    );


    public static void main(String[] args) {

        List<String> distinctLanguages = languages.stream()
                .distinct()
                .peek(System.out::println)
                .collect(Collectors.toList());

        for (String language : distinctLanguages) {
            XmlParserReader xmlParserReader = new XmlParserReader();
            xmlParserReader.executeXmlAndTranslationParser(language);
        }



    }
}
