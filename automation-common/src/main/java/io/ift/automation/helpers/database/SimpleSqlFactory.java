package io.ift.automation.helpers.database;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.Id;
import javax.persistence.Table;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据库sql工程
 * @param <T>
 */
public class SimpleSqlFactory<T> { // simple solution ,after that use framework to handle

    private String tableName;
    private T instance;
    private List objParams = new ArrayList();
    private Map<String,Object> fieldMap = new HashMap<String,Object>();


    public SimpleSqlFactory(T instance) throws Exception {
        this.instance = instance;
        tableName = getTableName(instance);
        loadFields(instance);
    }

    public String getTableName(T instance) throws Exception {

        Table[] tableAnnotation = instance.getClass().getAnnotationsByType(Table.class);
        if(tableAnnotation.length>0){
            return tableAnnotation[0].name();
        }else{
            throw new Exception("No Table defined!");
        }
    }

    public void loadFields(T instance) throws IllegalAccessException {

       Field[] fields = instance.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            fieldMap.put(field.getName(), field.get(instance));
        }
    }

    public String createSimpleUpdateSql(String ... fieldNames) throws Exception {
        String set =" ";
        for (String fieldName : fieldNames) {
            if(null==fieldMap.get(fieldName)){
                throw new Exception("no field mapped");
            }else{
                set = fieldName+"='"+fieldMap.get(fieldName)+"' And ";
            }
        }

        String updateSql = "update " +this.tableName +" set ";
        String where = " where " + getPrimaryKeyStatement(instance);
        set= set.substring(0,set.length()-"And ".length());
        return updateSql+set+where;
    }

    public String getPrimaryKeyStatement(T instance) throws Exception {
      Field[] fields = instance.getClass().getDeclaredFields();
        for (Field field : fields) {
            Id[] id = field.getAnnotationsByType(Id.class);
            field.setAccessible(true);
            if(id.length>0) return field.getName()+"="+field.get(instance);
        }

        throw new Exception("this is not primary key in entity "+instance.getClass().getName());
    }


    public Map<String, Object> getFieldMap() {
        return fieldMap;
    }

    public void setFieldMap(Map<String, Object> fieldMap) {
        this.fieldMap = fieldMap;
    }

    public List getObjParams() {
        return objParams;
    }

    public void setObjParams(List objParams) {
        this.objParams = objParams;
    }
}
