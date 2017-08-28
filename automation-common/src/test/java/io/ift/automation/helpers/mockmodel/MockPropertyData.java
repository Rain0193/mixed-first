package io.ift.automation.helpers.mockmodel;

import io.ift.automation.commonflows.models.EmployeeTestData;
import io.ift.automation.helpers.RandomHelper;

/**
 * Created by patrick on 15/4/29.
 *
 * @version $Id: MockPropertyData.java 1877 2015-09-24 09:38:06Z wuke $
 */


public class MockPropertyData extends EmployeeTestData {

    private String propertyUsage="住宅";
    private String estateName;
    private String address;
    private String roomNo;
    private String errMsgNew;
    private String permitNo;
    private String contactName ="测试姓名";
    private String relationship;
    private String residence;
    private String contactPhone = RandomHelper.generateMobilePhoneNumber();
    private String contactTel= RandomHelper.generateMobilePhoneNumber();
    private String propertySource;
    private String status;
    private String countF="2";
    private String countT="1";
    private String countW="2";
    private String square="100.00";
    private String floor="3";
    private String floorAll="6";
    private String isAuction="0";
    private String propertyDirection="南";
    private String propertyDecoration="精装修";
    private String buyinDate;
    private String flagLoan="1";
    private String remark="测试建立的房源";
    private String price="350";
    private String handPrice="340";
    private String isOnlyOne="0";

    public String getPropertyUsage() {
        return propertyUsage;
    }

    public void setPropertyUsage(String propertyUsage) {
        this.propertyUsage = propertyUsage;
    }

    public String getEstateName() {
        return estateName;
    }

    public void setEstateName(String estateName) {
        this.estateName = estateName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    public String getErrMsgNew() {
        return errMsgNew;
    }

    public void setErrMsgNew(String errMsgNew) {
        this.errMsgNew = errMsgNew;
    }

    public String getPermitNo() {
        return permitNo;
    }

    public void setPermitNo(String permitNo) {
        this.permitNo = permitNo;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public String getResidence() {
        return residence;
    }

    public void setResidence(String residence) {
        this.residence = residence;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getContactTel() {
        return contactTel;
    }

    public void setContactTel(String contactTel) {
        this.contactTel = contactTel;
    }

    public String getPropertySource() {
        return propertySource;
    }

    public void setPropertySource(String propertySource) {
        this.propertySource = propertySource;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCountF() {
        return countF;
    }

    public void setCountF(String countF) {
        this.countF = countF;
    }

    public String getCountT() {
        return countT;
    }

    public void setCountT(String countT) {
        this.countT = countT;
    }

    public String getCountW() {
        return countW;
    }

    public void setCountW(String countW) {
        this.countW = countW;
    }

    public String getSquare() {
        return square;
    }

    public void setSquare(String square) {
        this.square = square;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getFloorAll() {
        return floorAll;
    }

    public void setFloorAll(String floorAll) {
        this.floorAll = floorAll;
    }

    public String getIsAuction() {
        return isAuction;
    }

    public void setIsAuction(String isAuction) {
        this.isAuction = isAuction;
    }

    public String getPropertyDirection() {
        return propertyDirection;
    }

    public void setPropertyDirection(String propertyDirection) {
        this.propertyDirection = propertyDirection;
    }

    public String getPropertyDecoration() {
        return propertyDecoration;
    }

    public void setPropertyDecoration(String propertyDecoration) {
        this.propertyDecoration = propertyDecoration;
    }

    public String getBuyinDate() {
        return buyinDate;
    }

    public void setBuyinDate(String buyinDate) {
        this.buyinDate = buyinDate;
    }

    public String getFlagLoan() {
        return flagLoan;
    }

    public void setFlagLoan(String flagLoan) {
        this.flagLoan = flagLoan;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public static void main(String[] args) {
        MockPropertyData data = new MockPropertyData();
        data.toXLS("AddPropertyTestCases","AddPropertyTestCases.xls");
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getHandPrice() {
        return handPrice;
    }

    public void setHandPrice(String handPrice) {
        this.handPrice = handPrice;
    }

    public String getIsOnlyOne() {
        return isOnlyOne;
    }

    public void setIsOnlyOne(String isOnlyOne) {
        this.isOnlyOne = isOnlyOne;
    }

    @Override
    public void dataComposeAfter() {
        this.contactName="1234567";
    }
}
