package io.ift.automation.tm.testmodel;

import io.ift.automation.helpers.ExcelHelper;
import io.ift.automation.testscaffold.BaseWebTest;
import com.google.common.collect.Lists;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by patrick on 15/6/8.
 *
 * @version $Id: TestCaseDescriptionTest.java 1452 2015-06-15 06:35:56Z wuke $
 */
public class TestCaseDescriptionTest extends BaseWebTest {

    @DataProvider(name = "testcase_data")
    public Iterator<Object[]> getAPITestData(Method m) throws Exception {
        Map<String, Class> clazzMap = new HashMap<String, Class>();
        clazzMap.put("T", TestDescription.class);
        List<Object[]> y = ExcelHelper.build("testdata/testcase.xls")
                .loadExcelDataToList(clazzMap);
        List<Object[]>result= Lists.newArrayList();
        for (Object[] objects : y) {
            for (Object o : objects) {
                if(o instanceof TestDescription){
                    if (!((TestDescription)o).getTestMethodName().equalsIgnoreCase(m.getName())
                            ||!TestCasePriority.isRunPriority(super.priority,((TestDescription) o).getPriority())){
                        break;
                    }else{
                        result.add(objects);
                        break;
                    }
                }
            }

        }

        return result.iterator();
    }

    @Test(dataProvider = "testcase_data")
    public void testMethod(TestDescription td){

        System.out.println(td);
        System.out.println(td);
    }
}
