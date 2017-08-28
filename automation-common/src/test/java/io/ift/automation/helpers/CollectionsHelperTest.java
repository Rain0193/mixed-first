package io.ift.automation.helpers;

import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Created by patrick on 15/9/25.
 */
public class CollectionsHelperTest {

    @Test
    public void testLastElement() throws Exception {
        String path_1 ="com.test.automation";
        String path_2 ="automation";
        Assert.assertEquals(CollectionsHelper.lastElement(path_1.split("\\.")),"automation");
        Assert.assertEquals(CollectionsHelper.lastElement(path_2.split("\\.")),"automation");
    }
}
