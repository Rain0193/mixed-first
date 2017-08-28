<#if package_name?exists>
package com.Domain.automation.apis.${package_name};
<#else>
package com.Domain.automation.apis;
</#if>
import com.Domain.automation.commonflows.models.EmployeeTestData;
<#if isOpenAPI>
import com.Domain.automation.flows.AuthFlows;
<#else>
import com.Domain.automation.commonflows.DomainLoginHelper;
</#if>
import com.Domain.automation.helpers.RestTemplateClientHelper;
import com.Domain.automation.testscaffold.apitest.RequestData;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import com.Domain.automation.assertion.SoftAssertion;
import com.Domain.automation.tm.testmodel.TestDescription;

public class ${api_name}Test {

    @DataProvider(name="${api_name}_data")
        public Iterator<Object[]> getAPITestData(Method m) throws Exception{
        Map<String,Class> clazzMap = new HashMap<String,Class>();
        clazzMap.put("RequestData", RequestData.class);
        clazzMap.put("TestCase",TestDescription.class);
        clazzMap.put("User", EmployeeTestData.class);
        <#if package_name?exists>
        Iterator<Object[]> y= TestDescription.filterByMethod("testcase/apis/${package_dir}/${api_name}_testcase.xls",m,clazzMap);
        <#else>
        Iterator<Object[]> y= TestDescription.filterByMethod("testcase/apis/${api_name}_testcase.xls",m,clazzMap);
        </#if>

        return y;
    }

    @Test(dataProvider = "${api_name}_data")
    public void test${api_name}(String statusCode,String expectedBodyResult,RequestData data,TestDescription td,EmployeeTestData user){
        <#if isOpenAPI>
        RestTemplateClientHelper client = AuthFlows.getLoggedInOpenAPIClient(user.getUserCode());
        if (!data.isAuth()) client.removeAuthHeader();
        <#else>
        RestTemplateClientHelper client = DomainLoginHelper.getLoggedInAPIClient(user);
        </#if>

        ${api_name} api = new ${api_name}(client,data);
        api.execute();
        new SoftAssertion().assertApiResponse(api.getResponse(),statusCode,expectedBodyResult).getFinalResult();
    }
}
