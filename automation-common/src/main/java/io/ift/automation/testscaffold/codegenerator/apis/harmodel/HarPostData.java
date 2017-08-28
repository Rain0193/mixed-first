package io.ift.automation.testscaffold.codegenerator.apis.harmodel;

import java.util.List;

/**
 * Created by patrick on 15/11/23.
 */
public class HarPostData {
    private String mimeType;
    private String text;
    private List<HarHeader> params;

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<HarHeader> getParams() {
        return params;
    }

    public void setParams(List<HarHeader> params) {
        this.params = params;
    }

    @Override
    public String toString() {
        return "HarPostData{" +
                "mimeType='" + mimeType + '\'' +
                ", text='" + text + '\'' +
                ", params=" + params +
                '}';
    }
}
