package io.ift.automation.testscaffold.apitest;

import com.alibaba.fastjson.JSON;
import com.beust.jcommander.internal.Lists;
import io.ift.automation.helpers.FileHelper;
import io.ift.automation.testscaffold.codegenerator.apis.adaptors.HarToServiceDescriptionAdaptor;
import com.google.common.collect.Maps;
import org.springframework.http.HttpMethod;

import java.util.List;
import java.util.Map;

/**
 * 类的说明: define service
 *
 * @author wuke
 * @version $Id$
 * @since 2.0
 */

public class ServiceDescription extends BaseJsonEntity {

    private List<String> queryParameters=Lists.newArrayList();
    private List<String> pathParameters= Lists.newArrayList();
    private Map<String, String> headers = Maps.newConcurrentMap();
    private String resourceURL;
    private String method;
    private String bodyClass;
    private String body;
    private String contentType;
    private String apiDomainName;
    private String responseSample;
    /**
     * Load  service definition file
     * @param path
     * @return
     */
    public static ServiceDescription loadServiceDescriptionFromFile(String path){

        return JSON.parseObject(FileHelper.readFileToString(path), ServiceDescription.class);
    }

    /**
     * extract the given resource url to a service description
     * @param harPath
     * @param resourceURLExp
     * @return
     */
    public static ServiceDescription loadServiceDescriptionFromFile(String harPath,String resourceURLExp){

       return HarToServiceDescriptionAdaptor
               .build(FileHelper.readFileToString(harPath),resourceURLExp)
               .toServiceDescription();
    }

    /**
     * load service definition from String
     * @param serviceDescription
     * @return
     */
    public static ServiceDescription loadServiceDescription(String serviceDescription){
        return JSON.parseObject(serviceDescription,ServiceDescription.class);
    }

    public HttpMethod getMethod(){
        if(this.method.equalsIgnoreCase("GET")) return HttpMethod.GET;
        if(this.method.equalsIgnoreCase("POST")) return HttpMethod.POST;
        if(this.method.equalsIgnoreCase("DELETE")) return HttpMethod.DELETE;
        if(this.method.equalsIgnoreCase("PUT")) return HttpMethod.PUT;
        throw new RuntimeException("HTTP method "+method+"  is not defined");
    }


    public String getResourceURL() {
        return resourceURL;
    }

    public void setResourceURL(String resourceURL) {
        this.resourceURL = resourceURL;
    }

    public void setMethod(String method) {
        this.method = method;
    }


    public String getBodyClass() {
        return bodyClass;
    }

    public void setBodyClass(String bodyClass) {
        this.bodyClass = bodyClass;
    }


    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public List<String> getQueryParameters() {
        return queryParameters;
    }

    public void setQueryParameters(List<String> queryParameters) {
        this.queryParameters = queryParameters;
    }

    public List<String> getPathParameters() {
        return pathParameters;
    }

    public void setPathParameters(List<String> pathParameters) {
        this.pathParameters = pathParameters;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getApiDomainName() {
        return apiDomainName;
    }

    public void setApiDomainName(String apiDomainName) {
        this.apiDomainName = apiDomainName;
    }

    public void setAcceptType(String accept) {
        this.headers.put("Accept",accept);
    }
}

