package io.ift.automation.testscaffold.codegenerator.webui;

import io.ift.automation.helpers.mockmodel.MockPage;
import org.testng.Assert;
import org.testng.annotations.Test;


/**
 * Created by patrick on 15/6/2.
 *
 * @version $Id: WebFlowMakerTest.java 2440 2016-01-27 02:41:49Z wuke $
 */
public class WebFlowMakerTest {

    @Test
    public void testLoad() throws Exception {
        WebFlowMaker r = WebFlowMaker.build().load("webflow.xls");
//        Assert.assertTrue(r.getFlowElementsMapping().size() == 2);
        Assert.assertTrue(r.getPageElementsMapping().size()==1);
        Assert.assertTrue(r.getFlowElementsMapping().get("新增房源").size()==19);
        Assert.assertTrue(r.getPageElementsMapping().get("AddFangYuanSecondStep").size()==20);
    }
    @Test
    public void testReadClass(){
        System.out.println(WebFlowMaker.build().getWebElementDescription(MockPage.class));
    }

    @Test
    public void testWritePageObjectToExcel(){
        WebFlowMaker.build().writePagObjectToExcel("mockpage.xls",MockPage.class);
    }
}
