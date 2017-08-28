package io.ift.automation.testscaffold.webtest.enums;

import io.ift.automation.testscaffold.WebTestException;
import io.ift.automation.helpers.CollectionsHelper;
import com.google.common.collect.Maps;

/**
 * Created by patrick on 15/4/2.
 *  todo refactor this
 * @version $Id$
 */
public enum WebElementOperation {
    input, sendKeys, selectByValue, selectByIndex, selectAll,
    click, submit, deSelectByValue, deSelectByIndex,
    select, deselect, set, clearAndInput, selectDate,
    inputIfPresent, selectByVisibleText, selectPicType,
    upload, switchTab, closeTab, closePopUp,moveTo,clickIfPresent;


    /**
     * 获取WebElement 操作的枚举
     *
     * @param operationName
     * @return
     */
    public static WebElementOperation getWebElementOperation(String operationName) {

        WebElementOperation result = CollectionsHelper.filter(WebElementOperation.values(),
                actionName -> {
                    return actionName.toString().equals(operationName);
                });
        if (result == null) throw new WebTestException(operationName + " is Not a valid operation name");

        return result;
    }

    /**
     * 获取WebElement 操作的枚举
     * todo refactor to map method lookup,
     * and support registration default method for new customized elements
     *
     * @param type
     * @return
     */
    public static WebElementOperation getWebElementOperationByElementType(String type) {
        if ("inputbox".equalsIgnoreCase(type)) return inputIfPresent;
        if ("selector".equalsIgnoreCase(type)) return select;
        if ("NgRepeatableElement".equalsIgnoreCase(type)) return clearAndInput;
        if ("uploadElement".equalsIgnoreCase(type)) return upload;
        if ("pictureItem".equalsIgnoreCase(type)) return selectPicType;
        if ("ListPictureElement".equalsIgnoreCase(type) ||
                "DropDownElement".equalsIgnoreCase(type) ||
                "CheckBoxContainerElement".equalsIgnoreCase(type)
                ||"TagElement".equalsIgnoreCase(type)
                ||"SelectOptions".equalsIgnoreCase(type)) return select;
        if ("selectlist".equalsIgnoreCase(type)) return selectByVisibleText;
        if ("checkbox".equalsIgnoreCase(type)) return selectByVisibleText;
        if ("radio".equalsIgnoreCase(type)) return selectByVisibleText;
        if ("datepicker".equalsIgnoreCase(type)) return selectDate;
        if ("treeElement".equalsIgnoreCase(type)) return select;
        return clickIfPresent;
    }
}
