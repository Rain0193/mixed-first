package io.ift.automation.testscaffold.webtest.webUI.htmlelements;

import io.ift.automation.drivers.DriverFactory;
import io.ift.automation.helpers.StringHelper;
import io.ift.automation.helpers.webdriver.WebDriverHelper;
import io.ift.automation.logging.TestResultLogger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Created by patrick on 15/6/16.
 *
 * @version $Id: PictureItem.java 1782 2015-08-24 00:34:09Z wuke $
 */


public class PictureItem extends HtmlElement {

    public PictureItem(String name, WebElement element) {
        super(name, element);
    }

    public PictureItem(WebElement element) {
        super(element);
    }

    //actions:
//    1. delete last one
//    2. add picType
//    3. add pictDesc

    private List<WebElement> getAllPictureItem(){
        return getWrappedElement().findElements(By.cssSelector(".pictureItem"));
    }

    private WebElement getLastPictureItem(){
        List<WebElement> elements = getWrappedElement().findElements(By.cssSelector(".pictureItem"));
        if(!elements.isEmpty()){
            return elements.get(elements.size()-1);
        }else{
            throw  new RuntimeException("picture is not uploaded!");
        }
    }
    private WebElement getFirstPictureItem(){
        List<WebElement> elements = getWrappedElement().findElements(By.cssSelector(".pictureItem"));
        if(!elements.isEmpty()){
            return elements.get(0);
        }else{
            throw  new RuntimeException("picture is not uploaded!");
        }
    }

    public void deleteLast(){
        WebElement e = getLastPictureItem();
        TestResultLogger.log("开始删除图片........");
        e.findElement(By.cssSelector(".btnMini.miniDel")).click();
        WebDriverHelper.handleAlert(DriverFactory.get(),true);
    }

    public void deleteFirst(){
        WebElement e = getFirstPictureItem();
        TestResultLogger.log("开始删除图片........");
        e.findElement(By.cssSelector(".btnMini.miniDel")).click();
        WebDriverHelper.handleAlert(DriverFactory.get(),true);
    }

    public void delete(){
        deleteFirst();
    }
    public void deleteByIndex(int index){
        WebElement e = getAllPictureItem().get(index);
        TestResultLogger.log("开始删除图片........");
        e.findElement(By.cssSelector(".btnMini.miniDel")).click();
        WebDriverHelper.handleAlert(DriverFactory.get(),true);
    }
    /**
     * 添加最后一个图片描述
     * @param pictureDescription
     */
    public void addDescForLast(String pictureDescription){
        WebElement e = getLastPictureItem();
        TestResultLogger.log("开始输入图片描述........");
        e.findElement(By.cssSelector(".pictureDesc>textarea")).sendKeys(pictureDescription);
    }


    /**
     * 添加最后一个图片描述
     * @param pictureDescription
     */
    public void addDescForFirst(String pictureDescription){
        WebElement e = getFirstPictureItem();
        TestResultLogger.log("开始输入图片描述........");
        e.findElement(By.cssSelector(".pictureDesc>textarea")).sendKeys(pictureDescription);
    }


    /**
     * 添加第一个没有图片描述
     * @param pictureDescription
     */
    public void addDesc(String pictureDescription){
        List<WebElement> elements = getAllPictureItem();
        for (WebElement element : elements) {
            WebElement e = element.findElement(By.cssSelector(".pictureDesc>textarea"));
            if (!StringHelper.isNotEmptyOrNotBlankString(e.getText())) {
                TestResultLogger.log("开始输入图片描述........");
                e.findElement(By.cssSelector(".pictureDesc>textarea")).sendKeys(pictureDescription);
                return;
            }
        }
    }


    /**
     * 选择最后一个图片的类型
     * @param picType
     */
    public void selectPicTypeForLast(String picType){
        WebElement e = getLastPictureItem();
        TestResultLogger.log("开始选择图片类型........");
        WebElement type =e.findElement(By.cssSelector(".pictureType>input[type='text']"));
        WebDriverHelper.click(DriverFactory.get(),type);
        List<WebElement> elements = e.findElements(By.cssSelector("a[ng-click]"));
        for (WebElement element : elements) {
            if(element.getText().equalsIgnoreCase(picType)){
                element.click();
                return;
            }
        }
    }



    /**
     * 选择没有输入的图片描述的图片进行选择类型
     * @param picType
     */
    public void selectPicType(String picType){
        List<WebElement> elements = getAllPictureItem();
        for (WebElement element : elements) {
            WebElement e = element.findElement(By.cssSelector(".pictureDesc>textarea"));
            if(!StringHelper.isNotEmptyOrNotBlankString(e.getText())){
                TestResultLogger.log("开始选择图片类型........");
                WebElement type =element.findElement(By.cssSelector(".pictureType>input[type='text']"));
                WebDriverHelper.click(DriverFactory.get(),type);
                List<WebElement> types = e.findElements(By.cssSelector("a[ng-click]"));
                for (WebElement selection : types) {
                    if(selection.getText().equalsIgnoreCase(picType)){
                        selection.click();
                        return;
                    }
                }
            }
        }
    }

    /**
     * 选择第一个图片的类型
     * @param picType
     */
    public void selectPicTypeForFirst(String picType){
        WebElement e = getFirstPictureItem();
        TestResultLogger.log("开始选择图片类型........");
        WebElement type =e.findElement(By.cssSelector(".pictureType>input[type='text']"));
        WebDriverHelper.click(DriverFactory.get(),type);
        List<WebElement> elements = e.findElements(By.cssSelector("a[ng-click]"));
        for (WebElement element : elements) {
            if(element.getText().equalsIgnoreCase(picType)){
                element.click();
                return;
            }
        }
    }


}
