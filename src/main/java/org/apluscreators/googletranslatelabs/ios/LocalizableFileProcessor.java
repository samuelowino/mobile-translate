package org.apluscreators.googletranslatelabs.ios;

import java.io.File;
import java.util.List;
import org.apluscreators.googletranslatelabs.ios.model.LocalizableTable;

public interface LocalizableFileProcessor {
    /**
     * Extracts the key-values from XCode Localizable.string file
     *
     * @param localizableFile File
     * @return List
     */
    List<LocalizableTable> extractLocalizableTableFromFile(File localizableFile);

    /**
     * Runs translation for each string in the localizable table and returns a translated set of localizable values
     *
     * @param originalLocalizableValues
     * @param locale String such as en, fr ...
     * @return List of translated localizables
     */
    List<LocalizableTable> translateLocalizableTable(List<LocalizableTable> originalLocalizableValues, String locale);
}
