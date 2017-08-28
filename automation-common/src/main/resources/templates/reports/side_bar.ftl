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