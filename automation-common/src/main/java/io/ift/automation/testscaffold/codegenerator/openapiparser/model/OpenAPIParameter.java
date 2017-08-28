package io.ift.automation.testscaffold.codegenerator.openapiparser.model;

/**
 * Created by patrick on 15/4/22.
 *
 * @version $Id$
 */


public class OpenAPIParameter {

    private String name;
    private String type;
    private boolean isMandatory;
    private String defaultValue;
    private String comments;
    //service category name , not used yet
    private String scName;


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

    public boolean isMandatory() {
        return isMandatory;
    }

    public void setMandatory(boolean isMandatory) {
        this.isMandatory = isMandatory;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getScName() {
        return scName;
    }

    public void setScName(String scName) {
        this.scName = scName;
    }
}
