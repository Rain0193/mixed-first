package io.ift.automation.testscaffold.codegenerator.apis.harmodel;

public class HarQueryString {
    private  String name;
    private  String value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "HarQueryString{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
