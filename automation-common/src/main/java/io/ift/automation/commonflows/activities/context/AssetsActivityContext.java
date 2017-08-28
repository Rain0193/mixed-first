package io.ift.automation.commonflows.activities.context;

/**
 * Created by patrick on 15/8/25.
 */
public class AssetsActivityContext extends ActivityContext {

    private String frontLineOrgIds;
    private String backOfficeOrgIds;

    public String getFrontLineOrgIds() {
        return frontLineOrgIds;
    }

    public void setFrontLineOrgIds(String frontLineOrgIds) {
        this.frontLineOrgIds = frontLineOrgIds;
    }

    public String getBackOfficeOrgIds() {
        return backOfficeOrgIds;
    }

    public void setBackOfficeOrgIds(String backOfficeOrgIds) {
        this.backOfficeOrgIds = backOfficeOrgIds;
    }
}
