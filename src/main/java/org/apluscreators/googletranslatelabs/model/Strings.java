package org.apluscreators.googletranslatelabs.model;

import com.sun.xml.internal.txw2.annotation.XmlElement;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

/**
 * <string name="app_name">Life Planner™</string>
 */

@XmlElement
public class Strings {

    private String name;

    private String content;

    public Strings() {
    }

    public Strings(String name, String content) {
        this.name = name;
        this.content = content;
    }

    @XmlAttribute
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlValue
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Strings{" +
                "name='" + name + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
