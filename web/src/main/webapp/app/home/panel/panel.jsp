<%@ page import="com.ycrl.core.context.SecurityContext" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <%
        String contextPath = request.getContextPath();
        String userId = SecurityContext.getUserId();
    %>
    <title>仪表盘</title>
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/vendor/bootstrap-v3.0/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/style/standard/css/eccrm-common-new.css">
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/vendor/angular-motion-v0.3.2/angular-motion.css">

    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/jquery-all.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-all.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-strap-all.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/vendor/jquery-v1.8.3/jquery.md5.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/vendor/echart/echarts.min.js"></script>

    <script type="text/javascript">
        window.angular.contextPathURL = '<%=contextPath%>';
    </script>
    <style type="text/css">
        body {
            margin: 0;
            padding: 0;
            font-size: 14px;
        }

        .mybtn .btn-blue {
            height: 35px;
            width: 100px;
            line-height: 32px;
            font-size: 14px;
            font-weight: 500;
            font-family: "微软雅黑", "宋体"
        }
    </style>
</head>

<body id="ng-app">
<div class="main" ng-app="eccrm.panel.base.list" ng-controller="BaseCtrl" style="overflow: auto;">
    <input type="hidden" id="userId" value="<%=userId%>"/>

    <div class="row" style="height: 200px;padding: 5px 20px;">
        <table style="height: 100%;width: 100%;">
            <tbody>
            <tr>
                <td style="width: 180px;" id="imageId">
                </td>
                <td style="width: 200px;">
                    <div ng-cloak>
                        <p>姓名：{{beans.employeeName}}</p>

                        <p>性别：{{beans.genderName}}</p>

                        <p>职务：{{beans.duty}}</p>

                        <p>民族：{{beans.nationName}}</p>

                        <p>所属部门：{{beans.orgName}}</p>

                    </div>

                </td>
                <td>
                    <div class="row mybtn">
                        <a type="button" class="btn btn-blue"
                           href="<%=contextPath%>/base/employee/modify/<%=userId%>"
                           style="width: 110px;">
                            <span class="glyphicons plus"></span> 完善个人信息
                        </a>
                    </div>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
    <%-- 可注册试卷 --%>
    <div class="row" style="width: 80%;margin: 0 auto;">
        <h3>可注册试卷 <i class="icons refresh cp" ng-click="queryCanRegisterSurvey()" title="刷新"></i></h3>
        <div class="table-responsive panel panel-table">
            <table class="table table-striped table-hover text-center">
                <thead class="table-header">
                <tr>
                    <td style="border-left:1px solid #7acac1;">试卷名称</td>
                    <td>总题数</td>
                    <td>总分数</td>
                    <td>单选</td>
                    <td>多选</td>
                    <td>判断</td>
                    <td>填空</td>
                    <td>简答</td>
                    <td>生效时间</td>
                    <td>截止时间</td>
                    <td>操作</td>
                </tr>
                </thead>
                <tbody class="table-body">
                <tr ng-show="!canRegisterData.length">
                    <td colspan="11" class="text-center">没有可参加的考试！</td>
                </tr>

                <tr bindonce ng-repeat="foo in canRegisterData" ng-cloak>
                    <td title="点击查询明细！" style="cursor: pointer;">
                        <a ng-click="view(foo.id)" bo-text=" foo.name"></a>
                    </td>
                    <td bo-text="foo.totalSubjects"></td>
                    <td bo-text="foo.totalScore"></td>
                    <td bo-text="foo.xzCounts"></td>
                    <td bo-text="foo.dxCounts"></td>
                    <td bo-text="foo.pdCounts"></td>
                    <td bo-text="foo.tkCounts"></td>
                    <td bo-text="foo.jdCounts"></td>
                    <td bo-text="foo.startTime | eccrmDatetime"></td>
                    <td bo-text="foo.endTime | eccrmDatetime"></td>
                    <td>
                        <a class="btn btn-tiny" title="注册" ng-click="register(foo.id)">
                            <span class="icons card"></span>
                        </a>
                    </td>
                </tr>

                </tbody>
            </table>
        </div>
    </div>
    <%-- 可答题试卷 --%>
    <div class="row" style="width: 80%;margin: 15px auto;">
        <h3>已注册试卷 <i class="icons refresh cp" ng-click="queryUnfinishSurvey()" title="刷新"></i></h3>
        <div class="table-responsive panel panel-table">
            <table class="table table-striped table-hover text-center">
                <thead class="table-header">
                <tr>
                    <td style="border-left:1px solid #7acac1;">试卷名称</td>
                    <td>截止时间</td>
                    <td>注册时间</td>
                    <td>操作</td>
                </tr>
                </thead>
                <tbody class="table-body">
                <tr ng-show="!registerData.length">
                    <td colspan="4" class="text-center">没有可参加的考试！</td>
                </tr>

                <tr bindonce ng-repeat="foo in registerData" ng-cloak>
                    <td title="点击查询明细！" style="cursor: pointer;">
                        <a ng-click="view(foo.id)" bo-text=" foo.surveyName"></a>
                    </td>
                    <td bo-text="foo.effectDate | eccrmDatetime"></td>
                    <td bo-text="foo.registerDate | eccrmDatetime"></td>
                    <td>
                        <a class="btn btn-tiny" title="答题" ng-click="answer(foo.id,foo.surveyId)">
                            <span class="icons handle"></span>
                        </a>
                    </td>
                </tr>

                </tbody>
            </table>
        </div>
    </div>
</div>
</body>
<script type="text/javascript" src="<%=contextPath%>/app/employee/employee.js"></script>
<script type="text/javascript" src="<%=contextPath%>/app/base/user/user.js"></script>
<script type="text/javascript" src="<%=contextPath%>/app/knowledge/survey/survey.js"></script>
<script type="text/javascript" src="<%=contextPath%>/app/home/panel/panel.js"></script>
</html>



