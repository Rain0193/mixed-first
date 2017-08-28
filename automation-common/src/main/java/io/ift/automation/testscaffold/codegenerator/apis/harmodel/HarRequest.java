package io.ift.automation.testscaffold.codegenerator.apis.harmodel;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class HarRequest {
    private String method;
    private String url;
    private int headersSize;
    private int bodySize;
    private List<HarCookie> cookies;
    private List<HarHeader> headers;
    private String httpVersion;
    private List<HarQueryString> queryString;
    private HarPostData postData;

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getHeadersSize() {
        return headersSize;
    }

    public void setHeadersSize(int headersSize) {
        this.headersSize = headersSize;
    }

    public int getBodySize() {
        return bodySize;
    }

    public void setBodySize(int bodySize) {
        this.bodySize = bodySize;
    }

    public List<HarCookie> getCookies() {
        return cookies;
    }

    public void setCookies(List<HarCookie> cookies) {
        this.cookies = cookies;
    }

    public List<HarHeader> getHeaders() {
        return headers;
    }

    public void setHeaders(List<HarHeader> headers) {
        this.headers = headers;
    }

    public String getHttpVersion() {
        return httpVersion;
    }

    public void setHttpVersion(String httpVersion) {
        this.httpVersion = httpVersion;
    }

    public List<HarQueryString> getQueryString() {
        return queryString;
    }

    public void setQueryString(List<HarQueryString> queryString) {
        this.queryString = queryString;
    }

    public HarPostData getPostData() {
        return postData;
    }

    public void setPostData(HarPostData postData) {
        this.postData = postData;
    }

    public String getContextType() {
        for (HarHeader header : headers) {
            if (header.getName().equalsIgnoreCase("Content-Type")) return header.getValue();
        }

        return "application/json";
    }

    public String getAccept() {

        return headers.stream()
                .filter(header -> header.getName().equalsIgnoreCase("Accept"))
                .findFirst()
                .get()
                .getValue();
    }

    public List<String> getQueryParams(){
        if(queryString.size()==0) {
            if (this.postData == null) return Collections.EMPTY_LIST;
            if (this.postData.getParams() == null) return Collections.EMPTY_LIST;

            return this.postData.getParams().stream()
                    .map(item -> item.getName()).collect(Collectors.toList());
        }else{
            return this.queryString.stream()
                    .map(item -> item.getName()).collect(Collectors.toList());
        }
    }


    public String getResourceUrl(){
        int index = this.url.indexOf("/","https://".length());
        int indexForQuestionQuote = this.url.indexOf("?");
        if(indexForQuestionQuote==-1){
            return this.url.substring(index,this.url.length());
        }else{
            return  this.url.substring(index,indexForQuestionQuote);
        }
    }
    @Override
    public String toString() {
        return "HarRequest{" +
                "method='" + method + '\'' +
                ", url='" + url + '\'' +
                ", headersSize=" + headersSize +
                ", bodySize=" + bodySize +
                ", cookies=" + cookies +
                ", headers=" + headers +
                ", httpVersion='" + httpVersion + '\'' +
                ", queryString=" + queryString +
                ", postData=" + postData +
                '}';
    }
}
