package io.ift.automation.commonflows.activities.PrivilegeUrlMapping;

import com.google.common.collect.Maps;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Map;

import static org.testng.Assert.*;

/**
 * Created by patrick on 15/8/31.
 */
public class PrivilegesTest {
    Privileges privileges1 ;
    @Test(invocationCount = 2,threadPoolSize = 4)
    public void testGetInstance() throws Exception {
         privileges1 = Privileges.getInstance();
    }

    @Test
    public void testRegister() throws Exception {
        Privileges privileges = Privileges.getInstance();
        Map<String,String> url= Maps.newHashMap();
        url.put("test34","test009");
        privileges.register("test345", url);
        Assert.assertEquals(privileges.getPrivilegesByActivityName("test345"), url);

    }

    @Test
    public void testGetPrivilegesByActivityName() throws Exception {
        Privileges privileges = Privileges.getInstance();
        Assert.assertEquals(privileges.getPrivilegesByActivityName("test").size(),3);
    }
}