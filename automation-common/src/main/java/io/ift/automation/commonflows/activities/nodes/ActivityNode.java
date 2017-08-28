package io.ift.automation.commonflows.activities.nodes;

import io.ift.automation.commonflows.activities.context.ActivityContext;
import io.ift.automation.commonflows.models.EmployeeTestData;

import java.util.List;
import java.util.Map;

/**
 * Created by patrick on 15/8/27.
 *
 * @author patrick
 * @version $Id: $Id
 */
public interface  ActivityNode  {

    /**
     * <p>approvals.</p>
     *
     * @param urlMapping a {@link java.util.Map} object.
     * @param context a {@link ActivityContext} object.
     * @return a {@link java.util.List} object.
     */
    List<EmployeeTestData> approvals(Map<String,String> urlMapping
            , ActivityContext context);

}
