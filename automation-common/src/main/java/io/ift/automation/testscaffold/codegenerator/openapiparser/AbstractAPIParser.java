package io.ift.automation.testscaffold.codegenerator.openapiparser;

import io.ift.automation.testscaffold.apitest.ServiceDescription;
import io.ift.automation.testscaffold.codegenerator.HtmlParser;
import io.ift.automation.testscaffold.codegenerator.openapiparser.model.OpenAPIParameter;
import com.google.common.collect.Lists;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Created by patrick on 15/4/8.
 *
 * @version $Id$
 * @author patrick
 */
public abstract class AbstractAPIParser {
    String apiName;
    HtmlParser parser;
    String method;
    String resourceUrl;
    List<OpenAPIParameter> pathParameters=Lists.newArrayList();
    List<OpenAPIParameter> queryParameters= Lists.newArrayList();
    List<OpenAPIParameter> responseBody=Lists.newArrayList();
    List<OpenAPIParameter> requestBody=Lists.newArrayList();
    String overallResponseType;
    String overallRequestType;
    String responseSample;

    /**
     * <p>toServiceDescription.</p>
     *
     * @return a {@link ServiceDescription} object.
     */
    public ServiceDescription toServiceDescription(){
        ServiceDescription sd = new ServiceDescription();
        sd.setContentType("application/json");
        sd.setMethod(this.method);
        sd.setResourceURL(this.resourceUrl);
        sd.setPathParameters(pathParameters.stream()
                .map(OpenAPIParameter::getName).collect(toList()));
        sd.setQueryParameters(queryParameters.stream()
                .map(OpenAPIParameter::getName).collect(toList()));
//        sd.setResponseSample(this.responseSample);
        return sd;
    }

    /**
     * <p>parseAPIName.</p>
     */
    public abstract void parseAPIName();
    /**
     * <p>parseHeader.</p>
     */
    public abstract void parseHeader();
    /**
     * <p>method.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public abstract String method();
    /**
     * <p>pathParameters.</p>
     *
     * @return a {@link java.util.List} object.
     */
    public abstract List<OpenAPIParameter> pathParameters();
    /**
     * <p>queryParameters.</p>
     *
     * @return a {@link java.util.List} object.
     */
    public abstract List<OpenAPIParameter> queryParameters();
    /**
     * <p>parseRequestBody.</p>
     *
     * @return a {@link java.util.List} object.
     */
    public abstract List<OpenAPIParameter>  parseRequestBody();
    /**
     * <p>parseResponseBody.</p>
     *
     * @return a {@link java.util.List} object.
     */
    public abstract List<OpenAPIParameter> parseResponseBody();
    /**
     * <p>responseSample.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public abstract String responseSample();
    /**
     * <p>resourceUrl.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public abstract String resourceUrl();

    /**
     * <p>parse.</p>
     *
     * @return a {@link AbstractAPIParser} object.
     */
    public AbstractAPIParser parse(){
        parseAPIName();
        parseHeader();
        method();
        resourceUrl();
        pathParameters();
        queryParameters();
        parseRequestBody();
        parseResponseBody();
//        responseSample();
        return this;
    }
}
