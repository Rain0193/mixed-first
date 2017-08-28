package io.ift.automation.helpers.database;

import io.ift.automation.AppName;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

import static org.testng.Assert.*;

public class SpringJdbcTemplateUtilsTest {
    private static final Logger logger = LogManager.getLogger(SpringJdbcTemplateUtilsTest.class.getName());
    @Test
    public void testQueryForObject() throws Exception {
        String sql = "select * from T_BMS_ORDER where id = 100";
        BmsOrder order = SpringJdbcTemplateUtils.DEFAULT().queryForObject(sql,BmsOrder.class);
        logger.info(order);
        assertNotNull(order);
        assertEquals(order.getId(),Integer.valueOf(100));

    }

    @Test
    public void testQueryForSelfDefinedObject() throws Exception {
        String sql = "select * from T_BMS_ORDER where id = 100";
        SelfDefinedModel order = SpringJdbcTemplateUtils.DEFAULT().queryForObject(sql,SelfDefinedModel.class);
        logger.info(order);
        assertNotNull(order);
        assertEquals(order.getId(),Integer.valueOf(100));
    }

    @Test
    public void testQueryForList() throws Exception {
        String sql = "select top(1) column_name,is_nullable from INFORMATION_SCHEMA.COLUMNS where table_name='T_BMS_ORDER'";
        List<Map<String,Object>> result = SpringJdbcTemplateUtils.DEFAULT().getJdbcTemplate().queryForList(sql);
        List<Map<String,String>> mapResult = Lists.newArrayList();
        Map<String,String> map = Maps.newHashMap();
        map.put("column_name","id");
        map.put("is_nullable","no");
        mapResult.add(map);

        Map<String,Object> result1 = SpringJdbcTemplateUtils.DEFAULT().getJdbcTemplate().queryForMap(sql);
        logger.info(result);
        logger.info(result1);
    }

    @Test
    public void testGetStringValue() throws Exception {
        String sql = "select top(1) * from INFORMATION_SCHEMA.COLUMNS where table_name='T_BMS_ORDER'";
        Assert.assertEquals(SpringJdbcTemplateUtils.DEFAULT().getStringValue(sql, "COLUMN_NAME"), "ID");
    }


    @Test
    public void testGetStringValueByCriteria() throws Exception {
        String argument="测试";
        String sql = "select top(1) * from INFORMATION_SCHEMA.COLUMNS where table_name=?";
        System.out.println(SpringJdbcTemplateUtils.DEFAULT().getAllRawResult(sql, argument));
        Assert.assertEquals(SpringJdbcTemplateUtils.DEFAULT().getAllRawResult(sql, argument).size(),1);

    }


    @Test
    public void testGetAllRawResult() throws Exception {
        String argument="T_BMS_ORDER";
        String sql = "select * from INFORMATION_SCHEMA.COLUMNS where table_name=?";
        System.out.println(SpringJdbcTemplateUtils.DEFAULT().getAllRawResult(sql,argument));
        System.out.println(SpringJdbcTemplateUtils.DEFAULT().getAllRawResult(sql, argument).size());
    }


    @Test
    public void testGetTopNResult() throws Exception {
        String sql ="select * from INFORMATION_SCHEMA.COLUMNS ";
        Assert.assertEquals(SpringJdbcTemplateUtils.useDataBase(AppName.FEIYONG).getTopNResult(sql, 5).size(), 5);
    }

    @Test
    public void testGetTopNResult_withexistTop() throws Exception {
        String sql ="select top 1 * from INFORMATION_SCHEMA.COLUMNS ";
        Assert.assertEquals(SpringJdbcTemplateUtils.useDataBase(AppName.FEIYONG).getTopNResult(sql, 5).size(), 5);
    }

    @Test
    public void testJobExecution(){
        String sql = "exec dbo.SP_SyncAllowance";
        SpringJdbcTemplateUtils.useDataBase("salary").getJdbcTemplate().execute(sql);
        System.out.println("end Of testing!");
    }
}
