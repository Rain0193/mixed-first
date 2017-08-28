package io.ift.automation.drivers;

import com.beust.jcommander.internal.Lists;
import io.ift.automation.helpers.PropertiesHelper;
import io.ift.automation.testscaffold.webtest.WebTestContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ThreadGuard;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.events.WebDriverEventListener;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.List;

/**
 * Created by patrick on 15/3/10.
 *
 * @version $Id$
 */

public class DriverFactory {

    private static final Logger logger = LogManager.getLogger(DriverFactory.class.getName());
    private static ThreadLocal<WebTestContext> testContexts = new ThreadLocal<>();
    //protected static EventFiringWebDriver driver = (EventFiringWebDriver) get();

    private DriverFactory() {
    }

    /**
     * 获取线程保护的WebDriver，没有放置在上下文中，使用要小心
     *
     * @return
     */
    public static WebDriver createProtectedWebDriver() {
        return ThreadGuard.protect(new ModifiedWrappedWebDrive().getDriver());
    }

    /**
     * 清理上下文
     */
    public static void remove() {
        testContexts.remove();
    }

    /**
     * 获取ThreadLocal中的EventFiringWebDriver
     *
     * @return
     */
    public static synchronized EventFiringWebDriver get() {
        if (testContexts.get() == null) {
            testContexts.set(new WebTestContext(new
                    EventFiringWebDriver(DriverFactory.createProtectedWebDriver())));
        }
        return testContexts.get().getDriver();
    }

    /**
     * 获取ThreadLocal中的上下文，quick and dirty实现
     * todo move out of DriverFactory
     *
     * @return
     */
    public static synchronized WebTestContext getThreadLevelTestContext() {
        if (testContexts.get() == null) {
            testContexts.set(new WebTestContext(new
                    EventFiringWebDriver(DriverFactory.createProtectedWebDriver())));
        }

        return testContexts.get();
    }

    /**
     * 创建ThreadLocal中的Driver
     *
     * @return
     */
    public static WebDriver createWebDriver() {
        testContexts.set(new WebTestContext(new ModifiedWrappedWebDrive().getDriver()));
        return testContexts.get().getDriver();
    }

    public static ThreadLocal<WebTestContext> getTestContexts() {
        return testContexts;
    }

    public static class ModifiedWrappedWebDrive {
        private DesiredCapabilities capabilities;
        private EventFiringWebDriver driver;
        private List<WebDriverEventListener> webDriverEventListeners;

        public ModifiedWrappedWebDrive(String webDriverMode, String browserType, String url) {
            init(webDriverMode, browserType, url);
            webDriverEventListeners = Lists.newArrayList();
        }

        public ModifiedWrappedWebDrive(String webDriverMode, String browserType,
                                       String url, List<WebDriverEventListener> listeners) {
            webDriverEventListeners = Lists.newArrayList();
            webDriverEventListeners.addAll(listeners);
            init(webDriverMode, browserType, url);
        }

        public ModifiedWrappedWebDrive() {
            this(DriverConfiguration.defaultConfiguration().getWebDriverMode(),
                    DriverConfiguration.defaultConfiguration().getBrowserType()
                    , DriverConfiguration.defaultConfiguration().getGridUrl(),
                    DriverConfiguration.defaultConfiguration().getListeners());
        }

        private void initCapabilities(String browserType) {
            switch (BrowserType.getBrowserType(browserType)) {
                case Chrome: {
                    logger.info("start init chrome capability");
                    setChromeDriverProperty();
                    capabilities = DesiredCapabilities.chrome();
                    break;
                }
                case FireFox:
                    logger.info("start init firefox capability");
                    capabilities = DesiredCapabilities.firefox();
                    capabilities.setCapability(FirefoxDriver.PROFILE, getFFProfile());
                    break;
                case Andriod:
                    capabilities = DesiredCapabilities.android();
                    break;
                case PhantomJS:
                    capabilities = DesiredCapabilities.phantomjs();
                    break;
                case IE:
                    capabilities = DesiredCapabilities.internetExplorer();
                    capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
                    break;
                case HtmlUnit:
                    capabilities = DesiredCapabilities.htmlUnitWithJs();
                    break;
                case IPhone:
                    capabilities = DesiredCapabilities.iphone();
                    break;
                default:
                    logger.info("start init chrome capability");
                    setChromeDriverProperty();
                    capabilities = DesiredCapabilities.chrome();
            }
        }

        private void createRemoteEventFireWebDriver(String url, String browserType) {
            initCapabilities(browserType);
            WebDriver remoteDriver;
            try {
                remoteDriver = new RemoteWebDriver(new URL(url), capabilities);
            } catch (MalformedURLException e) {
                throw new RuntimeException("Grid URL" + url + " is not correct");
            }
            initEventFiringWebDriver(remoteDriver);
        }

        private void createLocalEventFireWebDriver(String browserType) {
            WebDriver d = initLocalWebDriver(browserType);
            initEventFiringWebDriver(d);
        }

        private void initEventFiringWebDriver(WebDriver d) {
            driver = new EventFiringWebDriver(d);
            webDriverEventListeners.forEach(driver::register);
        }

        public void addListeners(WebDriverEventListener... listeners) {
            Collections.addAll(webDriverEventListeners, listeners);
        }

        private WebDriver initLocalWebDriver(String browserType) {
            WebDriver d;
            //todo create more drivers
            switch (BrowserType.getBrowserType(browserType)) {
                case Chrome: {
                    logger.info("start init chrome driver");
                    setChromeDriverProperty();
                    d = new ChromeDriver();
                    break;
                }
                case FireFox:
                    logger.info("start init firefox driver");
                    d = new FirefoxDriver(getFFProfile());
                    break;
                case IE:
                    d = new InternetExplorerDriver();
                    break;
                default:
                    logger.info("start init chrome driver");
                    setChromeDriverProperty();
                    d = new ChromeDriver();
            }

            return d;
        }

        private FirefoxProfile getFFProfile() {
            FirefoxProfile firefoxProfile = new FirefoxProfile();
            firefoxProfile.setPreference("app.update.enabled", false);
            return firefoxProfile;
        }

        private void init(String webDriverMode, String browserType, String url) {
            if (WebDriverMode.getWebDriverMode(webDriverMode).equals(WebDriverMode.LOCAL)) {
                createLocalEventFireWebDriver(browserType);
            } else {
                createRemoteEventFireWebDriver(url, browserType);
            }

        }

        public EventFiringWebDriver getDriver() {
            return driver;
        }

        public void setDriver(EventFiringWebDriver driver) {
            this.driver = driver;
        }

        private void setChromeDriverProperty() {

            String driverPath = PropertiesHelper.getProperty("default_chromedriver_location"
                    , "C:\\autotest\\chromedriver.exe");
            File file = new File(driverPath);
            if (file.exists()) {
                System.setProperty("webdriver.chrome.driver", driverPath);
            } else {
                if (Platform.getCurrent().name().equalsIgnoreCase("MAC")) {
                    System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");
                } else if (Platform.getCurrent().family().name().contains("WIN") || Platform.getCurrent().name().contains("WIN")) {
                    String path = ClassLoader.getSystemClassLoader().getResource("").getPath() + "chromedriver.exe";
                    System.setProperty("webdriver.chrome.driver", path);
                } else {
                    throw new RuntimeException("Your platform is not supported in configuration,please set " +
                            "default_chromedriver_location" +
                            "value in env.properties file");
                }
            }
        }

    }
}
