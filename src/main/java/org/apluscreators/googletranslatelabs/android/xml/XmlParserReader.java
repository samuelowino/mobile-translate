package org.apluscreators.googletranslatelabs.android.xml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import org.apluscreators.googletranslatelabs.android.model.Resources;
import org.apluscreators.googletranslatelabs.android.model.String;
import org.apluscreators.googletranslatelabs.android.translate.GoogleTranslateFactory;

public class XmlParserReader {

    private static final Logger LOGGER = Logger.getLogger(XmlParserReader.class.getSimpleName());

    public void executeXmlAndTranslationParser(File outputFile, List<java.lang.String> targetLanguages) {

        LOGGER.info("Translating " + targetLanguages.size() + " locales to target output file " + outputFile.getAbsolutePath());

        for (java.lang.String targetLanguage : targetLanguages) {

            Resources untranslatedResources = fromXml(outputFile);

            GoogleTranslateFactory translateFactory = new GoogleTranslateFactory(untranslatedResources);
            Resources translatedResources = translateFactory.getTranslatedResources(targetLanguage);

            for (String string : translatedResources.getStrings()) {
                LOGGER.info(string.toString());
            }

            toXml(translatedResources,targetLanguage + ".xml");
        }
    }


    public void toXml(Resources contentRoot,java.lang.String outputFileName) {
        try {
            JAXBContext contextObj = JAXBContext.newInstance(Resources.class);
            Marshaller marshallerObj = contextObj.createMarshaller();
            marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshallerObj.marshal(contentRoot, new FileOutputStream(outputFileName));
        } catch (JAXBException | FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public Resources fromXml(File outputFile) {
        try {

            JAXBContext jaxbContext = JAXBContext.newInstance(Resources.class);

            Unmarshaller jaxbContextUnmarshaller = jaxbContext.createUnmarshaller();
            Resources resources = (Resources) jaxbContextUnmarshaller.unmarshal(outputFile);

            System.out.println("Resources | " + resources);

            return resources;

        } catch (JAXBException e) {
            e.printStackTrace();

            return null;
        }
    }

    private String[] getContentEntries() {
        String[] contentEntries = {
                new String("app_name", "app-name"),

        };

        return contentEntries;
    }


}
