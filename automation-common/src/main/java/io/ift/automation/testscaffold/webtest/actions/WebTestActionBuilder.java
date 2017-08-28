package io.ift.automation.testscaffold.webtest.actions;

import io.ift.automation.drivers.DriverFactory;
import io.ift.automation.helpers.StringHelper;
import io.ift.automation.helpers.webdriver.WebDriverHelper;
import io.ift.automation.logging.TestResultLogger;
import io.ift.automation.testscaffold.TestAction;
import io.ift.automation.testscaffold.WebTestException;
import io.ift.automation.data.DataComposer;
import io.ift.automation.data.TestData;
import io.ift.automation.helpers.CollectionsHelper;
import io.ift.automation.helpers.ReflectionHelper;
import io.ift.automation.testscaffold.webtest.enums.WebElementOperation;
import io.ift.automation.testscaffold.webtest.annotations.UIAction;
import io.ift.automation.testscaffold.webtest.annotations.UIActions;
import io.ift.automation.testscaffold.webtest.webUI.annotations.webpage.Verification;
import io.ift.automation.testscaffold.webtest.webUI.elementloader.ModifiedPageFactory;
import io.ift.automation.testscaffold.webtest.webUI.htmlelements.BasePage;
import com.google.common.collect.Lists;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

/**
 * Created by patrick on 15/4/2.
 * This is not a good practice for using static methods.
 * The mistake was made, re-think the implementation,the executor
 * mainly includes following things in a common practice:
 * 1. Executor
 * 2. Execution Context
 * 3. Parser for the elements and actions
 * 4. Handlers for different actions
 * 5. Template for executing a given element lists
 * or page lists with a defined process name
 * 6. Hook functions for different annotations or executing phrases
 * 7. need refactor
 * @version $Id$
 */
@SuppressWarnings("unchecked")
public class WebTestActionBuilder {

    public final static String CLOSE_POPUP = "closePopUp";
    public final static String CLOSE_TAB = "closeTab";
    public final static String SWITCH_TAB = "switchTab";
    public final static String WAIT_LOAD = "waitPageLoad";
    public final static String SCROLL_TO_BOTTOM = "scrollToBottom";
    public final static String SCROLL_TO_DIV_BOTTOM = "scrollToDivBottom";

    private WebTestActionBuilder() {
    }

    /**
     * get UI Action description from Annotation UIAction
     *
     * @param page
     * @param actionName
     * @param <T>
     * @return
     */
    private static <T extends BasePage> UIAction getUiAction(T page, final String actionName) {
        UIActions annotation = page.getClass().getAnnotation(UIActions.class);
        if (annotation == null) throw new WebTestException(
                page.getClass().getSimpleName() + "without UIActions annotation");

        UIAction[] actions = annotation.actions();
        if (actions.length == 0)
            throw new WebTestException(page.getClass().getSimpleName() + "without UIAction annotation");
        UIAction action = CollectionsHelper.filter(actions, input -> {
            if (input.processName() == null) return false;
            return actionName.equalsIgnoreCase(input.processName());
        });
        if (action == null) throw new WebTestException("action " + actionName + " " +
                "is not existing in " + page);

        return action;
    }


    /**
     * 解析element description，当异常输入时抛出@TestServiceException
     * "-" in element description is also supported
     *
     * @param page
     * @param elementDescription
     * @param <T>
     * @return
     */
    private static <T extends BasePage> String[] parseElementDescription(T page, String elementDescription) {
        String[] elementAction = elementDescription.trim().replaceAll(" ", "-").split("-");
        if (elementAction.length > 2) throw new WebTestException("please check your action " +
                "description in " + page + " for " + elementDescription);
        return elementAction;
    }

    /**
     * 根据注解描述执行描述中的操作
     *
     * @param page
     * @param uiAnnotationName
     * @param data
     * @param <T>              page 实例
     */
    public static <T extends BasePage> void executeUIAction(T page, String uiAnnotationName
            , TestData data) {
        UIAction action = getUiAction(page, uiAnnotationName);
        processElementAction(page, CollectionsHelper.arrayToList(action.elementActionDescription()), data);
    }

    /**
     * 根据描述执行描述中的操作
     *
     * @param page
     * @param elementActionPairList
     * @param data
     * @param <T>
     */
    public static <T extends BasePage> void executeUIAction(T page, List<String> elementActionPairList
            , TestData data) {
        processElementAction(page, elementActionPairList, data);
    }

    /**
     * process element action
     *
     * @param page
     * @param elementActionPairList
     * @param data
     * @param <T>
     */
    private static <T extends BasePage> void processElementAction(T page
            , List<String> elementActionPairList
            , TestData data) {
        DefinedVerificationHandler.handlerDefinedPageVerification(page);
        for (String s : elementActionPairList) {
            if (!DriverFactory.getThreadLevelTestContext().isNotQuit()) return;
            TestResultLogger.log("开始执行 {}", s);
            String[] stepDescription = parseElementDescription(page, s); //二维数组，[0]-元素，[1]-动作
            String locationName = page.getClass().getSimpleName() + "."
                    + stepDescription[0];

            processBeforeCheckPoints(locationName);
            executeElementDescription(page, stepDescription, data);
            processAfterCheckPoints(locationName);
        }
    }

    /**
     * 根据UIAction的描述，创建TestAction
     *
     * @param page
     * @param uiAnnotationNam
     * @param data
     * @param <T>
     * @return
     */
    public static <T extends BasePage> TestAction createTestActionByUIAction(T page,
                                                                             String uiAnnotationNam,
                                                                             TestData data) {

        return new BaseWebTestAction(page.getDriver(), data) {
            @Override
            public void execute() {
                executeUIAction(page, uiAnnotationNam, data);
            }
        };
    }

    /**
     * 根据UI Annotation创建Test Action
     *
     * @param clazz
     * @param uiAnnotationName
     * @param driver
     * @param data
     * @param <T>
     * @return
     */
    public static <T extends BasePage> TestAction
    createTestActionByUIAction(Class<T> clazz, String uiAnnotationName
            , WebDriver driver
            , TestData data) {

        T page = ModifiedPageFactory.createPageObject(driver, clazz, data);
        return new BaseWebTestAction(page.getDriver(), data) {
            @Override
            public void execute() {
                executeUIAction(page, uiAnnotationName, data);
            }
        };
    }

    /**
     * 根据注解名字执行多个页面的操作,前提是多个页面中具有相同的UIAction名称
     *
     * @param clazz
     * @param uiAnnotationNam
     * @param driver
     * @param data
     */
    public static void execute(List<Class> clazz, String uiAnnotationNam
            , WebDriver driver
            , TestData data) {
        if (!DriverFactory.getThreadLevelTestContext().isNotQuit()) return;
        for (Class t : clazz) {
            createTestActionByUIAction(t, uiAnnotationNam, driver, data).execute();
        }
    }

    /**
     * 创建TestAction
     *
     * @param clazz
     * @param elementActionPairList
     * @param driver
     * @param data
     * @param <T>
     * @return TestAction
     */
    public static <T extends BasePage> TestAction createTestActionByElementActionList(
            Class<T> clazz,
            List<String> elementActionPairList
            , WebDriver driver
            , TestData data) {

        T page = ModifiedPageFactory.createPageObject(driver, clazz, data);

        return new BaseWebTestAction(page.getDriver(), data) {
            @Override
            public void execute() {
                executeUIAction(page, elementActionPairList, data);
            }
        };
    }

    /**
     * 根据element action的描述执行Element Action
     * elementsAndActionPair,元素 动作
     *
     * @param page
     * @param stepDescription,二维数组，[0]-元素，[1]-动作
     * @param data
     * @param <T>
     */
    private static <T extends BasePage> void executeElementDescription(
            T page, String[] stepDescription, TestData data) {
        if (isIgnoreField(data, stepDescription)) return;
        if (isPredefinedHandled(page, stepDescription)) return; //todo move to base page
        if (handleMethod(page, stepDescription, data)) return;
        String actionName;
        Field field = page.getField(stepDescription[0]);
        Object elementInstance = ReflectionHelper.getFieldValue(page, field);
        Verification verification = field.getAnnotation(Verification.class);
        DefinedVerificationHandler.handlerDefinedElementVerification(elementInstance, verification, DriverFactory.get());
        String fieldType = ReflectionHelper.getAccessibleFieldTypeName(page, stepDescription[0]);

        if (stepDescription.length < 2) {
            if ("list".equalsIgnoreCase(fieldType)) { //if type is list, get generic type
                actionName = WebElementOperation.getWebElementOperationByElementType(
                        ReflectionHelper.getGenericParameterClass(page,
                                stepDescription[0]).getSimpleName()).toString();
                HtmlElementsActionHandler.handlerElementList((List<Object>) elementInstance, actionName, data.get(stepDescription[0]));
                return;
            } else {
                actionName = WebElementOperation.getWebElementOperationByElementType(fieldType).toString();
            }
        } else {
            if (stepDescription[1].length() < 1)
                throw new WebTestException("please check your action description in " + page + " for "
                        + Arrays.toString(stepDescription));
            actionName = stepDescription[1];

        }

        HtmlElementsActionHandler.handleElement(elementInstance, actionName, data != null ? data.get(stepDescription[0]) : null);
    }

    /**
     * 直接调用组合动作
     *
     * @param page
     * @param stepDescription
     * @param <T>
     * @return
     */
    private static <T extends BasePage> boolean handleMethod(
            T page, String[] stepDescription, TestData testdata) {
        Method m = page.getAction(stepDescription[0]);
        if (m != null) {
            String data = testdata.get(stepDescription[0]) == null ? StringHelper.EMPTY : testdata.get(stepDescription[0]);
            try {
                m.invoke(page, data);
                return true;
            } catch (IllegalAccessException | InvocationTargetException e) {
                TestResultLogger.warn(stepDescription[0] + "不存在,请检查是否是的错误" + page);
            }
            return false;
        }
        return false;
    }

    /**
     * handle switchTab,closeTab,closePopUp
     * refactor to a mapping
     *
     * @param page
     * @param stepDescription
     * @param <T>
     * @return
     */
    private static <T extends BasePage> boolean isPredefinedHandled(T page, String[] stepDescription) {
        //todo refactor to function mapping
        if (stepDescription[0].equalsIgnoreCase(WAIT_LOAD)) {
            WebDriverHelper.waitForSubmit(3000L);
            return true;
        }

        if (stepDescription[0].equalsIgnoreCase(SCROLL_TO_BOTTOM)) {
            WebDriverHelper.scrollToBottom(page.getDriver());
            return true;
        }


        if (stepDescription[0].equalsIgnoreCase(SCROLL_TO_DIV_BOTTOM)) {
            WebDriverHelper.scrollToDivBottom(page.getDriver());
            return true;
        }

        if (stepDescription[0].equalsIgnoreCase(SWITCH_TAB)) {
            WebDriverHelper.swithTab(page.getDriver());
            return true;
        }

        if (stepDescription[0].equalsIgnoreCase(CLOSE_TAB)) {
            WebDriverHelper.closeTab(page.getDriver());
            return true;
        }
        if (stepDescription[0].equalsIgnoreCase(CLOSE_POPUP)) {
            String message;
            if (stepDescription.length == 1) { //默认的操作
                message = WebDriverHelper.handleAlert(page.getDriver(), true);
            } else {
                message = WebDriverHelper.handleAlert(page.getDriver(), Boolean.parseBoolean(stepDescription[1]));
            }
            DriverFactory.getThreadLevelTestContext().setAlertMessage(message);
            return true;
        }
        return false;
    }


    /**
     * 如果是ignored field 则在执行过程中忽略
     *
     * @param data
     * @param stepDescription
     * @return
     */
    private static boolean isIgnoreField(TestData data, String[] stepDescription) {
        if (null != data) {
            for (String s : data.ignoredFields()) {
                if (s.equalsIgnoreCase(stepDescription[0])) return true;
            }
        }
        return false;
    }

    /**
     * 检查点校验
     * 可能需要传入页面类的实例来确定location
     *
     * @param locationName
     */
    //refactor to listener or template pattern
    private static void processAfterCheckPoints(String locationName) {
        DriverFactory.getThreadLevelTestContext().getAfterCheckPoints().
                stream().filter(checkPoint -> checkPoint.where().eval(locationName)).
                forEach(checkPoint -> {
                    TestResultLogger.log("开始进行检查点 {} 检查 ", checkPoint);
                    checkPoint.verify();
                    if (checkPoint.isExist())
                        DriverFactory.getThreadLevelTestContext().setNotQuit(false);
                });
    }

    private static void processBeforeCheckPoints(String locationName) {
        DriverFactory.getThreadLevelTestContext().getBeforeCheckPoints().
                stream().filter(checkPoint -> checkPoint.where().eval(locationName)).
                forEach(checkPoint -> {
                    TestResultLogger.log("开始进行检查点 {} 检查 ", checkPoint);
                    checkPoint.verify();
                    if (checkPoint.isExist())
                        DriverFactory.getThreadLevelTestContext().setNotQuit(false);
                });
    }


    /**
     * 使用页面的类,创建页面实例
     *
     * @param pageClass
     * @param driver
     * @param <T>
     * @return
     */
    public static <T extends BasePage> T use(Class<T> pageClass, WebDriver driver) {
        return ModifiedPageFactory.createPageObject(driver, pageClass);
    }

    public static <T extends BasePage> T use(Class<T> pageClass, WebDriver driver, TestData data) {
        return ModifiedPageFactory.createPageObject(driver, pageClass, data);
    }

    /**
     * parse element Description to ElementAction instance
     *
     * @param page
     * @param elementDescription
     * @param data
     * @param <T>
     * @return
     */
    @Deprecated
    private static <T extends BasePage> ElementAction parseToElementAction(T page, String elementDescription, TestData... data) {

        String[] elementAction = parseElementDescription(page, elementDescription);
        String actionName;
        String fieldType = ReflectionHelper.getAccessibleFieldTypeName(page, elementAction[0]);
        if (elementAction.length < 2) {
            if ("list".equalsIgnoreCase(fieldType)) { //if type is list, get generic type
                actionName = WebElementOperation.getWebElementOperationByElementType(
                        ReflectionHelper.getGenericParameterClass(page, elementAction[0]).getSimpleName()).toString();
            } else {
                actionName = WebElementOperation.getWebElementOperationByElementType(fieldType).toString();
            }
        } else {
            if (elementAction[1].length() < 1)
                throw new WebTestException("please check your action description in " + page + " for " + elementDescription);
            actionName = elementAction[1];
        }

        return new ElementAction(page, elementAction[0], actionName,
                (String) DataComposer.from(data).get(elementAction[0]));
    }

    /**
     * 获取所有的ElementAction
     *
     * @param page
     * @param actionName
     * @param data
     * @param <T>
     * @return
     */
    @Deprecated
    public static <T extends BasePage> List<ElementAction> buildActions(T page
            , final String actionName, TestData... data) {

        List<ElementAction> elementActionList = Lists.newArrayList();
        UIAction action = getUiAction(page, actionName);
        for (String s : action.elementActionDescription()) {
            elementActionList.add(parseToElementAction(page, s, data));
        }

        return elementActionList;
    }

}
