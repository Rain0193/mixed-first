package io.ift.automation.data;

/**
 * Created by patrick on 15/4/27.
 *
 * @version $Id$
 */


public class User extends TestData {
    private String userCode="abd";
    private String passWord="test";
    private String test;

    public User(String userName, String password) {
        this.userCode=userName;
        this.passWord=password;
    }

    public User() {
        this.test=this.userCode+this.passWord;
    }

    @Override
    public void dataComposeAfter() {
        System.out.println("data compose after is called");
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }
}
