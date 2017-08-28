package io.ift.automation.commonflows.activities.checkpoints;

import io.ift.automation.commonflows.activities.ActivityProviders;
import io.ift.automation.commonflows.activities.nodes.ActivityNodeMode;
import io.ift.automation.helpers.ReflectionHelper;
import io.ift.automation.testscaffold.CheckPoint;
import io.ift.automation.testscaffold.Where;
import io.ift.automation.testscaffold.webtest.actions.WebTestActionBuilder;
import io.ift.automation.testscaffold.webtest.webUI.htmlelements.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Created by patrick on 15/9/1.
 */
public class DefaultActivityCantProcessCheckPoint implements CheckPoint {

    private ActivityProviders activityContext;
    private WebDriver driver;
    private Class<? extends BasePage> pageClass;

    public DefaultActivityCantProcessCheckPoint(ActivityProviders activityContext,
                                                WebDriver driver, Class<? extends BasePage> pageClass) {
        this.activityContext = activityContext;
        this.driver = driver;
        this.pageClass = pageClass;
    }

    @Override
    public void verify() {

        //quick return
        if (!activityContext.getContext().getCurrentStatus().equals(ActivityNodeMode.CANTAPPROVE) &&
                !activityContext.getContext().getCurrentStatus().equals(ActivityNodeMode.CANTREJECT)) return;
        checkApproveButton();

    }

    @Override
    public Where where() {
        String pageName = pageClass.getName();
        return new Where(pageName+"."+"search",pageName+".query");
    }

    /**
     *  如果有需要可以被重写(override)
     */
    public void checkApproveButton() {
        BasePage page = WebTestActionBuilder.use(pageClass, driver);
        WebElement approve = (WebElement) ReflectionHelper.getFieldValue(page, "approve");
        try {
            if (!approve.isDisplayed() && !approve.isEnabled()) {
                sa.assertTrue(true,"验证不能找到审批确认按钮");
            }else{
                sa.assertTrue(false,"验证不能找到审批确认按钮");
            }
        } catch (Exception e) {
            sa.assertTrue(true,"验证不能找到审批确认按钮");
        }
    }



}
