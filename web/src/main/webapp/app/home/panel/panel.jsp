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

                        <p>所属县区：{{beans.orgName}}</p>

                        <p>配对状态：{{beans.matched?'已配对':'未配对'}}</p>
                    </div>

                </td>
                <td style="text-align: right;">
                    <img ng-if="youth" src="<%=contextPath%>/app/home/panel/toggle.png" width="150" height="120"/>
                </td>
                <td style="float: right;padding-right: 15px;" id="image2">
                </td>
                <td style="width: 200px;">
                    <div ng-cloak ng-if="youth">
                        <p>姓名：{{youth.name}}</p>

                        <p>性别：{{youth.sexName}}</p>

                        <p>年龄：{{youth.age}}</p>

                        <p>电话：{{youth.mobile}}</p>

                        <p>民族：{{youth.nationName}}</p>
                    </div>
                </td>
                <td style="width: 150px;">
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
    <div class="row" id="report">
    </div>
</div>
</body>
<script type="text/javascript" src="<%=contextPath%>/app/employee/employee.js"></script>
<script type="text/javascript" src="<%=contextPath%>/app/base/user/user.js"></script>
<script type="text/javascript" src="<%=contextPath%>/app/spec/youth/youth.js"></script>
<script type="text/javascript" src="<%=contextPath%>/app/home/panel/panel.js"></script>
</html>



