package io.ift.automation.drivers;

import io.ift.automation.helpers.CollectionsHelper;
import com.google.common.base.Predicate;

/**
 * Browser 类型枚举类
 * 用来生成不同类型的webdriver
 *
 * @link WebTestDriverBuilder
 * @author patrick
 * @version $Id: $Id
 */
public enum BrowserType {

    FireFox("*firefox"),
    Chrome("*chrome"),
    IE("*ie"),
    Safari("*safari"),
    Andriod("*andriod"),
    IPhone("*iphone"),
    PhantomJS("*phantomjs"),
    HtmlUnit("*htmlunit");

    private String type;
    BrowserType(String type) {
        this.type = type;
    }

    /**
     * 获取Browser Type
     *
     * @param type a {@link java.lang.String} object.
     * @return a {@link BrowserType} object.
     */
    public static BrowserType getBrowserType(final String type){
        if(null==type||type.isEmpty()){
            return BrowserType.Chrome; //default to chrome
        }

     BrowserType browserType= CollectionsHelper.filterByCondition(BrowserType.values(), new Predicate<BrowserType>() {
         @Override
         public boolean apply(BrowserType input) {
             if(input==null) return false;
             return input.getType().equalsIgnoreCase(type)||input.getType().contains(type.toLowerCase());
         }
     });

        return null==browserType?BrowserType.Chrome:browserType;
    }

    /**
     * <p>Getter for the field <code>type</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getType() {
        return type;
    }
}
