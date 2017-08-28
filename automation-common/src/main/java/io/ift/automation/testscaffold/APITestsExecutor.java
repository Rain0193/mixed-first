package io.ift.automation.testscaffold;

import io.ift.automation.helpers.JSONHelper;
import io.ift.automation.testscaffold.apitest.baseapi.BaseWebService;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import org.springframework.http.ResponseEntity;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Created by patrick on 15/6/4.
 *
 * @version $Id: APITestsExecutor.java 1995 2015-11-11 01:05:37Z wuke $
 */

@SuppressWarnings("unchecked")
@Deprecated
public class APITestsExecutor extends TestActionsExecutor {
    protected ResponseEntity latestResponse ;
    protected List<ResponseEntity> responseHolder = Lists.newArrayList();
    protected BaseWebService latestExecutedService;

    private void beforeAddSteps(){
        hookableFunctionList.add(new Function<TestAction,ResponseEntity>() {
            @Nullable
            @Override
            public ResponseEntity apply(@Nullable TestAction testStep) {
                if(testStep instanceof BaseWebService){
                       latestExecutedService=(BaseWebService)testStep;
                       latestResponse =((BaseWebService) testStep).getResponse();
                       responseHolder.add(latestResponse);
                }

                return latestResponse;
            }
        });
    }

    @Override
    protected APITestsExecutor processCheck(TestAction testStep) {
        super.processCheck(testStep);
        return this;
    }

    @Override
    public APITestsExecutor addTestSteps(TestAction... steps) {
        super.addTestSteps(steps);
        return this;
    }

    @Override
    public APITestsExecutor addCheckPoints(CheckPoint... checkPoints) {
         super.addCheckPoints(checkPoints);
         return this;
    }

    public static APITestsExecutor build(TestAction...actions){
        APITestsExecutor builder = new APITestsExecutor();
        builder.beforeAddSteps();
        builder.addTestSteps(actions);
        return builder;
    }


    public APITestsExecutor addHeader(String key,String expression){

        addTestSteps(() -> latestExecutedService.getProcessor().addGivenHeader(key,
                                                                               JSONHelper
                                                                                   .getValue(latestResponse, expression).toString()));
        return this;
    }

    public APITestsExecutor addDefaultTokenToHeader(){
        addTestSteps(() -> latestExecutedService.getProcessor().addGivenHeader("x-token",
                JSONHelper.getValue(latestResponse, "token").toString()));
        return this;
    }

    public ResponseEntity getLatestResponse() {
        return latestResponse;
    }


    public List<ResponseEntity> getResponseHolder() {
        return responseHolder;
    }


    public BaseWebService getLatestExecutedService() {
        return latestExecutedService;
    }

}
