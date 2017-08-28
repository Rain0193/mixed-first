package io.ift.automation.testscaffold.codegenerator.openapiparser;

import io.ift.automation.testscaffold.codegenerator.HtmlParser;
import io.ift.automation.testscaffold.codegenerator.openapiparser.model.OpenAPIParameter;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;

/**
 * Created by patrick on 15/4/8.
 *
 * @version $Id$
 */


public class OpenAPISpecParser extends AbstractAPIParser{
    private Element apiDocContainerElement;
    private Element apiSpecDetailElement;

    public OpenAPISpecParser buildFromFile(String path){
        return new OpenAPISpecParser(path);
    }

    public OpenAPISpecParser(String path) {
        super.parser = HtmlParser.buildFromFile(path);
        apiDocContainerElement = parser.getElementById("apidoc");
        apiSpecDetailElement = apiDocContainerElement.
                getElementsByTag("table").get(0).getElementsByTag("tbody").get(0);
    }

    public OpenAPISpecParser() {
    }

    public Element selectRowByHeader(Element table,String labelName){
      return  parser.getTableRowByColumnText(table, labelName);
    }


    public static OpenAPISpecParser build (String pageSource) {
        OpenAPISpecParser parser = new OpenAPISpecParser();
        parser.parser = HtmlParser.build(pageSource);
        parser.apiDocContainerElement = parser.parser.getElementById("apidoc");
        parser.apiSpecDetailElement = parser.apiDocContainerElement.
                getElementsByTag("table").get(0).getElementsByTag("tbody").get(0);

        return parser;
    }

    @Override
    public void parseAPIName() {
        apiName = parser.getTextByTagName(apiDocContainerElement,"h4");
    }

    @Override
    public void parseHeader() {

    }

    @Override
    public String method() {

        Element e = selectRowByHeader(apiSpecDetailElement,"请求方法");
        method = e.select("td:nth-child(2)>span").get(0).ownText();
        return method;
    }

    @Override
    public List<OpenAPIParameter> pathParameters() {
        Element tr = parser.getTableRowByColumnText(apiDocContainerElement, "URL变量");
        pathParameters = getOpenAPIParametersByRowHeader(tr,"URL变量");
        return pathParameters;
    }

    private List<OpenAPIParameter> getOpenAPIParametersByRowHeader(Element tr,String rowHeader) {
        List<OpenAPIParameter> parameters = Lists.newArrayList();
        if(tr==null) return parameters;
        Element content = tr.child(1);

        Elements rows = content.select("table>tbody").get(0).children();
        for (Element row : rows) {
            Elements td = row.children();
            OpenAPIParameter parameter = new OpenAPIParameter();
            parameter.setName(td.get(0).ownText());
            parameter.setType(td.get(1).ownText());
            parameter.setMandatory(td.get(2).ownText().contains("是"));
            parameter.setDefaultValue(td.get(3).ownText());
            parameter.setComments(td.get(4).text());
            parameters.add(parameter);
        }
        return parameters;
    }

    @Override
    public List<OpenAPIParameter> queryParameters() {
        Element tr = parser.getTableRowByColumnText(apiDocContainerElement, "请求参数");
        if(tr!=null){
            queryParameters=getOpenAPIParametersByRowHeader(tr,"请求参数");
        }
        return queryParameters;
    }

    @Override
    public List<OpenAPIParameter> parseRequestBody() {
        Element tr = parser.getTableRowByColumnText(apiDocContainerElement, "请求消息体");
        requestBody= getOpenAPIParametersByRowHeader(tr,"请求消息体");
        if(tr!=null) overallRequestType=tr.child(0).select("span").text();
        return requestBody;
    }

    @Override
    public String resourceUrl() {
        Element e = selectRowByHeader(apiSpecDetailElement,"请求URL");
        resourceUrl = e.select("td:nth-child(2)").get(0).ownText();
        resourceUrl= resourceUrl.replace("http://open.dooioo.org", "").trim();
        resourceUrl= resourceUrl.replace("http://open.dooioo.com","").trim();
        return resourceUrl;
    }

    @Override
    public List<OpenAPIParameter> parseResponseBody() {
        Element tr = parser.getTableRowByColumnText(apiDocContainerElement, "响应消息体");
        if(tr!=null){
            responseBody= getOpenAPIParametersByRowHeader(tr,"响应消息体");
            overallResponseType=tr.child(0).select("span").text();
        }
        return responseBody;
    }

    @Override
    public String responseSample() {
        Element tr = parser.getTableRowByColumnText(apiDocContainerElement, "响应消息体示例");

        if(tr!=null) {
            responseSample = tr.child(1).text();
        }else{
            responseSample= StringUtils.EMPTY;
        }
        return responseSample;
    }


    public Element getApiDocContainerElement() {
        return apiDocContainerElement;
    }

    public void setApiDocContainerElement(Element apiDocContainerElement) {
        this.apiDocContainerElement = apiDocContainerElement;
    }

    public Element getApiSpecDetailElement() {
        return apiSpecDetailElement;
    }

    public void setApiSpecDetailElement(Element apiSpecDetailElement) {
        this.apiSpecDetailElement = apiSpecDetailElement;
    }
}
