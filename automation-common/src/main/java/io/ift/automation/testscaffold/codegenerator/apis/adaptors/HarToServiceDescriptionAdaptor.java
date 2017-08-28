package io.ift.automation.testscaffold.codegenerator.apis.adaptors;


import io.ift.automation.helpers.JSONHelper;
import io.ift.automation.testscaffold.apitest.ServiceDescription;
import io.ift.automation.testscaffold.codegenerator.apis.harmodel.Har;
import io.ift.automation.testscaffold.codegenerator.apis.harmodel.HarEntry;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by patrick on 15/11/23.
 */
public class HarToServiceDescriptionAdaptor implements ServiceDescriptionAdaptor {

    private Har har;
    private String resourcePathExp;

    public static HarToServiceDescriptionAdaptor build(String harContent,String resourcePathExp){
       return  new HarToServiceDescriptionAdaptor(harContent,resourcePathExp);
    }

    public HarToServiceDescriptionAdaptor(String harContent,String resourcePathExp){
        this.har = JSONHelper.toBean(harContent, Har.class);
        this.resourcePathExp = resourcePathExp;
    }

    @Override
    public ServiceDescription toServiceDescription() {
        List<HarEntry> entries = har.getLog()
                .getEntries()
                .stream()
                .filter(harEntry -> harEntry.getRequest().getUrl()
                .contains(this.resourcePathExp)).collect(Collectors.toList());
        if(entries.size()==0) throw new RuntimeException("there is no matched resource URL in HAR file for "+ resourcePathExp);
        ServiceDescription sd = new ServiceDescription();
        sd.setMethod(entries.get(0).getRequest().getMethod());
        sd.setContentType(entries.get(0).getRequest().getContextType());
        sd.setAcceptType(entries.get(0).getRequest().getAccept());
        sd.setQueryParameters(entries.get(0).getRequest().getQueryParams());
        sd.setResourceURL(entries.get(0).getRequest().getResourceUrl());
        return sd;
    }

    public Har getHar() {
        return har;
    }

    public void setHar(Har har) {
        this.har = har;
    }
}
