package io.ift.automation.testscaffold.codegenerator.apis.harmodel;

import java.util.List;

public class Log {

    private String version;
    private HarCreator creator;
    private List<HarPage> pages;
    private HarBrowser browser;
    private List<HarEntry> entries;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }


    public List<HarPage> getPages() {
        return pages;
    }

    public void setPages(List<HarPage> pages) {
        this.pages = pages;
    }


    public List<HarEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<HarEntry> entries) {
        this.entries = entries;
    }

    public HarCreator getCreator() {
        return creator;
    }

    public void setCreator(HarCreator creator) {
        this.creator = creator;
    }

    public HarBrowser getBrowser() {
        return browser;
    }

    public void setBrowser(HarBrowser browser) {
        this.browser = browser;
    }
}
