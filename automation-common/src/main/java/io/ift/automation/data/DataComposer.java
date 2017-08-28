package io.ift.automation.data;

import io.ift.automation.helpers.CollectionsHelper;
import io.ift.automation.helpers.ReflectionHelper;
import com.google.common.collect.Lists;
import org.apache.commons.beanutils.BeanUtils;
import org.dozer.DozerBeanMapper;

import java.util.Collection;
import java.util.List;

/**
 * Created by patrick on 15/3/18.
 *
 * @version $Id$
 */


public class DataComposer {

    private List<Object> sources;
    private DataComposer(){
        sources = Lists.newArrayList();
    }

//    /**
//     * 输入待转换的java bean
//     * @param source
//     * @return
//     */
//    public static DataComposer from(TestData...source){
//        DataComposer composer = new DataComposer();
//        composer.addSourceObjects(source);
//
//        return composer;
//    }

    /**
     * 输入待转换的测试数据
     * @param data {@link TestData}测试数据接口
     * @return
     */
    @Deprecated
    public static DataComposer from(Object...data){
        DataComposer composer = new DataComposer();
        composer.addSourceObjects(data);

        return composer;
    }

    /**
     * 输入待转换的测试数据
     * @param data {@link TestData}测试数据接口
     * @return
     */
    public static DataComposer copyFrom(Object...data){
        DataComposer composer = new DataComposer();
        composer.addSourceObjects(data);

        return composer;
    }

    /**
     * 获取field 的值
     * @param fieldName
     * @return
     */
    public Object get(String ... fieldName){
        for (Object source : this.sources) {
            Object result =null;
            if(source instanceof TestData){
                result = ((TestData)source).get(fieldName);
            }else{
                try {
                    result=BeanUtils.describe(source).get(fieldName);
                } catch (Exception e) {
                    //do nothing
                }
            }
            if(result!=null) return result;
        }

        return null;
    }


    private void addSourceObjects(Object...source){
        CollectionsHelper.addAll(sources,source);
    }

    /**
     * 转换成目标类，目前不支持嵌套类
     * @param target
     * @param <T>
     * @return
     */
    public <T> T to(T target){
        for (Object source : sources) {
            BeanMapper.copy(source, target);
        }
        return target;
    }


    public DataComposer set(String key,String value){
        for (Object source : sources) {
            ReflectionHelper.setFieldValue(source,key,value);
        }

        return this;
    }

    public Object getOneSource(){
        return this.sources.get(0);
    }

    public List<Object> getSources(){
        return this.sources;
    }

    /**
     * 转换成目标类，目前不支持嵌套类
     * @param clazz
     * @param <T>
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public <T> T to(Class<T> clazz) throws IllegalAccessException, InstantiationException {

        T instance = clazz.newInstance();
        for (Object source : sources) {
            BeanMapper.copy(source, instance);
        }

        return instance;
    }

/*
    private Object getFieldValue(String fieldName,Object source){
        return ReflectionHelper.getFieldValue(source,fieldName);
    }*/


    /**
     * Bean 复制工具，复制Bean，或者转换Bean
     */
    public static class BeanMapper {

        private static DozerBeanMapper dozer = new DozerBeanMapper();

        private BeanMapper() {
        }

        /**
         * 基于dozer转换对应类型
         * @param source
         * @param destinationClass
         * @param <T>
         * @return
         */
        public static <T> T map(Object source,Class<T> destinationClass){
            return dozer.map(source, destinationClass);
        }

        /**
         * 基于dozer转换collection中对应的对象类型
         * @param sourceList
         * @param destinationClass
         * @param <T>
         * @return
         */
        public static <T> List<T> mapList(Collection sourceList,Class<T> destinationClass){
            List<T> destList= Lists.newArrayList();
            for (Object o : sourceList) {
                T destObject= dozer.map(o,destinationClass);
                destList.add(destObject);
            }

            return destList;
        }

        /**
         * 复制对象
         * @param source
         * @param destinationObject
         */
        public static void copy(Object source,Object destinationObject){
            dozer.map(source,destinationObject);
        }
    }

}
