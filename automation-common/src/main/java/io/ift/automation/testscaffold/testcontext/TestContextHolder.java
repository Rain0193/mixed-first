package io.ift.automation.testscaffold.testcontext;

import io.ift.automation.data.DataFixture;
import io.ift.automation.drivers.DriverFactory;
import io.ift.automation.helpers.MapsHelper;
import io.ift.automation.testscaffold.CheckPoint;
import io.ift.automation.testscaffold.webtest.WebTestContext;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;
import java.util.Map;


/**
 * Created by patrick on 15/8/20.
 * Todo too many static methods,not a good design
 *
 * @author patrick
 * @version $Id: $Id
 */
public class TestContextHolder {

    private static final Logger logger = LogManager.getLogger(TestContextHolder.class.getName());
    private static ThreadLocal<WebTestContext> testContexts = DriverFactory.getTestContexts();
//    private static ThreadLocal<WebTestContext> testContexts = new ThreadLocal<>();
    //global usedValue
    private static Map<String,List<String>> usedValue = Maps.newConcurrentMap();

    private TestContextHolder() {
    }

    public static void addUsedValue(String key,String value){
        MapsHelper.put(usedValue,key,value);
    }

    /**
     * add keys
     * @param key
     * @param otherNames
     * @return
     */
    public static List<String> getUsedValues(String key,String ...otherNames){
        List<String> result = Lists.newArrayList();
        MapsHelper.getMultiple(usedValue, key).values().stream().filter(item-> item != null).forEach(result::addAll);
        if(otherNames.length>0){
            MapsHelper.getMultiple(usedValue, otherNames).values().stream().filter(item->item!=null).forEach(result::addAll);
        }
        return result;
    }

    /**
     * add used values
     * @param key
     * @return
     */
    public static @Nonnull List<String> getUsedValue(String key){
        List<String> result = usedValue.get(key);
        return result==null? Collections.EMPTY_LIST:result;
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
     * @return a {@link org.openqa.selenium.support.events.EventFiringWebDriver} object.
     */
    public static synchronized EventFiringWebDriver get() {
        logger.debug("start create event fire web driver");
        return DriverFactory.get();
    }

    /**
     * 获取ThreadLocal中的上下文，quick and dirty实现
     * todo move out of DriverFactory
     *
     * @return a {@link WebTestContext} object.
     */
    public static synchronized WebTestContext getThreadLevelTestContext() {
        logger.debug("get current test current thread test context");
        return DriverFactory.getThreadLevelTestContext();
    }

    /**
     * 全局范围添加检查点,检查点会在流程中根据CheckPoint#where()设定进行检查
     *
     * @param checkPoints a {@link CheckPoint} object.
     */
    public static void addCheckPoint(CheckPoint... checkPoints){
        for (CheckPoint checkPoint : checkPoints) {
            testContexts.get().addCheckPoint(checkPoint);
        }
    }

    /**
     * 添加DataFixture,同时执行更新操作,更新预先需要的数据,同时保留更新前的数据以便回滚使用
     *
     * @param DataFixtures a {@link DataFixture} object.
     */
    public static void addDataFixture(DataFixture... DataFixtures){
        for (DataFixture dataFixture: DataFixtures) {
            testContexts.get().addDataFixture(dataFixture);
            dataFixture.update();
        }
    }

    /**
     * 回滚数据操作
     */
    public static void rollBackData(){
        testContexts.get().getDataFixtures().forEach(DataFixture::rollBack);
    }


}
