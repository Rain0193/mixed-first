package io.ift.automation.testscaffold.codegenerator.pageobjectparser;

import io.ift.automation.helpers.CollectionsHelper;
import io.ift.automation.helpers.StringHelper;
import io.ift.automation.testscaffold.codegenerator.HtmlParser;
import com.google.common.collect.Lists;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;

/**
 * Created by patrick on 15/4/28.
 *
 * @version $Id$
 */

@Deprecated
public class PageObjectParser {
    protected HtmlParser parser;
    static String parentDivCssSelector = "div[popup-title=%s]";
    static String seleniumParentDivXpathPrefix = "//div[@popup-title=\'%s\']";
    static String xpathStatement = "@FindBy(xpath=\"%s\")";
    static String elementNameStatement = "@ElementName(elementName=\"%s\")";
    static String fieldDeclaredStatement = "private %s %s;";

    public static PageObjectParser buildFromFilePath(String path) {
        PageObjectParser pop = new PageObjectParser();
        pop.parser = HtmlParser.buildFromFile(path);
        return pop;
    }

    private void outputLinks(Elements navLinks) {
        for (Element navLink : navLinks) {
            String ngCLick = navLink.parent().attr("ng-click");
            String elementName = StringHelper.extract(ngCLick, "['](.*)[']");
            System.out.println(String.format("@FindBy(linkText=\"%s\")", navLink.ownText()));
            System.out.println(format("@ElementName(elementName = \"%s\")", navLink.ownText()));
            System.out.println("private Link " + elementName.replace("'", "") + ";");
        }
    }

    public void parseLinksinNavDiv(String cssSelector) {
        Element element = parser.getElementsByCssSelector(cssSelector).get(0);
        Elements navLinks = element.select("a");
        outputLinks(navLinks);
    }

    private void parseLinks(String parentClassNameOrCssSelector, String linkCssSelector) {

        Element element = parser.getElementsByCssSelector(parentClassNameOrCssSelector).get(0);
        Elements navLinks = element.select(linkCssSelector);
        //todo filter tag with button class
        outputLinks(navLinks);
    }

    private static String format(String marker, String... args) {
        return String.format(marker, args);
    }

    //todo need to do more checking
    private boolean isPureLink(Element element) {
        return !element.classNames().contains("btn");
    }


    public void parseLinks() {
        Element container = parser.getRoot().body();
        Elements links = container.select("a");
        for (Element link : links) {
            if (!isPureLink(link)) {
                String name = link.ownText();
                String elementName;
                if (!StringHelper.isEmpty(name)) {
                    elementName = name;
                } else {
                    name = link.attr("ng-click");
                    elementName = name.substring(0, name.indexOf("("));
                }

                System.out.println(format("@FindBy(linkText=\"%s\")", link.ownText()));
                System.out.println(format("@ElementName(elementName = \"%s\")", link.ownText()));
                System.out.println(format("//href=%s", link.attr("href")));
                System.out.println(format("private Button %s;", elementName));
            } else {
                System.out.println(format("@FindBy(linkText=\"%s\")", link.ownText()));
                System.out.println(format("@ElementName(elementName = \"%s\")", link.ownText()));
                System.out.println(format("//href=%s", link.attr("href")));
                System.out.println(format("private Link %s;", link.ownText()));
            }

        }
    }

    public void parseSearchResult(String className) {
        Element element = parser.getTableByClassName(className);
        if (element != null) {
            System.out.println(String.format("@FindBy(className=\"%s\")", className));
            System.out.println("private Table searchResult;");
        }
    }

    //parse editable table for editable tr
    public void parseEditableTable(String parentCssSelector, String cssSelector) {
        Elements elements = parser.getElementsByCssSelector(parentCssSelector);
        for (Element element : elements) {
            Elements trs = element.select(cssSelector);
            for (Element tr : trs) {
                String fieldName = tr.attr("field_name");
                if (!StringHelper.isNotEmptyOrNotBlankString(fieldName)) {
                    fieldName = tr.getElementsByTag("td").get(0).ownText();
                    System.out.println(String.format("@FindBy(xpath=\"//td[text()='%s']\")", fieldName));
                } else {
                    System.out.println(String.format("@FindBy(xpath=\"//tr[@field_name='%s']\")", fieldName));
                }
                System.out.println(String.format("@ElementName(elementName = \"%s\")",
                        tr.getElementsByTag("td").get(0).ownText().replace("：", "").trim()));
                System.out.println(String.format("private EditableRow %s ;", fieldName));
            }
        }
    }

    /**
     * 解析输入框元素，attrName4VariableName，html某个元素作为变量名字
     *
     * @param attrName4VariableName，html某个元素作为变量名字
     */
    public void parseInputBox(String attrName4VariableName) { //name,ng-model
        Elements inputs = parser.getRoot().select("input[type=text]");
        for (Element input : inputs) {

            String elementName = CollectionsHelper.lastElement(input.attr(attrName4VariableName).split("\\."));
            System.out.println(format("@FindBy(name=\"%s\")", input.attr("name")));
            System.out.println(String.format("@ElementName(elementName = \"%s\")", input.attr(attrName4VariableName)));
            if (input.attr("class").contains("txtDate")) {
                System.out.println("private DatePicker " + elementName + ";");
            } else {
                System.out.println("private InputBox " + elementName + ";");
            }
        }
    }

    public void parseInputBoxInTable(String attrName4VariableName, String... xpathPrefix) { //name,ng-model
        Elements inputs = parser.getRoot().select("input[type=text]");
        String xpathDisplayPrefix = getXPathPrefix(xpathPrefix);
        List<String> names = Lists.newArrayList();
        for (Element input : inputs) {

            if (names.isEmpty()) {
                output(xpathDisplayPrefix, input, "input", "InputBox");
            } else {
                if (!names.contains(input.attr("ng-model"))) {
                    output(xpathDisplayPrefix, input, "input", "InputBox");
                }
            }
            names.add(input.attr("ng-model"));
        }
    }


    /**
     * 解析指定元素下面的Button
     */
    public void parseAllButtons(String buttonCssSelector, String... attrUsedAsName) {
        Elements elements = parser.getRoot().select(buttonCssSelector);
        for (Element element : elements) {
            System.out.println(format("@FindBy(linkText=\"%s\")", element.ownText()));
            System.out.println(format("@ElementName(elementName = \"%s\")", element.ownText()));
            System.out.println(format("//href=%s", element.attr("href")));
            if (attrUsedAsName == null) attrUsedAsName = new String[]{"id"};
            String fieldName = "";
            for (String s : attrUsedAsName) {
                if (StringHelper.isNotEmptyOrNotBlankString(element.attr(s))) {
                    fieldName = element.attr(s).replace("(", "").replace(")", "").trim();
                    break;
                }
            }

            System.out.println(format("private Button %s;", fieldName));
        }
    }

    private void parseRadioAsList() {
        Elements radios = parser.getRoot().select("input[type=radio]");
        String name = CollectionsHelper.lastElement(radios.get(0).attr("name").replace("_", ".").split("\\."));
        System.out.println(format("@FindBy(name=\"%s\")", radios.get(0).attr("name")));
        System.out.println(format("@ElementName(elementName=\"%s\")", radios.get(0).attr("ng-model")));
        System.out.println(format("private List<Radio> %s;", name));
    }

    /**
     * 解析Radio
     */
    public void parseRadio() {
        Elements radios = parser.getRoot().select("input[type=radio]");
        String initRadioName = "";
        for (Element radio : radios) {
            String radioName = CollectionsHelper.lastElement(radio.attr("name").replace("_", ".").split("\\."));
            if (StringHelper.isEmpty(initRadioName) || !initRadioName.equalsIgnoreCase(radioName)) {
                initRadioName = radioName;
                System.out.println(format("@FindBy(name=\"%s\")", radio.attr("name")));
                System.out.println(format("@ElementName(elementName=\"%s\")", radio.attr("ng-model")));
                System.out.println(format("private Radio %s;", radioName));
            }
        }
    }

    /**
     * 解析指定元素下面的Button,同时指定变量使用什么属性来命名
     *
     * @param parentCssSelector
     * @param buttonCssSelector
     * @param attrUsedAsName
     */
    public void parseButtons(String parentCssSelector, String buttonCssSelector, String... attrUsedAsName) {
        Element parent = parser.getElementsByCssSelector(parentCssSelector).get(0);
        Elements elements = parent.select(buttonCssSelector);
        for (Element element : elements) {
            System.out.println(format("@FindBy(linkText=\"%s\")", element.ownText()));
            System.out.println(format("@ElementName(elementName = \"%s\")", element.ownText()));
            System.out.println(format("//href=%s", element.attr("href")));
            if (attrUsedAsName == null) attrUsedAsName = new String[]{"id"};
            String fieldName = "";
            for (String s : attrUsedAsName) {
                if (StringHelper.isNotEmptyOrNotBlankString(element.attr(s))) {
                    fieldName = element.attr(s).replace("(", "").replace(")", "").trim();
                    break;
                }
            }

            System.out.println(format("private Button %s;", fieldName));
        }
    }

    /**
     * 解析弹层
     *
     * @param popupTitle
     */
    public void parseDivOverlay(String popupTitle) {
        String divXpathPrefix = format(seleniumParentDivXpathPrefix, popupTitle);
        Element parent = parser.getElementsByCssSelector(format(parentDivCssSelector, popupTitle)).get(0);

        parseInputTypeDistinctly(parent, "checkbox", "CheckBox", popupTitle);

        Elements buttons = parent.select(".btn-small");
        for (Element button : buttons) {
            System.out.println(String.format(xpathStatement, String.format(divXpathPrefix +
                    "//a[contains(@class,'btn-small')][@ng-click='%s']", button.attr("ng-click"))));
            System.out.println(String.format(elementNameStatement, button.text()));
            String variableName = button.attr("name");
            if (StringHelper.isEmpty(button.attr("name"))) {
                variableName = button.attr("ng-click").contains("submit") ? "submit" : "cancel";
            }
            System.out.println(String.format(fieldDeclaredStatement, "Button", variableName));
        }

        Elements inputBoxes = parent.select("input[type=text]");

        for (Element inputBox : inputBoxes) {
            System.out.println(String.format(xpathStatement, divXpathPrefix + format("//input[@type='text' and @name='%s']", inputBox.attr("name"))));
            System.out.println(String.format(elementNameStatement, inputBox.attr("ng-model")));
            if (inputBox.attr("class").contains("txtDate")) {
                System.out.println(String.format(fieldDeclaredStatement, "DatePicker", inputBox.attr("ng-model").split("\\.")[inputBox.attr("ng-model").split("\\.").length - 1]));
            } else {
                System.out.println(String.format(fieldDeclaredStatement, "InputBox", inputBox.attr("ng-model").split("\\.")[inputBox.attr("ng-model").split("\\.").length - 1]));
            }

        }

        Elements textArea = parent.select("textarea");
        for (Element element : textArea) {
            System.out.println(String.format(xpathStatement, divXpathPrefix + format("//textarea[@ng-model='%s']", element.attr("ng-model"))));
            System.out.println(String.format(elementNameStatement, element.attr("ng-model")));
            String[] ngModel = element.attr("ng-model").split("\\.");
            System.out.println(String.format(fieldDeclaredStatement, "InputBox", ngModel[ngModel.length - 1]));
        }


        Elements selects = parent.select("select");
        selects.stream().filter(select -> !(select.attr("class").contains("selYear")
                || select.attr("class").contains("selMonth")))
                .forEach(select -> {
            System.out.println(format("@FindBy(xpath=\"%s//select[@%s='%s']\")", divXpathPrefix, "name", select.attr("name")));
            System.out.println(format("@ElementName(elementName=\"%s\")", select.attr("ng-model")));
            System.out.println(format("private SelectList %s", select.attr("name")));
        });

        parseInputTypeDistinctly(parent, "radio", "Radio", popupTitle);

    }

    //todo add naming strategy
    private void parseInputTypeDistinctly(Element parent, String type, String variableType, String popupTitle) {
        String divXpathPrefix = format(seleniumParentDivXpathPrefix, popupTitle);
        Elements elements = parent.select(format("input[type=%s]", type));
        String xpathTypeLocator = format("//input[@type='%s']", type);
        String tempElementName = "";
        for (Element element : elements) {

            if (!StringHelper.isNotEmptyOrNotBlankString(tempElementName)) {
                System.out.println(String.format(xpathStatement, divXpathPrefix + xpathTypeLocator));
                System.out.println(String.format(elementNameStatement, element.attr("name")));
                System.out.println(String.format(fieldDeclaredStatement, StringHelper.capitalize(variableType), element.attr("name")));
            } else {
                if (!tempElementName.equalsIgnoreCase(element.attr("name"))) {
                    System.out.println(String.format(xpathStatement, divXpathPrefix + xpathTypeLocator));
                    System.out.println(String.format(elementNameStatement, element.attr("name")));
                    System.out.println(String.format(fieldDeclaredStatement, StringHelper.capitalize(variableType), element.attr("name")));
                }
            }

            tempElementName = element.attr("name");
        }
    }

    /**
     * 解析下拉框，由于不同的select 可能绑定相同的model变量，所以需要添加命名空间
     * 使用display：none来区别，同时过滤掉时间选择中的年月选择
     * selectList一般是在某个tr标签下面
     *
     * @param parentCssSelector
     */
    public void parseSelect(String parentCssSelector, String name, String... xpathPrefix) {
        Elements e = parser.getElementsByCssSelector(parentCssSelector);
        //初始策略是增加style not none 检查，对于绑定了相同model的元素增加
        String xpathDisplayPrefix = getXPathPrefix(xpathPrefix);
        Elements selects = e.select("select");
        List<String> names = Lists.newArrayList();
        for (Element select : selects) {
            if (names.isEmpty()) {
                outputForSelect(xpathDisplayPrefix, name,select);
            } else {
                if (!names.contains(select.attr(name))) {
                    outputForSelect(xpathDisplayPrefix, name,select);
                }
            }
            names.add(select.attr(name));
        }

    }


    public void parseSelect(String parentCssSelector) {
        parseSelect(parentCssSelector, "ng-model", null);
    }


    private String getXPathPrefix(String... xpathPrefix) {
        String xpathDisplayPrefix = "";
        if (xpathPrefix != null && xpathPrefix.length > 0) {
            xpathDisplayPrefix = "//tr[not(contains(@style,'display: none'))]" + StringHelper.join(xpathPrefix, "");
        }
        return xpathDisplayPrefix;
    }

    public void parseTextArea(String attrName) {
        Elements e = parser.getElementsByCssSelector("textarea");
        for (Element element : e) {
            System.out.println(element.text());
            String model = element.attr(attrName);
            String name = CollectionsHelper.lastElement(model.split("\\."));
            //System.out.println(input.attr("ng-model"));
            System.out.println(String.format("@FindBy(xpath=\"//textarea[@%s='%s']\")", attrName, model));
            System.out.println(String.format("@ElementName(elementName = \"%s\")", element.attr("placeholder")));
            System.out.println("private InputBox " + name + ";");
        }
    }


//    private void outputForSelect(String xpathDisplayPrefix, Element select) {
//        if (!(select.attr("class").contains("selYear") || select.attr("class").contains("selMonth"))) {
//            String naming;
//            if (select.attr("ng-model") == null) {
//                System.out.println(format("@FindBy(xpath=\"%s//select[@%s='%s']\")", xpathDisplayPrefix, "name", select.attr("name")));
//                System.out.println(format("@ElementName(elementName=\"%s\")", select.attr("name")));
//                naming = CollectionsHelper.lastElement(select.attr("name").replace("_", ".").split("\\."));
//            } else {
//                System.out.println(format("@FindBy(xpath=\"%s//select[@%s='%s']\")", xpathDisplayPrefix, "ng-model", select.attr("ng-model")));
//                System.out.println(format("@ElementName(elementName=\"%s\")", select.attr("ng-model")));
//                naming = CollectionsHelper.lastElement(select.attr("ng-model").replace("_", ".").split("\\."));
//            }
//            System.out.println(format("private SelectList %s;", naming));
//        }
//    }


    private void outputForSelect(String xpathDisplayPrefix, String attrName, Element select) {
        if (!(select.attr("class").contains("selYear") || select.attr("class").contains("selMonth"))) {
            String naming;
            if (select.attr(attrName) == null) {
                System.out.println(format("@FindBy(xpath=\"%s//select[@%s='%s']\")", xpathDisplayPrefix, attrName, select.attr(attrName)));
                System.out.println(format("@ElementName(elementName=\"%s\")", select.attr("name")));
                naming = CollectionsHelper.lastElement(select.attr(attrName).replace("_", ".").split("\\."));
            } else {
                System.out.println(format("@FindBy(xpath=\"%s//select[@%s='%s']\")", xpathDisplayPrefix, attrName, select.attr(attrName)));
                System.out.println(format("@ElementName(elementName=\"%s\")", select.attr(attrName)));
                naming = CollectionsHelper.lastElement(select.attr(attrName).replace("_", ".").split("\\."));
            }
            System.out.println(format("private SelectList %s;", naming));
        }
    }

    private void output(String xpathDisplayPrefix, Element element, String xpathType, String elementType) {
        String naming;
        if (StringHelper.isNotEmptyOrNotBlankString(element.attr("name"))) {
            naming = CollectionsHelper.lastElement(element.attr("name").replace("_", ".").split("\\."));
        } else {
            naming = CollectionsHelper.lastElement(element.attr("ng-model").replace("_", ".").split("\\."));
        }

        if (StringHelper.isNotEmptyOrNotBlankString(element.attr("name"))) {
            System.out.println(format("@FindBy(xpath=\"%s//%s[@%s='%s']\")",
                    xpathDisplayPrefix, xpathType, "name", element.attr("name")));
        } else {
            System.out.println(format("@FindBy(xpath=\"%s//%s[@%s='%s']\")",
                    xpathDisplayPrefix, xpathType, "ng-model", element.attr("ng-model")));
        }

        System.out.println(format("@ElementName(elementName=\"%s\")", element.attr("ng-model")));
        if (element.attr("class").contains("txtDate")) {
            System.out.println("private DatePicker " + naming + ";");
        } else {
            System.out.println(format("private %s %s;", elementType, naming));
        }

    }


    private void parseInputBoxes(Elements container, String attrName) {
        Elements inputs = container.select("input[type=text]");
        for (Element input : inputs) {
            System.out.println(input.text());
            String model = input.attr(attrName);
            String name = CollectionsHelper.lastElement(model.split("\\."));
            //System.out.println(input.attr("ng-model"));
            System.out.println(String.format("@FindBy(xpath=\"//input[%s=%s]\")", attrName, model));
            System.out.println(String.format("@ElementName(elementName = \"%s\")", input.attr("placeholder")));
            System.out.println("private InputBox " + name + ";");
        }
    }

}
