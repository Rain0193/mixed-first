package io.ift.automation.testscaffold.codegenerator;

import com.google.common.base.Joiner;
import com.google.common.io.Files;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Created by patrick on 15/4/1.
 *
 * @version $Id$
 */


public class HtmlParser {
    private Document root;
    private String resourcePath;
    private static final Logger logger = LogManager.getLogger(HtmlParser.class.getName());

    private HtmlParser() {
    }

    public HtmlParser parse(String htmlContent) {
        root = Jsoup.parse(htmlContent);
        return this;
    }

    /**
     * 初始化Jsoup parser
     * @param resourcePath
     * @return
     */
    public static HtmlParser buildFromFile(String resourcePath){

        HtmlParser parser = new HtmlParser();
        parser.resourcePath =resourcePath;
        String path = ClassLoader.getSystemClassLoader().getResource(parser.resourcePath).getPath();
        String input ;
        try {
            input = Joiner.on("\n").join(Files.readLines(new File(path), Charset.defaultCharset()));
        } catch (IOException e) {
            logger.error(e);
            throw new RuntimeException("there is no resource in "+resourcePath);
        }
        parser.root = Jsoup.parse(input);
        return parser;
    }

    public static HtmlParser build(String pageSource){
        HtmlParser parser = new HtmlParser();
        parser.root = Jsoup.parse(pageSource);
        return parser;
    }

    public Element getElementById(String id){
        return root.getElementById(id);
    }

    public String getTextByTagName(Element parent,String tagName){
        Elements elements = parent.getElementsByTag(tagName);
        return elements.get(0).ownText();
    }


    public Document getRoot() {
        return root;
    }

    public void setRoot(Document root) {
        this.root = root;
    }

    /**
     * table element holds the whole table,include all stuff in tag <tbody></tbody> or table
     * @param table holds the whole table
     * @param name column text value
     * @return
     */
    public @Nullable Element getTableRowByColumnText(Element table,String name){
        Elements rows = table.select("tr:has(td:matchesOwn("+name+"))");
        if(rows.size()==0) return null;

        if(rows.get(0).getElementsByTag("td").get(0).ownText().trim().equalsIgnoreCase(name)) return rows.get(0);
        return null;
    }

    /**
     * 从根节点获取table
     * @param index
     * @return
     */
    public Element getTableByIndex(int index) {
        return root.getElementsByTag("table").get(index);
    }

    public Element getTableByClassName(String className){
      //Elements elements = root.select("table."+className);
      return  root.select("table."+className).first();
    }
    /**
     * 从给定容器获取table
     * @param container
     * @param index
     * @return
     */
    public Element getTableInContainer(Element container,int index){
        return container.getElementsByTag("table").get(index);
    }

    /**
     * 获取页面所有的table
     * @return
     */
    public Elements getAllTables() {

        return root.getElementsByTag("table");
    }

    /**
     * 获取所有又href的link
     * @return
     */
    public Elements getAllLink(){
        return root.select("a[href]");
    }

    public Elements getElementsByClassName(String className) {
        return root.getElementsByClass(className);
    }

    public Elements getElementsByCssSelector(String selector){
        return root.select(selector);
    }
}
