package io.ift.automation.testscaffold.codegenerator.webui;

import com.google.common.base.MoreObjects;

/**
 * Created by patrick on 15/6/1.
 *
 * @version $Id$
 */


public class WebElementDescription {
    private String elementName;
    private String variableName;
    private String elementLocation;
    private String pageName;
    private String elementType;

    public String getElementName() {
        return elementName;
    }

    public void setElementName(String elementName) {
        this.elementName = elementName;
    }

    public String getElementLocation() {
        return elementLocation;
    }

    public void setElementLocation(String elementLocation) {
        this.elementLocation = elementLocation;
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }


    public String getElementType() {
        return elementType;
    }

    public void setElementType(String elementType) {
        this.elementType = elementType;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("elementName", elementName)
                .add("elementLocation", elementLocation)
                .add("pageName", pageName)
                .add("elementType", elementType)
                .add("variableName",variableName)
                .toString();
    }

    public String getVariableName() {
        return variableName;
    }

    public void setVariableName(String variableName) {
        this.variableName = variableName;
    }
}
