<#if package_name?exists>
package com.Domain.automation.apis.${package_name};
<#else>
package com.Domain.automation.apis;
</#if>
import com.Domain.automation.commonflows.models.EmployeeTestData;
import com.Domain.automation.flows.AuthFlows;
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
        RestTemplateClientHelper client = AuthFlows.getLoggedInOpenAPIClient(user.getUserCode(), user.getPassword());
        if (!data.isAuth()) client.removeAuthHeader();
        ${api_name} api = new ${api_name}(client,data);
        api.execute();
        new SoftAssertion().assertApiResponse(api.getResponse(),statusCode,expectedBodyResult).getFinalResult();
    }

<#--@DataProvider(name = "ApplyAsset_data")-->
<#--public Iterator<Object[]> getTestData(Method m) throws Exception{-->
<#--Map<String, Class> clazzMap = new HashMap<String, Class>();-->
<#--clazzMap.put("AssetData", AssetApplicationTestData.class);-->
<#--clazzMap.put("Activities", Activities.class);-->
<#--clazzMap.put("TestDescription", TestDescription.class);-->
<#--Iterator<Object[]> y = TestDescription.filterByMethod("testcase/flows/AssetApplyApprovalTestCases.xls", m, clazzMap);-->
<#--return y;-->
<#--}-->

<#--@Test(dataProvider = "ApplyAsset_data",description = "审批申请资产")-->
<#--public void test_ft_approval(Activities approvals,AssetApplicationTestData data,-->
<#--TestDescription td){-->
<#--AssetApplicationCheckPoint c = new AssetApplicationCheckPoint(data);-->
<#--DriverFactory.getThreadLevelTestContext().addCheckPoint(c);-->

<#--WebDriver driver = loginAndReturnDriver(approvals.startEmployee());-->
<#--AssetsApplicationFlows.addAssetApplication(driver, data);-->
<#--DomainLoginHelper.logout(driver);-->
<#--AssetsApplicationFlows.approveAssetApplication(driver, data, approvals);-->
<#--c.getTestResult().getFinalResult();-->
}
}
