package io.ift.automation;

import io.ift.automation.helpers.PropertiesHelper;
import io.ift.automation.helpers.StringHelper;
import com.google.common.base.MoreObjects;

/**
 * Created by patrick on 15/3/3.
 * 添加中文说明,详细
 * @version ${Id}$
 */


public class Environment{

    private EnvironmentName name;
    private String dbURL;
    private String dbDriver;
    private String dbUserName;
    private String dbPassword;
    private String serviceROOTPath;
    private String homeUrl;
    private String openAPIRoot;
    private String domainUrl;
    public Environment(String name, String dbURL, String dbDriver, String dbUserName
            , String dbPassword, String serviceROOTPath
            ,String homeUrl,String openAPIRoot,String domainUrl) {
        this.name = EnvironmentName.getEnvironmentName(name);
        this.dbURL = PropertiesHelper.getProperty(dbURL);
        this.dbDriver = PropertiesHelper.getProperty(dbDriver);
        this.dbUserName = PropertiesHelper.getProperty(dbUserName);
        this.dbPassword = PropertiesHelper.getProperty(dbPassword);
        this.serviceROOTPath=PropertiesHelper.getProperty(serviceROOTPath);
        this.homeUrl=PropertiesHelper.getProperty(homeUrl);
        this.openAPIRoot =PropertiesHelper.getProperty(openAPIRoot);
        this.domainUrl=PropertiesHelper.getProperty(domainUrl);

    }

    public EnvironmentName getName() {
        return name;
    }

    public void setName(EnvironmentName name) {
        this.name = name;
    }

    public String getDbURL() {
        return dbURL;
    }

    public void setDbURL(String dbURL) {
        this.dbURL = dbURL;
    }

    public String getDbDriver() {
        return dbDriver;
    }

    public void setDbDriver(String dbDriver) {
        this.dbDriver = dbDriver;
    }

    public String getDbUserName() {
        return dbUserName;
    }

    public void setDbUserName(String dbUserName) {
        this.dbUserName = dbUserName;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public void setDbPassword(String dbPassword) {
        this.dbPassword = dbPassword;
    }

    public String getServiceROOTPath() {
        return serviceROOTPath;
    }

    public void setServiceROOTPath(String serviceROOTPath) {
        this.serviceROOTPath = serviceROOTPath;
    }

    public String getHomeUrl() {
        return homeUrl;
    }

    public void setHomeUrl(String homeUrl) {
        this.homeUrl = homeUrl;
    }

    public String getOpenAPIRoot() {
        return openAPIRoot;
    }

    public void setOpenAPIRoot(String openAPIRoot) {
        this.openAPIRoot = openAPIRoot;
    }

    public String getDomainUrl() {
        if(!StringHelper.isNotEmptyOrNotBlankString(domainUrl)){
            //this only make backward compatible
            return PropertiesHelper.getProperty(this.name+"."+"domain.url");
        }
        return domainUrl;
    }

    public void setDomainUrl(String domainUrl) {
        this.domainUrl = domainUrl;
    }

    public enum EnvironmentName{
        TEST,INTEGRATION,PREPROD,PROD,ME;

        public static EnvironmentName getEnvironmentName(String name){
        //todo move to static map
            if ("test".equalsIgnoreCase(name)) {
                return EnvironmentName.TEST;
            } else if ("prod".equalsIgnoreCase(name)) {
                return EnvironmentName.PROD;
            } else if ("preprod".equalsIgnoreCase(name) || "latest".equalsIgnoreCase(name)) {
                return EnvironmentName.PREPROD;
            } else if ("Integration".equalsIgnoreCase(name)){
                return EnvironmentName.INTEGRATION;
            }else if("ME".equalsIgnoreCase(name)){
                return EnvironmentName.ME;
            }else {
                throw new RuntimeException("环境名称设置不对，目前可以选择的名称是test,me,integration,prod");
            }
        }
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("name", name)
                .add("dbURL", dbURL)
                .add("dbDriver", dbDriver)
                .add("dbUserName", dbUserName)
                .add("dbPassword", dbPassword).toString();
    }
}
