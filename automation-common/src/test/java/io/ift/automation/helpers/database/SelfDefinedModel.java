package io.ift.automation.helpers.database;

import java.sql.Date;

/**
 * Created by patrick on 15/3/30.
 *
 * @version $Id$
 */


public class SelfDefinedModel {
    private Integer id;
    private Integer caseId;
    private String receiptMoney;
    private Date receiptTime;

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
}
