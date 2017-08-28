package io.ift.automation.testscaffold.webtest.webUI.testpages;

import io.ift.automation.testscaffold.webtest.webUI.annotations.webpage.PageSectionObject;
import io.ift.automation.testscaffold.webtest.webUI.htmlelements.Link;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

/**
 * Created by patrick on 15/3/23.
 *
 * @version $Id$
 */

@PageSectionObject
public class BaiDuHeaderSection {
    @FindBy(how= How.LINK_TEXT,linkText = "新闻")
    private Link news;

    @FindBy(how= How.LINK_TEXT,linkText = "hao123")
    private Link hao123;

    public Link getNews() {
        return news;
    }

    public void setNews(Link news) {
        this.news = news;
    }

    public Link getHao123() {
        return hao123;
    }

    public void setHao123(Link hao123) {
        this.hao123 = hao123;
    }
}
