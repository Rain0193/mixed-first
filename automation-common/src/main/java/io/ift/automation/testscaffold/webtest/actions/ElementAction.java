package io.ift.automation.testscaffold.webtest.actions;

import io.ift.automation.testscaffold.webtest.webUI.htmlelements.BasePage;
import com.google.common.base.MoreObjects;

/**
 * 放置Element Action
 * 格式:
 *  - page -> page类
 *  - elementName -> element 定义在page类里面的名字
 *  - actionName -> 动作的定义：比如input，click
 *  - data -> 输入的data
 */
@Deprecated
public class ElementAction<T extends BasePage> {
    private Class<T> page;
    private String elementName;
    private String actionName;
    private String data;
    private T pageInstance;

    public ElementAction(Class<T> page,
                         String elementName, String actionName, String data) {
        this.page = page;
        this.elementName = elementName;
        this.actionName = actionName;
        this.data = data;
    }

    public ElementAction(T pageInstance,
                         String elementName, String actionName, String data) {
        this.pageInstance = pageInstance;
        this.elementName = elementName;
        this.actionName = actionName;
        this.data = data;
        this.page = (Class<T>) pageInstance.getClass();
    }
    public Class<T> getPage() {
        return page;
    }

    public void setPage(Class<T> page) {
        this.page = page;
    }

    public String getElementName() {
        return elementName;
    }

    public void setElementName(String elementName) {
        this.elementName = elementName;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public T getPageInstance() {
        return pageInstance;
    }

    public void setPageInstance(T pageInstance) {
        this.pageInstance = pageInstance;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("page", page)
                .add("elementName", elementName)
                .add("actionName", actionName)
                .add("data", data)
                .add("pageInstance", pageInstance)
                .toString();
    }
}
