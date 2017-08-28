package io.ift.automation.commonflows.web.flows;

import io.ift.automation.commonflows.DooiooLoginHelper;
import io.ift.automation.commonflows.models.EmployeeTestData;
import io.ift.automation.testscaffold.BaseWebTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginWebFlowTest extends BaseWebTest {

    @Test
    public void testExecute() throws Exception {
        EmployeeTestData employeeTestData = new EmployeeTestData();
        String password = DooiooLoginHelper.getIntegrationLoginPassword("110863","PW_654321");
        System.out.println(password);
        employeeTestData.setPassword(password);
        employeeTestData.setUserCode("110863");
        LoginWebFlow login = new LoginWebFlow(driver, employeeTestData);
        login.execute();
        Assert.assertEquals(driver.getTitle(),"员工登录 - 内网 - 链家");
    }
}
