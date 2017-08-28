package io.ift.automation.helpers;

import com.beust.jcommander.internal.Maps;
import io.ift.automation.data.MockAddFile;
import io.ift.automation.helpers.mockmodel.MockPropertyData;
import io.ift.automation.helpers.testmodel.TestCase_Test;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.*;

import static org.testng.Assert.*;

public class ExcelHelperTest {

    @Test
    public void testBuild() throws Exception {
        assertNotNull(ExcelHelper.build("testdata/test.xls"));
    }

    @Test(expectedExceptions = RuntimeException.class)
    public void testBuild_error_file() throws Exception {
        assertNotNull(ExcelHelper.build("testdata/testtext.xls"));
    }

    @Test
    public void testBuild1() throws Exception {
        assertNotNull(ExcelHelper.build("testdata/test1.xlsx","sheet1"));
    }

    @Test(expectedExceptions = RuntimeException.class)
    public void testBuild_invalid_file() throws Exception {
        assertNotNull(ExcelHelper.build("testdata/test.xml"));
    }

    @Test
    public void testLoadExcelDataToIterator() throws Exception {

        Map<String,Class> map = Maps.newHashMap();
        map.put("T",TestCase_Test.class);
        Iterator<Object[]> testNGDataProvider =ExcelHelper.build("testdata/test.xls").loadExcelDataToIterator(map);
        while (testNGDataProvider.hasNext()){
            Object[] objects = testNGDataProvider.next();
            System.out.println(objects);
            for (Object o : objects) {
                System.out.println(o);
            }
        }
        System.out.println(testNGDataProvider);
    }

    @Test
    public void testLoadExcel() throws Exception {

        Map<String,Class> map = Maps.newHashMap();
        map.put("T", TestCase_Test.class);
        Iterator<Object[]> testNGDataProvider = ExcelHelper.build("testdata/test2.xls").ToIteratorInColMode(map);
        while (testNGDataProvider.hasNext()){
            Object[] objects = testNGDataProvider.next();
            System.out.println(objects);
            for (Object o : objects) {
                System.out.println(o);
            }
        }
        System.out.println(testNGDataProvider);
    }


    @Test
    public void testReadAll(){
        List<List<String>> result = ExcelHelper.build("webflow.xls").readAll();
        Assert.assertTrue(!result.isEmpty());
    }

    @Test
    public void testLoadExcelData() throws Exception {
        Map<String, Class> clazzMap = new HashMap<String, Class>();
        clazzMap.put("PropertyTestData", MockPropertyData.class);
        Iterator<Object[]> y = ExcelHelper.build("addPropertyTestCasesColumn.xls").ToIteratorInColMode(clazzMap);
        while(y.hasNext()){
            Object[] it =y.next();
            for (Object o : it) {
                if (o instanceof MockPropertyData){
                    Assert.assertEquals(((MockPropertyData) o).getPrice(),"350");
                    Assert.assertEquals(((MockPropertyData) o).getContactName(),"1234567");
                }
            }
        }
    }

    @Test
    public void testLoadExcelData_1() throws Exception {
        Map<String, Class> clazzMap = new HashMap<String, Class>();
        clazzMap.put("AddFilingData", MockAddFile.class);
        Iterator<Object[]> y = ExcelHelper.build("addFilingTestCases.xls").ToIteratorInColMode(clazzMap);
        Assert.assertNotNull(y);
    }

}
