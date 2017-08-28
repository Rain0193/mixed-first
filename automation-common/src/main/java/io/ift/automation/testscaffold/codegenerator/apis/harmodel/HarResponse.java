package io.ift.automation.testscaffold.codegenerator.apis.harmodel;

import java.util.List;

public class HarResponse {
    private int status;
    private String statusText;
    private int headersSize;
    private int bodySize;
    private List<HarHeader> headers;
    private String httpVersion;
    private String redirectURL;
    private Content content;
    private List<HarCookie> cookies;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
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

    public String getRedirectURL() {
        return redirectURL;
    }

    public void setRedirectURL(String redirectURL) {
        this.redirectURL = redirectURL;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public List<HarCookie> getCookies() {
        return cookies;
    }

    public void setCookies(List<HarCookie> cookies) {
        this.cookies = cookies;
    }

    @Override
    public String toString() {
        return "HarResponse{" +
                "status=" + status +
                ", statusText='" + statusText + '\'' +
                ", headersSize=" + headersSize +
                ", bodySize=" + bodySize +
                ", headers=" + headers +
                ", httpVersion='" + httpVersion + '\'' +
                ", redirectURL='" + redirectURL + '\'' +
                ", content=" + content +
                ", cookies=" + cookies +
                '}';
    }
}
