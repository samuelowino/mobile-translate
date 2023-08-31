package com.owino.mobiletranslate.freighter.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class IOUtils {

    public static String readFileContents(File file) throws FileNotFoundException {
        String contents = null;
        String fileUrl = IOUtils.loadResourceFileUrl(file.getName()).toExternalForm();

        File resourceFile = new File("/home/samuel/Documents/projects/android-auto-task/src/main/resources/files/" + file.getName());

        FileInputStream fis = new FileInputStream(resourceFile);
        InputStreamReader inputStreamReader =
                new InputStreamReader(fis, StandardCharsets.UTF_8);
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(inputStreamReader)) {
            String line = reader.readLine();
            while (line != null) {
                stringBuilder.append(line).append('\n');
                line = reader.readLine();
            }
        } catch (IOException e) {
            // Error occurred when opening raw file for reading.
        } finally {
             contents = stringBuilder.toString();
        }

        return contents;
    }

    public static String readExternalFileContents(File file) throws FileNotFoundException {
        String contents = null;
        FileInputStream fis = new FileInputStream(file);
        InputStreamReader inputStreamReader =
                new InputStreamReader(fis, StandardCharsets.UTF_8);
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(inputStreamReader)) {
            String line = reader.readLine();
            while (line != null) {
                stringBuilder.append(line).append('\n');
                line = reader.readLine();
            }
        } catch (IOException e) {
            // Error occurred when opening raw file for reading.
        } finally {
            contents = stringBuilder.toString();
        }

        return contents;
    }

    public static void writeStringToFile(File file, String contents) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter( new FileWriter(file,false));
        bufferedWriter.write(contents);
        bufferedWriter.close();
    }

    public static URL loadResourceFileUrl(String fileName){
        return ClassLoader.getSystemClassLoader().getResource("files/" + fileName);
    }
}
