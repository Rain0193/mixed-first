package io.ift.automation.testscaffold.codegenerator.openapiparser;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.testng.annotations.Test;

public class HtmlParserTest {

    @Test
    public void testParseTable() throws Exception {
//        HtmlParser parser = HtmlParser.buildFromFile("source.txt");
//        Element table =parser.getTableByIndex(0);
////        Elements rows = table.getElementsByTag("tr");
////
////        Elements tables =parser.getTables();
////        System.out.println(table);
////        System.out.println(tables);
//
//        Elements elements = parser.getRoot().select("table>tbody>tr");
//        System.out.println(elements);
//        System.out.println(elements);
//
//        //Elements rows = table.select("tbody>tr");
//        Elements rows = table.select("tbody").get(0).children();
//
//        Elements tr = table.select("tbody>tr:has(td:containsOwn(URL变量))");
//
//
//        //System.out.println(tr);
//        //System.out.println(tr);
//
//        for (Element row : rows) {
//            Element td = row.child(0);
//            if(td.ownText().contains("请求方法")){
//                System.out.println(td.siblingElements().get(0).ownText());
//            }
//            System.out.println(td);
//            System.out.println(td);
//        }
    }

    @Test
    public void testGetAllLink() throws Exception {
//        HtmlParser parser = HtmlParser.buildFromFile("source.txt");
//        Elements links =parser.getAllLink();
//        String baseUrl = "http://open.dooioo.com";
//        for (Element link : links) {
//
//            System.out.println(baseUrl + link.attr("href"));
//        }
    }
}
