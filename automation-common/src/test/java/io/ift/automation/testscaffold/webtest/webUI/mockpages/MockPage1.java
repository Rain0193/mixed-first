package io.ift.automation.testscaffold.webtest.webUI.mockpages;

import io.ift.automation.testscaffold.webtest.actions.ExecutablePageObject;
import io.ift.automation.testscaffold.webtest.annotations.CompositeAction;
import io.ift.automation.testscaffold.webtest.annotations.UIAction;
import io.ift.automation.testscaffold.webtest.annotations.UIActions;
import io.ift.automation.testscaffold.webtest.webUI.annotations.webpage.ElementName;
import io.ift.automation.testscaffold.webtest.webUI.htmlelements.InputBox;
import io.ift.automation.testscaffold.webtest.webUI.htmlelements.Radio;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

/**
 * Created by patrick on 15/6/18.
 *
 * @version $Id: MockPage1.java 1737 2015-08-19 01:29:57Z wuke $
 */

@UIActions(actions = {@UIAction(processName = "新增住宅", elementActionDescription = {"propertyUsage", "estateName", "address", "roomNo"})
})
public class MockPage1 extends ExecutablePageObject {
    @FindBy(name = "property.propertyUsage")
    @ElementName(elementName = "property.propertyUsage")
    private Radio propertyUsage;
    @FindBy(name = "property.estateName")
    @ElementName(elementName = "property.estateName")
    private InputBox estateName;
    @FindBy(name = "property.address")
    @ElementName(elementName = "property.address")
    private InputBox address;
    @FindBy(name = "property.roomNo")
    @ElementName(elementName = "property.roomNo")
    private InputBox roomNo;
    @FindBy(name = "property.permitNo")
    @ElementName(elementName = "property.permitNo")
    private InputBox permitNo;

    public MockPage1(WebDriver driver) {
        super(driver);
    }

    public Radio getPropertyUsage() {
        return propertyUsage;
    }

    public void setPropertyUsage(Radio propertyUsage) {
        this.propertyUsage = propertyUsage;
    }

    public InputBox getEstateName() {
        return estateName;
    }

    public void setEstateName(InputBox estateName) {
        this.estateName = estateName;
    }

    public InputBox getAddress() {
        return address;
    }

    public void setAddress(InputBox address) {
        this.address = address;
    }

    public InputBox getRoomNo() {
        return roomNo;
    }

    @CompositeAction
    public void testMethodInvoke(String test){
        System.out.println("invoke test method invoke"+test);
    }
    public void setRoomNo(InputBox roomNo) {
        this.roomNo = roomNo;
    }

    public InputBox getPermitNo() {
        return permitNo;
    }

    public void setPermitNo(InputBox permitNo) {
        this.permitNo = permitNo;
    }
}
