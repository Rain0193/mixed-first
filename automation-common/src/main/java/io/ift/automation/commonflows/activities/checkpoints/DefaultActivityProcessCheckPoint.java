package io.ift.automation.commonflows.activities.checkpoints;

import io.ift.automation.commonflows.activities.ActivityProviders;
import io.ift.automation.data.TestData;
import io.ift.automation.helpers.database.SpringJdbcTemplateUtils;
import io.ift.automation.testscaffold.CheckPoint;
import io.ift.automation.testscaffold.Where;
import io.ift.automation.testscaffold.webtest.webUI.htmlelements.BasePage;

import java.util.List;
import java.util.Map;

/**
 * Created by patrick on 15/9/1.
 */
@Deprecated
public class DefaultActivityProcessCheckPoint implements CheckPoint {

    private ActivityProviders context;
    private TestData testData;
    private Class<? extends BasePage> pageClass;


    public DefaultActivityProcessCheckPoint(ActivityProviders context,TestData testData,
                                            Class<? extends BasePage> pageClass) {
        this.context = context;
        this.pageClass = pageClass;
        this.testData=testData;
    }

    @Override
    public void verify() {
//        //quick return
//        if (!context.getContext().getActivityName().equals(ActivityNodeMode.APPROVE) &&
//                !context.getContext().equals(ActivityNodeMode.REJECT)) return;

        String keyId = testData.get(context.getContext().getTaskKeyName());
        String getHistorySql = String.format("select top(1) * from %s where %s =%s order by operateTime desc"
                , context.getContext().getTaskHistoryTableName()
                , keyId, testData.get(keyId));
       List<Map<String,Object>> historyResult = SpringJdbcTemplateUtils.DEFAULT().getAllRawResult(getHistorySql);
       sa.assertTrue(historyResult.size()==1,"检查审批纪录是否存在");
       sa.assertEquals(context.getContext().getProcessedEmployee().getUserCode(),historyResult.get(0).get("operator").toString()
       ,"检查审批人是否审批");

    }



    @Override
    public Where where() {
        return new Where(pageClass.getName()+"."+"submit");
    }
}
