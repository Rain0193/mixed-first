package io.ift.automation.commonflows.web.pages;

import io.ift.automation.testscaffold.webtest.annotations.UIAction;
import io.ift.automation.testscaffold.webtest.annotations.UIActions;
import io.ift.automation.testscaffold.webtest.webUI.annotations.webpage.ElementName;
import com.dooioo.automation.testscaffold.webtest.webUI.htmlelements.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import io.ift.automation.testscaffold.webtest.webUI.htmlelements.BasePage;
import io.ift.automation.testscaffold.webtest.webUI.htmlelements.Button;
import io.ift.automation.testscaffold.webtest.webUI.htmlelements.InputBox;
import io.ift.automation.testscaffold.webtest.webUI.htmlelements.Link;
import io.ift.automation.testscaffold.webtest.webUI.htmlelements.Radio;

@UIActions(
    actions = {
            @UIAction(processName = "登陆",
                    elementActionDescription = {"userCode input"
                            ,"password input"
                            ,"companyId selectIfPresent"
                            ,"submit click"})
    }
)
public class LoginPage extends BasePage {

    @FindBy(id= "usercode")
    private InputBox userCode;
    @FindBy(id="password")
    private InputBox password;
    @FindBy(id="msg")
    private WebElement msg;

    @FindBy(name="companyId")
    @ElementName(elementName = "公司名称选择")
    private Radio companyId;

    @FindBy(className = "btn_login")
    private Button submit;

    @FindBy(xpath="//div[@class='navTop']//a[text()='退出']")
    private Link logOut;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public InputBox getUserCode() {
        return userCode;
    }

    public void setUserCode(InputBox userCode) {
        this.userCode = userCode;
    }

    public InputBox getPassword() {
        return password;
    }

    public void setPassword(InputBox password) {
        this.password = password;
    }

    public WebElement getMsg() {
        return msg;
    }

    public void setMsg(WebElement msg) {
        this.msg = msg;
    }

    public Button getSubmit() {
        return submit;
    }

    public void setSubmit(Button submit) {
        this.submit = submit;
    }

    public Link getLogOut() {
        return logOut;
    }

    public void setLogOut(Link logOut) {
        this.logOut = logOut;
    }

    public Radio getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Radio companyId) {
        this.companyId = companyId;
    }
}
