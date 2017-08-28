package io.ift.automation.testscaffold;

import io.ift.automation.helpers.StringHelper;
import io.ift.automation.helpers.mockmodel.MockPropertyData;
import io.ift.automation.testscaffold.codegenerator.WebUICodeGenerator;
import io.ift.automation.testscaffold.webtest.webUI.mockpages.MockPage1;
import io.ift.automation.testscaffold.webtest.webUI.mockpages.MockPage2;
import com.google.common.collect.Lists;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Created by patrick on 15/6/15.
 *
 * @version $Id: TestActionTest.java 1468 2015-06-23 10:17:17Z wuke $
 */
public class TestActionTest {
    @Test
    public void getContext(){
        TestAction a = new TestAction() {
            @Override
            public void execute() {
                System.out.println("true");
            }
        };

        Assert.assertNull(a.getTestContext());
        Assert.assertEquals(a.getTestActionName(), StringHelper.EMPTY);
        a.execute();
    }

    @Test
    public void parseToExcel(){
        WebUICodeGenerator.build().writePageObjectsToExcel("AddProperty.xls",
                                                           Lists.newArrayList(MockPage1.class,MockPage2.class));
    }

    @Test
    public void codeGenerate(){
        WebUICodeGenerator t=  WebUICodeGenerator.build("AddProperty.xls");
                t.generateAnnotationStatement("新增住宅");
        t.generateAnnotationStatement("新增别墅");
    }


    @Test
    public void codeGenerate_2(){
        WebUICodeGenerator t=  WebUICodeGenerator.build("AddProperty.xls");
        t.generateSingleTestStep("新增住宅");
        t.generateSingleTestStep("新增别墅");
    }

    @Test
    public void codeGenerate_3(){
        WebUICodeGenerator t=  WebUICodeGenerator.build();
        t.generateTestDataClass(Lists.newArrayList(MockPage1.class,MockPage2.class));
    }

    @Test
    public void codeGenerate_4(){
        new MockPropertyData().toXLS("addProperty","addProperty.xls");
    }
}
