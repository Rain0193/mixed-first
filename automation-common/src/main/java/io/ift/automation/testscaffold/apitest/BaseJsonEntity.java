package io.ift.automation.testscaffold.apitest;

import com.alibaba.fastjson.JSON;
import io.ift.automation.helpers.CollectionsHelper;
import io.ift.automation.helpers.ReflectionHelper;
import io.ift.automation.logging.TestResultLogger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;

/**
 * Json Entity的基类，用来比对json返回值,重写了equals，hashcode方法
 */
public class BaseJsonEntity {
    private static final Logger logger = LogManager.getLogger(BaseJsonEntity.class.getName());

    /**
     * 比对返回值时可以忽略某些字段
     * @param target source
     * @param fieldName
     * @return
     */
    public boolean equalsByPassIgnoreFields(Object target, String... fieldName){
        if (this == target) return true;
        boolean flag =true;
        if (target == null || getClass() != target.getClass()) return false;
        for (Field field : this.getClass().getDeclaredFields()) {
            if(!CollectionsHelper.arrayContains(fieldName, field.getName())){
                if(!compareField(target,field)) flag= false;
            }
        }
        return flag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        boolean flag=true;
        if (o == null || getClass() != o.getClass()) return false;
        for (Field field : this.getClass().getDeclaredFields()) {
           if(!compareField(o,field)) flag =false;
        }
        return flag;
    }

    /**
     * compare field value
     * @param target target resource
     * @param field
     * @return true or false
     */
    private boolean compareField(Object target,Field field){

        Object o1 = null;
        Object o2 =null;
        o1 = ReflectionHelper.getFieldValue(this, field);
        o2 = ReflectionHelper.getFieldValue(target, field);

        if (o1 != null ? !o1.equals(o2) : o2 != null) {
            logger.info("expected field:{} value is {} but actual it is {}",field.getName(),o1,o2);
            TestResultLogger
                .error("expected field:{} value is {} but actual it is {}", field.getName(), o1, o2);
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {

        int result=0;
        for (Field field : this.getClass().getDeclaredFields()) {
            Object o = null;
            o = ReflectionHelper.getFieldValue(this, field);
            result = 31*result +(o!=null?o.hashCode():0);
        }
        return result;
    }

    @Override
    public String toString() {

        return this.getClass().getName()+":"+JSON.toJSON(this).toString();
    }
}
