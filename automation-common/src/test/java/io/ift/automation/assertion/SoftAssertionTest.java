package io.ift.automation.assertion;

import io.ift.automation.AppName;
import io.ift.automation.helpers.database.SpringJdbcTemplateUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;


/**
 * Created by patrick on 15/6/4.
 *
 * @version $Id: SoftAssertionTest.java 2440 2016-01-27 02:41:49Z wuke $
 */
public class SoftAssertionTest {

    @Test(expectedExceptions = AssertionError.class)
    public void testGetFinalResult() throws Exception {
        SoftAssertion sa= new SoftAssertion();
        sa.assertEquals("1","2","check assert equal");
        sa.getFinalResult();
    }

    @Test
    public void testSimpleAssertApiResponse() throws Exception {
        String t= "{\"contactList\":[{\"contactName\":\"杨\",\"phoneList\":[{\"phoneNumberDecrypt\":\"1381****232\",\"phoneNumber\":\"sJI0eh3MUcfOl5MxomTRCA==\",\"province\":\"上海\",\"city\":\"上海\",\"telExtNumber\":\"77697531\",\"connectedTotal\":0,\"callTotal\":0,\"status\":0}],\"residence\":\"中国\",\"relationShip\":\"业主\",\"status\":\"0\"}],\"code\":0,\"telType\":\"业主\",\"message\":\"可以联系\",\"status\":\"ok\"}";
        SoftAssertion sa= new SoftAssertion();
        sa.simpleAssertApiResponse(new ResponseEntity(t, HttpStatus.OK), "200", t);
        sa.getFinalResult();
    }

    @Test
    public void testAssertDBValues() throws Exception {
        SoftAssertion.build().assertDBValues(AppName.FEIYONG, "select 1 as c",
                                             ExpectedResultSpec.build().addExpectation("c","1").end()).getFinalResult();
    }

    @Test
    public void testAssertApiResponse() throws Exception {
        String t= "{\"contactList\":[{\"contactName\":\"杨\",\"phoneList\":[{\"phoneNumberDecrypt\":\"1381****232\",\"phoneNumber\":\"sJI0eh3MUcfOl5MxomTRCA==\",\"province\":\"上海\",\"city\":\"上海\",\"telExtNumber\":\"77697531\",\"connectedTotal\":0,\"callTotal\":0,\"status\":0}],\"residence\":\"中国\",\"relationShip\":\"业主\",\"status\":\"0\"}],\"code\":0,\"telType\":\"业主\",\"message\":\"可以联系\",\"status\":\"ok\"}";
        SoftAssertion sa= new SoftAssertion();
        sa.assertApiResponse(new ResponseEntity(t,HttpStatus.OK),"200",t,"phoneNumber");
        sa.getFinalResult();
    }

    @Test
    public void testAssertApiResponse_NULLIST() throws Exception {
        String t= "[]";
        SoftAssertion sa= new SoftAssertion();
        sa.assertApiResponse(new ResponseEntity(t,HttpStatus.OK),"200",t);
        sa.getFinalResult();
    }

    @Test
    public void testAssertJsonObject() throws Exception {
        String t1= "{\"contactList\":[{\"contactName\":\"杨\",\"phoneList\":[{\"phoneNumberDecrypt\":\"1381****232\",\"phoneNumber\":\"sJI0eh3MUcfOl5MxomTRCA==\",\"province\":\"上海\",\"city\":\"上海\",\"telExtNumber\":\"77697531\",\"connectedTotal\":0,\"callTotal\":0,\"status\":0}],\"residence\":\"中国\",\"relationShip\":\"业主\",\"status\":\"0\"}],\"code\":0,\"telType\":\"业主\",\"message\":\"可以联系\",\"status\":\"ok\"}";
        String t2= "{\"contactList\":[{\"contactName\":\"杨\",\"phoneList\":[{\"phoneNumberDecrypt\":\"1381****232\",\"phoneNumber\":\"sJI0eh3MUcfOl5MxomTRCA==\",\"province\":\"上海\",\"city\":\"上海\",\"telExtNumber\":\"77697531\",\"connectedTotal\":0,\"callTotal\":0,\"status\":0}],\"residence\":\"中国\",\"relationShip\":\"业主\",\"status\":\"0\"}],\"code\":0,\"telType\":\"业主\",\"message\":\"可以联系\",\"status\":\"ok\"}";
        SoftAssertion.build().assertJsonObject(t1,t2,"compare json").getFinalResult();

    }

    @Test
    public void testAssertJsonObject_withIgnoredField() throws Exception {
        String t1= "{\"contactList\":[{\"contactName\":\"2\",\"phoneList\":[{\"phoneNumberDecrypt\":\"1381****232\",\"phoneNumber\":\"sJI0eh3MUcfOl5MxomTRCA==\",\"province\":\"上海\",\"city\":\"上海\",\"telExtNumber\":\"77697531\",\"connectedTotal\":0,\"callTotal\":0,\"status\":0}],\"residence\":\"中国\",\"relationShip\":\"业主\",\"status\":\"0\"}],\"code\":0,\"telType\":\"业主\",\"message\":\"可以联系\",\"status\":\"ok\"}";
        String t2= "{\"contactList\":[{\"contactName\":\"杨\",\"phoneList\":[{\"phoneNumberDecrypt\":\"1381****232\",\"phoneNumber\":\"sJI0eh3MUcfOl5MxomTRCA==\",\"province\":\"上海\",\"city\":\"上海\",\"telExtNumber\":\"77697531\",\"connectedTotal\":0,\"callTotal\":0,\"status\":0}],\"residence\":\"中国\",\"relationShip\":\"业主\",\"status\":\"0\"}],\"code\":0,\"telType\":\"业主\",\"message\":\"可以联系\",\"status\":\"ok\"}";
        SoftAssertion.build().assertJsonObject(t1,t2,"compare json","contactName").getFinalResult();

    }

    @Test
    public void testAssertJsonObject_arrayList() throws Exception {
        String t1= "[{\"contactName\":\"2\",\"phoneList\":[{\"phoneNumberDecrypt\":\"1381****232\",\"phoneNumber\":\"sJI0eh3MUcfOl5MxomTRCA==\",\"province\":\"上海\",\"city\":\"上海\",\"telExtNumber\":\"77697531\",\"connectedTotal\":0,\"callTotal\":0,\"status\":0}],\"residence\":\"中国\",\"relationShip\":\"业主\",\"status\":\"0\"}]";
        String t2= "[{\"contactName\":\"3\",\"phoneList\":[{\"phoneNumberDecrypt\":\"1381****232\",\"phoneNumber\":\"sJI0eh3MUcfOl5MxomTRCA==\",\"province\":\"上海\",\"city\":\"上海\",\"telExtNumber\":\"77697531\",\"connectedTotal\":0,\"callTotal\":0,\"status\":0}],\"residence\":\"中国\",\"relationShip\":\"业主\",\"status\":\"0\"}]";
        SoftAssertion.build().assertJsonObject(t1,t2,"compare json","contactName").getFinalResult();

    }
    @Test
    public void test_db_assert_assert_List_map() throws Exception {
        String sql = "select top(1) column_name,is_nullable from INFORMATION_SCHEMA.COLUMNS where table_name='T_BMS_ORDER'";
        List<Map<String,Object>> result = SpringJdbcTemplateUtils
            .DEFAULT().getJdbcTemplate().queryForList(sql);
        List<Map<String,String>> mapResult = Lists.newArrayList();
        Map<String,String> map = Maps.newHashMap();
        map.put("column_name","ID");
        map.put("is_nullable","NO");
        mapResult.add(map);

        new SoftAssertion().assertDBValues(AppName.JIAOYI.getName(), sql, mapResult).getFinalResult();
    }

    @Test
    public void test_array(){
        List<String> a = Lists.newArrayList("a","b");
        List<String> b = Lists.newArrayList("b", "a");

        new SoftAssertion().assertThat(a,containsInAnyOrder(b),"test");
    }
}
