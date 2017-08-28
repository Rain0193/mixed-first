package io.ift.automation.testscaffold.codegenerator.openapiparser;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.ift.automation.helpers.JSONHelper;
import io.ift.automation.helpers.StringHelper;

import java.util.Map;

/**
 * Created by patrick on 15/5/29.
 *
 * @version $Id$
 */


public class JsonParser {
    private static final String PRIVATE_STRING ="private String " ;

    private JsonParser() {
    }

    private static void parseJson(JSONObject jsonObject) {
        for (Map.Entry entry : jsonObject.entrySet()) {
            JSONObject o;
            if (entry.getValue() != null) {
                o = getJsonObject(entry.getValue().toString());
                if (null != o) {
                    System.out.println("subclass: private String " + entry.getKey()+";");
                    //create new class
                    parseJson(o);
                    System.out.println("end of " + entry.getKey());
                }

                JSONArray a = getJsonArray(entry.getValue().toString());

                if (null != a) {
                    //create new class
                    try{
                        System.out.println("subclass private " + StringHelper
                            .capitalize(entry.getKey().toString()) + " " + entry.getKey());
                        JSONObject sample = a.getJSONObject(0);
                        parseJson(sample);
                    }catch(ClassCastException e){
                        System.out.println(PRIVATE_STRING + entry.getKey()+";");
                        System.out.println("end key:" + entry.getKey());
                    }


                }
                System.out.println(PRIVATE_STRING+entry.getKey()+";");
            } else {
                System.out.println(PRIVATE_STRING+entry.getKey()+";");
            }
        }

    }

    private static JSONArray getJsonArray(String s){
        try {
            return JSONHelper.parseArray(s);
        } catch (Exception e) {
            return null;
        }
    }

    private static JSONObject getJsonObject(String s) {
        try {
            return JSONHelper.parseObject(s);
        } catch (Exception e) {
            return null;
        }
    }

    public static void output(String jsonString){
        output(JSONHelper.parseObject(jsonString));
    }

    public static void output(JSONObject o){


        for (Map.Entry<String, Object> entry : o.entrySet()) {
            JSONArray a =getJsonArray(entry.getValue().toString());
            if(null!=a){
                System.out.println("subclass: private String "+ entry.getKey() +";");
                output(a.getJSONObject(0));
                System.out.println("End of subclass: private String "+ entry.getKey() +";");
                continue;
            }

            JSONObject jsonObject = getJsonObject(entry.getValue().toString());
            if (null!=jsonObject){
                System.out.println("subclass: private String "+ entry.getKey() +";");
                output(jsonObject);
                System.out.println("End of subclass: private String "+ entry.getKey() +";");
                continue;
            }

            System.out.println(PRIVATE_STRING+ entry.getKey() +";");

        }
    }
}
