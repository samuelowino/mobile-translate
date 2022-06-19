package com.owino.mobiletranslate.android.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Arrays;

@XmlRootElement
public class Resources {

    private String[] strings;

    @XmlElement
    public String[] getStrings() {
        return strings;
    }

    public void setStrings(String[] strings) {
        this.strings = strings;
    }

    @Override
    public java.lang.String toString() {
        return "Resources{" +
                "strings=" + Arrays.toString(strings) +
                '}';
    }
}

