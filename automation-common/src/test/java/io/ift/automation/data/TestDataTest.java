package io.ift.automation.data;

import com.alibaba.fastjson.JSON;
import org.testng.Assert;
import org.testng.annotations.Test;


public class TestDataTest {

    @Test
    public void testGetAndSet() throws Exception {
        User user = new User("abcsd","testname");
        user.set("userCode","112345");
        Assert.assertEquals(user.getUserCode(),"112345");
        Assert.assertEquals(user.get("passWord"),"testname");
        Assert.assertEquals(user.get("userCode"),"112345");
    }

    @Test
    public void test_toCsv(){
        User user = new User("abcsd","testname");
        String[] csv= user.toCSV();
        String[] expected = new String[]{"User.passWord,User.test,User.userCode","testname,null,abcsd"};
        Assert.assertEquals(csv,expected);
    }

    @Test
    public void test_init(){
        User user = new User();
        Assert.assertEquals(user.getTest(),"abdtest");
        System.out.println(JSON.toJSON(user));
    }

    @Test
    public void test_toJsonString(){
        User user = new User("abcsd","testname");
        user.toXLS("user", "user.xls");
    }


    @Test
    public void test_produceTestData(){
        User user = new User("abcsd","testname");
        user.toXLS("user","user.xls");
    }

}
