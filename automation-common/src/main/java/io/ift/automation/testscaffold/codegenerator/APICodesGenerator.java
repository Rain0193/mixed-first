package io.ift.automation.testscaffold.codegenerator;

import com.alibaba.fastjson.JSON;
import com.beust.jcommander.internal.Maps;
import io.ift.automation.drivers.DriverFactory;
import com.dooioo.automation.helpers.*;

import io.ift.automation.helpers.ExcelHelper;
import io.ift.automation.helpers.FileHelper;
import io.ift.automation.helpers.RestTemplateClientHelper;
import io.ift.automation.helpers.StringHelper;
import io.ift.automation.helpers.TemplateReportHelper;
import io.ift.automation.helpers.webdriver.WebDriverHelper;
import io.ift.automation.testscaffold.apitest.APITestException;
import io.ift.automation.testscaffold.apitest.ServiceDescription;
import io.ift.automation.testscaffold.codegenerator.openapiparser.OpenAPISpecParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.regex.Pattern;

import static io.ift.automation.helpers.FileHelper.*;

/**
 * Created by patrick on 15/4/20.
 * todo: refactor to move API Description relative
 * and generate JSON file codes out of generator, the generator only accept:
 * API Description:
 * - Service Description
 * - API Domain Name
 * - API Name
 * - API Category
 *
 * @version $Id$
 */


public class APICodesGenerator implements TestCodeGenerator {

    private static final String API_CODE_BASEPATH = "com" + File.separator + "dooioo" + File.separator + "automation" + File.separator + "apis" + File.separator;
    private static final String PROJECT_SRC_PATH = getSrcPath();
    private static final String PROJECT_TESTSRC_PATH = getTestSrcPath();
    private static final String API_CLIENTCODE_TEMPLATE = "api_client_code.ftl";
    private static final String API_TESTCASECODE_TEMPLATE = "api_testcase_code.ftl";
    private static final String API_TESTRESOURCE_LOCATION = getTestResourcePath();
    private static final String API_RESOURCE_LOCATION = getResourcePath();
    private static final String JSONPOSTFIX = ".json";
    private static final String TESTCASE_API_PATH = "testcase" + File.separator + "apis" + File.separator;
    private static final String SD_PATH = "servicedescription";
    private String apiCategory;
    private String apiDescriptionPath;
    private String openApiDescriptionUrl;
    private String apiName;
    private String apiDomainName;
    private ServiceDescription sd;
    private String openAPIUsername = "admin";
    private String openAPIPassword = "1";
    private String openAPIPortalUrl = "http://open.dooioo.org";
    private WebDriver driver = new HtmlUnitDriver();
    private boolean useWebDriver = false;
    private static final Logger logger = LogManager.getLogger(APICodesGenerator.class.getName());


    public APICodesGenerator nameAPICategory(String apiCategory) {
        if (apiCategory.startsWith(File.separator))
            throw new RuntimeException("api category should not start with / or \\");
        this.apiCategory = apiCategory;
        return this;
    }

    public static APICodesGenerator nameAPIName(String apiName) {
        APICodesGenerator g = new APICodesGenerator();
        g.apiName = apiName;
        return g;
    }

    public APICodesGenerator nameAPIDomainName(String apiDomainName) {
        this.apiDomainName = apiDomainName;
        return this;
    }

    /**
     * build api generator by api description path(json location), and api domain name
     *
     * @param apiDescriptionPath
     * @param apiDomainName
     * @return
     */
    public static APICodesGenerator build(String apiDescriptionPath,
                                          String apiDomainName) {
        APICodesGenerator g = build(apiDescriptionPath);
        g.apiDomainName = apiDomainName;
        return g;

    }

    /**
     * build api generator by api description path(json location)
     *
     * @param apiDescriptionPath
     * @return
     */
    public static APICodesGenerator build(String apiDescriptionPath) {

        if (apiDescriptionPath.startsWith("http")) {
            throw new APITestException("请传入API描述JSON文件路径！");
        }
        APICodesGenerator g = new APICodesGenerator();
        g.apiDescriptionPath = apiDescriptionPath;
        String path[] = apiDescriptionPath.split(Pattern.quote(File.separator));
        g.apiCategory = path.length == 3 ? path[1] : File.separator; //init api category path
        String jsonFileName = path[path.length - 1];
        g.apiName = StringHelper
            .capitalize(jsonFileName.substring(0, jsonFileName.lastIndexOf(JSONPOSTFIX)));
        g.sd = ServiceDescription.loadServiceDescriptionFromFile(apiDescriptionPath);
        return g;

    }

    /**
     * use Har File to generate
     *
     * @param harFilePath
     * @param resourceUrlExp
     * @return
     */
    public APICodesGenerator useHarFile(String harFilePath, String resourceUrlExp) {

        if (harFilePath.startsWith("http") || !harFilePath.endsWith(".json")) {
            throw new APITestException("请传入API描述HAR文件路径！");
        }
        try {
            this.sd = ServiceDescription.loadServiceDescriptionFromFile(harFilePath, resourceUrlExp);
            this.sd.setApiDomainName(this.apiDomainName);
        } catch (Exception e) {
            throw new RuntimeException("please check your input HAR file,or your resourceUrl Expression", e);
        }

        this.writeJsonFile(apiCategory, apiName, this.sd);
        this.apiDescriptionPath = SD_PATH + File.separator + apiCategory + File.separator + this.apiName + JSONPOSTFIX;
        return this;

    }

    public APICodesGenerator useWebDriver(boolean flag) {
        this.useWebDriver = flag;
        return this;
    }

    /**
     * set the openAPIURL
     *
     * @param url
     * @return
     */
    public APICodesGenerator openAPIURL(String url) {
        this.openApiDescriptionUrl = url;
        this.apiDomainName = "openapi";
        return this;
    }

    /**
     * reset the url,user name,password
     *
     * @param url
     * @param openAPIUserName
     * @param openAPIPassword
     * @return
     */
    public APICodesGenerator openAPIURL(String url,
                                        String openAPIUserName,
                                        String openAPIPassword) {
        this.openApiDescriptionUrl = url;
        this.apiDomainName = "openapi";
        this.openAPIUsername = openAPIUserName;
        this.openAPIPassword = openAPIPassword;
        return this;
    }

    public APICodesGenerator useJsonFile(String apiDescriptionPath) {
        this.apiDescriptionPath = apiDescriptionPath;
        String path[] = apiDescriptionPath.split(Pattern.quote(File.separator));
        if (this.apiCategory == null) {
            this.apiCategory = path.length == 3 ? path[1] : File.separator;
        }
        String jsonFileName = path[path.length - 1];
        if (this.apiName == null) {
            apiName = StringHelper.capitalize(jsonFileName.substring(0, jsonFileName.lastIndexOf(JSONPOSTFIX)));
        }
        this.sd = ServiceDescription.loadServiceDescriptionFromFile(apiDescriptionPath);
        this.apiDomainName = sd.getApiDomainName();
        return this;
    }

    public static APICodesGenerator build(String openApiUrl
            , String apiCategory
            , String apiName) {
        APICodesGenerator g = new APICodesGenerator();
        g.apiCategory = apiCategory;
        g.openApiDescriptionUrl = openApiUrl;
        g.apiName = StringHelper.capitalize(apiName);
        g.useWebDriver=true;
        return g;
    }

    /**
     * user firefox driver
     *
     * @return
     */
    public APICodesGenerator useFirefox() {
        driver = new FirefoxDriver();
        return this;
    }

    /**
     * use chrome driver
     *
     * @return
     */
    public APICodesGenerator useChromeDriver() {
        driver = DriverFactory.get();
        return this;
    }

    @Override
    public File generateTestCaseData() {

        FileHelper.createDir(API_TESTRESOURCE_LOCATION + "testcase");
        FileHelper.createDir(API_TESTRESOURCE_LOCATION + TESTCASE_API_PATH);
        if (!File.separator.equalsIgnoreCase(apiCategory)) {
            //默认最多使用两层API Category结构,偷懒做法不想太复杂
            String[] folders = apiCategory.split(Pattern.quote("/"));
            if (folders.length == 2) {
                FileHelper.createDir(API_TESTRESOURCE_LOCATION + TESTCASE_API_PATH + folders[0]);
                FileHelper.createDir(API_TESTRESOURCE_LOCATION + TESTCASE_API_PATH + folders[0] + File.separator + folders[1]);
            } else {
                FileHelper.createDir(API_TESTRESOURCE_LOCATION + TESTCASE_API_PATH + apiCategory);
            }

        }
        String filePath = API_TESTRESOURCE_LOCATION + TESTCASE_API_PATH + apiCategory + File.separator + apiName + "_testcase.xls";

        return ExcelHelper.build().createTestCaseDataToExcel(filePath
                , sd, this.apiName);
    }

    @Override
    public File generateTestCaseCode() {
        try {
            return generateAPITestCaseCode();
        } catch (Exception e) {
            throw new RuntimeException("generate API test case failed");
        }

    }

    @Override
    public File generateTestStepCode() {
        try {
            return generateAPICode();
        } catch (Exception e) {
            throw new CodeGenerationException("generate API Codes failed");
        }
    }

    private File createAPIDirs(String srcBasePath) {

        FileHelper.createDir(srcBasePath + API_CODE_BASEPATH);
        return FileHelper.createDir(srcBasePath + API_CODE_BASEPATH, apiCategory);

    }

    /**
     * 生成API 客户端代码
     *
     * @return
     * @throws Exception
     */
    private File generateAPICode() throws Exception {
        File file = createAPIDirs(PROJECT_SRC_PATH);
        String fileLocation = file.getAbsolutePath() + File.separator + apiName + ".java";
        Map<String, String> dataSet = Maps.newHashMap();
        dataSet.put("api_name", apiName);
        if (apiDomainName != null) {
            dataSet.put("api_domain_name", apiDomainName);
        }
        if (!File.separator.equalsIgnoreCase(apiCategory)) {
            String apiPackage = apiCategory.endsWith(File.separator) ? apiCategory.substring(0, apiCategory.length() - 1) : apiCategory;
            dataSet.put("package_name", apiPackage.replace("/", ".").replace("\\", "."));
        }
        dataSet.put("service_description_path", apiDescriptionPath.replaceAll(Pattern.quote(File.separator), "/"));
        return TemplateReportHelper.generateAPICodes(dataSet, API_CLIENTCODE_TEMPLATE, fileLocation);
    }

    /**
     * 生成API Test 类代码
     *
     * @return
     * @throws Exception
     */
    private File generateAPITestCaseCode() throws Exception {
        File file = createAPIDirs(PROJECT_TESTSRC_PATH);
        String fileLocation = file.getAbsolutePath() + File.separator + apiName + "Test.java";
        Map<String, Object> dataSet = Maps.newHashMap();
        dataSet.put("api_name", apiName);
        if (!File.separator.equalsIgnoreCase(apiCategory)) {
            String apiPackage = apiCategory.endsWith(File.separator) ? apiCategory.substring(0, apiCategory.length() - 1) : apiCategory;
            dataSet.put("package_name", apiPackage.replace("/", ".").replace("\\", "."));
        }
        dataSet.put("isOpenAPI", this.apiDomainName.toLowerCase().contains("openapi"));
        dataSet.put("package_dir", apiCategory);
        return TemplateReportHelper.generateAPICodes(dataSet, API_TESTCASECODE_TEMPLATE, fileLocation);
    }


    /**
     * 生成API Service Description json文件
     *
     * @param openApiUrl
     * @param apiCategory
     * @param apiName
     * @return
     */
    private ServiceDescription generateJsonFileFromOpenApiPortal(String openApiUrl,
                                                                 String apiCategory,
                                                                 String apiName) {
        ServiceDescription sd ;
        try {
            String pageSource = useWebDriver ? getSourceCodeByWebDriver(openApiUrl) : getPageSourceByRestClient(openApiUrl);
            sd = OpenAPISpecParser.build(pageSource).parse().toServiceDescription();
            sd.setApiDomainName("openapi");
            writeJsonFile(apiCategory, apiName, sd);

        } finally {
            driver.quit();
        }

        return sd;
    }

    /**
     * Get API Doc source code by webdriver
     *
     * @param openApiUrl
     * @return
     */
    private String getSourceCodeByWebDriver(String openApiUrl) {
        driver.get(this.openAPIPortalUrl);
        WebDriverHelper.input(driver, By.id("login_username"), this.openAPIUsername);
        WebDriverHelper.input(driver, By.id("login_password"), this.openAPIPassword);
        WebDriverHelper.click(driver, By.id("login_0"));
        driver.get(openApiUrl);
        return driver.getPageSource();
    }

    /**
     * get API Doc Source code by RestHttpClient
     *
     * @param openApiUrl
     * @return
     */
    private String getPageSourceByRestClient(String openApiUrl) {
        String urlLogin = String.format("%s/login?username=%s&password=%s", this.openAPIPortalUrl, this.openAPIUsername, this.openAPIPassword);
        RestTemplateClientHelper clientHelper = RestTemplateClientHelper.defaultInstance();
        clientHelper.call(urlLogin, HttpMethod.POST);
        try {
            ResponseEntity res = clientHelper.call(java.net.URLDecoder.decode(openApiUrl, "UTF-8"), HttpMethod.GET);
            return res.getBody().toString();
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("获取API文档失败,请检查网站是否可用");
        }
    }

    /**
     * write description to a json file
     *
     * @param apiCategory
     * @param apiName
     * @param sd
     */
    private File writeJsonFile(String apiCategory, String apiName, ServiceDescription sd) {
        String fileDir = SD_PATH + File.separator + apiCategory + File.separator;

        FileHelper.createDir(API_TESTRESOURCE_LOCATION + SD_PATH + File.separator);
        FileHelper.createDir(API_RESOURCE_LOCATION + SD_PATH + File.separator);
        FileHelper.createDir(API_TESTRESOURCE_LOCATION + SD_PATH + File.separator, apiCategory);
        FileHelper.createDir(API_RESOURCE_LOCATION + SD_PATH + File.separator, apiCategory);
        FileHelper.writeToFile(API_TESTRESOURCE_LOCATION + fileDir + apiName + JSONPOSTFIX, JSON.toJSONString(sd), false);
        return FileHelper.writeToFile(API_RESOURCE_LOCATION + fileDir + apiName + JSONPOSTFIX, JSON.toJSONString(sd), false);
    }


    @Override
    public void generateAllCodes() {

        this.apiDescriptionPath = SD_PATH + File.separator + apiCategory + File.separator
                + this.apiName + JSONPOSTFIX;

        try {
            generateAllAPICodes();
        } catch (Exception e) {
            logger.error("error_exception={}", e);
        }
    }

    /**
     * 生成API代码
     *
     * @throws Exception
     */
    private void generateAllAPICodes() throws Exception {
        if (this.openApiDescriptionUrl != null) {
            this.sd = generateJsonFileFromOpenApiPortal(this.openApiDescriptionUrl, this.apiCategory, this.apiName);
            this.apiDomainName = this.sd.getApiDomainName();
        }
        generateAPICode();
        generateTestCaseData();
        generateAPITestCaseCode();
    }

    /**
     * 设置Open API UserName
     *
     * @param openAPIUsername
     * @return
     */
    public APICodesGenerator setOpenAPIUsername(String openAPIUsername) {
        this.openAPIUsername = openAPIUsername;
        return this;
    }

    /**
     * 设置Open API Password
     *
     * @param openAPIPassword
     * @return
     */
    public APICodesGenerator setOpenAPIPassword(String openAPIPassword) {
        this.openAPIPassword = openAPIPassword;
        return this;
    }

    /**
     * 设置API Portal URl
     *
     * @param openAPIPortalUrl
     * @return
     */
    public APICodesGenerator setOpenAPIPortalUrl(String openAPIPortalUrl) {
        this.openAPIPortalUrl = openAPIPortalUrl;
        return this;
    }
}
