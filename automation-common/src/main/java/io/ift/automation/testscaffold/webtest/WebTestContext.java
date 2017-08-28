package io.ift.automation.testscaffold.webtest;

import io.ift.automation.assertion.SoftAssertion;
import io.ift.automation.data.DataFixture;
import io.ift.automation.data.TestData;
import io.ift.automation.helpers.StringHelper;
import io.ift.automation.logging.TestResultLogger;
import io.ift.automation.testscaffold.CheckPoint;
import com.google.common.collect.Lists;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import java.util.List;

/**
 * Created by patrick on 15/5/27.
 * it is not really used for time being,later will refactor the code
 * @version $Id$
 * @author patrick
 */


public class WebTestContext {

    private EventFiringWebDriver driver;
    // flag for if execute the test actions
    private boolean notQuit =true;
    private List<CheckPoint> beforeCheckPoints = Lists.newArrayList();
    private List<CheckPoint> afterCheckPoints = Lists.newArrayList();
    private List<DataFixture> dataFixtures = Lists.newArrayList();
    private List<CheckPoint> definedCheckPoints = Lists.newArrayList();
    private String alertMessage ;
    private List<TestData> testData = Lists.newArrayList();

    /**
     * get WebTestResult
     * @return
     */
    public SoftAssertion getWebTestResult(){
        SoftAssertion sa = new SoftAssertion();
        for (CheckPoint checkPoint : beforeCheckPoints) {
            sa.addTestResult(checkPoint.getTestResult());
        }
        for (CheckPoint checkPoint : definedCheckPoints) {
            sa.addTestResult(checkPoint.getTestResult());
        }
        return sa;
    }
    /**
     * <p>Constructor for WebTestContext.</p>
     *
     * @param driver a {@link org.openqa.selenium.support.events.EventFiringWebDriver} object.
     */
    public WebTestContext(EventFiringWebDriver driver) {
        this.driver = driver;
    }

    /**
     * <p>Getter for the field <code>driver</code>.</p>
     *
     * @return a {@link org.openqa.selenium.support.events.EventFiringWebDriver} object.
     */
    public EventFiringWebDriver getDriver() {

        return driver;
    }

    /**
     * <p>isNotQuit.</p>
     *
     * @return a boolean.
     */
    public boolean isNotQuit() {
        return notQuit;
    }

    /**
     * <p>Setter for the field <code>notQuit</code>.</p>
     *
     * @param notQuit a boolean.
     */
    public void setNotQuit(boolean notQuit) {
        this.notQuit = notQuit;
    }


    /**
     * <p>addCheckPoint.</p>
     *
     * @param checkPoint a {@link CheckPoint} object.
     * @return a {@link WebTestContext} object.
     */
    public WebTestContext addCheckPoint(CheckPoint checkPoint){
        if(checkPoint.isBeforeCheckPoint()){
            beforeCheckPoints.add(checkPoint);
        }else{
            afterCheckPoints.add(checkPoint);
        }
        return this;
    }

    public WebTestContext addDefinedCheckPoint(CheckPoint checkPoint){
        definedCheckPoints.add(checkPoint);
        return this;
    }
    /**
     * <p>addDataFixture.</p>
     *
     * @param dataFixture a {@link DataFixture} object.
     * @return a {@link WebTestContext} object.
     */
    public WebTestContext addDataFixture(DataFixture dataFixture){
        dataFixtures.add(dataFixture);
        return this;
    }

    /**
     * <p>Getter for the field <code>dataFixtures</code>.</p>
     *
     * @return a {@link java.util.List} object.
     */
    public List<DataFixture> getDataFixtures() {
        return dataFixtures;
    }

    /**
     * <p>Setter for the field <code>dataFixtures</code>.</p>
     *
     * @param dataFixtures a {@link java.util.List} object.
     */
    public void setDataFixtures(List<DataFixture> dataFixtures) {
        this.dataFixtures = dataFixtures;
    }

    /**
     * <p>Setter for the field <code>driver</code>.</p>
     *
     * @param driver a {@link org.openqa.selenium.support.events.EventFiringWebDriver} object.
     */
    public void setDriver(EventFiringWebDriver driver) {
        this.driver = driver;
    }


    public List<CheckPoint> getBeforeCheckPoints() {
        return beforeCheckPoints;
    }

    public List<CheckPoint> getAfterCheckPoints() {
        return afterCheckPoints;
    }

    public List<CheckPoint> getDefinedCheckPoints() {
        return definedCheckPoints;
    }

    public String getAlertMessage() {
        return alertMessage;
    }

    public void setAlertMessage(String alertMessage) {
        this.alertMessage = alertMessage;
    }

    public void addTestData(TestData data){
        this.testData.add(data);
    }

    public String getTestData(String key){
        for (TestData data : testData) {
            String value = data.get(key);
            if(value!=null) return value;
        }
        TestResultLogger.warn("在当前线程中没有找到对应的测试数据");
        return StringHelper.EMPTY;
    }
}
