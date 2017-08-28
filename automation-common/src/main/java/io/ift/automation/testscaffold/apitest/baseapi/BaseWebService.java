package io.ift.automation.testscaffold.apitest.baseapi;

import io.ift.automation.data.TestData;
import io.ift.automation.helpers.RestTemplateClientHelper;
import io.ift.automation.testscaffold.apitest.APIRequestBody;
import io.ift.automation.testscaffold.apitest.RequestData;
import io.ift.automation.testscaffold.apitest.ServiceDescription;
import io.ift.automation.testscaffold.apitest.interfaces.WebServiceTestAction;
import org.springframework.http.ResponseEntity;

/**
 * Created by patrick on 15/3/31.
 *
 * @version $Id$
 */


public class BaseWebService<T> implements WebServiceTestAction {


    protected HttpServiceProcessor processor;

    public BaseWebService(String path, RequestData<T> data) {
        processor = HttpServiceProcessor.build(path, data);
    }

    @Deprecated
    // deprecate it because it is too complex and not very useful
    public BaseWebService(RestTemplateClientHelper client, String serviceDescPath,
                          APIRequestBody body, TestData... data) {
        processor = HttpServiceProcessor.build(client,serviceDescPath, body,data);
    }


    public BaseWebService(RestTemplateClientHelper client, String serviceDescPath, RequestData<T> data) {
        processor = HttpServiceProcessor.build(client, serviceDescPath, data);
    }


    //    private ServiceDescription description(){
//        this.getClass().getAnnotation(WebServiceDescription.class);
//        return new ServiceDescription();
//    }
    @Override
    public void execute() {
        processor.call();
    }

    @Override
    public ResponseEntity getTestContext() {
        return getResponse();
    }

    public ResponseEntity getResponse() {
        return processor.getResponse();
    }

    public ServiceDescription getServiceDescription(){
        return processor.getSd();
    }

    public void setAPIDomainName(String apiDomainName){
        processor.setApiDomainName(apiDomainName);
    }

    public HttpServiceProcessor<T> getProcessor() {
        return processor;
    }

    public void setProcessor(HttpServiceProcessor<T> processor) {

        this.processor = processor;
    }

    /**
     * 返回Body的字符串
     * @return
     */
    public String getBody(){
        return this.getResponse().getBody().toString();
    }

}
