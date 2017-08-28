package io.ift.automation.helpers.refectiontest;

import com.google.common.base.MoreObjects;

/**
 * Created by patrick on 15/3/12.
 *
 * @version $Id$
 */


public class PageDescription {
    private String keyword;
    private String submit;

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("keyword", keyword)
                .add("submit", submit)
                .toString();
    }
}
