<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-COMPATIBLE" content="IE=edge"/>
    <meta name="viewport" content="width=device-width initial-scale = 1"/>
    <meta name="description" content=""/>
    <meta name="author" content=""/>
    <title>测试结果报告</title>
    <link rel="stylesheet" href="css/bootstrap.min.css"/>
    <link rel="stylesheet" href="css/bootstrap-theme.css"/>
    <link rel="stylesheet" href="css/dashboard.css"/>

</head>
<body role="document">
<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapse"
                    data-toggle="collapse" data-target="#navbar-data-collapse">
                <span class="sr-only">切换导航</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">测试结果</a>
        </div>

        <div class="collapse navbar-collapse" id="navbar-data-collapse">
            <ul class="nav navbar-nav navbar-right">
                <li class="active"><a href="#">测试套件</a></li>
                <li><a href="#">测试用例</a></li>
                <li><a href="#">通过用例</a></li>
                <li><a href="#">失败用例</a></li>
            </ul>
            <div>
                <form class="navbar-form navbar-right" role="search">
                    <div class="form-group">
                        <input type="text" class="form-control" placeholder="search..."/>
                    </div>
                </form>
            </div>
        </div>
    </div>
</nav>
<!--side bar-->
<div class="contain-fluid">
    <div class="row">
        <div class="sidebar col-sm-3 col-md-2">
            <ul class="nav nav-sidebar">
                <li class="active"><a href="#">测试结果概要</a></li>
            <#if testSuiteMap?exists>
                <#list testSuiteMap?keys as key>
                    <li><a href="#">${key}</a></li>
                </#list>
            </#if>
            </ul>
        </div>
    </div>
</div>

<!--main section-->
<div class="container-fluid " role="main">
    <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
        <h2 class="page-header">测试结果概要</h2>



        <h3 class="sub-header">测试详细：</h3>

        <div class="row-fluid">
            <div class="table-responsive">
                <table class="table table-striped table-hover">
                    <thead>
                    <tr>
                        <th>#</th>
                        <th>TestSuiteName</th>
                        <th>TestName</th>
                        <th>TestClass</th>
                        <th>TestMethod</th>
                        <th>TestResult</th>
                        <th>截图</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>1</td>
                        <td>TestSuiteName</td>
                        <td>TestName</td>
                        <td>TestClass</td>
                        <td>TestMethod</td>
                        <td>
                            <span class="label label-success">pass</span>
                        </td>
                        <td>Screenshote</td>
                    </tr>
                    <tr>
                        <td>1</td>
                        <td>TestSuiteName</td>
                        <td>TestName</td>
                        <td>TestClass</td>
                        <td>TestMethod</td>
                        <td>
                            <span class="label label-danger">failed</span>
                        </td>
                        <td>
                            <a onclick="hideOrShowScreenshot('#screenshot1')"
                               class="btn btn-primary btn-xs">Screenshot</a>
                        </td>
                        <div id="screenshot1" class="carousel slide hide" data-ride="carousel">
                            <ol class="carousel-indicators">
                                <li data-target="#carousel-example-generic" data-slide-to="0" class=""></li>
                                <li data-target="#carousel-example-generic" data-slide-to="1" class="active"></li>
                                <li data-target="#carousel-example-generic" data-slide-to="2" class=""></li>
                            </ol>
                            <div class="carousel-inner" role="listbox">
                                <div class="item">
                                    <img alt="First slide [1140x500]" src="images/screenshot-1426553166647.jpg"
                                         data-holder-rendered="true">
                                </div>
                                <div class="item active">
                                    <img alt="Second slide [1140x500]" src="images/screenshot-1426553167499.jpg"
                                         data-holder-rendered="true">
                                </div>
                                <div class="item">
                                    <img alt="Third slide [1140x500]" src="images/screenshot-1426553167499.jpg"
                                         data-holder-rendered="true">
                                </div>
                            </div>
                            <a class="left carousel-control" href="#carousel-example-generic" role="button"
                               data-slide="prev">
                                <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
                                <span class="sr-only">Previous</span>
                            </a>
                            <a class="right carousel-control" href="#carousel-example-generic" role="button"
                               data-slide="next">
                                <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
                                <span class="sr-only">Next</span>
                            </a>
                        </div>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>

    </div>
</div>
<!-- Placed at the end of the document so the pages load faster -->
<script src="js/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script>
    function hideOrShowScreenshot(locator) {
        if ($(locator).hasClass('hide')) {
            $(locator).removeClass('hide');
        } else {
            $(locator).addClass('hide');
        }
    }
</script>
</body>
</html>