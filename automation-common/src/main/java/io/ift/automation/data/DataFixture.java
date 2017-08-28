package io.ift.automation.data;

import io.ift.automation.AppName;
import io.ift.automation.helpers.StringHelper;
import io.ift.automation.helpers.database.SpringJdbcTemplateUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;

/**
 * Created by patrick on 15/8/19.
 */
public final class DataFixture {

    private static final Logger logger = LogManager.getLogger(DataFixture.class);
    private String tableName;
    private Map<String, String> updatedValues = Maps.newHashMap();
    private Map<String, Object> valueBeforeUpdated = Maps.newHashMap();
    private String key;
    private String originalSql;
    private String domainName;

    private DataFixture() {
    }

    private DataFixture build(){
        return new DataFixture();
    }

    /**
     * 构建DataFixture
     * @param appName
     * @param sql
     * @param uniqueKeyName
     * @return
     */
    public static  DataFixture build(String appName,
                                     String sql,
                                     String uniqueKeyName){
        DataFixture f =  new DataFixture();
        f.sql(sql);
        f.domain(appName);
        f.id(uniqueKeyName);
        return f;
    }

    public static  DataFixture build(AppName appName,
                                     String sql,
                                     String uniqueKeyName){
        return build(appName.getName(),sql,uniqueKeyName);
    }

    public static  DataFixture build(AppName appName,
                                     String sql
                                     ){
        return build(appName.getName(),sql,"id");
    }

    public static  DataFixture build(String appName,
                                     String sql
    ){
        return build(appName,sql,"id");
    }

    /**
     * 设置APP名字
     * @param appName
     * @return
     */
    public DataFixture domain(String appName) {
        this.domainName = appName;
        return this;
    }

    /**
     * 设置主键的字段名
     * @param key
     * @return
     */
    public DataFixture id(String key) {
        this.key = key;
        return this;
    }

    /**
     * 设置更新需要的SQL
     * @param sql
     * @return
     */
    public DataFixture sql(String sql) {
        this.originalSql = sql.replaceAll("\n"," ");
        return this;
    }

    /**
     * 更新数据库
     */
    public void update() {
        backUp();
        SpringJdbcTemplateUtils.useDataBase(this.domainName).update(this.originalSql);

    }

    /**
     * 备份数据,only support delete
     */
    public void backUp() {
        if(this.originalSql.toLowerCase().trim().startsWith("delete")) return;
        String[] splitWhere = this.originalSql.toUpperCase().split("WHERE");
        if (splitWhere.length !=2) throw new RuntimeException("update语句没有条件或者有嵌套查询,目前不支持");
        String whereClause = splitWhere[1];
        String[] tableAndSet = splitWhere[0].replace("UPDATE", "").split(" SET");
        this.tableName = tableAndSet[0];
        String getIdSql = String.format("select * from %s where %s", this.tableName, whereClause);
        String[] keyAndValues = tableAndSet[1].split(",");
        for (String s : keyAndValues) {
            String[] keyAndValue = s.split("=");
            updatedValues.put(keyAndValue[0].trim(), keyAndValue[1].trim());
        }
        List<String> keys = Lists.newArrayList(updatedValues.keySet());
        keys.add(this.key);

        this.valueBeforeUpdated = SpringJdbcTemplateUtils
                .useDataBase(domainName).getMultipleValue(getIdSql, keys);
    }

    /**
     * 回滚更新的数据
     */
    public void rollBack() {
        logger.info("start rollback if value has been updated");
        if(valueBeforeUpdated.isEmpty()) return;
        if(valueBeforeUpdated.get(this.key)==null){
            throw new RuntimeException("不执行rollback,因为没有primary key 定义,已经执行的sql是"+this.originalSql);
        }
        logger.info("start rollback....");
        String rollBackSql = "update %s set %s where %s ";
        StringBuilder set = new StringBuilder();
        StringBuilder where = new StringBuilder();
        for (Map.Entry<String, Object> entry : valueBeforeUpdated.entrySet()) {
            String value = "";
            if(entry.getValue()!=null && !entry.getValue().toString().equalsIgnoreCase("null")) {
                value = entry.getValue().toString();
            }
            if (entry.getKey().equalsIgnoreCase(this.key)) {
                where.append(entry.getKey()).append(
                        String.format("= '%s'", value)
                );
            } else {
                set.append(entry.getKey())
                        .append(String.format("= '%s',", value));
            }
        }
        String whereClause = StringHelper.removeEnd(where.toString(), ",");
        String setClause= StringHelper.removeEnd(set.toString(), ",");
        String runRollbackSql = String.format(rollBackSql,this.tableName,setClause,whereClause);
        SpringJdbcTemplateUtils
                .useDataBase(domainName).update(runRollbackSql);
    }

}
