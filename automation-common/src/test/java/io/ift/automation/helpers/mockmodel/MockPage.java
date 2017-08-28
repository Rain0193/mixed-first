package io.ift.automation.helpers.mockmodel;

import io.ift.automation.testscaffold.webtest.actions.ExecutablePageObject;
import io.ift.automation.testscaffold.webtest.annotations.UIAction;
import io.ift.automation.testscaffold.webtest.annotations.UIActions;
import io.ift.automation.testscaffold.webtest.webUI.annotations.webpage.ElementName;
import com.dooioo.automation.testscaffold.webtest.webUI.htmlelements.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import io.ift.automation.testscaffold.webtest.webUI.htmlelements.InputBox;
import io.ift.automation.testscaffold.webtest.webUI.htmlelements.Link;
import io.ift.automation.testscaffold.webtest.webUI.htmlelements.Radio;
import io.ift.automation.testscaffold.webtest.webUI.htmlelements.SelectList;

/**
 * Created by patrick on 15/4/27.
 *
 * @version $Id$
 */
@UIActions(actions =
@UIAction(processName = "新增房源", elementActionDescription = {"contactName," +
        "relationship,residence,contactPhone,contactTel" +
        ",propertySource,status,countF,countT,countW,square,floor,floorAll,isAuction" +
        ",propertyDirection,propertyDecoration,buyinDate,flagLoan,remark" })
)
public class MockPage extends ExecutablePageObject {

    public MockPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(linkText = "纠错")
    private Link correct;

    @FindBy(name = "property_contactName")
    @ElementName(elementName = "主要联系人")
    private InputBox contactName;


    @FindBy(name = "property_relationship")
    @ElementName(elementName = "身份")
    private SelectList relationship;

    @FindBy(name = "property_residence")
    @ElementName(elementName = "国籍")
    private SelectList residence;

    @FindBy(name = "property_contactPhone")
    @ElementName(elementName = "联系电话")
    private InputBox contactPhone;

    @FindBy(name = "property_contactTel")
    @ElementName(elementName = "其他号码")
    private InputBox contactTel;

    @FindBy(partialLinkText = "如何添加有分机号的号码?")
    private Link howToCreateWithExtensionNo;

    @FindBy(name = "property_propertySource")
    @ElementName(elementName = "来源")
    private Radio propertySource;

    @FindBy(name = "property_status")
    @ElementName(elementName = "状态")
    private Radio status;

    @FindBy(name = "property_countF")
    @ElementName(elementName = "室")
    private SelectList countF;

    @FindBy(name = "property_countT")
    @ElementName(elementName = "厅")
    private SelectList countT;

    @FindBy(name = "property_countW")
    @ElementName(elementName = "卫")
    private SelectList countW;

    @FindBy(name = "property_square")
    @ElementName(elementName = "面积")
    private InputBox square;

    @FindBy(name = "property_floor")
    @ElementName(elementName = "楼层")
    private InputBox floor;

    @FindBy(name = "property_floorAll")
    @ElementName(elementName = "zon")
    private InputBox floorAll;

    @FindBy(name = "property_isAuction")
    @ElementName(elementName = "是否拍卖")
    private Radio isAuction;

    @FindBy(name = "property_propertyDirection")
    @ElementName(elementName = "朝向")
    private SelectList propertyDirection;

    @FindBy(name = "property_propertyDecoration")
    @ElementName(elementName = "装修")
    private SelectList propertyDecoration;

    @FindBy(className = "txtDate")
    @ElementName(elementName = "装修")
    private InputBox buyinDate;

    @FindBy(className = "property_flagLoan")
    @ElementName(elementName = "贷款")
    private Radio flagLoan;

    @FindBy(className = "property_remark")
    @ElementName(elementName = "备注")
    private InputBox remark;

    public enum PageElements{
        贷款,备注
    }


    public Link getCorrect() {
        return correct;
    }

    public void setCorrect(Link correct) {
        this.correct = correct;
    }

    public SelectList getRelationship() {
        return relationship;
    }

    public void setRelationship(SelectList relationship) {
        this.relationship = relationship;
    }

    public SelectList getResidence() {
        return residence;
    }

    public void setResidence(SelectList residence) {
        this.residence = residence;
    }

    public InputBox getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(InputBox contactPhone) {
        this.contactPhone = contactPhone;
    }

    public InputBox getContactTel() {
        return contactTel;
    }

    public void setContactTel(InputBox contactTel) {
        this.contactTel = contactTel;
    }

    public Link getHowToCreateWithExtensionNo() {
        return howToCreateWithExtensionNo;
    }

    public void setHowToCreateWithExtensionNo(Link howToCreateWithExtensionNo) {
        this.howToCreateWithExtensionNo = howToCreateWithExtensionNo;
    }

    public SelectList getCountF() {
        return countF;
    }

    public void setCountF(SelectList countF) {
        this.countF = countF;
    }

    public SelectList getCountT() {
        return countT;
    }

    public void setCountT(SelectList countT) {
        this.countT = countT;
    }

    public SelectList getCountW() {
        return countW;
    }

    public void setCountW(SelectList countW) {
        this.countW = countW;
    }

    public InputBox getSquare() {
        return square;
    }

    public void setSquare(InputBox square) {
        this.square = square;
    }

    public InputBox getFloor() {
        return floor;
    }

    public void setFloor(InputBox floor) {
        this.floor = floor;
    }

    public InputBox getFloorAll() {
        return floorAll;
    }

    public void setFloorAll(InputBox floorAll) {
        this.floorAll = floorAll;
    }

    public Radio getPropertySource() {
        return propertySource;
    }

    public void setPropertySource(Radio propertySource) {
        this.propertySource = propertySource;
    }

    public Radio getStatus() {
        return status;
    }

    public void setStatus(Radio status) {
        this.status = status;
    }

    public Radio getIsAuction() {
        return isAuction;
    }

    public void setIsAuction(Radio isAuction) {
        this.isAuction = isAuction;
    }

    public InputBox getContactName() {
        return contactName;
    }

    public void setContactName(InputBox contactName) {
        this.contactName = contactName;
    }

    public SelectList getPropertyDirection() {
        return propertyDirection;
    }

    public void setPropertyDirection(SelectList propertyDirection) {
        this.propertyDirection = propertyDirection;
    }

    public SelectList getPropertyDecoration() {
        return propertyDecoration;
    }

    public void setPropertyDecoration(SelectList propertyDecoration) {
        this.propertyDecoration = propertyDecoration;
    }

    public InputBox getBuyinDate() {
        return buyinDate;
    }

    public void setBuyinDate(InputBox buyinDate) {
        this.buyinDate = buyinDate;
    }

    public Radio getFlagLoan() {
        return flagLoan;
    }

    public void setFlagLoan(Radio flagLoan) {
        this.flagLoan = flagLoan;
    }

    public InputBox getRemark() {
        return remark;
    }

    public void setRemark(InputBox remark) {
        this.remark = remark;
    }

}
