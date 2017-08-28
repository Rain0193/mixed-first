package io.ift.automation.testscaffold.webtest.webUI.htmlelements;

/**
 * Created by patrick on 15/3/18.
 *
 * @version $Id$
 */


public interface NamedElement {
    /**
     * return element的名字
     * @return
     */
    String getName();

    /**
     * return element 所在页面的名字
     * @return
     */
    String getLocationName();
}
