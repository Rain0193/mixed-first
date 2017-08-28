package io.ift.automation.helpers.database;

import com.google.common.base.MoreObjects;

import javax.persistence.*;
import java.sql.Date;


@Entity
@Table(name = "T_BMS_ORDER")
public class BmsOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer caseId;
    private String receiptMoney;
    private Date receiptTime;
    private String orderPrice;
    private Date orderDate;
    private String achievement;
    private String payedCommision;
    private String contractPrice;
    private Date commissionEndDate;
    private String buyerConfirmMoney;
    private String vendorConfirmMoney;
    private String cashBackMoney;
    private String intentMoney;
    private Date intentEndDate;
    private String intentLetterNo;
    private String afterSaleGroup;
    private String afterSaleGroupMember;
    private String buildingCertificateAddress;
    private String certificateNo;
    private String certificateYear;
    private String certificateAreaNo;
    private Date signDate;
    private String signLocation;
    private String withdrawReason;
    private String withdrawCustomerReason;
    private Date withdrawTime;
    private String complete;
    private String deleted;
    private Integer status;
    private String safe;
    private String fillStatus;
    private String visible;
    private Integer version;
    private Date updateTime;
    private Date createTime;
    private String creator;
    private String creatorName;
    private String direct;
    private Date receiptedTime;
    private String isPartmodify;
    private String addPrice;
    private Date intentConfirmDate;
    private String signedAchievement;
    private Date signedMonth;
    private Integer hasBackMoney;
    private Integer hasBackMoneyInvoice;
    private String backMoneyTaxBearer;
    private String backMoneyTax;
    private String backMoneyActual;
    private String backMoneyDeduct;
    private Integer hasLawerMoney;
    private String lawerMoney;
    private String otherIncomeMoney;
    private Integer hasOtherIncome;
    private Date latestSignedDate;
    private Integer hasDiscount;
    private String discountReason;
    private String customerAchievement;
    private String ownerAchievement;
    private String backmoneyReason;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCaseId() {
        return caseId;
    }

    public void setCaseId(Integer caseId) {
        this.caseId = caseId;
    }

    public String getReceiptMoney() {
        return receiptMoney;
    }

    public void setReceiptMoney(String receiptMoney) {
        this.receiptMoney = receiptMoney;
    }

    public Date getReceiptTime() {
        return receiptTime;
    }

    public void setReceiptTime(Date receiptTime) {
        this.receiptTime = receiptTime;
    }

    public String getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(String orderPrice) {
        this.orderPrice = orderPrice;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getAchievement() {
        return achievement;
    }

    public void setAchievement(String achievement) {
        this.achievement = achievement;
    }

    public String getPayedCommision() {
        return payedCommision;
    }

    public void setPayedCommision(String payedCommision) {
        this.payedCommision = payedCommision;
    }

    public String getContractPrice() {
        return contractPrice;
    }

    public void setContractPrice(String contractPrice) {
        this.contractPrice = contractPrice;
    }

    public Date getCommissionEndDate() {
        return commissionEndDate;
    }

    public void setCommissionEndDate(Date commissionEndDate) {
        this.commissionEndDate = commissionEndDate;
    }

    public String getBuyerConfirmMoney() {
        return buyerConfirmMoney;
    }

    public void setBuyerConfirmMoney(String buyerConfirmMoney) {
        this.buyerConfirmMoney = buyerConfirmMoney;
    }

    public String getVendorConfirmMoney() {
        return vendorConfirmMoney;
    }

    public void setVendorConfirmMoney(String vendorConfirmMoney) {
        this.vendorConfirmMoney = vendorConfirmMoney;
    }

    public String getCashBackMoney() {
        return cashBackMoney;
    }

    public void setCashBackMoney(String cashBackMoney) {
        this.cashBackMoney = cashBackMoney;
    }

    public String getIntentMoney() {
        return intentMoney;
    }

    public void setIntentMoney(String intentMoney) {
        this.intentMoney = intentMoney;
    }

    public Date getIntentEndDate() {
        return intentEndDate;
    }

    public void setIntentEndDate(Date intentEndDate) {
        this.intentEndDate = intentEndDate;
    }

    public String getIntentLetterNo() {
        return intentLetterNo;
    }

    public void setIntentLetterNo(String intentLetterNo) {
        this.intentLetterNo = intentLetterNo;
    }

    public String getAfterSaleGroup() {
        return afterSaleGroup;
    }

    public void setAfterSaleGroup(String afterSaleGroup) {
        this.afterSaleGroup = afterSaleGroup;
    }

    public String getAfterSaleGroupMember() {
        return afterSaleGroupMember;
    }

    public void setAfterSaleGroupMember(String afterSaleGroupMember) {
        this.afterSaleGroupMember = afterSaleGroupMember;
    }

    public String getBuildingCertificateAddress() {
        return buildingCertificateAddress;
    }

    public void setBuildingCertificateAddress(String buildingCertificateAddress) {
        this.buildingCertificateAddress = buildingCertificateAddress;
    }

    public String getCertificateNo() {
        return certificateNo;
    }

    public void setCertificateNo(String certificateNo) {
        this.certificateNo = certificateNo;
    }

    public String getCertificateYear() {
        return certificateYear;
    }

    public void setCertificateYear(String certificateYear) {
        this.certificateYear = certificateYear;
    }

    public String getCertificateAreaNo() {
        return certificateAreaNo;
    }

    public void setCertificateAreaNo(String certificateAreaNo) {
        this.certificateAreaNo = certificateAreaNo;
    }

    public Date getSignDate() {
        return signDate;
    }

    public void setSignDate(Date signDate) {
        this.signDate = signDate;
    }

    public String getSignLocation() {
        return signLocation;
    }

    public void setSignLocation(String signLocation) {
        this.signLocation = signLocation;
    }

    public String getWithdrawReason() {
        return withdrawReason;
    }

    public void setWithdrawReason(String withdrawReason) {
        this.withdrawReason = withdrawReason;
    }

    public String getWithdrawCustomerReason() {
        return withdrawCustomerReason;
    }

    public void setWithdrawCustomerReason(String withdrawCustomerReason) {
        this.withdrawCustomerReason = withdrawCustomerReason;
    }

    public Date getWithdrawTime() {
        return withdrawTime;
    }

    public void setWithdrawTime(Date withdrawTime) {
        this.withdrawTime = withdrawTime;
    }

    public String getComplete() {
        return complete;
    }

    public void setComplete(String complete) {
        this.complete = complete;
    }

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getSafe() {
        return safe;
    }

    public void setSafe(String safe) {
        this.safe = safe;
    }

    public String getFillStatus() {
        return fillStatus;
    }

    public void setFillStatus(String fillStatus) {
        this.fillStatus = fillStatus;
    }

    public String getVisible() {
        return visible;
    }

    public void setVisible(String visible) {
        this.visible = visible;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getDirect() {
        return direct;
    }

    public void setDirect(String direct) {
        this.direct = direct;
    }

    public Date getReceiptedTime() {
        return receiptedTime;
    }

    public void setReceiptedTime(Date receiptedTime) {
        this.receiptedTime = receiptedTime;
    }

    public String getIsPartmodify() {
        return isPartmodify;
    }

    public void setIsPartmodify(String isPartmodify) {
        this.isPartmodify = isPartmodify;
    }

    public String getAddPrice() {
        return addPrice;
    }

    public void setAddPrice(String addPrice) {
        this.addPrice = addPrice;
    }

    public Date getIntentConfirmDate() {
        return intentConfirmDate;
    }

    public void setIntentConfirmDate(Date intentConfirmDate) {
        this.intentConfirmDate = intentConfirmDate;
    }

    public String getSignedAchievement() {
        return signedAchievement;
    }

    public void setSignedAchievement(String signedAchievement) {
        this.signedAchievement = signedAchievement;
    }

    public Date getSignedMonth() {
        return signedMonth;
    }

    public void setSignedMonth(Date signedMonth) {
        this.signedMonth = signedMonth;
    }

    public Integer getHasBackMoney() {
        return hasBackMoney;
    }

    public void setHasBackMoney(Integer hasBackMoney) {
        this.hasBackMoney = hasBackMoney;
    }

    public Integer getHasBackMoneyInvoice() {
        return hasBackMoneyInvoice;
    }

    public void setHasBackMoneyInvoice(Integer hasBackMoneyInvoice) {
        this.hasBackMoneyInvoice = hasBackMoneyInvoice;
    }

    public String getBackMoneyTaxBearer() {
        return backMoneyTaxBearer;
    }

    public void setBackMoneyTaxBearer(String backMoneyTaxBearer) {
        this.backMoneyTaxBearer = backMoneyTaxBearer;
    }

    public String getBackMoneyTax() {
        return backMoneyTax;
    }

    public void setBackMoneyTax(String backMoneyTax) {
        this.backMoneyTax = backMoneyTax;
    }

    public String getBackMoneyActual() {
        return backMoneyActual;
    }

    public void setBackMoneyActual(String backMoneyActual) {
        this.backMoneyActual = backMoneyActual;
    }

    public String getBackMoneyDeduct() {
        return backMoneyDeduct;
    }

    public void setBackMoneyDeduct(String backMoneyDeduct) {
        this.backMoneyDeduct = backMoneyDeduct;
    }

    public Integer getHasLawerMoney() {
        return hasLawerMoney;
    }

    public void setHasLawerMoney(Integer hasLawerMoney) {
        this.hasLawerMoney = hasLawerMoney;
    }

    public String getLawerMoney() {
        return lawerMoney;
    }

    public void setLawerMoney(String lawerMoney) {
        this.lawerMoney = lawerMoney;
    }

    public String getOtherIncomeMoney() {
        return otherIncomeMoney;
    }

    public void setOtherIncomeMoney(String otherIncomeMoney) {
        this.otherIncomeMoney = otherIncomeMoney;
    }

    public Integer getHasOtherIncome() {
        return hasOtherIncome;
    }

    public void setHasOtherIncome(Integer hasOtherIncome) {
        this.hasOtherIncome = hasOtherIncome;
    }

    public Date getLatestSignedDate() {
        return latestSignedDate;
    }

    public void setLatestSignedDate(Date latestSignedDate) {
        this.latestSignedDate = latestSignedDate;
    }

    public Integer getHasDiscount() {
        return hasDiscount;
    }

    public void setHasDiscount(Integer hasDiscount) {
        this.hasDiscount = hasDiscount;
    }

    public String getDiscountReason() {
        return discountReason;
    }

    public void setDiscountReason(String discountReason) {
        this.discountReason = discountReason;
    }

    public String getCustomerAchievement() {
        return customerAchievement;
    }

    public void setCustomerAchievement(String customerAchievement) {
        this.customerAchievement = customerAchievement;
    }

    public String getOwnerAchievement() {
        return ownerAchievement;
    }

    public void setOwnerAchievement(String ownerAchievement) {
        this.ownerAchievement = ownerAchievement;
    }

    public String getBackmoneyReason() {
        return backmoneyReason;
    }

    public void setBackmoneyReason(String backmoneyReason) {
        this.backmoneyReason = backmoneyReason;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("caseId", caseId)
                .add("receiptMoney", receiptMoney)
                .add("receiptTime", receiptTime)
                .add("orderPrice", orderPrice)
                .add("orderDate", orderDate)
                .add("achievement", achievement)
                .add("payedCommision", payedCommision)
                .add("contractPrice", contractPrice)
                .add("commissionEndDate", commissionEndDate)
                .add("buyerConfirmMoney", buyerConfirmMoney)
                .add("vendorConfirmMoney", vendorConfirmMoney)
                .add("cashBackMoney", cashBackMoney)
                .add("intentMoney", intentMoney)
                .add("intentEndDate", intentEndDate)
                .add("intentLetterNo", intentLetterNo)
                .add("afterSaleGroup", afterSaleGroup)
                .add("afterSaleGroupMember", afterSaleGroupMember)
                .add("buildingCertificateAddress", buildingCertificateAddress)
                .add("certificateNo", certificateNo)
                .add("certificateYear", certificateYear)
                .add("certificateAreaNo", certificateAreaNo)
                .add("signDate", signDate)
                .add("signLocation", signLocation)
                .add("withdrawReason", withdrawReason)
                .add("withdrawCustomerReason", withdrawCustomerReason)
                .add("withdrawTime", withdrawTime)
                .add("complete", complete)
                .add("deleted", deleted)
                .add("status", status)
                .add("safe", safe)
                .add("fillStatus", fillStatus)
                .add("visible", visible)
                .add("version", version)
                .add("updateTime", updateTime)
                .add("createTime", createTime)
                .add("creator", creator)
                .add("creatorName", creatorName)
                .add("direct", direct)
                .add("receiptedTime", receiptedTime)
                .add("isPartmodify", isPartmodify)
                .add("addPrice", addPrice)
                .add("intentConfirmDate", intentConfirmDate)
                .add("signedAchievement", signedAchievement)
                .add("signedMonth", signedMonth)
                .add("hasBackMoney", hasBackMoney)
                .add("hasBackMoneyInvoice", hasBackMoneyInvoice)
                .add("backMoneyTaxBearer", backMoneyTaxBearer)
                .add("backMoneyTax", backMoneyTax)
                .add("backMoneyActual", backMoneyActual)
                .add("backMoneyDeduct", backMoneyDeduct)
                .add("hasLawerMoney", hasLawerMoney)
                .add("lawerMoney", lawerMoney)
                .add("otherIncomeMoney", otherIncomeMoney)
                .add("hasOtherIncome", hasOtherIncome)
                .add("latestSignedDate", latestSignedDate)
                .add("hasDiscount", hasDiscount)
                .add("discountReason", discountReason)
                .add("customerAchievement", customerAchievement)
                .add("ownerAchievement", ownerAchievement)
                .add("backmoneyReason", backmoneyReason)
                .toString();
    }
}
