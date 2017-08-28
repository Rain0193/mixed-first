package io.ift.automation.helpers.database.codegenerator;

import com.google.common.collect.Maps;

import java.util.Map;

public final class SqlServerJavaTypeHelper {

    private static SqlServerJavaTypeHelper ourInstance = new SqlServerJavaTypeHelper();
    private static Map<String,String> typeMap = Maps.newConcurrentMap();
    static{
        typeMap.put("bit","Integer");
        typeMap.put("bigint","Integer");
        typeMap.put("smallint","Integer");
        typeMap.put("money","BigDecimal");
        typeMap.put("numberic","String");
        typeMap.put("varchar","String");
        typeMap.put("nchar","String");
        typeMap.put("text","String");
        typeMap.put("varchar","String");
        typeMap.put("date","Date");
        typeMap.put("datetime","Date");
        typeMap.put("timestamp","Date");
        typeMap.put("mediumtext","String");
        typeMap.put("float","Float");
        typeMap.put("decimal","BigDecimal");
        typeMap.put("longtext","String"); //understand what's used for longtext
    }

    public static SqlServerJavaTypeHelper getInstance() {
        return ourInstance;
    }


    private SqlServerJavaTypeHelper() {

    }

    public static String getJavaType(String mysqlType){

        return typeMap.get(mysqlType)==null?"String": typeMap.get(mysqlType);
    }

}
