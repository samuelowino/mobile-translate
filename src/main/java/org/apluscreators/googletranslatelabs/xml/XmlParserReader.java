package org.apluscreators.googletranslatelabs.xml;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apluscreators.googletranslatelabs.model.Strings;
import org.apluscreators.googletranslatelabs.model.Resources;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class XmlParserReader {

    public void executeParser() {
        Resources resources = new Resources();

        Strings[] stringsEntries = getContentEntries();

        resources.setStrings(stringsEntries);

        System.out.println("Content Root | " + resources);

        toXml(resources);
    }


    public void toXml(Resources contentRoot){
        try {
            JAXBContext contextObj = JAXBContext.newInstance(Resources.class);
            Marshaller marshallerObj = contextObj.createMarshaller();
            marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshallerObj.marshal(contentRoot, new FileOutputStream("output.xml"));

        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    private Strings[] getContentEntries() {
        Strings[] contentEntries = {
                new Strings("app_name","Learn Physics"),
        };

        return contentEntries;
    }


}
