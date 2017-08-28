package io.ift.automation.testscaffold.apitest;

import io.ift.automation.testscaffold.Where;

/**
 * Created by patrick on 15/6/4.
 *
 * @version $Id: API200CheckPoint.java 1771 2015-08-21 06:02:12Z wuke $
 */


public class API200CheckPoint extends BaseAPICheckPoint {

    @Override
    public void verify() {
        sa.assertEquals(response.getStatusCode().toString(),"200","检查API是否访问成功");
    }

    @Override
    public Where where() {
        return new Where("*");
    }

}
