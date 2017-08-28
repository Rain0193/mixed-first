package io.ift.automation.testscaffold.codegenerator;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class APICodesGeneratorTest {

//    @Test
//    public void testGenerateAPICode() throws Exception {
//        APICodesGenerator g = APICodesGenerator.build("servicedescription/loginapi.json");
//        g.generateTestStepCode();
//    }
//
//    @Test
//    public void testGenerateAPICode_ApiCategory() throws Exception {
//        APICodesGenerator g = APICodesGenerator.build("servicedescription/login/loginapi.json");
//        g.generateTestStepCode();
//    }
//
//    @Test
//    public void testGenerateAPITestCaseCode_apiCategory() throws Exception {
//        APICodesGenerator g = APICodesGenerator.build("servicedescription/login/loginapi.json");
//        g.generateTestCaseCode();
//    }
//
//    @Test
//    public void testGenerateAPITestCaseCode() throws Exception {
//        APICodesGenerator g = APICodesGenerator.build("servicedescription/loginapi.json");
//        g.generateTestCaseCode();
//    }
//
//    @Test
//    public void testGenerateTestCaseData() throws Exception {
//        APICodesGenerator g = APICodesGenerator.build("servicedescription/loginapi.json");
//        assertTrue(g.generateTestCaseData().exists());
//    }
//
//    @Test
//    public void testGenerateTestCaseData_APICategory() throws Exception {
//        APICodesGenerator g = APICodesGenerator.build("servicedescription/login/loginapi.json");
//        assertTrue(g.generateTestCaseData().exists());
//
//    }

    @Test
    public void testGenerateTestCaseData_openAPI() throws Exception {
        APICodesGenerator g = APICodesGenerator.build("http://open.dooioo.org/" +
                "rest/docs?module=%E6%88%BF%E6%BA%90&api=%E6%88%BF%E6%BA%90-%E8%8E%B7%E5%8F%96%E5%8C%BA%E5%9F%9F%E6%9D%BF%E5%9D%97%E5%9C%B0%E9%93%81%E4%BF%A1%E6%81%AF&version=v4"
                ,"enDorsedMobile","getReleaseVersion");
//        assertTrue(g.generateTestCaseData().exists());
        g.generateAllCodes();
    }

//    @Test
//    public void test_har_generator(){
//        APICodesGenerator g = APICodesGenerator
//                .build("har.json",
//                        "salary/updateBaseInfo", "salary", "UpdateBeanInfoAPI", "salary");
//        g.generateAllCodes();
//
//    }
//
    @Test
    public void test_har_generator_fluent(){

                APICodesGenerator
                .nameAPIName("UpdateBeanInfoAPI")
                .nameAPICategory("salary").nameAPIDomainName("salary")
                        .openAPIURL("http://192.168.3.85:9000/", "admin","lianjia")
                        .setOpenAPIPortalUrl("http://192.168.3.85:9000/")
                .generateAllCodes();

    }
}
