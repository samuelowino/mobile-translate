package com.owino.mobiletranslate.common;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.util.Scanner;
import java.util.logging.Logger;
import com.owino.mobiletranslate.TranslateApplication;
import com.owino.mobiletranslate.enums.TargetOS;

public class RunnerInputReader {

    public static final Logger LOGGER = Logger.getLogger(TranslateApplication.class.getSimpleName());

    public static File getOutputFileFromPrompt(){
        LOGGER.severe("Enter the output file directory for the translated text....");

        var scanner = new Scanner(System.in);
        var filePath = scanner.nextLine();
        var outputFile = new File(filePath);

        boolean fileExists = Files.exists(outputFile.toPath(), new LinkOption[]{LinkOption.NOFOLLOW_LINKS});

        if (!fileExists) {
            LOGGER.severe("This output file does not exist");
            outputFile = getOutputFileFromPrompt();
        } else {
            LOGGER.info("Processing translation into output file [" + filePath + "]");
        }

        return outputFile;
    }

    public static TargetOS getTargetOSFromUser() {
        LOGGER.severe("What is the target mobile operating system for this localization? type in (A) for Android or (I) for iOS...");

        var scanner = new Scanner(System.in);

        var option = scanner.nextLine();

        switch (option){
            case "A":
                return TargetOS.ANDROID;
            case "I":
                return TargetOS.IOS;
            default:
                throw new AssertionError("Invalid OS option " + option);
        }
    }
}
