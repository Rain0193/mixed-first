package io.ift.automation.helpers.database;

import io.ift.automation.AppName;
import io.ift.automation.helpers.*;

import io.ift.automation.helpers.CollectionsHelper;
import io.ift.automation.helpers.DateHelper;
import io.ift.automation.helpers.MapsHelper;
import io.ift.automation.helpers.StringHelper;
import io.ift.automation.testscaffold.testcontext.TestContextHolder;
import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Maps;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.persistence.Id;
import javax.persistence.Table;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.sql.Date;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import io.ift.automation.helpers.EnvironmentHelper;

/**
 * 数据库操作类，基于spring jdbc template
 */
@SuppressWarnings("unchecked")
public final class SpringJdbcTemplateUtils {

    private static final Logger logger = LogManager.getLogger(SpringJdbcTemplateUtils.class);
    private DriverManagerDataSource dataSource;
    private JdbcTemplate template;
    private static Map<String, SpringJdbcTemplateUtils> instances = Maps.newConcurrentMap();
    private String appName;


    private SpringJdbcTemplateUtils() {
    }


    private void initDataSource(String name) {
        this.dataSource = new DriverManagerDataSource();
        dataSource.setUrl(EnvironmentHelper.get(name).getDbURL());
        dataSource.setDriverClassName(EnvironmentHelper.get(name).getDbDriver());
        dataSource.setUsername(EnvironmentHelper.get(name).getDbUserName());
        dataSource.setPassword(EnvironmentHelper.get(name).getDbPassword());
    }

    private void initTemplate() {
        this.template = new JdbcTemplate();
        this.template.setDataSource(this.dataSource);
    }

    /**
     * 使用不同的数据库,名字需要和env.properties 文件中匹配
     * @param name: 数据库在配置文件中的名字
     * @return
     */
    public static SpringJdbcTemplateUtils useDataBase(String name) {
        if (instances.get(name) == null) {
           try {
               SpringJdbcTemplateUtils utils = new SpringJdbcTemplateUtils();
               utils.initDataSource(name);
               utils.initTemplate();
               utils.appName = name;
               instances.put(name, utils);
               return utils;
           }catch (Exception e){
               throw new RuntimeException("env.properties中"+name+"数据库的设置可能存在问题,请检查, 错误是:"+e);
           }
        }
        return instances.get(name);
    }

    public static SpringJdbcTemplateUtils useDataBase(AppName name){
        return useDataBase(name.getName());
    }

    public static SpringJdbcTemplateUtils DEFAULT() {
        return useDataBase(EnvironmentHelper.DEFAULT_APPNAME);
    }

    /**
     * query for return a object
     *
     * @param sql
     * @param clazz
     * @param args
     * @param <T>
     * @return
     */
    public <T> T queryForObject(String sql, Class<T> clazz, Object... args) {
        RowMapper<T> rowMapper = BeanPropertyRowMapper.newInstance(clazz);
        logger.info("sql={}", sql);
        logger.info("args={}", StringHelper.join(args, ","));
        try {
            return template.queryForObject(sql, rowMapper, args);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }

    }

    /**
     * 更新数据库
     *
     * @param sql
     * @param args
     * @return
     */
    public int update(String sql, Object... args) {
        logger.info("sql={}", sql);
        logger.info("args={}", StringHelper.join(args,","));
        return template.update(sql, args);
    }

    /**
     * 插入数据
     *
     * @param instance
     * @param <T>
     * @throws Exception
     */
    public <T> void insert(T instance) throws Exception {
        String tableName = null;
        for (Annotation annotation : instance.getClass().getAnnotations()) {
            if (annotation instanceof Table) {
                tableName = ((Table) annotation).name();
            }
        }

        if (tableName == null) {
            throw new RuntimeException("no table found for entity " + instance.toString());
        }

        String sql = "Insert Into ";
        StringBuilder values = new StringBuilder("( ");
        StringBuilder columns = new StringBuilder("(");
        for (Field field : instance.getClass().getDeclaredFields()) {

            if (field.getAnnotation(Id.class) == null) {
                columns.append(field.getName());
                values.append("'");
                field.setAccessible(true);
                if (field.getType().equals(Date.class)) {
                    values.append(DateHelper.formatISODateTime((Date) field.get(instance)));
                } else {
                    values.append(field.get(instance));
                }
                values.append("'");
                values.append(",");
                columns.append(",");
            }

        }

        String valueClause = values.substring(0, values.length() - 1);
        valueClause = valueClause + ")";
        String columnClause = columns.substring(0, columns.length() - 1);
        columnClause = columnClause + ")";

        sql = sql + tableName + columnClause + "values" + valueClause;

        logger.info("sql={}", sql);
        template.execute(sql);

    }

    /**
     * query for list of object
     *
     * @param sql
     * @param clazz
     * @param args
     * @param <T>
     * @return
     */
    public <T> List<T> queryForList(String sql, Class<T> clazz, Object... args) {
        RowMapper<T> rowMapper = BeanPropertyRowMapper.newInstance(clazz);
        logger.info("sql={}",sql);
        logger.info("args={}", StringHelper.join(args, ","));
        try {

            return template.query(sql, rowMapper, args);

        } catch (EmptyResultDataAccessException e) {

            return Collections.emptyList();
        }

    }

    /**
     * 返回Map,字段名: 字段值
     * @param sql
     * @param clazz
     * @param keyName
     * @param args
     * @param <T>
     * @return
     */
    public <T> Map<String, T> queryForMap(String sql, Class<T> clazz, String keyName, Object... args) {
        RowMapper<T> rowMapper = BeanPropertyRowMapper.newInstance(clazz);
        logger.info("sql={}",sql);
        logger.info("args={}", StringHelper.join(args, ","));
        return template.query(sql, args, resultSet -> {

            Map map = Maps.newHashMap();
            int rowNum = 0;
            while (resultSet.next()) {
                T object = rowMapper.mapRow(resultSet, rowNum++);
                map.put(resultSet.getString(keyName), object);
            }
            return map;
        });
    }

    /**
     * 获取字段值
     *
     * @param sql
     * @param key
     * @return
     */
    public String getStringValue(String sql, String key) {

        logger.info("sql={}", sql);
        try {
            return (String) template.queryForMap(sql).get(key);
        } catch (ClassCastException e) {
            return template.queryForMap(sql).get(key).toString();
        } catch (EmptyResultDataAccessException e) {
            logger.warn("{}，没有结果集返回", sql);
            return StringHelper.EMPTY;
        }
    }

    /**
     * 获取字段值
     *
     * @param sql
     * @param key
     * @param args
     * @return
     */
    public String getStringValue(String sql, String key, Object... args) {
        logger.info("sql={}", sql);
        logger.info("args={}", StringHelper.join(args,","));
        try {
            return (String) template.queryForMap(sql, args).get(key);
        } catch (ClassCastException e) {
            return template.queryForMap(sql, args).get(key).toString();
        } catch (EmptyResultDataAccessException e) {
            logger.warn("{}，没有结果集返回", sql);
            return StringHelper.EMPTY;
        }

    }


    /**
     * 获取字段值
     *
     * @param sql
     * @param key
     * @return
     */
    public Object getValue(String sql, String key) {
        logger.info("sql={}", sql);
        return template.queryForMap(sql).get(key);
    }

    /**
     * 返回指定字段的Map,字段名: 字段值
     *
     * @param sql  : sql,需要完整的SQL
     * @param keys : 多个字段名
     * @return
     */
    public Map<String, Object> getMultipleValue(String sql, String... keys) {
        logger.info("sql={}", sql);
        Map<String, Object> rawResult = template.queryForMap(sql);
        return MapsHelper.getMultiple(rawResult, keys);
    }

    /**
     * 返回指定字段的Map,字段名: 字段值
     * @param sql: 查询SQL
     * @param keys: 字段名
     * @return
     */
    public Map<String, Object> getMultipleValue(String sql, List<String> keys) {
        logger.info("sql={}", sql);
        Map<String, Object> rawResult = template.queryForMap(sql);
        return MapsHelper.getMultiple(rawResult, keys);
    }

    /**
     * 返回指定字段的Map,字段名: 字段值
     * @param sql: 查询SQL
     * @param keys: 字段名
     * @param function: 转换方法
     * @return
     */
    public Map<String, Object> getMultipleValue(String sql, List<String> keys, Function function) {
        logger.info("sql={}", sql);
        Map<String, Object> rawResult = template.queryForMap(sql);
        return MapsHelper.getMultiple(rawResult, keys, function);
    }

    /**
     * 获取查询返回的某个字段值
     *
     * @param sql  查询sql
     * @param key  数据库查询返回的字段名
     * @param args sql 变量
     * @return
     */
    public Object getValue(String sql, String key, Object... args) {
        logger.info("sql={}", sql);
        logger.info("args={}", StringHelper.join(args,","));
        return template.queryForMap(sql, args).get(key);
    }

    /**
     * 获取数据库原始数据
     *
     * @param sql
     * @param args
     * @return
     */
    public List<Map<String, Object>> getAllRawResult(String sql, Object... args) {
        logger.info("sql={}", sql);
        logger.info("args={}", StringHelper.join(args,","));
        return template.queryForList(sql, args);
    }

//    /**
//     * 获取数据库原始数据
//     *
//     * @param sql
//     * @param args
//     * @return
//     */
//    public List<Map<String, Object>> getAllResult(String sql, Object... args) {
//        logger.info("sql={}", sql);
//        logger.info("args={}", StringHelper.join(args,","));
//        return template.queryForList(sql,args);
//    }
    /**
     * 根据筛选条件获取查询结果
     *
     * @param sql
     * @param criteria
     * @param args
     * @return
     */
    public List<Map<String, Object>> getResultByCriteria(String sql
            , Predicate<Map<String, Object>> criteria, Object... args) {
        logger.info("sql={}", sql);
        logger.info("args={}", StringHelper.join(args,","));
        List<Map<String, Object>> originalResult = template.queryForList(sql, args);
        return CollectionsHelper.filterForList(originalResult, criteria);
    }

    /**
     * 根据筛选条件获取结果
     *
     * @param sql
     * @param criteria
     * @param args
     * @return
     */
    public Map<String, Object> getSingleResultByCriteria(String sql
            , Predicate<Map<String, Object>> criteria, Object... args) {
        logger.info("sql={}", sql);
        List<Map<String, Object>> originalResult = template.queryForList(sql, args);
        return CollectionsHelper.filter(originalResult, criteria);
    }

    /**
     * 获取Top N的结果
     * @param sql
     * @param topN
     * @param keys
     * @return
     */
    public List<Map<String, Object>> getTopNResult(String sql, int topN,String... keys) {
        logger.info("sql={}", sql);
        String topValue = StringHelper.extract(sql, "top (\\d)");
        if(StringHelper.isNotEmptyOrNotBlankString(topValue)){
            sql =sql.replaceFirst("top (\\d)","top "+topN+" ");
        }else{
            sql=sql.replaceFirst("select ","select top "+topN+" ");
        }

        return getAllRawResult(sql);
    }

    /**
     * 获取根据SQL取得的返回值,同时这个值在其他地方没有被用到过的,得到的返回值在调用这个方法后,再调用这个方法就取不到了,这个方法
     * 主要为了给DataProvider使用,有些值使用一遍就不能再使用了
     * @param sql 获取值的sql
     * @param filterKey 这个filterKey可以是alias.key 这种格式, 或者直接key这个方式,alias的值需要是你在sql中key所在表的alias名字
     *                  同时key必须要在sql的返回值中存在
     * @param returnedKey, sql返回值中的key,returnedKey有可能和filterKey不一样,所以就再留了一个参数
     *
     * @return 获取根据SQL取得的返回值,同时这个值在其他地方没有被用到过的
     *
     * <p>example:</p>
     * <P>
     *    sql:
     *     select top 1 propertyNo as houseSourceNo,e.estateName , e.Address as estateAddress,e.EstateNo,p.RoomNo,p.PropertyID
    " +
    "        from agencyjizhong..property p with(nolock)\n" +
    "        left join agencyjizhong..Estate e with(nolock) on p.estateID = e.EstateID\n" +
    "        where p.FlagDeleted = 0 and p.saleStatus ='出售'\n" +
    "        and EstateName is not null and e.Address is not null and EstateNo is not null and p.RoomNo is not null\n" +
    "        and not exists(\n" +
    "            select 1 from T_FMS_CASE c with(nolock) where p.propertyNo = c.HOURSE_SOURCE_NO\n" +
    "        )\n" +
    "        and IsHidden=0\n" +
    "        and p.PropertyUsage!='商铺'\n" +
    "        and len(AreaID)>5
        p.propertyNo 是filterKey, houseSourceNo 是returnKey, propertyNo会被加入到使用中的数据

     * </P>
     */
    public Map<String,Object> getFilteredMultipleValue(String sql, String filterKey,String returnedKey){
        String keyName=CollectionsHelper.lastElement(filterKey.split("\\."));
        for (String s : TestContextHolder.getUsedValues(keyName)) {
            String appendSql = String.format(" and %s != '%s' %n",filterKey,s);
            sql = sql+ appendSql;
        }
        logger.info("sql={}",sql);
        Map<String,Object> result = getMultipleValue(sql);
        Object value = result.get(returnedKey);
        if(value!=null){
            TestContextHolder.addUsedValue(keyName,value.toString());
        }else{
            throw new RuntimeException("使用的filterKey不正确");
        }
        return result;
    }

    /**
     * 获取给定条件的返回值,所有conditions 中某个value是null,则忽略这个条件
     * 名字起的不好,Deprecated
     * @param sql
     * @param conditions
     * @return
     */
    @Deprecated
    public Map<String,Object> getResultByConditions(String sql,Map<String,String> conditions){

        StringBuilder sb = new StringBuilder();

        conditions.entrySet().stream().filter(entry -> entry.getValue() != null).forEach(entry -> {
            sb.append(entry.getKey());
            sb.append("=");
            sb.append(String.format("'%s'", entry.getValue()));
            sb.append("\n");
        });

        return getMultipleValue(sql+" "+sb.toString());
    }

    /**
     * 获取JDBC template
     * @return
     */
    public JdbcTemplate getJdbcTemplate() {
        return template;
    }

}
