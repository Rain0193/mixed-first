package io.ift.automation.data;

import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Created by patrick on 15/7/6.
 *
 * @version $Id: BeanMapperTest.java 1514 2015-07-08 00:41:59Z wuke $
 */
public class BeanMapperTest {


    @Test
    public void testCopy() throws Exception {
        User user = new User("test123","Test3345");
        User target = new User();
        DataComposer.from(user).to(target);
        Assert.assertEquals(target.getUserCode(),"test123");

    }
}
