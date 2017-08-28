package io.ift.automation.testscaffold.webtest.webUI.mockpages;

import io.ift.automation.data.TestData;
import io.ift.automation.testscaffold.webtest.actions.BaseWebTestAction;
import io.ift.automation.testscaffold.webtest.actions.WebTestActionBuilder;
import io.ift.automation.testscaffold.webtest.webUI.elementloader.ModifiedPageFactory;
import org.openqa.selenium.WebDriver;

/**
 * Created by patrick on 15/6/18.
 *
 * @version $Id: MockFlow.java 1771 2015-08-21 06:02:12Z wuke $
 */


public class MockFlow extends BaseWebTestAction {

    public MockFlow(WebDriver driver, TestData testData) {
        super(driver, testData);
    }

    @Override
    public void execute() {
        MockPage1 page1 = ModifiedPageFactory.createPageObject(driver,MockPage1.class);
        page1.getPropertyUsage().selectByVisibleText(testData.get("propertyUsage"));
        page1.getAddress().input(testData.get("address"));
        page1.getEstateName().input(testData.get("estateName"));
        page1.getRoomNo().input(testData.get("roomNo"));
        MockPage2 page2 = ModifiedPageFactory.createPageObject(driver,MockPage2.class);
        page2.getContactName().input(testData.get("contractName"));

        WebTestActionBuilder.createTestActionByUIAction(MockPage1.class, "新增住宅",driver, testData).execute();
        WebTestActionBuilder.createTestActionByUIAction(MockPage2.class, "新增住宅",driver, testData).execute();
    }
}
