package org.apluscreators.googletranslatelabs.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Arrays;

@XmlRootElement
public class Resources {

    private Strings[] strings;

    public Strings[] getStrings() {
        return strings;
    }

    public void setStrings(Strings[] strings) {
        this.strings = strings;
    }

    @Override
    public String toString() {
        return "Resources{" +
                "strings=" + Arrays.toString(strings) +
                '}';
    }
}

