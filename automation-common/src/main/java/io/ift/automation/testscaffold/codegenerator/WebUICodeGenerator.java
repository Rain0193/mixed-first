package io.ift.automation.testscaffold.codegenerator;

import io.ift.automation.helpers.MapsHelper;
import io.ift.automation.helpers.ReflectionHelper;
import io.ift.automation.helpers.StringHelper;
import io.ift.automation.testscaffold.codegenerator.pageobjectparser.PageObjectParsers;
import io.ift.automation.testscaffold.codegenerator.webui.WebElementDescription;
import io.ift.automation.testscaffold.codegenerator.webui.WebFlowMaker;
import io.ift.automation.testscaffold.codegenerator.webui.WebTestAtomicAction;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.System.*;

/**
 * Created by patrick on 15/6/1.
 *
 * @version $Id$
 * @author patrick
 */
public class WebUICodeGenerator {

    private Map<String,List<WebElementDescription>> pageElementsMapping = Maps.newHashMap();
    private Map<String,List<WebTestAtomicAction>> flowElementMapping = Maps.newHashMap();

    /**
     * <p>build.</p>
     *
     * @param path a {@link java.lang.String} object.
     * @return a {@link WebUICodeGenerator} object.
     */
    public static WebUICodeGenerator build(String path){
        WebUICodeGenerator g = new WebUICodeGenerator();
        return g.init(path);
    }

    /**
     * <p>build.</p>
     *
     * @return a {@link WebUICodeGenerator} object.
     */
    public static WebUICodeGenerator build(){
        return new WebUICodeGenerator();
    }

    /**
     * <p>init.</p>
     *
     * @param path a {@link java.lang.String} object.
     * @return a {@link WebUICodeGenerator} object.
     */
    public WebUICodeGenerator init(String path){
        WebFlowMaker reader = WebFlowMaker.build().load(path);
        this.pageElementsMapping = reader.getPageElementsMapping();
        this.flowElementMapping=reader.getFlowElementsMapping();
        return this;
    }

//    @Override
//    public File generateTestStepCode() {
//        Map<String,String> result = generatePageObject();
//        for (Map.Entry<String,String> entry : result.entrySet()) {
//            out.println(entry.getKey());
//            out.println(entry.getValue());
//        }
//        return null;
//    }

    /**
     * 自己生成PageObject类其实不是很方便,这个方法没有使用
     * @return
     */
    private Map<String,String> generatePageObject(){

        String packageName="package com.dooioo.automation.pages; %n";
        String importString="import com.dooioo.automation.testscaffold.webtest.actions.ExecutablePageObject;%n" +
                "import com.dooioo.automation.testscaffold.webtest.webUI.annotations.webpage.ElementName;%n" +
                "import com.dooioo.automation.testscaffold.webtest.webUI.htmlelements.*;%n" +
                "import com.dooioo.automation.testscaffold.webtest.annotations.UIAction;%n" +
                "import com.dooioo.automation.testscaffold.webtest.annotations.UIActions;%n"+
                "import org.openqa.selenium.WebDriver;%n"+
                "import org.openqa.selenium.support.FindBy;%n %n";
        //generate page object from html code
        Map<String,String> result = Maps.newHashMap();
        for (Map.Entry<String, List<WebElementDescription>> entry : pageElementsMapping.entrySet()) {
            StringBuilder sb = new StringBuilder();
            sb.append(packageName);
            sb.append(importString);
//            sb.append(generateAnnotationStatement());
            sb.append(String.format("public class %s extends ExecutablePageObject{%n", StringHelper
                .capitalize(entry.getKey())));
            sb.append(String.format(" public %s(WebDriver driver) {%n" +
                    "        super(driver);%n" +
                    "    }",StringHelper.capitalize(entry.getKey())));
            sb.append("%n");

            for (WebElementDescription description : entry.getValue()) {
                sb.append(String.format("@FindBy(%s)",description.getElementLocation()));
                sb.append("%n");
                sb.append(String.format("@ElementName(elementName=\"%s\")", description.getElementName()));
                sb.append("%n");
                sb.append(String.format("private %s %s;",description.getElementType(),description.getVariableName()));
                sb.append("%n");
            }

            sb.append("}");
            result.put(entry.getKey(),sb.toString());
        }

        return result  ;
    }

    /**
     * 根据页面类生成流程注解,不需要excel文件
     *
     * @param flowName a {@link java.lang.String} object.
     * @param clazz a {@link java.lang.Class} object.
     * @return a {@link WebUICodeGenerator} object.
     */
    public WebUICodeGenerator generateAnnotationStatement(String flowName,Class clazz){
        System.out.println("-- Class Name:"+clazz.getSimpleName());
        String uiActions = "@UIActions(actions={%s})";
        String action="@UIAction(processName=\"%s\",elementActionDescription={%s})%n";
        List<String> result = Lists.newArrayList(ReflectionHelper.getAccessibleFields(clazz)).stream()
                .filter(field -> !field.getType().getSimpleName().equalsIgnoreCase("Button") &&
                        !field.getType().getSimpleName().equalsIgnoreCase("Link")&&
                !field.getName().equalsIgnoreCase("actionHolder")
                &&!field.getName().equalsIgnoreCase("driver")).map(item -> "\"" + item.getName() + "\"").collect(Collectors.toList());
        out.println(String.format(uiActions,String.format(action,flowName,String.join(",",result))));
        return this;
    }

    /**
     * 根据注解生成注解代码,可以是多个页面
     *
     * @param flowName a {@link java.lang.String} object.
     * @param pages a {@link java.lang.Class} object.
     * @return a {@link WebUICodeGenerator} object.
     */
    public WebUICodeGenerator generateFlowCodesForAnnotatedPage(String flowName,Class...pages){
        out.println( String.format("public static void %s(WebDriver driver,TestData testData){",flowName));
        String template =" WebTestActionBuilder.execute(Lists.newArrayList(%n" +
                "                %s)%n" +
                "        ,\"%s\",driver,testData);%n" +
                "}";
        String pageSteps = String.join(",", Lists.newArrayList(pages).stream().map(item -> item.getSimpleName() + ".class")
                .collect(Collectors.toList()));
        out.println(String.format(template,pageSteps,flowName));
        return this;

    }

    /**
     * generate annotation from excel
     *
     * @param flowName a {@link java.lang.String} object.
     * @return a {@link WebUICodeGenerator} object.
     */
    @Deprecated
    public WebUICodeGenerator generateAnnotationStatement(String flowName){
        String uiActions = "@UIActions(actions={%s})";
        String action="@UIAction(processName=\"%s\",elementActionDescription={%s})%n";
        Map<String, List<String>> pageAnnotationMapping = getPageActionsMap(flowName);

        for (Map.Entry<String, List<String>> entry : pageAnnotationMapping.entrySet()) {
            out.println(entry.getKey()+":");
            String elementActionDescription = String.join(",",entry.getValue().stream().map(item->"\""+item+"\"").collect(Collectors.toList()));
            out.println(String.format(uiActions,String.format(action,flowName,elementActionDescription
                    )));
        }
        return this;
    }


    /**
     * <p>generateAnnotationFromExcel.</p>
     *
     * @param flowName a {@link java.lang.String} object.
     * @return a {@link WebUICodeGenerator} object.
     */
    public WebUICodeGenerator generateAnnotationFromExcel(String flowName){
        String uiActions = "@UIActions(actions={%s})";
        String action="@UIAction(processName=\"%s\",elementActionDescription={%s})%n";
        Map<String, List<String>> pageAnnotationMapping = getPageActionsMap(flowName);

        for (Map.Entry<String, List<String>> entry : pageAnnotationMapping.entrySet()) {
            out.println(entry.getKey()+":");
            String elementActionDescription = String.join(",",entry.getValue().stream().map(item->"\""+item+"\"").collect(Collectors.toList()));
            out.println(String.format(uiActions,String.format(action,flowName,elementActionDescription
            )));
        }
        return this;
    }


    /**
     * <p>generateAnnotationAndFlowCode.</p>
     *
     * @param flowName a {@link java.lang.String} object.
     * @param classes a {@link java.lang.Class} object.
     * @return a {@link WebUICodeGenerator} object.
     */
    public WebUICodeGenerator generateAnnotationAndFlowCode(String flowName,Class...classes){
        for (Class aClass : classes) {
            generateAnnotationStatement(flowName, aClass);
        }

        generateFlowCodesForAnnotatedPage(flowName,classes);
        return this;
    }

    /**
     * 根据Excel文件生成每一个页面的流程的步骤
     * @param flowName
     * @return Map, 格式为{{页面1={元素1,元素2}},{页面2={元素1,元素2}}}
     */
    private Map<String, List<String>> getPageActionsMap(String flowName) {
        List<WebTestAtomicAction> actions = flowElementMapping.get(flowName);
        Collections.sort(actions);

        Map<String,List<String>> pageAnnotationMapping = Maps.newLinkedHashMap();

        for (WebTestAtomicAction webTestAtomicAction : actions) {
            MapsHelper.put(pageAnnotationMapping
                    , webTestAtomicAction.getElement().getPageName(), webTestAtomicAction.getElementAndActionPair());
        }
        return pageAnnotationMapping;
    }

    /**
     * 根据excel文件生成测试步骤代码
     *
     * @return a {@link WebUICodeGenerator} object.
     */
    @Deprecated
    public WebUICodeGenerator generateTestSteps(){
        //generate page object from excel
        flowElementMapping.keySet().forEach(this::generateSingleTestStep);
        return this;
    }

    /**
     * 根据excel文件生成测试步骤代码
     *
     * @return a {@link WebUICodeGenerator} object.
     */
    public WebUICodeGenerator generateTestStepsFromExcel(){
        //generate page object from excel
        flowElementMapping.keySet().forEach(this::generateSingleTestStep);
        return this;
    }

    /**
     * 根据excel文件生成指定测试流程代码
     *
     * @param flowName a {@link java.lang.String} object.
     * @return a {@link WebUICodeGenerator} object.
     */
    @Deprecated
    public WebUICodeGenerator generateSingleTestStep(String flowName) {
        Map<String, List<String>> pageActionMapping = getPageActionsMap(flowName);
        out.println( String.format("public static void %s(WebDriver driver,TestData testData){",flowName));
        for (Map.Entry<String, List<String>> entry : pageActionMapping.entrySet()) {
            String template = "WebTestActionBuilder." +
                    "createTestActionByElementActionList(%s.class,%s,driver,testData).execute();";
            out.println(String.format(template, entry.getKey(),
                    "Lists.newArrayList(" + String.join(",", entry.getValue().stream().map(item -> "\"" +
                            item + "\"").collect(Collectors.toList())) + ")"));
        }
        out.println("}");
        return this;
    }

    /**
     * same as generateSingleTestStep just change the method name
     *
     * @param flowName a {@link java.lang.String} object.
     * @return a {@link WebUICodeGenerator} object.
     */
    public WebUICodeGenerator generateSingleTestStepFromExcel(String flowName) {
        Map<String, List<String>> pageActionMapping = getPageActionsMap(flowName);
        out.println( String.format("public static void %s(WebDriver driver,TestData testData){",flowName));
        for (Map.Entry<String, List<String>> entry : pageActionMapping.entrySet()) {
            String template = "WebTestActionBuilder." +
                    "createTestActionByElementActionList(%s.class,%s,driver,testData).execute();";
            out.println(String.format(template, entry.getKey(),
                    "Lists.newArrayList(" + String.join(",", entry.getValue().stream().map(item -> "\"" +
                            item + "\"").collect(Collectors.toList())) + ")"));
        }
        out.println("}");
        return this;
    }


    /**
     * 将页面类写入到excel文件
     *
     * @param excelFilePath a {@link java.lang.String} object.
     * @param clazz a {@link java.util.List} object.
     */
    public void writePageObjectsToExcel(String excelFilePath,List<Class> clazz){
        WebFlowMaker.build().writeMultiplePagObjectsToExcel(excelFilePath, clazz);
    }

    /**
     * 根据页面类生成测试数据类
     *
     * @param clazz a {@link java.util.List} object.
     * @param ignoredType a {@link java.lang.String} object.
     */
    public void generateTestDataClass(List<Class> clazz,String ...ignoredType){
        TestDataGenerator.build(ignoredType).parseTestDataByClassList(clazz);
    }

    /**
     * <p>generateTestDataClass.</p>
     *
     * @param clazz a {@link java.lang.Class} object.
     * @param ignoredType a {@link java.lang.String} object.
     */
    public void generateTestDataClass(Class clazz,String ...ignoredType){
        TestDataGenerator.build(ignoredType).parseTestDataByClasses(clazz.getSimpleName(), clazz);
    }

    /**
     * <p>generatePageObjectClass.</p>
     *
     * @param sourcePath a {@link java.lang.String} object.
     */
    public void generatePageObjectClass(String sourcePath){
        PageObjectParsers.buildFromFilePath(sourcePath).parse();
    }

}
