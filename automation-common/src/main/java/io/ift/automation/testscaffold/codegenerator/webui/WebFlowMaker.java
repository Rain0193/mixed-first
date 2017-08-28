package io.ift.automation.testscaffold.codegenerator.webui;

import io.ift.automation.helpers.ExcelHelper;
import io.ift.automation.helpers.MapsHelper;
import io.ift.automation.helpers.ReflectionHelper;
import io.ift.automation.helpers.StringHelper;
import io.ift.automation.testscaffold.webtest.webUI.annotations.webpage.ElementName;
import io.ift.automation.testscaffold.webtest.webUI.htmlelements.BasePage;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.openqa.selenium.support.FindBy;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * Created by patrick on 15/6/1.
 *
 * @version $Id: WebFlowMaker.java 1826 2015-09-08 06:54:22Z wuke $
 */

@SuppressWarnings("unchecked")
public class WebFlowMaker {

    private Map<String, List<WebElementDescription>> pageElementsMapping = Maps.newHashMap();
    private Map<String, List<WebTestAtomicAction>> flowElementsMapping = Maps.newHashMap();
    //Map 的格式： "Header"=List<String>
    private static final Map<String, String> DEFAULT_HEADER_MAPPING = Maps.newHashMap();

    static {
        DEFAULT_HEADER_MAPPING.put("元素名称", "elementName");
        DEFAULT_HEADER_MAPPING.put("元素变量名", "variableName");
        DEFAULT_HEADER_MAPPING.put("元素类型", "elementType");
        DEFAULT_HEADER_MAPPING.put("元素定位", "elementLocation");
        DEFAULT_HEADER_MAPPING.put("页面", "pageName");
    }

    public static WebFlowMaker build() {
        return new WebFlowMaker();
    }

    /**
     * 加载Excel文件
     *
     * @param path
     * @return
     */
    public WebFlowMaker load(String path) {
        List<List<String>> rawData = ExcelHelper.build(path).readAll();
        List<String> header = rawData.get(0);
        for (int i = 1; i < rawData.size(); i++) {
            WebElementDescription d = new WebElementDescription();
            List<String> currentLine = rawData.get(i);
            for (int j = 0; j < header.size(); j++) {
                if (DEFAULT_HEADER_MAPPING.keySet().contains(header.get(j))) {
                    ReflectionHelper.setFieldValue(d, DEFAULT_HEADER_MAPPING.get(header.get(j)), currentLine.get(j));
                } else {
                    if (currentLine.get(j).trim().length() > 0) { //has value
                        WebTestAtomicAction action = new WebTestAtomicAction(d,currentLine.get(j));
                        MapsHelper.put(flowElementsMapping, header.get(j), action);
                    }
                }
            }

            if (!d.getPageName().equals(StringHelper.EMPTY)) {
                MapsHelper.put(pageElementsMapping, d.getPageName(), d);
            }
        }

        return this;
    }

    /**
     * 解析find by
     *
     * @param by
     * @return
     */
    private static String parseFindBy(FindBy by) {
      /*  How how() default How.ID;
        String using() default "";
        String id() default "";
        String name() default "";
        String className() default "";
        String css() default "";
        String tagName() default "";
        String linkText() default "";
        String partialLinkText() default "";
        String xpath() default "";*/
        List<String> locatorName = Lists.newArrayList("css", "id", "name"
                , "className", "tagName", "linkText", "xpath", "partialLinkText()");
        String result = StringHelper.EMPTY;

        for (String s : locatorName) {
            try {
                result = (String) ReflectionHelper.invokeMethodByName(by, s, new Object[]{});
                if (!result.equalsIgnoreCase(StringHelper.EMPTY)) {
                    result = s + "=" + result;
                    return result;
                }
            } catch (Exception e) {
                //do nothing
            }
        }
        return result;
    }

    /**
     * 获取多个页面类的描述的Mapping
     *
     * @param clazz
     * @return
     */
    public  Map<String, List<WebElementDescription>> getWebElementDescriptionsMapping(Class... clazz) {
        Map<String, List<WebElementDescription>> map = Maps.newHashMap();

        for (Class tClass : clazz) {
            map.put(tClass.getClass().getSimpleName(), getWebElementDescription(tClass));
        }

        return map;
    }

    /**
     * 获取多页面的页面描述
     *
     * @param clazz
     * @return
     */
    public  List<WebElementDescription> getMultiplePageWD(List<Class> clazz) {
        List<WebElementDescription> allElements = Lists.newArrayList();

        for (Class tClass : clazz) {
            allElements.addAll(getWebElementDescription(tClass));
        }
        return allElements;
    }

    /**
     * 根据类读取里面的页面元素描述
     *
     * @param pageObjectClazz
     * @param <T>
     * @return
     */
    public <T> List<WebElementDescription> getWebElementDescription(Class<T> pageObjectClazz) {
        Field[] fields = pageObjectClazz.getDeclaredFields();
        List<WebElementDescription> elements = Lists.newArrayList();
        for (Field field : fields) {
            WebElementDescription e = new WebElementDescription();
            FindBy findBy = field.getAnnotation(FindBy.class);
            if (field.getAnnotation(FindBy.class) != null) {
                e.setElementLocation(parseFindBy(findBy));
            }
            ElementName name = field.getAnnotation(ElementName.class);
            if (name != null) {
                e.setElementName((String) ReflectionHelper.invokeMethodByName(name, "elementName", new Object[]{}));
            } else {
                e.setElementName(field.getName());
            }

            e.setPageName(pageObjectClazz.getSimpleName());
            e.setElementType(field.getType().getSimpleName());
            e.setVariableName(field.getName());
            elements.add(e);
        }
        return elements;
    }

    public Map<String, List<WebElementDescription>> getPageElementsMapping() {
        return pageElementsMapping;
    }

    public void setPageElementsMapping(Map<String, List<WebElementDescription>> pageElementsMapping) {
        this.pageElementsMapping = pageElementsMapping;
    }

    public Map<String, List<WebTestAtomicAction>> getFlowElementsMapping() {
        return flowElementsMapping;
    }

    public void setFlowElementsMapping(Map<String, List<WebTestAtomicAction>> flowElementsMapping) {
        this.flowElementsMapping = flowElementsMapping;
    }

    /**
     * write page object elements to a excel
     *
     * @param path
     * @param clazz
     * @param <T>
     */
    public <T extends BasePage> void writePagObjectToExcel(String path, Class<T> clazz) {

        writeWDToExcel(path,getWebElementDescription(clazz));
    }

    /**
     * 把页面元素写入到Excel
     * @param path
     * @param wdList
     */
    private  void writeWDToExcel(String path,List<WebElementDescription> wdList) {
        String webElementHeader = "页面,元素定位,元素类型,元素变量名,元素名称";
        List<List<String>> data = Lists.newArrayList();
        for (WebElementDescription description : wdList) {
            data.add(Lists.newArrayList(description.getPageName(),description.getElementLocation(),
                    description.getElementType(),description.getVariableName(),description.getElementName()));
        }

        ExcelHelper.createTestCaseData("data", path, webElementHeader, data);
    }

    /**
     * 将多个page object类写入到excel
     * @param path
     * @param clazz
     */
    public  void writeMultiplePagObjectsToExcel(String path, List<Class> clazz) {
        List<WebElementDescription> wdList = getMultiplePageWD(clazz);
        writeWDToExcel(path, wdList);
    }

    /**
     * 将多个page object写入到excel
     * @param path
     * @param classes
     */
    public  void writeMultiplePagObjectsToExcel(String path, Class ...classes) {
        List<WebElementDescription> wdList = getMultiplePageWD(Lists.newArrayList(classes));
        writeWDToExcel(path,wdList);
    }
}
