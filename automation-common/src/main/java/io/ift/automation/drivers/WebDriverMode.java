package io.ift.automation.drivers;

import io.ift.automation.helpers.CollectionsHelper;
import io.ift.automation.helpers.StringHelper;
import com.google.common.base.Predicate;

/**
 * WebDriver 模式的枚举
 * Local ： 本地
 * GRID： selenium grid
 * LOCALGRID: local selenium grid
 */
public enum WebDriverMode {
    LOCAL,GRID,LOCALGRID, WebDriverMode;

    /**
     * 获取Webdriver 运行mode
     * @param mode
     * @return
     */
    public static WebDriverMode getWebDriverMode(final String mode){
        WebDriverMode driverMode =null;
        if(StringHelper.isNotEmptyOrNotBlankString(mode)){

            driverMode = CollectionsHelper.filter(WebDriverMode.values(), input -> {
                if(input==null) return false;
                return input.toString().equalsIgnoreCase(mode);
            });
        }

       return driverMode==null?LOCAL:driverMode;
    }
}
