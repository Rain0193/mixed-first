package io.ift.automation.helpers.refectiontest;

import io.ift.automation.helpers.ReflectionHelper;
import io.ift.automation.helpers.XMLDocHelper;
import com.google.common.base.Function;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

import java.util.Map;

public class PageDescriptionTest {

    private static final Logger logger = LogManager.getLogger(PageDescription.class.getName());
    @Test
    public void testPageDescription(){

        // k:v = field name/ field description
       Map<String,String> map= XMLDocHelper.build("pages/BaiDuHomePageResource.xml")
               .getNameAndTextForAllElements();
        PageDescription pd = new PageDescription();
        for (Map.Entry<String,String> entry: map.entrySet()) {
           try {
               ReflectionHelper.setFieldValue(pd, entry.getKey(), entry.getValue());
           }catch (Exception e){
               logger.error(e);
           }
        }

        System.out.println(pd);
    }

    @Test
    public void testPageDescriotion_2(){
        // k:v = field name/ field description
        Map<String,String> map= XMLDocHelper.build("pages/BaiDuHomePageResource.xml")
                .getNameAndTextForAllElements();
        PageDescription pd = new PageDescription();
        for (Map.Entry<String,String> entry: map.entrySet()) {
            try {
                Function function = new Function() {
                    @Override
                    public Object apply(Object input) {
                        return input+"test";
                    }
                };
                //add field decorator here then transfer entry.getValue to defined value
                ReflectionHelper.setFieldValue(pd, entry.getKey(), function.apply(entry.getValue()));
            }catch (Exception e){
                logger.error(e);
            }
        }

        System.out.println(pd);
    }

    @Test
    public void testPage2(){
        PageDescription pd = new PageDescription();
        Map<String,String> map= XMLDocHelper.build("pages/BaiDuHomePageResource.xml")
                .getNameAndTextForAllElements();

        convertTo(map, pd, new Function<String, String>() {
            @Override
            public String apply(String input) {
                return input.toUpperCase()+" 3435466543";
            }
        });
        logger.info(pd);
    }

    private <T,I,R> void  convertTo(Map<String,String> map,T instance,Function<I,R> function){
        for (Map.Entry<String,String> entry: map.entrySet()) {
            try {
               Function function1 = new Function() {
                   @Override
                   public Object apply(Object input) {
                       return input.toString().substring(0,
                               input.toString().length()-2);
                   }
               };
                //add field decorator here then transfer entry.getValue to defined value
                ReflectionHelper.setFieldValue(instance,
                        (String) function1.apply(entry.getKey()), function.apply((I)entry.getValue()));
            }catch (Exception e){
                logger.error(e);
            }
        }
    }
}
