package io.ift.automation.testscaffold.webtest.webUI.mockpages;

import io.ift.automation.testscaffold.webtest.actions.ExecutablePageObject;
import io.ift.automation.testscaffold.webtest.webUI.annotations.webpage.ElementName;
import io.ift.automation.testscaffold.webtest.webUI.htmlelements.Button;
import io.ift.automation.testscaffold.webtest.webUI.htmlelements.ListPictureElement;
import io.ift.automation.testscaffold.webtest.webUI.htmlelements.UploadElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

/**
 * Created by patrick on 15/6/16.
 *
 * @version $Id: UploadPicsOverlay.java 1468 2015-06-23 10:17:17Z wuke $
 */


public class UploadPicsOverlay extends ExecutablePageObject {
    public UploadPicsOverlay(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "div#refereceLayoutPopLayer [type=file]")
    @ElementName(elementName = "上传户型")
    private UploadElement uploadLayout ;

    @FindBy(css="[ng-click=confirmSelectRoomLayout()]")
    @ElementName(elementName = "提交")
    private Button submit;

    @FindBy(css="div#refereceLayoutPopLayer [ng-click=confirmSelectRoomLayout()]")
    @ElementName(elementName = "取消")
    private Button cancel;

    @FindBy(css="div#refereceLayoutPopLayer .list_quote")
    @ElementName(elementName = "户型图集")
    private ListPictureElement listPics;

    public UploadElement getUploadLayout() {
        return uploadLayout;
    }

    public void setUploadLayout(UploadElement uploadLayout) {
        this.uploadLayout = uploadLayout;
    }

    public Button getSubmit() {
        return submit;
    }

    public void setSubmit(Button submit) {
        this.submit = submit;
    }

    public Button getCancel() {
        return cancel;
    }

    public void setCancel(Button cancel) {
        this.cancel = cancel;
    }

    public ListPictureElement getListPics() {
        return listPics;
    }

    public void setListPics(ListPictureElement listPics) {
        this.listPics = listPics;
    }
}
