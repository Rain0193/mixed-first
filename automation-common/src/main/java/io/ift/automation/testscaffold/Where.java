package io.ift.automation.testscaffold;

import io.ift.automation.helpers.StringHelper;
import io.ift.automation.logging.TestResultLogger;
import io.ift.automation.testscaffold.webtest.IWhere;

/**
 * Created by patrick on 15/6/4.
 *  Where Class is for indicator where the checkpoint is processed
 * @version $Id: Where.java 2691 2016-03-14 01:09:26Z guoxh $
 */


public class Where implements IWhere{

    private String[] locationName;

    public Where(String ... locationName) {
        this.locationName=locationName;
    }

    @Override
    public boolean eval(String locationName){
        TestResultLogger
            .log("start eval checkpoint location name for expression={},real_action_name={}", this.locationName, locationName);
        for (String location : this.locationName) {
            if (location.equalsIgnoreCase("*") &&
                StringHelper.isNotEmptyOrNotBlankString(locationName)) return true;
            if(location.equalsIgnoreCase(locationName)) return true;
        }
        return false;
    }
}
