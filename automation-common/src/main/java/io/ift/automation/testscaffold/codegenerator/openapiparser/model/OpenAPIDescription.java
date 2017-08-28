package io.ift.automation.testscaffold.codegenerator.openapiparser.model;

import com.beust.jcommander.internal.Lists;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

/**
 * Created by patrick on 15/4/22.
 *
 * @version $Id$
 * @author patrick
 */
public class OpenAPIDescription {
    private List<OpenAPIParameter> queryParameters= Lists.newArrayList();
    private List<OpenAPIParameter> pathParameters= Lists.newArrayList();
    private Map<String, String> headers = Maps.newConcurrentMap();
    private String resourceURL;
    private String method;
    private List<OpenAPIParameter> body;
    private String contentType;


    /**
     * <p>Getter for the field <code>queryParameters</code>.</p>
     *
     * @return a {@link java.util.List} object.
     */
    public List<OpenAPIParameter> getQueryParameters() {
        return queryParameters;
    }

    /**
     * <p>Setter for the field <code>queryParameters</code>.</p>
     *
     * @param queryParameters a {@link java.util.List} object.
     */
    public void setQueryParameters(List<OpenAPIParameter> queryParameters) {
        this.queryParameters = queryParameters;
    }

    /**
     * <p>Getter for the field <code>pathParameters</code>.</p>
     *
     * @return a {@link java.util.List} object.
     */
    public List<OpenAPIParameter> getPathParameters() {
        return pathParameters;
    }

    /**
     * <p>Setter for the field <code>pathParameters</code>.</p>
     *
     * @param pathParameters a {@link java.util.List} object.
     */
    public void setPathParameters(List<OpenAPIParameter> pathParameters) {
        this.pathParameters = pathParameters;
    }

    /**
     * <p>Getter for the field <code>headers</code>.</p>
     *
     * @return a {@link java.util.Map} object.
     */
    public Map<String, String> getHeaders() {
        return headers;
    }

    /**
     * <p>Setter for the field <code>headers</code>.</p>
     *
     * @param headers a {@link java.util.Map} object.
     */
    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    /**
     * <p>Getter for the field <code>resourceURL</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getResourceURL() {
        return resourceURL;
    }

    /**
     * <p>Setter for the field <code>resourceURL</code>.</p>
     *
     * @param resourceURL a {@link java.lang.String} object.
     */
    public void setResourceURL(String resourceURL) {
        this.resourceURL = resourceURL;
    }

    /**
     * <p>Getter for the field <code>method</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getMethod() {
        return method;
    }

    /**
     * <p>Setter for the field <code>method</code>.</p>
     *
     * @param method a {@link java.lang.String} object.
     */
    public void setMethod(String method) {
        this.method = method;
    }

    /**
     * <p>Getter for the field <code>contentType</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getContentType() {
        return contentType;
    }

    /**
     * <p>Setter for the field <code>contentType</code>.</p>
     *
     * @param contentType a {@link java.lang.String} object.
     */
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    /**
     * <p>Getter for the field <code>body</code>.</p>
     *
     * @return a {@link java.util.List} object.
     */
    public List<OpenAPIParameter> getBody() {
        return body;
    }

    /**
     * <p>Setter for the field <code>body</code>.</p>
     *
     * @param body a {@link java.util.List} object.
     */
    public void setBody(List<OpenAPIParameter> body) {
        this.body = body;
    }
}
