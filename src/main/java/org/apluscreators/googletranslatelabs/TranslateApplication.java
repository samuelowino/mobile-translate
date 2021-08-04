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
            "ha", "pl", "am", "ar", "az", "be", "bg", "bn", "bs", "fr", "ga",
            "ca", "cs", "da", "es", "et", "fa", "fi", "gl",
            "it", "iw", "ja", "jv", "ka", "km", "ko", "ky",
            "my", "nb", "ne", "nl", "nn", "no", "pa", "ro", "ru", "si", "sk", "sl", "sq", "sr",
            "sv", "sw", "ta", "te", "th", "tr", "vi", "zh", "zu", "kn", "kk",
            "af","de", "fil","hi","hr", "hu","hy","id","in","lo","lv","mk","ml","mn","pt","uk","ur","ms","is","el","mr"
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
