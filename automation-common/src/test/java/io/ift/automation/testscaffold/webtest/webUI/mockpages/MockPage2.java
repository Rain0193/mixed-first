package io.ift.automation.testscaffold.webtest.webUI.mockpages;

import io.ift.automation.testscaffold.webtest.actions.ExecutablePageObject;
import io.ift.automation.testscaffold.webtest.annotations.UIAction;
import io.ift.automation.testscaffold.webtest.annotations.UIActions;
import io.ift.automation.testscaffold.webtest.webUI.annotations.webpage.ElementName;
import io.ift.automation.testscaffold.webtest.webUI.htmlelements.InputBox;
import io.ift.automation.testscaffold.webtest.webUI.htmlelements.Radio;
import io.ift.automation.testscaffold.webtest.webUI.htmlelements.SelectList;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

/**
 * Created by patrick on 15/6/18.
 *
 * @version $Id: MockPage2.java 1468 2015-06-23 10:17:17Z wuke $
 */

@UIActions(actions={@UIAction(processName="新增住宅",elementActionDescription={"contactName","floorAll","isAuction","currentStatus","status","propertyDecoration"})
})
public class MockPage2 extends ExecutablePageObject{
    @FindBy(name="property_propertySource")
    @ElementName(elementName="property.propertySource")
    private Radio propertySource;
    @FindBy(name="property_status")
    @ElementName(elementName="property.status")
    private Radio status;
    @FindBy(name="property_shape")
    @ElementName(elementName="property.shape")
    private Radio shape;
    @FindBy(name="property_currentStatus")
    @ElementName(elementName="property.currentStatus")
    private Radio currentStatus;
    @FindBy(name="property_propertyLook")
    @ElementName(elementName="property.propertyLook")
    private Radio propertyLook;
    @FindBy(name="property_isAuction")
    @ElementName(elementName="property.isAuction")
    private Radio isAuction;
    @FindBy(name="property_flagLoan")
    @ElementName(elementName="property.flagLoan")
    private Radio flagLoan;
    @FindBy(xpath="//tr[not(contains(@style,'display: none'))]//input[@name='property_contactName']")
    @ElementName(elementName="property.contactName")
    private InputBox contactName;
    @FindBy(xpath="//tr[not(contains(@style,'display: none'))]//input[@name='property_floorAll']")
    @ElementName(elementName="property.floorAll")
    private InputBox floorAll;
    @FindBy(xpath="//tr[not(contains(@style,'display: none'))]//input[@name='property_floorHeight']")
    @ElementName(elementName="property.floorHeight")
    private InputBox floorHeight;
    @FindBy(xpath="//tr[not(contains(@style,'display: none'))]//select[@name='property_relationship']")
    @ElementName(elementName="property.relationship")
    private SelectList relationship;
    @FindBy(xpath="//tr[not(contains(@style,'display: none'))]//select[@name='property_residence']")
    @ElementName(elementName="property.residence")
    private SelectList residence;
    @FindBy(xpath="//tr[not(contains(@style,'display: none'))]//select[@name='rentUnitName']")
    @ElementName(elementName="property.rentUnitName")
    private SelectList rentUnitName;
    @FindBy(xpath="//tr[not(contains(@style,'display: none'))]//select[@name='property_countF']")
    @ElementName(elementName="property.countF")
    private SelectList countF;
    @FindBy(xpath="//tr[not(contains(@style,'display: none'))]//select[@name='property_countT']")
    @ElementName(elementName="property.countT")
    private SelectList countT;
    @FindBy(xpath="//tr[not(contains(@style,'display: none'))]//select[@name='property_countW']")
    @ElementName(elementName="property.countW")
    private SelectList countW;
    @FindBy(xpath="//tr[not(contains(@style,'display: none'))]//select[@name='property_countGarden']")
    @ElementName(elementName="property.countGarden")
    private SelectList countGarden;
    @FindBy(xpath="//tr[not(contains(@style,'display: none'))]//select[@name='property_countCellar']")
    @ElementName(elementName="property.countCellar")
    private SelectList countCellar;
    @FindBy(xpath="//tr[not(contains(@style,'display: none'))]//select[@name='property_propertyDirection']")
    @ElementName(elementName="property.propertyDirection")
    private SelectList propertyDirection;
    @FindBy(xpath="//tr[not(contains(@style,'display: none'))]//select[@name='property_waterToward']")
    @ElementName(elementName="property.waterToward")
    private SelectList waterToward;
    @FindBy(xpath="//tr[not(contains(@style,'display: none'))]//select[@name='property_officeGrade']")
    @ElementName(elementName="property.officeGrade")
    private SelectList officeGrade;
    @FindBy(xpath="//tr[not(contains(@style,'display: none'))]//select[@name='property_propertyDecoration']")
    @ElementName(elementName="property.propertyDecoration")
    private SelectList propertyDecoration;

    public MockPage2(WebDriver driver) {
        super(driver);
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

    public Radio getShape() {
        return shape;
    }

    public void setShape(Radio shape) {
        this.shape = shape;
    }

    public Radio getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(Radio currentStatus) {
        this.currentStatus = currentStatus;
    }

    public Radio getPropertyLook() {
        return propertyLook;
    }

    public void setPropertyLook(Radio propertyLook) {
        this.propertyLook = propertyLook;
    }

    public Radio getIsAuction() {
        return isAuction;
    }

    public void setIsAuction(Radio isAuction) {
        this.isAuction = isAuction;
    }

    public Radio getFlagLoan() {
        return flagLoan;
    }

    public void setFlagLoan(Radio flagLoan) {
        this.flagLoan = flagLoan;
    }

    public InputBox getContactName() {
        return contactName;
    }

    public void setContactName(InputBox contactName) {
        this.contactName = contactName;
    }

    public InputBox getFloorAll() {
        return floorAll;
    }

    public void setFloorAll(InputBox floorAll) {
        this.floorAll = floorAll;
    }

    public InputBox getFloorHeight() {
        return floorHeight;
    }

    public void setFloorHeight(InputBox floorHeight) {
        this.floorHeight = floorHeight;
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

    public SelectList getRentUnitName() {
        return rentUnitName;
    }

    public void setRentUnitName(SelectList rentUnitName) {
        this.rentUnitName = rentUnitName;
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

    public SelectList getCountGarden() {
        return countGarden;
    }

    public void setCountGarden(SelectList countGarden) {
        this.countGarden = countGarden;
    }

    public SelectList getCountCellar() {
        return countCellar;
    }

    public void setCountCellar(SelectList countCellar) {
        this.countCellar = countCellar;
    }

    public SelectList getPropertyDirection() {
        return propertyDirection;
    }

    public void setPropertyDirection(SelectList propertyDirection) {
        this.propertyDirection = propertyDirection;
    }

    public SelectList getWaterToward() {
        return waterToward;
    }

    public void setWaterToward(SelectList waterToward) {
        this.waterToward = waterToward;
    }

    public SelectList getOfficeGrade() {
        return officeGrade;
    }

    public void setOfficeGrade(SelectList officeGrade) {
        this.officeGrade = officeGrade;
    }

    public SelectList getPropertyDecoration() {
        return propertyDecoration;
    }

    public void setPropertyDecoration(SelectList propertyDecoration) {
        this.propertyDecoration = propertyDecoration;
    }
}
