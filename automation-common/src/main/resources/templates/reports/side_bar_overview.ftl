<div class="contain-fluid">
    <div class="row">
        <div class="sidebar col-sm-3 col-md-2">
            <ul class="nav nav-sidebar">
                <li class="active"><a href="#">测试结果概要</a></li>
                <#list testSuiteMap?keys as key>
                    <li><a href="#">${key}</a></li>
                </#list>
            </ul>
        </div>
    </div>
</div>


<h3>
<#if isFailed>
    <span class="label label-danger">Total: ${total_suite_count} test suites, ${total_test_case_count} test cases,${total_passed_case_count} test cases passed
    ${total_failed_case_count} test cases failed</span>
<#else>
    <span class="label label-success">Total: ${total_suite_count} test suites, ${total_test_case_count} test cases,${total_passed_case_count} test cases passed</span>
</#if>

</h3>