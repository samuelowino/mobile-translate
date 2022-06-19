package org.apluscreators.googletranslatelabs.common;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.apluscreators.googletranslatelabs.TranslateApplication;

public class LanguagesDirectory {

    public static final Logger LOGGER = Logger.getLogger(TranslateApplication.class.getSimpleName());

    private final List<String> languages = Arrays.asList(
            "ha", "pl", "am", "ar", "az", "be", "bg", "bn", "bs", "fr", "ga",
            "ca", "cs", "da", "es", "et", "fa", "fi", "gl",
            "it", "iw", "ja", "jv", "ka", "km", "ko", "ky",
            "my", "nb", "ne", "nl", "nn", "no", "pa", "ro", "ru", "si", "sk", "sl", "sq", "sr",
            "sv", "sw", "ta", "te", "th", "tr", "vi", "zh", "zu", "kn", "kk",
            "af", "de", "fil", "hi", "hr", "hu", "hy", "id", "in", "lo", "lv", "mk", "ml", "mn", "pt", "uk", "ur", "ms", "is", "el", "mr"
    );

    public List<String> findUniqueListOfLocales() {

        LOGGER.info("Cleaning up locales list...");

        return languages.stream()
                .distinct()
                .peek(System.out::println)
                .collect(Collectors.toList());
    }
}
