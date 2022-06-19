package com.owino.mobiletranslate;

import com.owino.mobiletranslate.android.AndroidTranslationProcessor;
import com.owino.mobiletranslate.common.LanguagesDirectory;
import com.owino.mobiletranslate.common.RunnerInputReader;
import com.owino.mobiletranslate.enums.TargetOS;
import com.owino.mobiletranslate.ios.iOSTranslationProcessor;

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
