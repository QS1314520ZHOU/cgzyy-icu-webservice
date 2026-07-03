package com.digixmed.icu.pojo;

import org.dom4j.Element;
import org.dom4j.tree.DefaultElement;
import org.springframework.util.StringUtils;

public class CustomElement {
    public static final String REQUEST_ELE_NAME = "Request";
    public static final String HEADER_ELE_NAME = "Header";
    public static final String BODY_ELE_NAME = "Body";
    Element rootElement;

    public static CustomElement getInstance() {
        return new CustomElement();
    }

    public CustomElement(String key) {
        this.rootElement = new DefaultElement(key);
    }

    public CustomElement() {
        this(REQUEST_ELE_NAME);
    }

    public void addHeaderElement(String sourceSystem, String messageId) {
        DefaultElement defaultElement = new DefaultElement(HEADER_ELE_NAME);
        DefaultElement defaultElement2 = new DefaultElement("SourceSystem");
        setElementText(defaultElement2, sourceSystem);
        DefaultElement defaultElement3 = new DefaultElement("MessageID");
        setElementText(defaultElement3, messageId);
        defaultElement.add(defaultElement2);
        defaultElement.add(defaultElement3);
        if (this.rootElement == null) {
            this.rootElement = new DefaultElement(REQUEST_ELE_NAME);
        }
        this.rootElement.add(defaultElement);
    }

    public void addBodyElement(String qName) {
        DefaultElement defaultElement = new DefaultElement(BODY_ELE_NAME);
        if (!StringUtils.isEmpty(qName)) {
            defaultElement.add(new DefaultElement(qName));
        }
        this.rootElement.add(defaultElement);
    }

    public void addElement(Element element, String key, String value) {
        if (element == null) {
            return;
        }
        DefaultElement defaultElement = new DefaultElement(key);
        defaultElement.setText(value);
        element.add(defaultElement);
    }

    public Element getBodyElement() {
        return this.rootElement.element(BODY_ELE_NAME);
    }

    public Element getHeaderElement() {
        return this.rootElement.element(HEADER_ELE_NAME);
    }

    public void setElementText(Element element, String value) {
        if (StringUtils.isEmpty(value)) {
            value = "";
        }
        element.setText(value);
    }

    public String getElementAsXmlStr() {
        return this.rootElement.asXML();
    }
}