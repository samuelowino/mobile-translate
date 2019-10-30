package org.apluscreators.googletranslatelabs;


import org.apluscreators.googletranslatelabs.xml.XmlParserReader;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class TranslateApplication {

    private static final Logger LOGGER = Logger.getLogger(TranslateApplication.class.getName());

    private static final List<String> languages = Arrays.asList(
            "ha", "pl", "af", "am", "ar", "az", "be", "bg", "bn", "bs", "fr", "ga", "hi", "hu", "id", "is",
            "ca", "cs", "da", "de", "el", "es", "et", "fa", "fi", "fil", "gl", "hr", "hy", "in",
            "it", "iw", "ja", "jv", "ka", "km", "ko", "ky", "lo", "lv", "mk", "ml", "mn", "mr", "ms",
            "my", "nb", "ne", "nl", "nn", "no", "pa", "pt", "ro", "ru", "si", "sk", "sl", "sq", "sr",
            "sv", "sw", "ta", "te", "th", "tr", "uk", "ur", "vi", "zh", "zu", "kn", "kk"
    );

    private static final Set<String> uniqueLanguagesList = new HashSet<>(languages);

    public static void main(String[] args) {

        checkForDuplicates(languages);

        List<String> distinctLanguages = uniqueLanguagesList.stream()
                .distinct()
                .peek(System.out::println)
                .collect(Collectors.toList());

        for (String language : distinctLanguages) {
            XmlParserReader xmlParserReader = new XmlParserReader();
            xmlParserReader.executeXmlAndTranslationParser(language);
        }

    }

    public static void checkForDuplicates(List<String> languages) {
        LOGGER.info("Original List" + languages);
        LOGGER.info("Unique List " + uniqueLanguagesList);
    }
}
