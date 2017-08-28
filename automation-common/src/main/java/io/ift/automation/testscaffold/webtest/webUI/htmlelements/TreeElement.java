package io.ift.automation.testscaffold.webtest.webUI.htmlelements;

import io.ift.automation.drivers.DriverFactory;
import io.ift.automation.helpers.CollectionsHelper;
import io.ift.automation.helpers.webdriver.WebDriverHelper;
import io.ift.automation.logging.TestResultLogger;
import com.google.common.base.Predicate;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Created by patrick on 15/7/16.
 *
 * @version $Id: TreeElement.java 2919 2016-04-27 08:31:04Z wuke $
 */

@SuppressWarnings("unchecked")
public class TreeElement extends HtmlElement {
    public static final String SELECTBYNAMEANDORGNAME = "selectByNameAndOrgName";
    public static final String SELECTBYSPANID = "selectBySpanId";
    public static final String SELECT = "select";

    public TreeElement(String name, WebElement element) {
        super(name, element);
    }


    public TreeElement(WebElement element) {
        super(element);
    }

    /**
     * 根据组织架构选择
     * 不管是树状结构或者扁平结构可以支持
     *
     * @param depIds
     */
    public void select(String depIds) {
        WebDriverHelper.clickIfPresent(DriverFactory.get(), getWrappedElement(), By.tagName("input"));
        String xpath = "//*[@id='%s'] | //*[@node_id='%s']";
        String[] ids = depIds.split("-");
        //switch click
        for (int i = 0; i < ids.length - 1; i++) {
            //find span by id
            String xpath_li = String.format(xpath, ids[i], ids[i]);
            List<WebElement> nodes = getWrappedElement().findElements(By.xpath(xpath_li));
            WebElement parent = CollectionsHelper.filter(nodes, element -> {
                return element.isDisplayed();
            });
            WebElement switchSpan = null;
            try {
                switchSpan = parent.findElement(By.xpath("preceding-sibling::span[contains(@class,'switch')] " +
                        "| span[contains(@class,'switch')]"));
            } catch (Exception e) {
            }

            if (switchSpan == null) break;
            try {
                switchSpan.click();
            } catch (Exception e) {
                TestResultLogger.warn("error_result=" + e);
                TestResultLogger.warn("可能输入了不需要的depId,可能无效的depId是:" + ids[i]);
            }
        }
        //click the link
        WebElement last;
        try {
            last = CollectionsHelper.filter(getWrappedElement().findElements(By.xpath(String.format(xpath,
                    ids[ids.length - 1], ids[ids.length - 1]))), element -> {
                return element.isDisplayed();
            });
        } catch (Exception e) {
            last = CollectionsHelper.filter(DriverFactory.get().findElements(By.xpath(String.format(xpath,
                    ids[ids.length - 1], ids[ids.length - 1]))), element -> {
                return element.isDisplayed();
            });
        }
        if (last != null) {
            last.click();
        } else {
            throw new RuntimeException("can't find element " + String.format(xpath,
                    ids[ids.length - 1], ids[ids.length - 1]));
        }

    }

    /**
     * 输入人名 和组织名称,在组织架构树中使用
     *
     * @param nameAndOrgName
     */
    public void selectByNameAndOrgName(String nameAndOrgName) {
        WebDriverHelper.clickIfPresent(DriverFactory.get(), getWrappedElement(), By.tagName("input"));
        String[] nameOrgNamePair = nameAndOrgName.split("-");
        WebDriverHelper.input(DriverFactory.get(), By.xpath("//duitree/div/input"), nameOrgNamePair[0]);
        WebDriverHelper.waitForSubmit(1000L);
        List<WebElement> links = WebDriverHelper.findElements(DriverFactory.get(),
                getWrappedElement(), By.xpath("div/div/div[contains(@class,autoComplete_tree)]/a"));
        for (WebElement link : links) {
            if (nameOrgNamePair.length > 1) {
                if (link.getText().contains(nameOrgNamePair[0])
                        && link.getText().contains(nameOrgNamePair[1])) {
                    link.click();
                    return;
                }
            } else {
                if (link.getText().contains(nameOrgNamePair[0])) {
                    link.click();
                    return;
                }
            }
        }
        if (links != null && links.size() > 0) {
            links.get(0).click();
            return;
        }
        return;
    }

    /**
     * TreeElement select by span's id
     *
     * @param depIds
     */
    public void selectBySpanId(String depIds) {
        String[] ids = depIds.split("-");
        WebElement parentElement = getWrappedElement();
        int lastIndex = ids.length;
        String xpath;
        for (int i = 0; i < lastIndex; i++) {
            if (i > 0) {
                WebElement switchElement = parentElement.findElement(By.cssSelector("li>span.switch"));
                switchElement.click();
            }
            if (i < lastIndex - 1) {
                String id = ids[i];
                xpath = String.format("//span[@id='%s']/../..", id);
                parentElement = parentElement.findElement(By.xpath(xpath));
            } else {
                parentElement.findElement(By.id(ids[lastIndex - 1])).click();
            }

        }
    }
}
