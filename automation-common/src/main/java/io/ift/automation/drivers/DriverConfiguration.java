package io.ift.automation.drivers;

import io.ift.automation.helpers.CollectionsHelper;
import io.ift.automation.helpers.PropertiesHelper;
import io.ift.automation.helpers.StringHelper;
import com.google.common.collect.Lists;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.support.events.WebDriverEventListener;

import java.util.List;

/**
 * Created by patrick on 15/3/11.
 *
 * @version $Id$
 */


public class DriverConfiguration {

    private String browserType ;
    private String webDriverMode;
    private String gridUrl;
    private List<WebDriverEventListener> l;
    //todo move to configuration
    private static final String DEFAULT_LISTENER_PREFIX ="com.dooioo.automation.listener.webdriver.";
    private static final Logger logger = LogManager.getLogger(DriverConfiguration.class.getName());

    public DriverConfiguration() {
        l = Lists.newArrayList();
    }

    /**
     * 获取Webdriver default configuration
     * 支持可配置的监听器，todo--可配置的file detector
     * @return
     */
    public static DriverConfiguration defaultConfiguration(){

        DriverConfiguration configuration = new DriverConfiguration();
        //todo get the grid information from testng file or other place but not properties file
        configuration.browserType = PropertiesHelper
            .getProperty("driverconfiguration.browsetype", "chrome");
        configuration.webDriverMode = PropertiesHelper.getProperty("driverconfiguration.webdrivermode","LOCAL");
        configuration.gridUrl = PropertiesHelper.getProperty("driverconfiguration.gridurl","http://localhost:8080:4444/wd/hub");
        String eventListener = PropertiesHelper.getProperty("driverconfiguration.eventlistener",
                                                            StringHelper.EMPTY);

        for (String s : eventListener.split(",")) {
            try {
                if(StringHelper.isNotEmptyOrNotBlankString(s)&&s.length()>0)
                    configuration.addListeners((WebDriverEventListener) Class.forName(DEFAULT_LISTENER_PREFIX + s.trim()).newInstance());
            } catch (Exception e) {
                logger.warn("listener {} is not initialized",s);
                logger.warn(e);
            }
        }
        return configuration;
    }


    public String getBrowserType() {
        return browserType;
    }

    public void setBrowserType(String browserType) {
        this.browserType = browserType;
    }

    public String getWebDriverMode() {
        return webDriverMode;
    }

    public void setWebDriverMode(String webDriverMode) {
        this.webDriverMode = webDriverMode;
    }

    public String getGridUrl() {
        return gridUrl;
    }

    public void setGridUrl(String gridUrl) {
        this.gridUrl = gridUrl;
    }

    public List<WebDriverEventListener> getListeners() {
        return l;
    }

    public void addListeners(WebDriverEventListener ... listeners) {
        CollectionsHelper.addAll(l, listeners);
    }
}
