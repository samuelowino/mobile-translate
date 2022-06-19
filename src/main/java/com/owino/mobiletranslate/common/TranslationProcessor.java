package com.owino.mobiletranslate.common;

import java.io.File;
import java.util.List;

public interface TranslationProcessor {
    void runTranslation(File outputFile, List<String> targetLanguages);
}
