package io.ift.automation.helpers;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static org.testng.Assert.*;

public class CSVHelperTest {

    @Test
    public void test_csv() throws Exception{

        Map<String,Class> clazzMap = new HashMap<String,Class>();
        Iterator<Object[]> y= CSVHelper.loadCSVtoIterator("testcase.csv", clazzMap);
        Assert.assertNotNull(y);
    }
}
