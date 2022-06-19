package org.apluscreators.googletranslatelabs;

import org.apluscreators.googletranslatelabs.android.AndroidTranslationProcessor;
import org.apluscreators.googletranslatelabs.common.LanguagesDirectory;
import org.apluscreators.googletranslatelabs.common.RunnerInputReader;
import org.apluscreators.googletranslatelabs.enums.TargetOS;
import org.apluscreators.googletranslatelabs.ios.iOSTranslationProcessor;

public class TranslateApplication {

    public static void main(String[] args) {

        var targetOS = RunnerInputReader.getTargetOSFromUser();

        var outputFile = RunnerInputReader.getOutputFileFromPrompt();

        var distinctLanguages = new LanguagesDirectory().findUniqueListOfLocales();

        if (targetOS == TargetOS.ANDROID) {
            new AndroidTranslationProcessor().runTranslation(outputFile, distinctLanguages);
        } else if (targetOS == TargetOS.IOS) {
            new iOSTranslationProcessor().runTranslation(outputFile, distinctLanguages);
        }

    }
}
