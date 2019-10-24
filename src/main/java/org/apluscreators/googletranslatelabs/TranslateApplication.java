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
            "ro", "sr", "sv", "ur", "ha", "pa", "te", "jv", "ml", "mr", "mn", "mk", "my", "pl",
            "hy", "iw", "id", "in", "km", "ky", "ka",
            "no", "nn", "nl", "nb", "ne", "nb", "ar",
            "be", "bg", "bs", "ca", "cs", "da", "de", "es", "et", "el", "uk", "vi",
            "pt", "hi", "hr", "hu", "lv", "ms", "th",
            "sw", "sq", "it", "is", "fi"
            ,"ja", "ru", "ga", "zh", "si", "sk", "sl",
            "ta", "tr", "zu", "fa", "gl",
            "ar", "be", "bg", "bs", "ca", "cs",
            "da", "de", "es", "et", "el",
            "uk", "vi", "pt", "hi", "hr", "hu",
            "lv", "ms", "th", "sw", "sq",
            "it", "is", "lo", "fil", "fr"
            ,"ru", "ga", "ko", "zh", "af", "am", "az", "bn",
            "si", "sk", "sl", "ta", "tr", "zu", "fa", "gl", "ja"
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
