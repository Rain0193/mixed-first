package io.ift.automation.testscaffold.codegenerator.pageobjectparser;

import io.ift.automation.helpers.CollectionsHelper;
import io.ift.automation.helpers.StringHelper;
import io.ift.automation.testscaffold.codegenerator.HtmlParser;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;

/**
 * Created by patrick on 15/7/13.
 *
 * @version $Id: PageObjectParsers.java 2691 2016-03-14 01:09:26Z guoxh $
 */

//todo need to abstract name strategy, and refactor

public class PageObjectParsers implements POParser {

    protected HtmlParser parser;
    static String parentDivCssSelector = "div[popup-title=%s]";
    static String seleniumParentDivXpathPrefix = "//div[@popup-title=\'%s\']";
    static String xpathStatement = "@FindBy(xpath=\"%s\")";
    static String elementNameStatement = "@ElementName(elementName=\"%s\")";
    static String fieldDeclaredStatement = "private %s %s;";
    private String inputAttrName4VariableName = "ng-model";
    private List<String> buttonCssSelectors = Lists.newArrayList(".btn", ".btnOpH24", ".s_btn"
            , "input[type=submit]", "input[type=button]");
    private List<String> buttonAttrUsedAsName = Lists.newArrayList("id", "name");
    private String selectAttrName = "name";
    private List<String> namingKeys = Lists.newArrayList("ng-model", "name", "id");

    public static PageObjectParsers buildFromFilePath(String path) {
        PageObjectParsers pop = new PageObjectParsers();
        pop.parser = HtmlParser.buildFromFile(path);
        return pop;
    }

    private String format(String marker, String... args) {
        return String.format(marker, args);
    }

    @Override
    public void parseButtons() {
        Elements elements = new Elements();
        elements.addAll(parser.getRoot().body().select(Joiner.on(",").join(buttonCssSelectors)));
//        for (String s : buttonCssSelectors) {
//            elements.addAll(parser.getRoot().body().select(s));
//        }

        for (Element element : elements) {
//            System.out.println(format("@FindBy(linkText=\"%s\")", element.ownText()));
//            System.out.println(format("@ElementName(elementName = \"%s\")", element.ownText()));
//            System.out.println(format("//href=%s", element.attr("href")));
            String fieldName = "";
            String naming = null;
            for (String s : buttonAttrUsedAsName) {
                naming = element.attr(s);
                if (StringHelper.isNotEmptyOrNotBlankString(naming)) {
                    naming = naming.trim();
                    System.out.println(format("@FindBy(%s=\"%s\")", s, naming));
                    System.out.println(format("@ElementName(elementName = \"%s\")", naming));
                    fieldName = element.attr(s).replace("(", "").replace(")", "").trim();
                    System.out.println(format("private Button %s;", fieldName));
                    break;
                }
            }

            if (!StringHelper.isNotEmptyOrNotBlankString(naming)) {
                String name = element.ownText().trim(); //null check??
                System.out.println(format("@FindBy(partialLinkText = \"%s\")", name));
                System.out.println(format("@ElementName(elementName = \"%s\")", name));
                System.out.println(format("private Button %s;", name));
            }

        }
    }

    @Override
    public void parseLinks() {
        Element container = parser.getRoot().body();
        Elements links = container.select("a");
        for (Element link : links) {
//            if(!link.parent().hasAttr("class")) continue; //filter the links in a td which is without class
            try {
                if (Integer.parseInt(link.ownText()) > 0) continue; //ignore same links
            } catch (Exception e) {
                //do nothing
            }
            if (!isPureLink(link)) {
                String name = link.ownText();
                String elementName;
                if (!StringHelper.isEmpty(name)) {
                    elementName = name;
                } else {
                    name = link.attr("ng-click");
                    if (StringHelper.isNotEmptyOrNotBlankString(name)) {
                        elementName = name.substring(0, name.indexOf('('));
                    } else {
                        elementName = link.ownText();
                    }
                }

                System.out.println(format("@FindBy(linkText=\"%s\")", link.ownText().trim()));
                System.out.println(format("@ElementName(elementName = \"%s\")", link.ownText().trim()));
//                System.out.println(format("//href=%s", link.attr("href")));
                System.out.println(format("private Button %s;", elementName.trim()));
            } else {
                if (StringHelper.isNotEmptyOrNotBlankString(link.ownText())) {
                    String name = link.ownText().trim();
                    System.out.println(format("@FindBy(linkText=\"%s\")", name));
                    System.out.println(format("@ElementName(elementName = \"%s\")", name));
//                System.out.println(format("//href=%s", link.attr("href")));
                    System.out.println(format("private Link %s;", name));
                }
            }

        }
    }

    private boolean isPureLink(Element element) {
        for (String className : element.classNames()) {
            if (className.contains("btn")) return false;
        }
        return true;
    }


    private void outputForSelect(String xpathDisplayPrefix, String attrName, Element select) {
        if (!(select.attr("class").contains("selYear") || select.attr("class").contains("selMonth"))) {
            String naming;
            if (!StringHelper.isNotEmptyOrNotBlankString(select.attr(attrName))) {
               if(StringHelper.isNotEmptyOrNotBlankString(select.attr("id"))) {
                   System.out.println(format("@FindBy(xpath=\"%s//select[@%s='%s']\")", xpathDisplayPrefix, "id", select.attr("id")));
                   System.out.println(format("@ElementName(elementName=\"%s\")", select.attr("id")));
                   naming = CollectionsHelper.lastElement(select.attr("id").replace("_", ".").split("\\."));
               }else{
                   System.out.println(format("@FindBy(xpath=\"%s//select[@%s='%s']\")", xpathDisplayPrefix, "ng-model", select.attr("ng-model")));
                   System.out.println(format("@ElementName(elementName=\"%s\")", select.attr("ng-model")));
                   naming = CollectionsHelper.lastElement(select.attr("ng-model").replace("_", ".").split("\\."));
               }

            } else {
                System.out.println(format("@FindBy(xpath=\"%s//select[@%s='%s']\")", xpathDisplayPrefix, attrName, select.attr(attrName)));
                System.out.println(format("@ElementName(elementName=\"%s\")", select.attr(attrName)));
                naming = CollectionsHelper.lastElement(select.attr(attrName).replace("_", ".").split("\\."));
            }
            System.out.println(format("private SelectList %s;", naming.trim()));
        }
    }


    @Override
    public void parseSelectList() {
        Elements selects = parser.getRoot().select("select");
        for (Element select : selects) {
            outputForSelect("", selectAttrName, select);
        }
    }

    @Override
    public void parseRadio() {
        parseInputTypeDistinctly(parser.getRoot(), "radio", "Radio");
    }

    @Override
    public void parseCheckBox() {
        parseInputTypeDistinctly(parser.getRoot(), "checkbox", "CheckBox");
    }

    /**
     * 解析CheckBox,Radio 这样的元素,有多个选项,但是多个选项绑定到同一个数据模型元素
     * @param parent
     * @param type
     * @param variableType
     */
    private void parseInputTypeDistinctly(Element parent, String type, String variableType) {
        // String divXpathPrefix = format(seleniumParentDivXpathPrefix, popupTitle);
        Elements elements = parent.select(format("input[type=%s]", type));

        String tempElementName = "";
        for (Element element : elements) {
//            String variableName = generateNameByStrategy(element);
//            String xpathTypeLocator = format("//input[@name='%s']", element.attr("name"));
            Pair naming = generateNameByStrategyList(element);
            String variableName = CollectionsHelper.lastElement(naming.getValue().replaceAll("\\.", "_").split("_"));
            String xpathTypeLocator = format("//input[@%s='%s']", naming.getKey(), naming.getValue());
            //合并相同相同元素
            if (StringHelper.isNoneContentString(tempElementName)) {
                System.out.println(String.format(xpathStatement, xpathTypeLocator));
                System.out.println(String.format(elementNameStatement, variableName));
                System.out.println(String.format(fieldDeclaredStatement, StringHelper.capitalize(variableType), variableName));
            } else {
                if (!tempElementName.equalsIgnoreCase(naming.getValue())) {
                    System.out.println(String.format(xpathStatement, xpathTypeLocator));
                    System.out.println(String.format(elementNameStatement, variableName));
                    System.out.println(String.format(fieldDeclaredStatement, StringHelper.capitalize(variableType), variableName));
                }
            }

            tempElementName = naming.getValue();
        }
    }

    //todo  ng-model try
    @Override
    public void parseTextArea() {
        Elements textArea = parser.getRoot().select("textarea");
        for (Element element : textArea) {
            if (StringHelper.isNotEmptyOrNotBlankString(element.attr("name"))) {
                System.out.println(String.format(xpathStatement, format("//textarea[@name='%s']", element.attr("name"))));
                System.out.println(String.format(elementNameStatement, element.attr("name")));
                String[] ngModel = element.attr("name").split("\\.");
                System.out.println(String.format(fieldDeclaredStatement, "InputBox", ngModel[ngModel.length - 1]));

            } else {
                System.out.println(String.format(xpathStatement, format("//textarea[@ng-model='%s']", element.attr("ng-model"))));
                System.out.println(String.format(elementNameStatement, element.attr("ng-model")));
                String[] ngModel = element.attr("ng-model").split("\\.");
                System.out.println(String.format(fieldDeclaredStatement, "InputBox", ngModel[ngModel.length - 1]));
            }
        }
    }

    @Override
    public void parseInputBox() {
        Elements inputs = parser.getRoot().select("input")
                .select(":not(input[type=hidden])").select(":not(input[type=radio])")
                .select(":not(input[type=checkbox])");
        for (Element input : inputs) {

            String elementName = generateNameByStrategy(input);
            //todo add name strategy
            if (StringHelper.isNotEmptyOrNotBlankString(elementName)) {
                if(StringHelper.isNotEmpty(input.attr("name"))){
                    System.out.println(format("@FindBy(name=\"%s\")", input.attr("name")));
                }else{
                    System.out.println(format(xpathStatement, format("//input[@ng-model='%s']",input.attr("ng-model"))));
                }
                System.out.println(format("@ElementName(elementName = \"%s\")", elementName));
                if (input.attr("class").contains("txtDate")||input.attr("class").contains("tDate")) {
                    System.out.println("private DatePicker " + elementName + ";");
                } else {
                    System.out.println("private InputBox " + elementName + ";");
                }
            }
        }
    }

    private static class Pair {
        public Pair(String key, String value) {
            this.key = key;
            this.value = value;
        }

        String key;
        String value;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    private Pair generateNameByStrategyList(Element input) {
        for (String namingKey : namingKeys) {
            if (StringHelper.isNotEmptyOrNotBlankString(input.attr(namingKey))) {
                return new Pair(namingKey, input.attr(namingKey));
            }
        }

        return new Pair("name", input.attr("name"));
    }


    private String generateNameByStrategy(Element input) {

        String name = input.attr(inputAttrName4VariableName);
        if (StringHelper.isNotEmptyOrNotBlankString(name)) {
            return CollectionsHelper.lastElement(name.split("\\."));
        } else {
            name = input.attr("name");
            if (StringHelper.isNotEmptyOrNotBlankString(name)) {
                return CollectionsHelper.lastElement(name.split("\\."));
            }
        }

        return StringHelper.EMPTY;
    }

    //most of functionality is for parsing :
//    1. button
//    2. links
//    3. radio
//    4. checkbox
//    5. selectList
//    6. datepicker
//    7. editTable
//    8. uploadElement

    public PageObjectParsers addButtonCssSelector(String... cssSelectors) {
        CollectionsHelper.addAll(buttonCssSelectors, cssSelectors);
        return this;
    }

    public PageObjectParsers addButtonName(String... namingStrategies) {
        CollectionsHelper.addAll(buttonAttrUsedAsName, namingStrategies);
        return this;
    }

    public PageObjectParsers setInputNamingStrategy(String attrName) {
        this.inputAttrName4VariableName = attrName;
        return this;
    }

}
