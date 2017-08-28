<h3 class="sub-header">测试详细：</h3>

<#if testSuiteMap?exists>
    <#list testSuiteMap?keys as key>

    <div class="row-fluid">

        <h4>
        ${key}测试详细：
            <#assign flag=testSuiteMap[key].isPassedSuite>
            <#assign keyId=key?replace(" ","_")>
            <#if flag>
                <span class="label label-success" onclick="hideOrShowScreenshot('#${keyId}')">收起或者展开详细内容</span>
            <#else>
                <span class="label label-danger" onclick="hideOrShowScreenshot('#${keyId}')">收起或者展开详细内容</span>
            </#if>

        </h4>

        <div id="${keyId}" class="table-responsive">
            <table class="table table-striped table-hover">
                <thead>
                <tr>
                    <th>#</th>
                    <th>TestName</th>
                    <th>TestClass</th>
                    <th>TestMethod</th>
                <#--<th>TestMethodParameters</th>-->
                    <th>TestResult</th>
                    <th>截图</th>
                </tr>
                </thead>
                <tbody>
                    <#assign testcases=testSuiteMap[key].testCases>
                    <#list testcases as testcase>
                    <tr>
                        <td class="idColumn">${testcase_index+1}</td>
                        <td>${testcase.testDescription}</td>
                        <td>${testcase.testClassName}</td>
                        <td>${testcase.testMethodName}</td>
                    <#--<#if testcase.parameters?exists&&testcase.parameters??>-->
                    <#--<td>${testcase.parameters}</td>-->
                    <#--<#else>-->
                    <#--<td>没有输入参数</td>-->
                    <#--</#if>-->

                        <td>
                            <#if testcase.status==2>
                                <span class="label label-danger">failed</span>
                            <#elseif testcase.status==1>
                                <span class="label label-success">passed</span>
                            <#else>
                                <span class="label label-warning">passed</span>
                            </#if>
                        </td>
                        <#if testcase.status==2>
                            <#assign screenshotId="${keyId}_${testcase_index+1}_${testcase.testMethodName}"/>
                            <td>
                                <a onclick="hideOrShowScreenshot('#${screenshotId}')"
                                   class="btn btn-danger btn-xs">出错信息和截图</a>
                            </td>
                            <div id="${screenshotId}" class="carousel slide hide" data-ride="carousel"
                                 ondblclick="hideOrShowScreenshot('#${screenshotId}')">
                                <ol class="carousel-indicators">
                                    <li data-target="#${screenshotId}" data-slide-to="0" class=""></li>
                                    <li data-target="#${screenshotId}" data-slide-to="1" class="active"></li>
                                    <li data-target="#${screenshotId}" data-slide-to="2" class=""></li>
                                </ol>

                                <div class="carousel-inner" role="listbox">
                                    <#assign screenShotPathList=testcase.screenShots />
                                    <#list screenShotPathList as path>
                                        <div class="item">
                                            <img alert="${path_index+1} slide[1140x500]]" src="${path}"
                                                 data-holder-rendered="true">
                                        </div>
                                    </#list>
                                    <#assign errorMessage=testcase.errorMessage>
                                    <#if errorMessage?exists&&errorMessage??>
                                        <div class="item active">
                                            <div class="alert alert-danger">
                                                <p class="">Error Message: ${errorMessage}</p>
                                            </div>
                                        </div>
                                    </#if>
                                </div>
                                <a class="left carousel-control" href="#${screenshotId}" role="button"
                                   data-slide="prev">
                                    <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
                                    <span class="sr-only">Previous</span>
                                </a>
                                <a class="right carousel-control" href="#${screenshotId}" role="button"
                                   data-slide="next">
                                    <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
                                    <span class="sr-only">Next</span>
                                </a>
                            </div>
                        <#else>
                            <td>
                                <a class="btn btn-success btn-xs">通过无截图</a>
                            </td>
                        </#if>
                    </tr>
                    </#list>
                </tbody>
            </table>
        </div>
    </div>

    </#list>

</#if>
