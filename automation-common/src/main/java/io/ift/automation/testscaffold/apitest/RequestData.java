package io.ift.automation.testscaffold.apitest;

import io.ift.automation.data.DataComposer;
import io.ift.automation.data.TestData;
import io.ift.automation.helpers.StringHelper;
import com.google.common.base.MoreObjects;
import com.google.common.collect.Maps;
import java.util.Map;

/**
 * Created by patrick on 15/3/30.
 *
 * @version $Id$
 */


public class RequestData<T> extends TestData {

    private Map<String,String> queryParameters= Maps.newHashMap();
    private Map<String,String> pathParameters=  Maps.newHashMap();
    private Map<String,String> headers = Maps.newHashMap();
    private T body; //todo understand file attachment
    private boolean isAuth= true;
    private int repeatTimes =1;
    private transient String bodyTemplate;
    private transient Map<String,String> bodyParameters = Maps.newHashMap();

    public static RequestData build(){
        return new RequestData();
    }
    /**
     * add query parameters
     * @param key
     * @param value
     * @return
     */
    public RequestData addQueryParameter(String key,String value){
        queryParameters.put(key,value);
        return this;
    }

    public RequestData addBody(T body){
        this.body=body;
        return this;
    }
    /**
     * add path parameters
     * @param key
     * @param value
     * @return
     */
    public RequestData addPathParameter(String key,String value){
        pathParameters.put(key,value);
        return this;
    }

    /**
     * add header
     * @param key
     * @param value
     * @return
     */
    public RequestData addHeader(String key,String value){
        headers.put(key,value);
        return this;
    }

    /**
     * init request boday data
     * @param sd
     * @param body
     * @param data
     * @return
     */

    @Deprecated
    public RequestData init(ServiceDescription sd,T body,TestData... data){
        for (String s : sd.getPathParameters()) {
            addPathParameter(s, (String) DataComposer.from(data).get(s));
        }

        for (String s : sd.getQueryParameters()) {
            addQueryParameter(s, (String)DataComposer.from(data).get(s));
        }
        this.body=body;
        return this;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("queryParameters", queryParameters)
                .add("pathParameters", pathParameters)
                .add("headers", headers)
                .add("body",body)
                .toString();
    }

    public Map<String, String> getQueryParameters() {
        return queryParameters;
    }

    public void setQueryParameters(Map<String, String> queryParameters) {
        this.queryParameters = queryParameters;
    }

    public Map<String, String> getPathParameters() {
        return pathParameters;
    }

    public void setPathParameters(Map<String, String> pathParameters) {
        this.pathParameters = pathParameters;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }


    public int getRepeatTimes() {
        return repeatTimes;
    }

    public void setRepeatTimes(int repeatTimes) {
        this.repeatTimes = repeatTimes;
    }

    public boolean isAuth() {
        return isAuth;
    }

    public void setIsAuth(boolean isAuth) {
        this.isAuth = isAuth;
    }

    public String getBodyTemplate() {
        return bodyTemplate;
    }

    public void setBodyTemplate(String bodyTemplate) {
        this.bodyTemplate = bodyTemplate;
    }

    public Map<String, String> getBodyParameters() {
        return bodyParameters;
    }

    public void setBodyParameters(Map<String, String> bodyParameters) {
        this.bodyParameters = bodyParameters;
    }

    public RequestData setBodyParameter(String key,String value){
        this.bodyParameters.put(key,value);
        return this;
    }
    @Override
    public void dataComposeAfter() {
        if(StringHelper.isEmpty(bodyTemplate)) return;
        String bodyContent = bodyTemplate;
        for (Map.Entry<String, String> entry : bodyParameters.entrySet()) {
            bodyContent=bodyContent.replaceAll(String.format("_%s", entry.getKey().trim()), entry.getValue());
        }
        this.body=(T) bodyContent;
    }
}
