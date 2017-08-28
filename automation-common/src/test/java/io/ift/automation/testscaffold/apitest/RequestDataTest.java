package io.ift.automation.testscaffold.apitest;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.beanutils.BeanUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class RequestDataTest {

    @Test
    public void reflectionRequestDataTest() throws InvocationTargetException, IllegalAccessException {
        RequestData data = new RequestData();
        Map<String,String> a = Maps.newConcurrentMap();
        //a.put("test1", "test2");
        Map<String,String> map = Maps.newConcurrentMap();
        map.put("test","test1");
        map.put("test1","test");
        BeanUtils.setProperty(data,"queryParameters", map);
        Assert.assertTrue(data.getQueryParameters().size() == 2);
    }

    @Test
    public void testComposeBody(){
        RequestData d = new RequestData();
        String template ="{\"user\":\"_user\",\"password\":\"_password\"}";
        d.setBodyTemplate(template);
        d.setBodyParameter("user","test")
                .setBodyParameter("password", "1234564").dataComposeAfter();
        Assert.assertEquals(d.getBody(), "{\"user\":\"test\",\"password\":\"1234564\"}");
    }

}
