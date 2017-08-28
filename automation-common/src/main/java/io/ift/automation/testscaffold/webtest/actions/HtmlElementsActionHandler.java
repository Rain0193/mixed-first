package io.ift.automation.testscaffold.webtest.actions;

import io.ift.automation.helpers.RandomHelper;
import io.ift.automation.helpers.ReflectionHelper;
import io.ift.automation.logging.TestResultLogger;
import io.ift.automation.testscaffold.WebTestException;
import com.google.common.collect.Lists;
import org.openqa.selenium.WebElement;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Created by patrick on 15/4/3.
 *
 * @version $Id$
 */

@SuppressWarnings("unchecked")
public final class HtmlElementsActionHandler {

    private HtmlElementsActionHandler(){}
    /**
     * 处理WebElement没有变量的动作
     * @param htmlElement
     * @param actionName
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static  void handlerNullArgs(Object htmlElement,String actionName){
        try{
            ReflectionHelper.invokeMethodByName(htmlElement, actionName, null);
        }catch (IllegalArgumentException e){
            TestResultLogger.warn(htmlElement+"没有数据输入，可能你的测试有问题，进行的操作是:"+actionName);
        }

    }

    public static <T> void handlerIntArgs(T htmlElement,String actionName,Integer data){
        ReflectionHelper.invokeMethodByName(htmlElement, actionName, new Object[]{data});
    }
    /**
     * 处理Webelement带变量的参数
     * @param htmlElement
     * @param actionName
     * @param data
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static void handlerStringArgs(Object htmlElement,String actionName,String data){
       try {
           ReflectionHelper.invokeMethodByName(htmlElement, actionName, new Object[]{data});
       }catch (IllegalArgumentException e){
           //ignore this error message,as it is data driven, if no data, leave it there
           TestResultLogger.warn(htmlElement+"没有数据输入，可能你的测试有问题，进行的操作是:"+actionName);
       }
    }

    /**
     * 处理Webelement带变量的参数
     * @param htmlElement
     * @param actionName
     * @param data
     * @param <T>
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static <T> void handlerCharSequenceArgs(Object htmlElement,String actionName,String data){

        ReflectionHelper.invokeMethodByName(htmlElement, actionName, new Object[]{new CharSequence[]{data}});
    }

    /**
     * 处理htmlelement list的操作
     * @param htmlElements
     * @param actionName
     * @param data
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static  void handlerElementList(List<Object> htmlElements,String actionName,Object data){
        List<String> input = Lists.newArrayList() ;
        if(data instanceof List) {
            input.addAll((List<String>)data);
        }else{
            throw new WebTestException("the input data "+data+" is not correct");
        }

        boolean flag =false;
        for (String s : input) {
            if(s.startsWith(ListElementHandlerToken.index.name())){ //index
                int elementIndex = Integer.parseInt(s.replace(ListElementHandlerToken.index.name()+":","").trim());
                if(elementIndex>htmlElements.size()) throw new WebTestException("index is exceeding the element size");
                ReflectionHelper.invokeMethodByName(htmlElements.get(elementIndex),actionName,new Object[]{elementIndex});
                flag=true;
            }else if(s.startsWith(ListElementHandlerToken.random.name())){//random for index
                int elementIndex = RandomHelper.generateRandomInt(htmlElements.size());
                ReflectionHelper.invokeMethodByName(htmlElements.get(elementIndex),actionName,new Object[]{elementIndex});
                flag=true;
            }else{
                for (Object htmlElement : htmlElements) { //filter by text,value,id
                    String text = ((WebElement)htmlElement).getText();
                    String value = ((WebElement)htmlElement).getAttribute("value");
                    String id = ((WebElement)htmlElement).getAttribute("id");
                    String title = ((WebElement)htmlElement).getAttribute("title");
                    if(s.equalsIgnoreCase(text)||
                            s.equalsIgnoreCase(value)||s.equalsIgnoreCase(id)||s.equalsIgnoreCase(title)){
                        ReflectionHelper.invokeMethodByName(htmlElement,actionName,null);
                        flag=true;
                        break;
                    }
                }

            }
        }
        //if the data is not correct,select the first one
        if(!flag){
            ReflectionHelper.invokeMethodByName(htmlElements.get(0),actionName,null);
        }
    }

    private enum ListElementHandlerToken{
        index,random;
    }
    /**
     * 处理WebElement/HTMLElement元素的入口，根据元素输入的动作名称，数据，进行操作元素
     * @param element
     * @param actionName
     * @param data
     */
    public static  void handleElement(Object element,String actionName,Object data){

        //todo to be enhanced,to refactor for better
        TestResultLogger.log("开始处理 element={}, action={},data={}",element,actionName,data);
        if(data==null||actionName.equalsIgnoreCase("click")||actionName.contains("click")) {//for some action,no need to have
            handlerNullArgs(element,actionName);
            return;
        }

        if(element instanceof List) {
            handlerElementList((List<Object>)element,actionName,data);
            return;
        }

        if(actionName.contains("sendKeys")){
            handlerCharSequenceArgs(element, actionName, (String) data);
            return;
        }

        if(actionName.contains("Index")||actionName.contains("index")){
            handlerIntArgs(element, actionName, Integer.valueOf((String)data));
            return;
        }

        handlerStringArgs(element,actionName,(String) data);

    }
}
