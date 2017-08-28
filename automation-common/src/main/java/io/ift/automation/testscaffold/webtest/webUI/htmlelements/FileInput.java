package io.ift.automation.testscaffold.webtest.webUI.htmlelements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebElement;


import static io.ift.automation.helpers.FileHelper.existsInClasspath;
import static io.ift.automation.helpers.FileHelper.getPathForSystemFile;
import static io.ift.automation.helpers.FileHelper.getResourceFromClasspath;
import static io.ift.automation.testscaffold.webtest.webUI.support.HtmlElementHelper.isOnRemoteWebDriver;

@Deprecated
public class FileInput extends HtmlElement {


    public FileInput(String name, WebElement element) {
        super(name, element);
    }

    public FileInput(WebElement element) {
        super(element);
    }

    public void setFileToUpload(final String fileName) {
        // Proxy can't be used to check the element class, so find real WebElement
        WebElement fileInputElement = getNotProxiedInputElement();
        // Set local file detector in case of remote driver usage
        if (isOnRemoteWebDriver(fileInputElement)) {
            setLocalFileDetector((RemoteWebElement) fileInputElement);
        }

        String filePath = getFilePath(fileName);
        fileInputElement.sendKeys(filePath);
    }


    public void submit() {
        getWrappedElement().submit();
    }

    private WebElement getNotProxiedInputElement() {
        return getWrappedElement().findElement(By.xpath("."));
    }

    private void setLocalFileDetector(RemoteWebElement element) {
        element.setFileDetector(new LocalFileDetector());
    }

    private String getFilePath(final String fileName) {
        if (existsInClasspath(fileName)) {
            return getPathForResource(fileName);
        }
        return getPathForSystemFile(fileName);
    }

    private String getPathForResource(final String fileName) {
        return getResourceFromClasspath(fileName).getPath();
    }


}
