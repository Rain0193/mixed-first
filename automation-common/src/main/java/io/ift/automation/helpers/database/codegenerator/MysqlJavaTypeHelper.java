package io.ift.automation.helpers.database.codegenerator;

import com.google.common.collect.Maps;

import java.util.Map;

public final class MysqlJavaTypeHelper {

    private static MysqlJavaTypeHelper ourInstance = new MysqlJavaTypeHelper();
    private static Map<String,String> typeMap = Maps.newConcurrentMap();
    static{
        typeMap.put("int","Integer");
        typeMap.put("bigint","Integer");
        typeMap.put("varchar","String");
        typeMap.put("tinyint","Boolean");
        typeMap.put("smallint","Integer");
        typeMap.put("datetime","Long");
        typeMap.put("date","Date");
        typeMap.put("timestamp","Date");
        typeMap.put("text","String");
        typeMap.put("mediumtext","String");
        typeMap.put("float","Float");
        typeMap.put("decimal","BigDecimal");
        typeMap.put("longtext","String"); //understand what's used for longtext
    }

    public static MysqlJavaTypeHelper getInstance() {
        return ourInstance;
    }


    private MysqlJavaTypeHelper() {

    }

    public static String getJavaType(String mysqlType){

        if(typeMap.get(mysqlType)==null) throw new RuntimeException(mysqlType+":no suitable type matched");
        return typeMap.get(mysqlType);
    }

}
