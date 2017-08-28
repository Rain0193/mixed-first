package io.ift.automation.testscaffold.codegenerator.webui;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by patrick on 15/6/1.
 * a Class describe Web Page flow, it contains:
 * 1. page name
 * 2. web element sequence
 * 3.
 * @version $Id$
 */

@Deprecated
public class WebPageFlowDescription {

    private List<WebTestAtomicAction> actions = Lists.newArrayList();


    public List<WebTestAtomicAction> getActions() {
        return actions;
    }

    public void setActions(List<WebTestAtomicAction> actions) {
        this.actions = actions;
    }
}
