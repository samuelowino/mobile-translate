package com.owino.mobiletranslate.common;

import com.owino.mobiletranslate.enums.Workflow;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.util.Scanner;
import java.util.logging.Logger;
import com.owino.mobiletranslate.TranslateApplication;
import com.owino.mobiletranslate.enums.TargetOS;

public class RunnerInputReader {

    public static final Logger LOGGER = Logger.getLogger(TranslateApplication.class.getSimpleName());

    public static File getOutputFileFromPrompt() {
        LOGGER.severe("Enter the root file with pre-translation text....");

        var scanner = new Scanner(System.in);
        var filePath = scanner.nextLine();
        var outputFile = new File(filePath);

        boolean fileExists = Files.exists(outputFile.toPath(), LinkOption.NOFOLLOW_LINKS);

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

        switch (option) {
            case "A":
                return TargetOS.ANDROID;
            case "I":
                return TargetOS.IOS;
            default:
                throw new AssertionError("Invalid OS option " + option);
        }
    }

    public static File requestRootLocalizableFile() {
        LOGGER.severe("Enter path to root localizable file...");

        var filePath = new Scanner(System.in).nextLine();
        var localizableFile = new File(filePath);

        if (Files.exists(localizableFile.toPath(), LinkOption.NOFOLLOW_LINKS)){
            return localizableFile;
        } else {
            LOGGER.severe("This localizable file does not exist");
            localizableFile = requestRootLocalizableFile();
        }
        return localizableFile;
    }

    public static String requestRootDestinationFolder() {
        LOGGER.severe("Enter the folder path where you would like us to store the final translated files...");

        var folderPath = new Scanner(System.in).nextLine();

        var localizableFolder = new File(folderPath);

        if (Files.exists(localizableFolder.toPath(), LinkOption.NOFOLLOW_LINKS)){
            return folderPath;
        } else {
            LOGGER.severe("This folder does not exist");
            folderPath = requestRootDestinationFolder();
        }
        return folderPath;
    }

    public static File requestLocalizableFileForValidation(){
        LOGGER.severe("Enter path to localizable file...");

        var filePath = new Scanner(System.in).nextLine();
        var localizableFile = new File(filePath);

        if (Files.exists(localizableFile.toPath(), LinkOption.NOFOLLOW_LINKS)){
            return localizableFile;
        } else {
            LOGGER.severe("This localizable file does not exist");
            localizableFile = requestLocalizableFileForValidation();
        }

        return localizableFile;
    }

    public static Workflow getWorkflowFromUser() {
        LOGGER.severe("Which workflow would you like to run? type in (L) for Translation or (V) for localized syntax analysis...");
        var scanner = new Scanner(System.in);

        var option = scanner.nextLine();

        switch (option) {
            case "L":
                return Workflow.TRANSLATION;
            case "V":
                return Workflow.VALIDATION;
            default:
                throw new AssertionError("Invalid option " + option);
        }
    }
}
