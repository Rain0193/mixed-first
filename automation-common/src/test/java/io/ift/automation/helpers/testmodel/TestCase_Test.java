package io.ift.automation.helpers.testmodel;

import io.ift.automation.data.TestData;
import io.ift.automation.testscaffold.webtest.webUI.htmlelements.Button;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

/**
 * Created by patrick on 15/3/11.
 *
 * @version $Id$
 */


public class TestCase_Test extends TestData {

    private String testId;
    private String description;
    private String error;
    private Button test;
    private List<String> nodes = Lists.newArrayList();
    private Map<String,String> request = Maps.newHashMap();
    private List<TestCase_Test> lists = Lists.newArrayList();

    public String getTestId() {
        return testId;
    }

    public void setTestId(String testId) {
        this.testId = testId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Button getTest() {
        return test;
    }

    public void setTest(Button test) {
        this.test = test;
    }

    public List<TestCase_Test> getLists() {
        return lists;
    }

    public void setLists(List<TestCase_Test> lists) {
        this.lists = lists;
    }

    public List<String> getNodes() {
        return nodes;
    }

    public void setNodes(List<String> nodes) {
        this.nodes = nodes;
    }

    public Map<String, String> getRequest() {
        return request;
    }

    public void setRequest(Map<String, String> request) {
        this.request = request;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TestCase_Test that = (TestCase_Test) o;

        if (testId != null ? !testId.equals(that.testId) : that.testId != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (error != null ? !error.equals(that.error) : that.error != null) return false;
        if (test != null ? !test.equals(that.test) : that.test != null) return false;
        if (nodes != null ? !nodes.equals(that.nodes) : that.nodes != null) return false;
        if (request != null ? !request.equals(that.request) : that.request != null) return false;
        return !(lists != null ? !lists.equals(that.lists) : that.lists != null);

    }

    @Override
    public int hashCode() {
        int result = testId != null ? testId.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (error != null ? error.hashCode() : 0);
        result = 31 * result + (test != null ? test.hashCode() : 0);
        result = 31 * result + (nodes != null ? nodes.hashCode() : 0);
        result = 31 * result + (request != null ? request.hashCode() : 0);
        result = 31 * result + (lists != null ? lists.hashCode() : 0);
        return result;
    }
}
