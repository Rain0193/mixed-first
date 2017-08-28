package io.ift.automation.testscaffold.codegenerator;

import io.ift.automation.helpers.CollectionsHelper;
import io.ift.automation.helpers.StringHelper;
import com.google.common.collect.Lists;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by patrick on 15/4/29.
 *  Not Used yet
 * @version $Id$
 */


public class TestDataGenerator {

    private static List<String> ignoredField = Lists.newArrayList("Button","Link","Image","Table","ListPictureElement");

    public static TestDataGenerator build(String ...ignoredType){
        TestDataGenerator g = new TestDataGenerator();
        CollectionsHelper.addAll(g.ignoredField, ignoredType);
        return g;
    }

    public void parseTestData(Class pageClazz){
        Field[] fields = pageClazz.getDeclaredFields();

        for (Field field : fields) {
            if(field.getType().getSimpleName().equalsIgnoreCase("List")){
                System.out.println("private List<String> "+field.getName()+"=Lists.newArrayList();");
            }else if(!ignoredField.contains(field.getType().getSimpleName())){
                System.out.println("private String "+field.getName()+";");
                //System.out.println(field.getType());
            }
        }
    }


    public void parseTestDataByClassList(List<Class> pages){
        pages.forEach(this::parseTestData);
    }

    public void parseTestDataByClasses(String dataClassName,Class...pages){
        System.out.println("--- 测试数据类:" + StringHelper.capitalize(dataClassName));
        for (Class page : pages) {
            parseTestData(page);
        }
        System.out.println("--- End of 测试数据类:"+ StringHelper.capitalize(dataClassName));
    }

}
