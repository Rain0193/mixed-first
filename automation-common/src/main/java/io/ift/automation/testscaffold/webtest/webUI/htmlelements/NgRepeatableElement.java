package io.ift.automation.testscaffold.webtest.webUI.htmlelements;

import com.alibaba.fastjson.TypeReference;
import io.ift.automation.drivers.DriverFactory;
import io.ift.automation.helpers.CollectionsHelper;
import io.ift.automation.helpers.JSONHelper;
import io.ift.automation.helpers.StringHelper;
import io.ift.automation.helpers.webdriver.WebDriverHelper;
import io.ift.automation.testscaffold.webtest.webUI.elementloader.internal.HtmlElementsFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Map;

/**
 * Created by patrick on 15/6/26.
 *
 * @version $Id: NgRepeatableElement.java 1874 2015-09-24 09:00:15Z wuke $
 */


public class NgRepeatableElement extends HtmlElement {

    //trigger repeat input element, for example it is a button or link,
    //after click the button or link, one new row is added for adding new information
    private String repeatTriggerXpathLocator;
    private int initCount;
    private String offsetXPathForRepeatableRoot;

    public NgRepeatableElement(String name, WebElement element) {
        super(name, element);
    }

    public NgRepeatableElement(WebElement element) {
        super(element);
    }

    /**
     * 输入需要重复输入的数据
     * @param json
     */
    public void clearAndInput(String json){
        if(json==null) throw new RuntimeException("没有数据输入，请检查你的输入数据，位置是"+this.toString());
        List<Map<String,String>> source= JSONHelper.toMapList(json);
        WebDriver driver = DriverFactory.get();
        validate();

        //fill the initial count
        if(initCount==0){//如果初始化格式是没有name直接trigger输入
            triggerRepeatableAction(driver);

        }else{
            for (int i = 0; i <initCount ; i++) {
                inputOne(source, driver, i);
            }
        }

        //fill the rest count
        for (int i = initCount; i <source.size() ; i++) {
            inputOne(source, driver, i);
            if(i<source.size()-1){
                triggerRepeatableAction(driver);
            }
        }

    }

    private void triggerRepeatableAction(WebDriver driver) {
        if(StringHelper.isNotEmptyOrNotBlankString(repeatTriggerXpathLocator)){
            WebDriverHelper.click(driver, driver.findElement(By.xpath(repeatTriggerXpathLocator)));
        }
    }


    /**
     *
     * todo need to think about
     * 输入某一行数据
     * @param source
     * @param driver
     * @param i index
     */
    private void inputOne(List<Map<String, String>> source, WebDriver driver, int i) {

        for (Map.Entry<String,String> entry : source.get(i).entrySet()) {
            //key:ng-model name,value:value
            String offsetXpath=generateXPath(entry.getKey());
            List<WebElement> displayedElements = CollectionsHelper.filterForList(
                    getWrappedElement().findElements(By.xpath(offsetXpath)),
                    input -> {
                        String modelName = input.getAttribute("ng-model").toUpperCase();
                        return input.isDisplayed() && modelName.contains(entry.getKey().toUpperCase());
                    });

            if(displayedElements.get(i).getTagName().equalsIgnoreCase("input")){
                WebDriverHelper.input(driver, displayedElements.get(i), entry.getValue());
            }else{
                if(displayedElements.get(i).getTagName().equalsIgnoreCase("select")){
                    SelectList list = HtmlElementsFactory
                        .createHtmlElementInstance(SelectList.class, displayedElements.get(i));
                    list.selectByVisibleText(entry.getValue());
                }
            }
        }

    }

    public void fillAll (String json){
        if(json==null) throw new RuntimeException("没有数据输入，请检查你的输入数据，位置是"+this.toString());
        List<Map<String,String>> sources=JSONHelper.toMapList(json);
        WebDriver driver = DriverFactory.get();
        validate();
        String offsetXpath;
        for (Map<String, String> source: sources) {
            for (Map.Entry<String, String> entry : source.entrySet()) {
                //entry.key: model name: entry.value(): value
                if(StringHelper.isNotEmptyOrNotBlankString(entry.getValue())){//ignore empty field
                    offsetXpath =generateXPath(entry.getKey());
                    WebElement wrappedElement = WebDriverHelper.findElement(driver,getWrappedElement(),By.xpath(offsetXpath));
                    if(wrappedElement.getAttribute("type").equalsIgnoreCase("checkbox")){
                        CheckBox current= HtmlElementsFactory.createHtmlElementInstance(CheckBox.class,wrappedElement);
                        current.selectByVisibleText(entry.getValue());
                        continue;
                    }
                    if(wrappedElement.getAttribute("type").equalsIgnoreCase("radio")){
                        Radio current = HtmlElementsFactory.createHtmlElementInstance(Radio.class,wrappedElement);
                        current.selectByVisibleText(entry.getValue());
                        continue;
                    }

                    if(wrappedElement.getTagName().equalsIgnoreCase("select")){
                        SelectList current = HtmlElementsFactory.createHtmlElementInstance(SelectList.class,wrappedElement);
                        current.selectByVisibleText(entry.getValue());
                        continue;
                    }
                    WebDriverHelper.input(driver,wrappedElement,entry.getValue());
                }

            }
        }

    }

    private String generateXPath(String key) {
        String ngXPath="//*[contains(@ng-model,'%s'))]";
        String offsetXpath=String.format(ngXPath,key);
        if(StringHelper.isNotEmptyOrNotBlankString(offsetXPathForRepeatableRoot)) {
            offsetXpath = offsetXPathForRepeatableRoot + offsetXpath;
        }

        return offsetXpath;
    }

    private void validate() {
        String ngRepeat=getWrappedElement().getAttribute("ng-repeat");
        if(!StringHelper.isNotEmptyOrNotBlankString(ngRepeat)) throw new RuntimeException("当前元素不是ng-repeat元素");
    }

    public String getRepeatTriggerXpathLocator() {
        return repeatTriggerXpathLocator;
    }

    public void setRepeatTriggerXpathLocator(String repeatTriggerXpathLocator) {
        this.repeatTriggerXpathLocator = repeatTriggerXpathLocator;
    }

    public int getInitCount() {
        return initCount;
    }

    public void setInitCount(int initCount) {
        this.initCount = initCount;
    }

    public String getOffsetXPathForRepeatableRoot() {
        return offsetXPathForRepeatableRoot;
    }

    public void setOffsetXPathForRepeatableRoot(String offsetXPathForRepeatableRoot) {
        this.offsetXPathForRepeatableRoot = offsetXPathForRepeatableRoot;
    }
}
