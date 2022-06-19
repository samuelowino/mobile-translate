package com.owino.mobiletranslate.ios.translate;

import com.owino.mobiletranslate.googletranslate.GoogleTranslator;
import com.owino.mobiletranslate.ios.translate.impl.LocalizableFileProcessorImpl;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import javax.annotation.PreDestroy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;

class LocalizableFileProcessorTest {

    private LocalizableFileProcessor localizableFileProcessor;
    private File testLocalizableFile;


    @BeforeEach
    void setUp() {
        localizableFileProcessor = new LocalizableFileProcessorImpl();
        var urlResource = ClassLoader.getSystemClassLoader().getResource("localizable-test.strings");
        var fileUrl = urlResource.getFile();
        testLocalizableFile = new File(fileUrl);

        assertThat(testLocalizableFile).isNotNull();
        assertThat(Files.exists(testLocalizableFile.toPath())).isTrue();
    }

    @Test
    void shouldGetLocalizableTableFromStringTest() {
        String lineOfLocalizable = "\"hello-page-title\" = \"Hello;\"";
        var localeTable = localizableFileProcessor.getLocalizableTableFromString(lineOfLocalizable);
        assertThat(localeTable).isNotNull();
        assertThat(localeTable.getKey()).isNotNull();
        assertThat(localeTable.getTranslatableResource()).isNotNull();
        assertThat(localeTable.getTranslatableResource()).isNotEmpty();
        assertThat(localeTable.getKey()).isNotEmpty();
        assertThat(localeTable.getKey().contains(" ")).isFalse();
        assertThat(localeTable.getTranslatableResource().contains(" ")).isFalse();
        assertThat(localeTable.getKey().contains("\"")).isFalse();
        assertThat(localeTable.getTranslatableResource().contains("\"")).isFalse();

        System.out.println("Table => " + localeTable);
    }

    @Test
    void extractLocalizableTableFromFile() {
        var localeTables = localizableFileProcessor.extractLocalizableTableFromFile(testLocalizableFile);

        assertThat(localeTables).isNotEmpty();
        assertThat(localeTables.size()).isEqualTo(9);
        assertThat(localeTables.get(0)).isNotNull();
        assertThat(localeTables.get(0).getKey()).isNotEmpty();
        assertThat(localeTables.get(0).getKey()).isNotNull();
        assertThat(localeTables.get(0).getTranslatableResource()).isNotNull();
        assertThat(localeTables.get(0).getTranslatableResource()).isNotEmpty();
        assertThat(localeTables.get(0).getKey()).isNotEmpty();
        assertThat(localeTables.get(0).getKey().contains(" ")).isFalse();
        assertThat(localeTables.get(0).getTranslatableResource().contains(" ")).isFalse();
        assertThat(localeTables.get(0).getKey().contains("\"")).isFalse();
        assertThat(localeTables.get(0).getTranslatableResource().contains("\"")).isFalse();

        System.out.println(localeTables);
    }

    @Test
    void shouldTranslateLocalizableTableTest() {
        GoogleTranslator translator = Mockito.mock(GoogleTranslator.class);

        Mockito.when(translator.getTranslatedText(ArgumentMatchers.anyString(), ArgumentMatchers.anyString()))
                .thenReturn("こんにちはあなたの人生を整理します");

        var localeTables = localizableFileProcessor.extractLocalizableTableFromFile(testLocalizableFile);

        var translatedTable = localizableFileProcessor.translateLocalizableTable(localeTables, "ja");

        assertThat(localeTables).isNotEmpty();
        assertThat(localeTables.size()).isEqualTo(9);
        assertThat(localeTables.get(0)).isNotNull();
        assertThat(localeTables.get(0).getKey()).isNotEmpty();
        assertThat(localeTables.get(0).getKey()).isNotNull();
        assertThat(localeTables.get(0).getTranslatableResource()).isNotNull();
        assertThat(localeTables.get(0).getTranslatableResource()).isNotEmpty();
        assertThat(localeTables.get(0).getKey()).isNotEmpty();
        assertThat(localeTables.get(0).getKey().contains(" ")).isFalse();
        assertThat(localeTables.get(0).getTranslatableResource().contains(" ")).isFalse();
        assertThat(localeTables.get(0).getKey().contains("\"")).isFalse();
        assertThat(localeTables.get(0).getTranslatableResource().contains("\"")).isFalse();

        System.out.println(translatedTable);

    }
}