package io.ift.automation.data;

/**
 * Created by patrick on 15/6/16.
 *
 * @version $Id: MockAddFile.java 1468 2015-06-23 10:17:17Z wuke $
 */


public class MockAddFile {
    private String contactsPhone = "12345664";
    private String followContent="test";
    private String contactsType="开发伤";
    private String filingName="filename";
    private String submitTime;
    private String filingType;
    private String contactsName;

    public String getContactsPhone() {
        return contactsPhone;
    }

    public void setContactsPhone(String contactsPhone) {
        this.contactsPhone = contactsPhone;
    }

    public String getFollowContent() {
        return followContent;
    }

    public void setFollowContent(String followContent) {
        this.followContent = followContent;
    }

    public String getContactsType() {
        return contactsType;
    }

    public void setContactsType(String contactsType) {
        this.contactsType = contactsType;
    }

    public String getFilingName() {
        return filingName;
    }

    public void setFilingName(String filingName) {
        this.filingName = filingName;
    }

    public String getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(String submitTime) {
        this.submitTime = submitTime;
    }


    public String getFilingType() {
        return filingType;
    }

    public void setFilingType(String filingType) {
        this.filingType = filingType;
    }

    public String getContactsName() {
        return contactsName;
    }

    public void setContactsName(String contactsName) {
        this.contactsName = contactsName;
    }
}
