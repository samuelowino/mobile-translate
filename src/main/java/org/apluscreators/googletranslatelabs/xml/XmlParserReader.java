package org.apluscreators.googletranslatelabs.xml;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apluscreators.googletranslatelabs.model.String;
import org.apluscreators.googletranslatelabs.model.Resources;
import org.apluscreators.googletranslatelabs.translate.GoogleTranslateFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class XmlParserReader {

    public void executeXmlAndTranslationParser(java.lang.String targetLanguage) {

       Resources untranslatedResources = fromXml();

        GoogleTranslateFactory translateFactory = new GoogleTranslateFactory(untranslatedResources);
        Resources translatedResources = translateFactory.getTranslatedResources(targetLanguage);

        for (String string : translatedResources.getStrings()) {
            System.out.println(string);
        }

        toXml(translatedResources,targetLanguage + ".xml");
    }


    public void toXml(Resources contentRoot,java.lang.String outputFileName) {
        try {
            JAXBContext contextObj = JAXBContext.newInstance(Resources.class);
            Marshaller marshallerObj = contextObj.createMarshaller();
            marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshallerObj.marshal(contentRoot, new FileOutputStream(outputFileName));

        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public Resources fromXml() {
        try {

            File file = new File("C:/Users/hp/projects/Google-Translate-Script/output.xml");
            JAXBContext jaxbContext = JAXBContext.newInstance(Resources.class);

            Unmarshaller jaxbContextUnmarshaller = jaxbContext.createUnmarshaller();
            Resources resources = (Resources) jaxbContextUnmarshaller.unmarshal(file);

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
