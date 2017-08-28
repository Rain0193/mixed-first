package io.ift.automation.testscaffold.webtest.webUI.htmlelements;

import io.ift.automation.drivers.DriverFactory;
import io.ift.automation.helpers.StringHelper;
import io.ift.automation.helpers.webdriver.WebDriverHelper;
import io.ift.automation.logging.TestResultLogger;
import com.google.common.base.MoreObjects;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.internal.WrapsElement;

import java.util.Arrays;
import java.util.List;

/**
 * Created by patrick on 15/3/18.
 *
 * @version $Id$
 */

public class HtmlElement implements WrapsElement
        , NamedElement, WebElement {

    public static final String CLEAR = "clear";
    public static final String GETNAME = "getName";
    public static final String GETLOCATION = "getLocation";
    public static final String SETNAME = "setName";
    public static final String GETSIZE = "getSize";
    public static final String ISENABLED = "isEnabled";
    public static final String GETTEXT = "getText";
    public static final String GETBY = "getBy";
    public static final String ISREADYONLY = "isReadyOnly";
    public static final String CLICKIFPRESENT = "clickIfPresent";
    public static final String CLEARANDINPUT = "clearAndInput";
    public static final String GETWRAPPEDELEMENT = "getWrappedElement";
    public static final String SETWRAPPEDELEMENT = "setWrappedElement";
    public static final String SETLOCATIONNAME = "setLocationName";
    public static final String SETBY = "setBy";
    public static final String GETLOCATIONNAME = "getLocationName";
    public static final String TRIGGERCLICK = "triggerClick";
    public static final String GETLOCATOREXPRESSION = "getLocatorExpression";
    public static final String SETLOCATOREXPRESSION = "setLocatorExpression";
    public static final String GETPRECONDITIONACTIONNAME = "getPreconditionActionName";
    public static final String SETPRECONDITIONACTIONNAME = "setPreconditionActionName";
    public static final String GETPRECONDITIONELEMENTXPATHLOCATOR = "getPreconditionElementXpathLocator";
    public static final String SETPRECONDITIONELEMENTXPATHLOCATOR = "setPreconditionElementXpathLocator";
    public static final String PERFORMPRECONDITIONACTION = "performPreconditionAction";
    public static final String MOVETO = "moveTo";
    public static final String GETATTRIBUTE = "getAttribute";
    public static final String SUBMIT = "submit";
    public static final String FINDELEMENT = "findElement";
    public static final String GETCSSVALUE = "getCssValue";
    public static final String ISDISPLAYED = "isDisplayed";
    public static final String FINDELEMENTS = "findElements";
    public static final String GETTAGNAME = "getTagName";
    public static final String CLICK = "click";
    public static final String ISSELECTED = "isSelected";
    public static final String SENDKEYS = "sendKeys";
    public static final String HIGHLIGHT = "highlight";
    public static final String INPUT = "input";

    private WebElement wrappedElement;
    private String name;
    private String locationName;
    private By by;
    private String locatorExpression;
    private String preconditionActionName= StringHelper.EMPTY;
    private String preconditionElementXpathLocator=StringHelper.EMPTY;
    private static final Logger logger = LogManager.getLogger(HtmlElement.class.getName());

    public static enum ActionsDescription{
        clickIfPresent,click,
    }

    public HtmlElement(String locatorExpression,
                       String name, String locationName) {
        this.locatorExpression = locatorExpression;
        this.name = name;
        this.locationName = locationName;
    }

    public HtmlElement(By by
            , String locationName, String name) {
        this.by = by;
        this.locationName = locationName;
        this.name = name;
    }

    public HtmlElement(String name, WebElement element) {
        this.wrappedElement = element;
        this.name = name;
    }

    public HtmlElement(WebElement element) {
        this.wrappedElement = element;
    }


    @Override
    public String getName() {
        return this.name;
    }

    public boolean isReadyOnly(){
        try{
            return getWrappedElement().getAttribute("readonly").equalsIgnoreCase("true");
        }catch (Exception e){
            return false;
        }

    }

    public void clickIfPresent() {

        WebDriverHelper.clickIfPresent(DriverFactory.get(), getWrappedElement());
    }

    /**
     * 点击按钮
     */
    @Override
    public void click() {

        TestResultLogger.log("开始点击 {} ", toString());
        int retry_time = 5;
        boolean flag = false;
        while (!flag && retry_time > 0) {
            TestResultLogger.log("开始点击 {}", toString());
            flag = WebDriverHelper.click(DriverFactory.get(), wrappedElement);
            if (flag) {
                WebDriverHelper.waitForSubmit(1000L);
                return;
            }
            retry_time--;
        }

        throw new WebDriverException(wrappedElement + "点击失败,可能是因为是加载时间太长了");

    }

    public void moveTo() {

        WebDriverHelper.moveTo(DriverFactory.get(),getWrappedElement());
//        Action action = new Actions(DriverFactory.get()).moveToElement(getWrappedElement()).build();
//        action.perform();
    }

    @Override
    public void submit() {
        TestResultLogger.log("通过{} 发送提交请求", toString());
        click();
        WebDriverHelper.waitForSubmit(4000L);
    }

    @Override
    public void sendKeys(CharSequence... keysToSend) {
        TestResultLogger.log("开始输入 {} ", Arrays.toString(keysToSend));
        wrappedElement.sendKeys(keysToSend);
    }

    public void sendKeys(String inputs) {
        TestResultLogger.log("开始输入 {} in {}", inputs, toString());
        wrappedElement.sendKeys(inputs);
    }

    public void input(String inputs) {
        TestResultLogger.log("开始输入 {} in {}", inputs, toString());
        wrappedElement.sendKeys(inputs);
    }

    public void clearAndInput(String inputs) {
        TestResultLogger.log("开始清除并输入 {} in {}", inputs, toString());
        WebDriverHelper.waitForSubmit(500L);
        clear(); //workaround for cursor-to-end
        clear();
        wrappedElement.sendKeys(inputs);
    }

    @Override
    public void clear() {
        TestResultLogger.log("开始清理 {}", toString());
        wrappedElement.clear();
    }

    @Override
    public String getTagName() {
        logger.info("start get tag name for {}", toString());
        return wrappedElement.getTagName();
    }

    @Override
    public String getAttribute(String name) {

        logger.info("start get attribute name for {}", toString());
        return wrappedElement.getAttribute(name);
    }

    @Override
    public boolean isSelected() {
        logger.info("check is selected for {}", toString());
        return wrappedElement.isSelected();
    }

    @Override
    public boolean isEnabled() {
        logger.info("check is selected for {}", toString());
        return wrappedElement.isEnabled();
    }

    @Override
    public String getText() {
        logger.info(" get text in {}", toString());
        return getWrappedElement().getText();
    }

    @Override
    public List<WebElement> findElements(By by) {
        logger.info(" find elements {} for {}", by.toString(), toString());
        return wrappedElement.findElements(by);
    }

    @Override
    public WebElement findElement(By by) {
        logger.info(" find elements {} for {}", by.toString(), toString());
        return wrappedElement.findElement(by);
    }

    @Override
    public boolean isDisplayed() {
        logger.info("check is displayed for {}", toString());
        return wrappedElement.isDisplayed();
    }

    public void highlight() {
        DriverFactory.get().executeScript("arguments[0].style.border='3px solid red'", getWrappedElement());
    }


    @Override
    public Point getLocation() {
        logger.info("get location for {}", toString());
        return wrappedElement.getLocation();
    }

    @Override
    public Dimension getSize() {
        logger.info("get dimension for {}", toString());
        return wrappedElement.getSize();
    }

    @Override
    public String getCssValue(String propertyName) {
        logger.info("get CSS value for {} {}", toString(), propertyName);
        return wrappedElement.getCssValue(propertyName);
    }

    @Override
    public WebElement getWrappedElement() {
        return this.wrappedElement;
    }

    public void setWrappedElement(WebElement wrappedElement) {
        this.wrappedElement = wrappedElement;
    }

    public void setName(String name) {
        this.name = name;
    }


    public void setLocationName(String location) {
        this.locationName = location;
    }

    public By getBy() {
        return by;
    }

    public void setBy(By by) {
        this.by = by;
    }

    @Override
    public String getLocationName() {
        return locationName;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("name", name)
                .add("by", by == null ? "by" : by.toString())
                .toString();
    }

    public void triggerClick(String elementIds) {
        String[] ids = elementIds.split("-");
        for (String id : ids) {
            WebDriverHelper.getJSEvalValue(DriverFactory.get(), String.format("$(\"#%s\").trigger(\"click\")", id));
        }
    }

    public String getLocatorExpression() {
        return locatorExpression;
    }

    public void setLocatorExpression(String locatorExpression) {
        this.locatorExpression = locatorExpression;
    }

    public String getPreconditionActionName() {
        return preconditionActionName;
    }

    public void setPreconditionActionName(String preconditionActionName) {
        this.preconditionActionName = preconditionActionName;
    }

    public String getPreconditionElementXpathLocator() {
        return preconditionElementXpathLocator;
    }

    public void setPreconditionElementXpathLocator(String preconditionElementXpathLocator) {
        this.preconditionElementXpathLocator = preconditionElementXpathLocator;
    }

    /**
     * 在处理某个元素前进行指定操作
     */
    public void performPreconditionAction(){
        if(this.preconditionActionName==null) return;
        if(this.preconditionActionName.equalsIgnoreCase("moveTo")){

            if(StringHelper.isNotEmptyOrNotBlankString(this.preconditionElementXpathLocator)){
                logger.info("start moveTo action as precondition action ");
                WebDriverHelper.moveTo(DriverFactory.get(), By.xpath(this.preconditionElementXpathLocator));
            }
        }
    }
}
