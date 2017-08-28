package io.ift.automation.commonflows.models;

import io.ift.automation.data.TestData;

/**
 * Created by patrick on 15/4/27.
 *
 * @version $Id$
 */


public class EmployeeTestData extends TestData {

    private String userCode;
    private String password="009";
    private String companyId ="1"; //1: domain shanghai,2:suzhou
    private String orgId;
    private String orgName;
    private String userName;
    private String position;
    private String positionName;

    public EmployeeTestData(String userCode, String password) {
        this.userCode = userCode;
        this.password = password;
    }

    public EmployeeTestData() {
    }

    public EmployeeTestData(String userCode,String password,String companyId) {
        this.userCode = userCode;
        this.companyId=companyId;
        this.password=password;
    }

    public EmployeeTestData(String userCode) {
        this.userCode = userCode;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }
}
