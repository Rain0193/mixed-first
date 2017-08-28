package io.ift.automation.testscaffold.webtest.webUI.htmlelements;

import io.ift.automation.drivers.DriverFactory;
import io.ift.automation.helpers.FileHelper;
import io.ift.automation.helpers.StringHelper;
import io.ift.automation.helpers.webdriver.WebDriverHelper;
import io.ift.automation.logging.TestResultLogger;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Created by patrick on 15/6/16.
 *
 * @author patrick
 * @version $Id: UploadElement.java 2838 2016-04-06 05:16:50Z wuke $
 */
public class UploadElement extends Button {
    public static final String UPLOAD = "upload";

    /**
     * <p>Constructor for UploadElement.</p>
     *
     * @param name    a {@link java.lang.String} object.
     * @param element a {@link org.openqa.selenium.WebElement} object.
     */
    public UploadElement(String name, WebElement element) {
        super(name, element);
    }

    /**
     * <p>Constructor for UploadElement.</p>
     *
     * @param element a {@link org.openqa.selenium.WebElement} object.
     */
    public UploadElement(WebElement element) {
        super(element);
    }

    /**
     * <p>upload.</p>
     *
     * @param filesPath a {@link java.lang.String} object.
     */
    public void upload(String filesPath) {
        if (StringHelper.isNotEmptyOrNotBlankString(filesPath)) {
            String[] files = filesPath.replace(",", "-").split("-");
            for (String file : files) {
                TestResultLogger.log("开始上传文件，{}", file);
                String path = FileHelper.getFilePath(file);
                performPreconditionAction();
                List<WebElement> elements = WebDriverHelper.findElements(DriverFactory.get(), getBy());
                elements.get(elements.size() - 1).sendKeys(path);
            }
        }
        WebDriverHelper.waitForSubmit(2000L);
    }

    /**
     * 上传到一个元素
     *
     * @param filesPath
     */
    @Deprecated
    public void uploadForDistinctElement(String filesPath) {
        if (StringHelper.isNotEmptyOrNotBlankString(filesPath)) {
            String[] files = filesPath.replace(",", "-").split("-");
            for (String file : files) {
                TestResultLogger.log("开始上传文件，{}", file);
                String path = FileHelper.getFilePath(file);
                getWrappedElement().sendKeys(path);
            }
        }
        WebDriverHelper.waitForSubmit(2000L);
    }

    /**
     * <p>fakeUpload.</p>
     *
     * @param filesPath a {@link java.lang.String} object.
     */
    @Deprecated
    public void fakeUpload(String filesPath) {
        WebDriverHelper.getJSEvalValue(DriverFactory.get(),
                String.format("$('#%s').val('%s')", getWrappedElement().getAttribute("id")
                        , filesPath));
    }
}
