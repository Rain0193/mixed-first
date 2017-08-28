package io.ift.automation.data;

import io.ift.automation.AppName;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Created by patrick on 15/8/19.
 */
public class DataFixtureTest {

    @Test
    public void testUpdate() throws Exception {
        String sql = "update dbo.T_ASSETS_MANAGERS set idle=1,occupier=null\n" +
                "where  id='10043758' and status='正常'";

        DataFixture d = DataFixture.build(AppName.ZICHAN.getName(),sql,"id");
        d.backUp();
        System.out.println(d);
        d.update();
        d.rollBack();

    }

    @Test
    public void testRollBack() throws Exception {

    }
}
