package io.ift.automation.commonflows.models;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;

import static org.testng.Assert.*;

/**
 * Created by patrick on 15/8/18.
 */
public class ActivitiesTest {

    @Test
    public void testApprovalConfirmChain() throws Exception {
        Activities a = new Activities();
        a.getConfirmEmployeeIds().add("83562-88619");
        Assert.assertEquals(a.approvalConfirmChain().get(0).size(), 2, "check size");
        System.out.println(a.approvalConfirmChain());
    }
}
