package io.ift.automation.testscaffold.webtest.webUI.htmlelements;

import ch.lambdaj.Lambda;
import ch.lambdaj.function.convert.Converter;
import io.ift.automation.drivers.DriverFactory;
import io.ift.automation.helpers.StringHelper;
import io.ift.automation.helpers.webdriver.WebDriverHelper;
import io.ift.automation.testscaffold.webtest.webUI.exceptions.HtmlElementsException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ch.lambdaj.Lambda.convert;
import static ch.lambdaj.Lambda.convertMap;
import static io.ift.automation.testscaffold.webtest.webUI.htmlelements.Table.ListConverter.toListsConvertingEachItem;
import static io.ift.automation.testscaffold.webtest.webUI.htmlelements.Table.MapConverter.toMapsConvertingEachValue;
import static io.ift.automation.testscaffold.webtest.webUI.htmlelements.Table.WebElementToTextConverter.toText;
import static io.ift.automation.testscaffold.webtest.webUI.htmlelements.Table.WebElementToTextConverter.toTextValues;

/**
 * 定制的Table
 */
public class Table extends HtmlElement {

    public static final String GETROWS = "getRows";
    public static final String GETHEADINGS = "getHeadings";
    public static final String GETFIRSTROWASHEADING = "getFirstRowAsHeading";
    public static final String GETHEADINGSASSTRING = "getHeadingsAsString";
    public static final String GETVALUEBYTDTITLE = "getValueByTdTitle";
    public static final String GETROWSASSTRING = "getRowsAsString";
    public static final String GETCOLUMNS = "getColumns";
    public static final String GETCOLUMNSASSTRING = "getColumnsAsString";
    public static final String GETCELLAT = "getCellAt";
    public static final String GETROWSMAPPEDTOHEADINGS = "getRowsMappedToHeadings";
    public static final String GETROWSASSTRINGMAPPEDTOHEADINGS = "getRowsAsStringMappedToHeadings";
    
    public Table(String name, WebElement element) {
        super(name, element);
    }

    public Table(WebElement element) {
        super(element);
    }

    /**
     * th或者trHead 作为头
     * @return
     */
    public List<WebElement> getHeadings() {

        List<WebElement> headers = WebDriverHelper.
                findElementsIfPresent(getWrappedElement(), By.tagName("th"));
        if(headers==null||headers.isEmpty()){
            headers=WebDriverHelper.findElementsIfPresent(getWrappedElement(),By.cssSelector(".trHead"));
        }
        if(headers==null||headers.isEmpty()){
           headers = getFirstRowAsHeading();
        }

        return headers;
    }

    /**
     * 第一行作为头
     * @return
     */
    private List<WebElement> getFirstRowAsHeading(){
        WebElement firstRow  = WebDriverHelper.
                findElements(DriverFactory.get(), getWrappedElement(), By.tagName("th")).get(0);
        return firstRow.findElements(By.tagName("td"));
    }


    /**
     * 头时转化为String
     * @return
     */
    public List<String> getHeadingsAsString() {
        List<WebElement> headers = getHeadings();
        return convert(headers, toTextValues());

    }

    /**
     * Returns 每行的WebElement list
     *
     * @return List where each item is a table row.
     */
    public List<List<WebElement>> getRows() {
        List<List<WebElement>> rows = new ArrayList<List<WebElement>>();
        List<WebElement> rowElements = WebDriverHelper.findElements(DriverFactory.get(),
                getWrappedElement(),By.xpath(".//tr"));
        rows.addAll(rowElements.stream().
                map(rowElement -> rowElement.findElements(By.xpath(".//td")))
                .collect(Collectors.toList()));
        return rows;
    }

    public String getValueByTdTitle(String tdTitle){
        List<List<WebElement>> rows = getRows();
        for (List<WebElement> row : rows) {
            if(tdTitle.equalsIgnoreCase(row.get(0).getText())){
                return row.get(1).getText();
            }
        }

        return StringHelper.EMPTY;
    }
    /**
     * Returns text values of table cell elements grouped by rows.
     *
     * @return List where each item is text values of a table row.
     */
    public List<List<String>> getRowsAsString() {
        return convert(getRows(), toListsConvertingEachItem(toTextValues()));
    }

    /**
     * Returns 每列的webElement数据
     *
     * @return List where each item is a table column.
     */
    public List<List<WebElement>> getColumns() {
        List<List<WebElement>> columns = new ArrayList<List<WebElement>>();
        List<List<WebElement>> rows = getRows();

        if (rows.isEmpty()) {
            return columns;
        }

        int columnsNumber = rows.get(0).size();
        for (int i = 0; i < columnsNumber; i++) {
            List<WebElement> column = new ArrayList<WebElement>();
            for (List<WebElement> row : rows) {
                column.add(row.get(i));
            }
            columns.add(column);
        }

        return columns;
    }

    /**
     * Returns text values of table cell elements grouped by columns.
     *
     * @return List where each item is text values of a table column.
     */
    public List<List<String>> getColumnsAsString() {
        return convert(getColumns(), toListsConvertingEachItem(toTextValues()));
    }



    /**
     * Returns table cell element at i-th row and j-th column
     * 返回单元格WebElement,i -> 行，j-> 列
     *
     * @param i Row number
     * @param j Column number
     * @return Cell element at i-th row and j-th column.
     */
    public WebElement getCellAt(int i, int j) {
        return getRows().get(i).get(j);
    }

    /**
     * Returns list of maps where keys are table headings and values are table row elements.
     */
    public List<Map<String, WebElement>> getRowsMappedToHeadings() {
        return getRowsMappedToHeadings(getHeadingsAsString());
    }

    /**
     * //todo there is some cases that there is not heading ,need to handle this case
     * Returns list of maps where keys are passed headings and values are table row elements.
     *
     * @param headings List containing strings to be used as table headings.
     */
    public List<Map<String, WebElement>> getRowsMappedToHeadings(List<String> headings) {
        List<Map<String, WebElement>> rowsMappedToHeadings = new ArrayList<Map<String, WebElement>>();
        List<List<WebElement>> rows = getRows();

        if (rows.isEmpty()) {
            return rowsMappedToHeadings;
        }

        for (List<WebElement> row : rows) {
            if (row.size() != headings.size()) {
                throw new HtmlElementsException("Headings count is not equal to number of cells in row");
            }

            Map<String, WebElement> rowToHeadingsMap = new HashMap<String, WebElement>();
            int cellNumber = 0;
            for (String heading : headings) {
                rowToHeadingsMap.put(heading, row.get(cellNumber));
                cellNumber++;
            }
            rowsMappedToHeadings.add(rowToHeadingsMap);
        }

        return rowsMappedToHeadings;
    }

    /**
     * Same as {@link #getRowsMappedToHeadings()} but retrieves text from row elements.
     */
    public List<Map<String, String>> getRowsAsStringMappedToHeadings() {
        return getRowsAsStringMappedToHeadings(getHeadingsAsString());
    }

    /**
     * Same as {@link #getRowsMappedToHeadings(java.util.List)} but retrieves text from row elements.
     */
    public List<Map<String, String>> getRowsAsStringMappedToHeadings(List<String> headings) {
        return convert(getRowsMappedToHeadings(headings), toMapsConvertingEachValue(toText()));
    }


    /* Inner utility converters */

    /**
     * Converts {@link WebElement} to text contained in it
     */
    static final class WebElementToTextConverter implements Converter<WebElement, String> {

        public static Converter<WebElement, String> toText() {
            return new WebElementToTextConverter();
        }

        public static Converter<WebElement, String> toTextValues() {
            return new WebElementToTextConverter();
        }

        private WebElementToTextConverter() {
        }

        @Override
        public String convert(WebElement element) {
            return element.getText();
        }
    }

    /**
     * Converts {@code List&lt;F&gt;} to {@code List&lt;T&gt;} by applying specified converter to each list element.
     */
    static final class ListConverter<F, T> implements Converter<List<F>, List<T>> {
        private final Converter<F, T> itemsConverter;

        public static <F, T> Converter<List<F>, List<T>> toListsConvertingEachItem(Converter<F, T> itemsConverter) {
            return new ListConverter<F, T>(itemsConverter);
        }

        private ListConverter(Converter<F, T> itemsConverter) {
            this.itemsConverter = itemsConverter;
        }

        @Override
        public List<T> convert(List<F> list) {
            return Lambda.convert(list, itemsConverter);
        }
    }

    /**
     * Converts {@code Map&lt;K, F&gt;} to {@code Map&lt;K, T&gt;} by applying specified converter to each value
     * in a map.
     */
    static final class MapConverter<K, F, T> implements Converter<Map<K, F>, Map<K, T>> {
        private final Converter<F, T> valueConverter;

        public static <F, T> Converter<Map<String, F>, Map<String, T>> toMapsConvertingEachValue(Converter<F, T> valueConverter) {
            return new MapConverter<String, F, T>(valueConverter);
        }

        private MapConverter(Converter<F, T> valueConverter) {
            this.valueConverter = valueConverter;
        }

        @Override
        public Map<K, T> convert(Map<K, F> map) {
            return convertMap(map, valueConverter);
        }
    }
}
