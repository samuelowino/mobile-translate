package com.owino.mobiletranslate.ios.translate;

import com.owino.mobiletranslate.googletranslate.GoogleTranslator;
import com.owino.mobiletranslate.ios.model.LocalizableTable;
import com.owino.mobiletranslate.ios.translate.impl.LocalizableFileProcessorImpl;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Collections;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;
@Slf4j
class LocalizableFileProcessorTest {
    @Mock
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
        assertThat(localeTable.getKey()).isNotEmpty();
        System.out.println("Table => " + localeTable);
    }

    @Test
    void extractLocalizableTableFromFile() {
        var localeTables = localizableFileProcessor.extractLocalizableTableFromFile(testLocalizableFile);
        log.debug("Locale Tables " + localeTables);
        assertThat(localeTables).isNotEmpty();
        assertThat(localeTables.size()).isEqualTo(9);
        assertThat(localeTables.get(0)).isNotNull();
        assertThat(localeTables.get(0).getKey()).isNotEmpty();
        assertThat(localeTables.get(0).getKey()).isNotNull();
        assertThat(localeTables.get(0).getKey()).isNotEmpty();
        System.out.println(localeTables);
    }

    @Test
    void shouldTranslateLocalizableTableTest() {
        var translator = Mockito.mock(GoogleTranslator.class);
        var processor = Mockito.mock(LocalizableFileProcessor.class);

        Mockito.when(translator.getTranslatedBytes(ArgumentMatchers.anyString(), ArgumentMatchers.anyString()))
                .thenReturn("こんにちはあなたの人生を整理します".getBytes(StandardCharsets.UTF_16));
        var localeTables = localizableFileProcessor.extractLocalizableTableFromFile(testLocalizableFile);

        Mockito.when(processor.translateLocalizableTable(localeTables, "ja"))
                .thenReturn(Collections.singletonList(new LocalizableTable(ArgumentMatchers.anyString(), "こんにちはあなたの人生を整理します")));

        assertThat(localeTables).isNotEmpty();
        assertThat(localeTables.size()).isEqualTo(9);
        assertThat(localeTables.get(0)).isNotNull();
        assertThat(localeTables.get(0).getKey()).isNotEmpty();
        assertThat(localeTables.get(0).getKey()).isNotNull();
        assertThat(localeTables.get(0).getKey()).isNotEmpty();
        assertThat(localeTables.get(0).getTranslatableResource()).isNotEmpty();
    }

    @Test
    void shouldGenerateLocalizableDestinationFileTest() throws IOException {
        var locale = "en";
        var file = localizableFileProcessor.generateLocalizableDestinationFile("en");
        assertThat(file).isNotNull();
        assertThat(file.getName()).isEqualTo(locale + ".txt");
    }
}