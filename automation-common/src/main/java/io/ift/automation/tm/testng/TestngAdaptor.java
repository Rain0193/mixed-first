package io.ift.automation.tm.testng;

import io.ift.automation.helpers.StringHelper;
import io.ift.automation.tm.testmodel.TestCase;
import io.ift.automation.tm.testmodel.TestDescription;
import org.testng.ITestResult;

/**
 * Created by patrick on 15/3/16.
 *
 * @version $Id$
 */


public class TestngAdaptor {

    private TestngAdaptor() {
    }

    public static TestCase covertToTestCase(TestCase tc, ITestResult result) {
        tc.setStartedMillis(result.getStartMillis());
        tc.setEndMills(result.getEndMillis());
        tc.setParameters(result.getParameters());
        tc.setTestClassName(result.getTestClass().getRealClass().getSimpleName());
        tc.setTestRealClassName(result.getTestClass().getRealClass().getName());
        tc.setStatus(result.getStatus());
        tc.setTestMethodName(result.getMethod().getMethodName());
        tc.setErrors(result.getThrowable());
        setTestDescription(tc,result);
        return tc;
    }

    public static void updateResult(TestCase tc,ITestResult result){
        tc.setStatus(result.getStatus());
        tc.setErrors(result.getThrowable());
    }

    private static void setTestDescription(TestCase tc, ITestResult result) {
        String des = null;
        for (Object o : result.getParameters()) {
            if (o instanceof TestDescription) {
                if (StringHelper.isNotEmptyOrNotBlankString(((TestDescription) o).getTestDescription())) {
                    des = ((TestDescription) o).getTestDescription();
                    break;
                }
            }
        }
        if (null == des) des = result.getMethod().getDescription();
        if(StringHelper.isNotEmptyOrNotBlankString(des)){
            tc.setTestDescription(des);
        }else{
            tc.setTestDescription(result.getMethod().getMethodName());
        }

    }
}
