package io.ift.automation.helpers;

import io.ift.automation.helpers.testmodel.TestCase_Test;
import com.dooioo.automation.testscaffold.webtest.webUI.htmlelements.*;
import com.google.common.collect.Lists;
import org.openqa.selenium.*;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import io.ift.automation.testscaffold.webtest.webUI.htmlelements.Button;
import io.ift.automation.testscaffold.webtest.webUI.htmlelements.UploadElement;

public class ReflectionHelperTest {

    private TestCase_Test testCaseTest = new TestCase_Test();

    @BeforeSuite
    public void init(){
        testCaseTest.setDescription("Test");
        testCaseTest.setError("error");
        testCaseTest.setTestId("testId");
        testCaseTest.setTest(new Button("name", new WebElement() {
            @Override
            public void click() {

            }

            @Override
            public void submit() {

            }

            @Override
            public void sendKeys(CharSequence... charSequences) {

            }

            @Override
            public void clear() {

            }

            @Override
            public String getTagName() {
                return null;
            }

            @Override
            public String getAttribute(String s) {
                return null;
            }

            @Override
            public boolean isSelected() {
                return false;
            }

            @Override
            public boolean isEnabled() {
                return false;
            }

            @Override
            public String getText() {
                return null;
            }

            @Override
            public List<WebElement> findElements(By by) {
                return null;
            }

            @Override
            public WebElement findElement(By by) {
                return null;
            }

            @Override
            public boolean isDisplayed() {
                return false;
            }

            @Override
            public Point getLocation() {
                return null;
            }

            @Override
            public Dimension getSize() {
                return null;
            }

            @Override
            public String getCssValue(String s) {
                return null;
            }
        }));
    }

    @Test
    public void testGetFieldValue() throws Exception {
        System.out.println(ReflectionHelper.getFieldValue(testCaseTest, "testId"));
        System.out.println(testCaseTest.getTestId());
        ReflectionHelper.setFieldValue(testCaseTest, "testId", "1111");
        System.out.println(ReflectionHelper.getFieldValue(testCaseTest, "testId"));
        //ReflectionHelper.setFieldValue(testCase,"tttt","3345");
    }

    @Test
    public void hasGenericTypeTest(){
        List<String> test = Lists.newArrayList();
        test.add("test");
        for (String s : test) {
            System.out.println(s instanceof String);
        }
    }
    @Test
    public void testInvokeGetter() throws Exception {
        Object a = ReflectionHelper.invokeGetter(testCaseTest, "test");
        System.out.println(a.getClass().getSimpleName());
    }

    @Test
    public void testInvokeSetter() throws Exception {

    }

    @Test
    public void testGetFieldValue1() throws Exception {
        Assert.assertEquals(ReflectionHelper.getFieldTypeAndValue(testCaseTest, "test").getLeft()
                , "Button");
        Assert.assertEquals(ReflectionHelper.getFieldTypeAndValue(testCaseTest, "test").getRight()
                .getClass().getSimpleName(),"Button");
    }

    @Test
    public void testSetFieldValue() throws Exception {
        ReflectionHelper.setFieldValue(testCaseTest, "testId", "testId1234");
        Assert.assertEquals(testCaseTest.getTestId(),"testId1234");
    }

    @Test
    public void testInvokeMethod() throws Exception {
        List<String> test = Lists.newArrayList();
        test.add("test");
        Method m = test.getClass().getMethod("size");
        Assert.assertEquals(ReflectionHelper.invokeMethod(test, m), 1);
    }

    @Test
    public void testInvokeMethodByName() throws Exception {
        List<String> test = Lists.newArrayList();
        test.add("test");
        Assert.assertEquals(ReflectionHelper.invokeMethodByName(test, "size", null),1);
    }


    @Test(expectedExceptions = StaleElementReferenceException.class)
    public void testInvokeMethodByName_Exception() {
        ThrowStaleElementReferenceException test = new ThrowStaleElementReferenceException();
//        test.throwStaleException("test");
           Object test1= ReflectionHelper.invokeMethodByName(test, "throwStaleException",
                   new Object[]{"error message"});
    }
    @Test(expectedExceptions = RuntimeException.class)
    public void testInvokeMethodByName_RunException() {
        ThrowStaleElementReferenceException test = new ThrowStaleElementReferenceException();
//        test.throwStaleException("test");
        Object test1= ReflectionHelper.invokeMethodByName(test, "noSuchElement",
                new Object[]{"error message"});
    }


    public class ThrowStaleElementReferenceException{
        public String throwStaleException(String test){
            System.out.println(test);
            if(test.equalsIgnoreCase("123")){
                return "123";
            }
            throw new StaleElementReferenceException("this is stale exception");
        }

        public String noSuchElement(String test){
            System.out.println(test);
            if(test.equalsIgnoreCase("123")){
                return "123";
            }
            throw new NoSuchElementException("this is stale exception");
//            throw new RuntimeException("this is stale exception");
        }
    }
    @Test
    public void testGetAccessibleField() throws Exception {
        Field field = ReflectionHelper.getAccessibleField(testCaseTest,"testId");
        Assert.assertEquals(field.getType().getSimpleName(),"String");
    }

    @Test
    public void testGetAccessibleMethod() throws Exception {
        List<TestCase_Test> test = Lists.newArrayList();
        test.add(testCaseTest);
        Method m = ReflectionHelper.getAccessibleMethod(test,"get",int.class);
        Assert.assertEquals(m.getGenericReturnType().getTypeName(), "E");
    }

    @Test
    public void testGetAccessibleMethodByName() throws Exception {
        List<String> test = Lists.newArrayList();
        test.add("test");
        Method m = ReflectionHelper.getAccessibleMethodByName(test, "size");
        Assert.assertEquals(m.getGenericReturnType().getTypeName(),"int");
    }

    @Test
    public void testOutputMethods() throws Exception {
        ReflectionHelper.outPutMethodName(UploadElement.class);
    }

    @Test
    public void testMakeAccessible1() throws Exception {

    }

    @Test
    public void testGetClassGenricType() throws Exception {

    }

    @Test
    public void testGetClassGenricType1() throws Exception {

    }

    @Test
    public void testGetUserClass() throws Exception {

    }

    @Test
    public void testConvertReflectionExceptionToUnchecked() throws Exception {

    }

    @Test
    public void testCopyProperties() throws Exception {

    }


}
