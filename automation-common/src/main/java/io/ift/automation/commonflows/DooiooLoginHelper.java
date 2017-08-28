package io.ift.automation.commonflows;

import io.ift.automation.Environment;
import io.ift.automation.commonflows.apis.GetOAuth2TokenAPI;
import io.ift.automation.commonflows.base.api.AccessTokenResponse;
import io.ift.automation.commonflows.models.EmployeeTestData;
import io.ift.automation.commonflows.web.flows.LoginWebFlow;
import com.domain.automation.helpers.*;

import io.ift.automation.helpers.RestTemplateClientHelper;
import io.ift.automation.helpers.webdriver.WebDriverHelper;
import io.ift.automation.logging.TestResultLogger;
import io.ift.automation.testscaffold.TestAction;
import io.ift.automation.testscaffold.apitest.RequestData;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.List;

import io.ift.automation.helpers.EnvironmentHelper;
import io.ift.automation.helpers.JSONHelper;
import io.ift.automation.helpers.PropertiesHelper;

/**
 * Created by patrick on 15/4/24.
 *
 * @version $Id$
 */


public class domainLoginHelper {

    private static final String GRANT_TYPE = "grant_type";
    private static final String CLIENT_ID = "client_id";
    private static final String CLIENT_SECRET = "client_secret";
    public static final String USER_CODE = PropertiesHelper.getProperty("TESTER_USER_CODE", "default");
    public static final String PASSWORD = PropertiesHelper.getProperty("TEST_PASSWORD","defaultemail@");

    private domainLoginHelper() {
    }

    /**
     * 获取登陆的Accesstoken，已经是Bearer开头的
     *
     * @return
     */
    public static String getAccessToken() {
        RequestData data = RequestData.build()
                .addQueryParameter(GRANT_TYPE, "client_credential")
                .addQueryParameter(CLIENT_ID, PropertiesHelper.getValueFor(CLIENT_ID))
                .addQueryParameter(CLIENT_SECRET, PropertiesHelper.getValueFor(CLIENT_SECRET));
        GetOAuth2TokenAPI api = new GetOAuth2TokenAPI(data);
        api.execute();
        AccessTokenResponse r = JSONHelper.toBean(api.getResponse(), AccessTokenResponse.class);
        return "Bearer " + r.getAccess_token();
    }

    /**
     * 获取AccessToken的Response类
     *
     * @return
     * @see AccessTokenResponse
     */
    public static AccessTokenResponse getAccessTokenResponse() {
        RequestData data = RequestData.build()
                .addQueryParameter(GRANT_TYPE, "client_credential")
                .addQueryParameter(CLIENT_ID, PropertiesHelper.getValueFor(CLIENT_ID))
                .addQueryParameter(CLIENT_SECRET, PropertiesHelper.getValueFor(CLIENT_SECRET));
        GetOAuth2TokenAPI api = new GetOAuth2TokenAPI(data);
        api.execute();
        return JSONHelper.toBean(api.getResponse(), AccessTokenResponse.class);
    }

    /**
     * 根据用户名和密码获取AccessToken，已经用Bearer 开始了
     *
     * @param username 用户employee id
     * @param password 用户的密码
     * @return
     */
    public static String getAccessTokenByUser(String username, String password) {
        RequestData data = RequestData.build()
                .addQueryParameter(GRANT_TYPE, PropertiesHelper.getValueFor(GRANT_TYPE))
                .addQueryParameter(CLIENT_ID, PropertiesHelper.getValueFor(CLIENT_ID))
                .addQueryParameter(CLIENT_SECRET, PropertiesHelper.getValueFor(CLIENT_SECRET))
                .addQueryParameter("username", username).addQueryParameter("password", password);
        GetOAuth2TokenAPI api = new GetOAuth2TokenAPI(data);
        api.execute();
        return "Bearer " + JSONHelper.toBean(api.getResponse(), AccessTokenResponse.class).getAccess_token();
    }

    /**
     * 获取测试站的登陆头，用来设置cookie，用来测试非open api
     *
     * @param empNo 用户的employee id
     * @return set-cookie的值
     */
    public static List<String> getTestLoginHeader(String empNo) {
        String urlLogin = "https://login.domain.net:18100/login?usercode=" + empNo + "&password=12345&companyId=1"+"&encrypt=false";
        ResponseEntity response = RestTemplateClientHelper
            .getHttpClientImplInstance().call(urlLogin, HttpMethod.POST);
        return response.getHeaders().get("set-cookie");
    }

    /**
     * 获取集成环境站的登陆头，用来设置cookie，用来测试非open api
     * 请使用 domainHelper#getLoggedInAPIClient
     *
     * @param empNo
     * @param password
     * @return
     */
    @Deprecated
    public static List<String> getIntegrationLoginHeader(String empNo, String password) {
        String urlLogin = "https://login.domain.org/login?usercode=" + empNo + "&password=" + password + "&companyId=1&encrypt=false";
        ResponseEntity response = RestTemplateClientHelper.getHttpClientImplInstance().call(urlLogin, HttpMethod.POST);
        return response.getHeaders().get("set-cookie");
    }

    /**
     * 获取集成环境站的万能密码
     *
     * @param empNo    employeeId
     * @param password 密码
     * @return 万能密码
     */
    public static String getIntegrationLoginPassword(String empNo, String password) {
        return getIntegrationLoginPassword(new EmployeeTestData(empNo, password));
    }

    /**
     * 返回万能密码
     * @return
     */
    public static String getUniversalPassword(){
        if (Environment.EnvironmentName.INTEGRATION.toString().equalsIgnoreCase(EnvironmentHelper.getTargetEnvironmentName())) {
            return getIntegrationLoginPassword(USER_CODE,PASSWORD);
        } else {
            return "1";
        }
    }
    /**
     * @param testData 员工信息对象
     * @return
     */
    public static String getIntegrationLoginPassword(EmployeeTestData testData) {
        RestTemplateClientHelper client =  RestTemplateClientHelper.getHttpClientImplInstance();
        String urlLogin = "https://login.domain.org/login?usercode="+testData.getUserCode()+"&password="+testData.getPassword()
                +"&companyId="+testData.getCompanyId()+"&encrypt=false";
        TestResultLogger.log("request_url={}", urlLogin);
        ResponseEntity response = client.call(urlLogin, HttpMethod.POST);
        client.addHeader("set-cookie",response.getHeaders().get("set-cookie"));
        ResponseEntity passwordResponse = client.call("https://login.domain.org/rootPassword/get"
                , HttpMethod.GET);
        TestResultLogger.log("response={}",passwordResponse.getBody().toString());
        try {
            return JSONHelper.getStringValue(passwordResponse, "password");
        } catch (Exception e) {

            throw new RuntimeException("获取万能密码失败，请检查环境是否正常", e);
        }

    }

    /**
     * 使用万能密码登陆
     *
     * @param employeeTestData 员工信息对象
     * @param driver
     */
    public static void loginBySuperPassword(EmployeeTestData employeeTestData, WebDriver driver) {
        //todo move the password to configuration
        String password = domainLoginHelper.getIntegrationLoginPassword(USER_CODE, PASSWORD);
        employeeTestData.setPassword(password);
        LoginWebFlow login = new LoginWebFlow(driver, employeeTestData);
        login.execute();
    }

    /**
     * 测试环境登陆
     *
     * @param driver
     */
    public static void loginInTestEnv(EmployeeTestData employeeTestData, WebDriver driver) {
        LoginWebFlow login = new LoginWebFlow(driver, employeeTestData);
        login.execute();
    }

    /**
     * 根据property 进行登陆
     *
     * @param empNo
     * @param password
     * @param driver
     */
    public static void login(String empNo, String password, WebDriver driver) {
        EmployeeTestData testData = new EmployeeTestData(empNo, password);
        login(testData, driver);
    }

    public static void login(EmployeeTestData testData, WebDriver driver,TestAction ...handlers) {

        if (Environment.EnvironmentName.INTEGRATION.toString().equalsIgnoreCase(EnvironmentHelper.getTargetEnvironmentName())) {
            loginBySuperPassword(testData, driver);
        } else {
            loginInTestEnv(testData, driver);
        }
        WebDriverHelper.get(driver, EnvironmentHelper.getDomainUrl());
        for (TestAction handler : handlers) {
            handler.execute();
        }
    }


    public static String password(EmployeeTestData testData) {
        if (EnvironmentHelper.getTargetEnvironmentName().equalsIgnoreCase(Environment.EnvironmentName.INTEGRATION.toString())) {
            return getIntegrationLoginPassword(testData);
        } else {
            return "1234";
        }
    }


    public static void logout(WebDriver driver) {
        WebDriverHelper.waitForSubmit(2000L);
        String logoutUrl = driver.findElement(By.xpath("//a[contains(@href,'logout')]")).getAttribute("href");
        WebDriverHelper.get(driver, logoutUrl);
    }


    //todo need refactor
    public static RestTemplateClientHelper getLoggedInAPIClient(String empNo, String password, String companyId) {
        String urlLogin;
        if (EnvironmentHelper.getTargetEnvironmentName().
                equalsIgnoreCase(Environment.EnvironmentName.INTEGRATION.toString())) {
            password = getIntegrationLoginPassword(new EmployeeTestData(USER_CODE, PASSWORD));
            urlLogin = "https://login.domain.org/login?usercode=" + empNo + "&password=" + password + "&companyId=" + companyId + "&encrypt=false";
        } else {
            String domainName = EnvironmentHelper.getTargetEnvironmentName();
            if (domainName.contains("me")) {
                urlLogin = "https://login.domain.me/login?usercode=" + empNo + "&password=" + password + "&companyId=" + companyId + "&encrypt=false";
            } else {
                urlLogin = "https://login.domain.net:18100/login?usercode=" + empNo + "&password=12345&companyId=" + companyId + "&encrypt=false";
            }
        }
        RestTemplateClientHelper client = RestTemplateClientHelper.getHttpClientImplInstance();
        ResponseEntity response = client.call(urlLogin, HttpMethod.POST);
        client.addHeader("set-cookie", response.getHeaders().get("set-cookie"));
        return client;
    }

    public static RestTemplateClientHelper getLoggedInAPIClient(EmployeeTestData employee){
       return getLoggedInAPIClient(employee.getUserCode(),employee.getPassword(),employee.getCompanyId());
    }

    public static RestTemplateClientHelper getLoggedInMicroServiceClient(String empNo,String password,String companyId){
        RestTemplateClientHelper client = RestTemplateClientHelper.defaultInstance();
        String requestUrl =String.format("http://api.route.domain.org/v1/client/token?userCode=%s&password=%s&companyId=%s"
                , empNo, password, companyId);
        ResponseEntity response = client.call(requestUrl,HttpMethod.POST);

        try {
            String xToken = JSONHelper.getStringValue(response, "token");
            client.addXToken(xToken);
        }catch (Exception e){
            client.addXToken("Error_Password");
        }

        return client;
    }

    /**
     * todo adaptor to microservice
     * @param employee
     * @return
     */
    public static RestTemplateClientHelper getLoggedInMicroServiceClient(EmployeeTestData employee){
        RestTemplateClientHelper client = RestTemplateClientHelper.defaultInstance();
        String requestUrl =String.format("http://api.route.domain.org/v1/client/token?userCode=%s&password=%s&companyId=%s"
                , employee.getUserCode(), employee.getPassword(), employee.getCompanyId());
        ResponseEntity response = client.call(requestUrl,HttpMethod.POST);

        try {
            String xToken = JSONHelper.getStringValue(response, "token");
            client.addXToken(xToken);
        }catch (Exception e){
            client.addXToken("Error_Password");
        }

        return client;
    }


}
