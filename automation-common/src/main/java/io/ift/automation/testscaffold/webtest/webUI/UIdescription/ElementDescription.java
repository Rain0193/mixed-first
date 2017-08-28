package io.ift.automation.testscaffold.webtest.webUI.UIdescription;

import io.ift.automation.helpers.StringHelper;
import io.ift.automation.helpers.XMLDocHelper;
import io.ift.automation.helpers.YAMLHelper;
import com.google.common.base.MoreObjects;
import com.google.common.collect.Maps;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ByIdOrName;
import org.openqa.selenium.support.How;

import java.util.List;
import java.util.Map;

/**
 * Created by patrick on 15/3/10.
 *
 * @version $Id$
 */


public class ElementDescription {
    private String name;
    private String type;
    private String value;
    private String action;

    public ElementDescription(String name, String type, String value) {
        this.name = name;
        this.type = type;
        this.value = value;
    }

    public ElementDescription() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("name", name)
                .add("type", type)
                .add("value", value)
                .toString();
    }

    //convert existing xml or yaml to element description
    @SuppressWarnings("unchecked")
    public static Map<String,ElementDescription> getElementDescriptionFromResource(String path) {

        if (path.lastIndexOf(".xml") > 0) {
            Map<String, String> elementMap = XMLDocHelper.build(path).getNameAndTextForAllElements();
            return convertToElementDescriptionMap(elementMap);
        } else if (path.lastIndexOf(".yaml") > 0) {
            try {
                return YAMLHelper.parseAsMap(path, ElementDescription.class, "name");
            } catch (Exception e) {
                Map<String,ElementDescription> elements = Maps.newConcurrentMap();

                Map<String, List<Map<String, String>>> map = YAMLHelper.parseAsMap(path);
                for (Map.Entry<String, List<Map<String, String>>> entry : map.entrySet()) {
                    for (Map<String, String> values : entry.getValue()) {
                        ElementDescription d = new ElementDescription(entry.getKey(),
                                values.get("type").toLowerCase(), values.get("value").toLowerCase());
                        elements.put(entry.getKey(), d);
                    }
                }
                return elements;
            }
        } else {
            throw new RuntimeException(path + " is not a valid resource file");
        }

    }

    private static Map<String,ElementDescription> convertToElementDescriptionMap(Map<String, String> elementMap) {
        Map<String,ElementDescription> elements = Maps.newConcurrentMap();
        for (Map.Entry<String, String> entry : elementMap.entrySet()) {
            ElementDescription d = new ElementDescription();
            d.setName(entry.getKey());
            d.setValue(entry.getValue());
            for (How how : How.values()) {
                if (entry.getValue().toLowerCase().startsWith(how.name().toLowerCase())) {
                    d.setType(how.name().toLowerCase());
                    break;
                }
            }

            if (StringHelper.isEmpty(d.getType())) d.setType(How.XPATH.name().toLowerCase());
            elements.put(entry.getKey(),d);
        }
        return elements;
    }


    public By buildBy() {
        if (type.equalsIgnoreCase(How.ID.name())) return By.id(value);
        if (type.equalsIgnoreCase(How.CLASS_NAME.name())) return By.className(value);
        if (type.equalsIgnoreCase(How.CSS.name())) return By.cssSelector(value);
        if (type.equalsIgnoreCase(How.ID_OR_NAME.name())) return new ByIdOrName(value);
        if (type.equalsIgnoreCase(How.LINK_TEXT.name())) return By.linkText(value);
        if (type.equalsIgnoreCase(How.NAME.name())) return By.name(value);
        if (type.equalsIgnoreCase(How.PARTIAL_LINK_TEXT.name())) return By.partialLinkText(value);
        if (type.equalsIgnoreCase(How.TAG_NAME.name())) return By.tagName(value);
        if (type.equalsIgnoreCase(How.XPATH.name())) return By.xpath(value);
        throw new IllegalArgumentException("Cannot determine how to locate element ");
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
