package io.ift.automation.commonflows.models;

import io.ift.automation.helpers.StringHelper;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * <p>Activities class.</p>
 *
 * @author patrick
 * @version $Id: Activities.java 1853 2015-09-18 08:01:33Z wuke $Id
 */
public class Activities  {
    private List<String> employeeIds;
    private List<String> financeEmployeeIds;
    private String processName;
    private List<String> purchaseEmployeeIds;
    private List<String> adminEmployeeIds;
    private List<String> confirmEmployeeIds;
    private List<String> financeConfirmEmployeeIds;
    private List<String> adminConfirmEmployeeIds;
    private List<String> assistanceEmployeeIds;

    /**
     * <p>Constructor for Activities.</p>
     */
    public Activities() {
        financeConfirmEmployeeIds = Lists.newArrayList();
        adminConfirmEmployeeIds = Lists.newArrayList();
        assistanceEmployeeIds = Lists.newArrayList();
        confirmEmployeeIds = Lists.newArrayList();
        adminEmployeeIds = Lists.newArrayList();
        purchaseEmployeeIds = Lists.newArrayList();
        financeEmployeeIds = Lists.newArrayList();
        employeeIds = Lists.newArrayList();
    }


    /**
     * <p>Getter for the field <code>employeeIds</code>.</p>
     *
     * @return a {@link java.util.List} object.
     */
    public List<String> getEmployeeIds() {
        return employeeIds;
    }

    /**
     * <p>Setter for the field <code>employeeIds</code>.</p>
     *
     * @param employeeIds a {@link java.util.List} object.
     */
    public void setEmployeeIds(List<String> employeeIds) {
        this.employeeIds = employeeIds;
    }

    /**
     * <p>Getter for the field <code>processName</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getProcessName() {
        return processName;
    }

    /**
     * <p>Setter for the field <code>processName</code>.</p>
     *
     * @param processName a {@link java.lang.String} object.
     */
    public void setProcessName(String processName) {
        this.processName = processName;
    }


    /**
     * 前台人员审核列表
     *
     * @return a {@link java.util.List} object.
     */
    public List<List<EmployeeTestData>> approveFrontChain(){
       List<String> chains= Lists.newArrayList();
       chains.addAll(employeeIds);
       chains.remove(0);
        return buildEmployeeChain(chains);
    }

    /**
     * 前台人员审核列表
     *
     * @return a {@link java.util.List} object.
     */
    public List<List<EmployeeTestData>> approveSelfOrgChain(){
        List<String> chains= Lists.newArrayList();
        chains.addAll(employeeIds);
        chains.remove(0);
        return buildEmployeeChain(chains);
    }


    /**
     * 财务部门审批列表
     *
     * @return a {@link java.util.List} object.
     */
    public List<List<EmployeeTestData>> approveFinanceConfirmChain(){
        return buildEmployeeChain(financeConfirmEmployeeIds);
    }

    /**
     * 财务部门审批列表
     *
     * @return a {@link java.util.List} object.
     */
    public List<List<EmployeeTestData>> approveAdminConfirmChain(){
        return buildEmployeeChain(adminConfirmEmployeeIds);
    }

    /**
     * 财务部门审批列表
     *
     * @return a {@link java.util.List} object.
     */
    public List<List<EmployeeTestData>> approveAssistanceChain(){
        return buildEmployeeChain(assistanceEmployeeIds);
    }
    /**
     * 财务部门审批列表
     *
     * @return a {@link java.util.List} object.
     */
    public List<List<EmployeeTestData>> approveFinanceChain(){
        return buildEmployeeChain(financeEmployeeIds);
    }
    /**
     * 行政部门审批列表
     *
     * @return a {@link java.util.List} object.
     */
    public List<List<EmployeeTestData>> approvalAdminChain(){
        return buildEmployeeChain(adminEmployeeIds);
    }

    /**
     * 采购部门审批列表
     *
     * @return a {@link java.util.List} object.
     */
    public List<List<EmployeeTestData>> approvalPurchaseChain(){
        return buildEmployeeChain(purchaseEmployeeIds);
    }

    /**
     * <p>approvalConfirmChain.</p>
     *
     * @return a {@link java.util.List} object.
     */
    public List<List<EmployeeTestData>> approvalConfirmChain(){
        return buildEmployeeChain(confirmEmployeeIds);
    }

    private List<List<EmployeeTestData>> buildEmployeeChain(List<String> chains) {
        List<List<EmployeeTestData>> result= Lists.newArrayList();
        chains.stream().forEach(item->{
            if(!StringHelper.isNotEmptyOrNotBlankString(item)) return;
            String[] ids = item.split("-");
            List<EmployeeTestData> nodes = Lists.newArrayList();
            for (String id : ids) {
                nodes.add(new EmployeeTestData(id,"99"));
            }
            result.add(nodes);
        });

        return result;
    }

    /**
     * <p>startEmployee.</p>
     *
     * @return a {@link EmployeeTestData} object.
     */
    public EmployeeTestData startEmployee(){
        return new EmployeeTestData(employeeIds.get(0),"000");
    }

    /**
     * <p>Getter for the field <code>financeEmployeeIds</code>.</p>
     *
     * @return a {@link java.util.List} object.
     */
    public List<String> getFinanceEmployeeIds() {
        return financeEmployeeIds;
    }

    /**
     * <p>Setter for the field <code>financeEmployeeIds</code>.</p>
     *
     * @param financeEmployeeIds a {@link java.util.List} object.
     */
    public void setFinanceEmployeeIds(List<String> financeEmployeeIds) {
        this.financeEmployeeIds = financeEmployeeIds;
    }

    /**
     * <p>Getter for the field <code>purchaseEmployeeIds</code>.</p>
     *
     * @return a {@link java.util.List} object.
     */
    public List<String> getPurchaseEmployeeIds() {
        return purchaseEmployeeIds;
    }

    /**
     * <p>Setter for the field <code>purchaseEmployeeIds</code>.</p>
     *
     * @param purchaseEmployeeIds a {@link java.util.List} object.
     */
    public void setPurchaseEmployeeIds(List<String> purchaseEmployeeIds) {
        this.purchaseEmployeeIds = purchaseEmployeeIds;
    }

    /**
     * <p>Getter for the field <code>adminEmployeeIds</code>.</p>
     *
     * @return a {@link java.util.List} object.
     */
    public List<String> getAdminEmployeeIds() {
        return adminEmployeeIds;
    }

    /**
     * <p>Setter for the field <code>adminEmployeeIds</code>.</p>
     *
     * @param adminEmployeeIds a {@link java.util.List} object.
     */
    public void setAdminEmployeeIds(List<String> adminEmployeeIds) {
        this.adminEmployeeIds = adminEmployeeIds;
    }

    /**
     * <p>Getter for the field <code>confirmEmployeeIds</code>.</p>
     *
     * @return a {@link java.util.List} object.
     */
    public List<String> getConfirmEmployeeIds() {
        return confirmEmployeeIds;
    }

    /**
     * <p>Setter for the field <code>confirmEmployeeIds</code>.</p>
     *
     * @param confirmEmployeeIds a {@link java.util.List} object.
     */
    public void setConfirmEmployeeIds(List<String> confirmEmployeeIds) {
        this.confirmEmployeeIds = confirmEmployeeIds;
    }
}
