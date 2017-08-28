package io.ift.automation.testscaffold.apitest.baseapi;

import io.ift.automation.data.TestData;
import com.dooioo.automation.helpers.*;

import io.ift.automation.helpers.RestTemplateClientHelper;
import io.ift.automation.logging.TestResultLogger;
import io.ift.automation.testscaffold.apitest.APIRequestBody;
import io.ift.automation.testscaffold.apitest.RequestData;
import io.ift.automation.testscaffold.apitest.ServiceDescription;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import io.ift.automation.helpers.EnvironmentHelper;
import io.ift.automation.helpers.FileHelper;
import io.ift.automation.helpers.JSONHelper;
import io.ift.automation.helpers.PropertiesHelper;
import io.ift.automation.helpers.StringHelper;

/**
 * Created by patrick on 15/3/30.
 *
 * @version $Id$
 */

@SuppressWarnings("unchecked")
public final class HttpServiceProcessor<T>{

    private RestTemplateClientHelper restHelper;
    private ServiceDescription sd;
    private RequestData<T> requestData;
    private String requestUrl;
    private ResponseEntity response;
    private String apiDomainName;
    /**
     * 构建HTTP service processor
     * @param client
     * @param serviceDescriptionLocation
     * @param requestData
     * @return
     */
    public static HttpServiceProcessor build(RestTemplateClientHelper client
            ,String serviceDescriptionLocation,RequestData requestData){
        HttpServiceProcessor service = new HttpServiceProcessor(serviceDescriptionLocation,requestData);
        service.restHelper=client;
        return service;
    }

    /**
     * test data to build api request body, too complex to do so,move it to client code
     * @param client
     * @param serviceDescriptionLocation
     * @param requestBody
     * @param data
     * @return
     */

    @Deprecated
    public static HttpServiceProcessor build(RestTemplateClientHelper client
            , String serviceDescriptionLocation, APIRequestBody requestBody, TestData...data){
        RequestData rd = RequestData.build();
        HttpServiceProcessor service = new HttpServiceProcessor(serviceDescriptionLocation,rd);
        rd.init(service.sd,requestBody,data);
        service.restHelper=client;
        service.buildRequest();
        return service;
    }

    public static HttpServiceProcessor build(RestTemplateClientHelper client,ServiceDescription st,RequestData data){
        HttpServiceProcessor service =new HttpServiceProcessor(client);
        service.sd=st;
        service.requestData=data;
        service.buildRequest();
        return  service;
    }

    private HttpServiceProcessor (RestTemplateClientHelper client){
        this.restHelper=client;
    }

    private HttpServiceProcessor(String serviceDescriptionLocation,RequestData data){
        this.requestData=data;
        this.loadServiceDescriptionFromFile(serviceDescriptionLocation);
        if(this.sd.getApiDomainName()==null||this.sd.getApiDomainName().trim().length()==1){
            this.apiDomainName="openapi";
        }else {
            this.apiDomainName=this.sd.getApiDomainName();
        }
    }

    /**
     * 构建HTTP service processor
     * @param serviceDescriptionLocation
     * @param requestData
     * @return
     */
    public static HttpServiceProcessor build(String serviceDescriptionLocation,RequestData requestData){
        HttpServiceProcessor service = new HttpServiceProcessor(serviceDescriptionLocation,requestData);
        service.restHelper=RestTemplateClientHelper.getHttpClientImplInstance();
        service.buildRequest();
        return service;
    }

    private void loadServiceDescriptionFromFile(String serviceDescriptionLocation){
        this.sd = ServiceDescription.loadServiceDescriptionFromFile(serviceDescriptionLocation);
    }

    /**
     * 建立HTTP 请求
     */
    private void buildRequest(){
        buildHeaders();
        buildRequestUrl();
    }

    /**
     * build headers according to the service description
     */
    private void buildHeaders(){

        for (Map.Entry<String,String> head : requestData.getHeaders().entrySet()) {
            restHelper.addHeader(head.getKey(),head.getValue());
        }
        setContextType();
    }

    private void setContextType(){
        if(StringHelper.isNotEmptyOrNotBlankString(sd.getContentType())){
            restHelper.setContentType(MediaType.parseMediaType(sd.getContentType()));
        }
    }
    /**
     * add additional headers is used for
     * add headers which are used for authorization or other authorize purposes
     * @param headers
     */
    public HttpServiceProcessor addGivenHeaders(Map<String,String> headers){
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            restHelper.addHeader(entry.getKey(),entry.getValue());
        }

        return this;
    }

    public HttpServiceProcessor addGivenHeader(String key,String value){
        restHelper.addHeader(key, value);
        return this;
    }

    public HttpServiceProcessor addAuthHeader(String value){
        restHelper.addHeader("Authorization", value);
        return this;
    }

    private void buildRequestUrl(){
        joinRootAndResourceUrl();
        addPathParameter();
        addQueryParameter();
    }

    /**
     * replace the path parameter
     * the request url is like that /ticket/{ticketId}  so replace the ticketId with the value of ticketId
     */
    private void addPathParameter(){
        for (Map.Entry<String,String> pathParameter : this.requestData.getPathParameters().entrySet()) {
            this.requestUrl= this.requestUrl.replace(String.format("{%s}",
                    pathParameter.getKey()),pathParameter.getValue());
        }
    }

    /**
     * 根据api domain name查找api的域名,没有必要做的这么复杂,需要重构简化
     * 1. 如果ServiceDescription#apiDomainName 为空，取service root 值
     * 2. 否则根据api domain name取域名
     * work around to fetch API domain URL
     * @return
     */
    private String getAPIRootPathByAPIDomainName(){
        //openapi is special, so do it first
        if("openapi".equalsIgnoreCase(apiDomainName)){
            return EnvironmentHelper.getOpenApiRootPath();
        }
        if(StringHelper.isNoneContentString(apiDomainName)){
            return EnvironmentHelper.getServiceRootPath();
        }
        return EnvironmentHelper.getApiRootByApiDomain(apiDomainName);
    }

    private void joinRootAndResourceUrl() {
        String rootPath = getAPIRootPathByAPIDomainName();
        if(rootPath==null) throw new RuntimeException("请检查"+this.apiDomainName+"在配置文件有中没有配置相对应的API根路径");
        if(rootPath.endsWith("/")){
            if(sd.getResourceURL().startsWith("/")){
                this.requestUrl = rootPath+sd.getResourceURL().substring(1);
            }else{
                this.requestUrl = rootPath+sd.getResourceURL();
            }
        }else{
            if(sd.getResourceURL().startsWith("/")){
                this.requestUrl = rootPath+sd.getResourceURL();
            }else{
                this.requestUrl = rootPath+"/"+sd.getResourceURL();
            }
        }
    }

    /**
     * add query parameter like /ticket?ticketId=XXX&ticketName=&&&
     */
    private void addQueryParameter(){
        StringBuilder sb = new StringBuilder(this.requestUrl);
        sb.append("?");
        this.requestData.getQueryParameters().entrySet().stream().filter(
                queryParameter ->
                        null!=queryParameter.getValue())
                .forEach(queryParameter -> {
                    sb.append(queryParameter.getKey());
                    sb.append("=");
                    if (queryParameter.getValue().contains("{") || queryParameter.getValue().contains("[")) {
                        try {
                            sb.append(URLEncoder.encode(queryParameter.getValue(), "UTF-8"));
                        } catch (UnsupportedEncodingException e) {
                            TestResultLogger.warn(e);
                        }
                    } else {
                        sb.append(queryParameter.getValue());
                    }
                    sb.append("&"); //todo handle the last (&)
                });
        this.requestUrl=sb.toString();
    }

    public HttpServiceProcessor call(){
        // call according body
        this.buildRequest();
        if (PropertiesHelper.getProperty("write_api_response", "false").equalsIgnoreCase("true")) {
            FileHelper.writeToFile("apiResponse", "api_request_url:" + this.requestUrl);
            FileHelper.writeToFile("apiResponse", "api_request_data:"+this.requestData.toString());
        }

        while(requestData.getRepeatTimes()>=1){
            TestResultLogger.log("request_url={}", this.requestUrl);
            TestResultLogger.log("request_headers={}",this.restHelper.getHeaders());
            TestResultLogger.log("request_http_entity={}",this.restHelper.getHttpEntity());
            if(requestData.getBody()!=null){
                if(requestData.getBody() instanceof String){
//                if(("openapi".equalsIgnoreCase(apiDomainName)||
//                        "appserver".equalsIgnoreCase(apiDomainName))
//                        &&requestData.getBody() instanceof String) {
                    try {
                        this.response = restHelper.call(this.requestUrl, sd.getMethod(), JSONHelper
                            .parseObject((String) requestData.getBody()));
                    }catch (Exception e){
                        TestResultLogger.error("body: {} is not JSON format,please check your input",requestData.getBody().toString());
                    }
                }else{
                    this.response=restHelper.call(this.requestUrl,sd.getMethod(),requestData.getBody());
                }

            }else{
                this.response=restHelper.call(this.requestUrl,sd.getMethod(),requestData.getBody());
            }

            logResponse();
            requestData.setRepeatTimes(requestData.getRepeatTimes()-1);
        }

        if (PropertiesHelper.getProperty("write_api_response", "false").equalsIgnoreCase("true")) {
            if(getResponse()!=null){
                if(getResponse().getBody()!=null){
                    FileHelper.writeToFile("apiResponse", "api_response:"+getResponse().getBody().toString());
                }
            }
        }

        return this;
    }

    public <R> R call(Class<R> clazz) {
        this.buildRequest();
        while(requestData.getRepeatTimes()>=1) {

            TestResultLogger.log("request_url={}", this.requestUrl);
            TestResultLogger.log("request_headers={}", this.restHelper.getHeaders());
            TestResultLogger.log("request_http_entity={}", this.restHelper.getHttpEntity());

            if (requestData.getBody() != null) {
                if ("openapi".equalsIgnoreCase(apiDomainName) && requestData.getBody() instanceof String) {
                    try {
                        this.response = restHelper.call(this.requestUrl, sd.getMethod(), JSONHelper.parseObject((String) requestData.getBody()));
                    } catch (Exception e) {
                        TestResultLogger.error("body: {} is not JSON format,please check your input", requestData.getBody().toString());
                    }
                } else {
                    this.response = restHelper.call(this.requestUrl, sd.getMethod(), requestData.getBody());
                }
            } else {
                this.response = restHelper.call(this.requestUrl, sd.getMethod(), clazz);
            }

            logResponse();
            requestData.setRepeatTimes(requestData.getRepeatTimes()-1);
        }
        return (R)this.response.getBody();

    }

    private void logResponse() {
        if(this.response!=null&&this.response.getBody()!=null){
            TestResultLogger.log("response={}", this.response.getBody().toString());
        }else{
            TestResultLogger.log("response={}", this.response);
        }
    }


    public ResponseEntity getResponse() {
        return response;
    }

    public ServiceDescription getSd() {
        return sd;
    }

    public void setSd(ServiceDescription sd) {
        this.sd = sd;
    }

    public String getApiDomainName() {
        return apiDomainName;
    }

    public void setApiDomainName(String apiDomainName) {
        this.apiDomainName = apiDomainName;
    }

    public RequestData<T> getRequestData() {
        return requestData;
    }

    public void setRequestData(RequestData<T> requestData) {
        this.requestData = requestData;
    }
}
