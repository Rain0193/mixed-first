package io.ift.automation.testscaffold;


/**
 * Created by patrick on 15/4/2.
 *
 * @version $Id$
 */


public interface TestAction {

    /**
     * 执行测试步骤的函数，可以是一个页面的多个步骤，可以是多个页面的步骤
     */
    void execute();

    /**
     * test action 的名字，默认是TestAction class的名字
     * @return
     */
    default String getTestActionName(){
        return this.getClass().getSimpleName();
    }

    /**
     * 默认返回值是null
     * @param <T>
     * @return
     */
    default <T> T getTestContext(){
        return null;
    }
}
