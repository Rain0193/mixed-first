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
<#include "side_bar.ftl"/>
<!--main section-->
<div class="container-fluid " role="main">
    <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
        <#include "test_module.ftl"/>
        <#include "test_datail.ftl"/>
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