<h2 class="page-header">测试结果概要</h2>
<div class="row-fluid">
    <h3>
    <#if isFailed>
        <span class="label label-danger">Total: ${total_suite_count} test suites, ${total_test_case_count}
            test cases,${total_passed_case_count} test cases 通过,
        ${total_failed_case_count} test cases 失败，其他为相同case或者重试通过case</span>
    <#else>
        <span class="label label-success">Total: ${total_suite_count} test suites, ${total_test_case_count}
            test cases,${total_passed_case_count} test cases passed,其他为相同case或者重试通过case</span>
    </#if>

    </h3>

    <div class="table-responsive">
        <table class="table table-striped table-hover">
            <thead>
            <tr>
                <th>#</th>
                <th>测试模块</th>
                <th>测试Case数量</th>
                <th>测试结果</th>
            </tr>
            <tbody>
            <#if testSuiteMap?exists>
                <#list testSuiteMap?keys as key>
                <tr>
                    <td class="idColumn">${key_index+1}</td>
                    <td>${key}</td>
                    <#assign testsuite=testSuiteMap[key]>
                    <td>${testsuite.testCases?size}</td>
                    <td>
                        <#assign flag=testsuite.isPassedSuite>
                        <#if flag>
                            <span class="label label-success" >pass</span>
                        <#else >
                            <span class="label label-danger" >failed</span>
                        </#if>
                    </td>
                </tr>
                </#list>
            </#if>
            </tbody>
            </thead>
        </table>
    </div>

</div>