package io.ift.automation.tm.testmodel;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by patrick on 15/6/9.
 *
 * @version $Id: TestCasePriorityTest.java 1452 2015-06-15 06:35:56Z wuke $
 */
public class TestCasePriorityTest {

    @Test
    public void testGetPriorityByName() throws Exception {
        Assert.assertEquals(TestCasePriority.getPriorityByName("smoketesting"),TestCasePriority.SmokeTesting);
        Assert.assertEquals(TestCasePriority.getPriorityByName(""),TestCasePriority.Others);
        Assert.assertEquals(TestCasePriority.getPriorityByName("Nothing"), TestCasePriority.Others);
    }

    @Test
    public void testIsApplied() throws Exception {
        Assert.assertEquals(TestCasePriority.isRunPriority("smoketesting", "P1"),false);
        Assert.assertEquals(TestCasePriority.isRunPriority("P2", "P1"),true);
        Assert.assertEquals(TestCasePriority.isRunPriority("", "P1"),true);
    }
}
