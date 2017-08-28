package io.ift.automation.testscaffold;

import io.ift.automation.assertion.SoftAssertion;
import io.ift.automation.testscaffold.webtest.IWhere;

/**
 * Created by patrick on 15/4/2.
 *
 * @version $Id$
 */


public interface CheckPoint<C> {

    SoftAssertion sa = new SoftAssertion();
    /**
     * 执行验证动作
     */
    void verify();

    /**
     * 用来判断是否需要退出整个测试
     * @return
     */
    default boolean isExist(){
        return false;
    }

    default boolean isBeforeCheckPoint(){
        return false;
    }

    /**
     * 获取测试结果
     * @return
     */
    default SoftAssertion getTestResult(){
        return sa;
    }

    /**
     * 表示检查点的位置
     * @param {@link com.dooioo.automation.testscaffold.Where}
     * @return
     */
   default IWhere where(){
     return new Where(this.getClass().getSimpleName());
   }

    /**
     * 此方法默认什么都不做,todo refactor this context for passing the context, it is not good design
     * because it is stateful
     * @param context
     */
   default void setContext(C context){

   }

}
