<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>用户注册</title>
    <meta content="text/html" charset="utf-8">
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/vendor/bootstrap-v3.0/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/style/standard/css/eccrm-common-new.css">
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/jquery-all.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-all.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-strap-all.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/vendor/jquery-v1.8.3/jquery.md5.js"></script>
    <script type="text/javascript">
        window.angular.contextPathURL = "<%=contextPath%>";
    </script>
    <style>
        .row {
            margin-top: 10px;
        }
    </style>
</head>
<body>
<div class="main" ng-app="eccrm.base.user.register" ng-controller="Ctrl" style="margin-top:20px;">
    <form name="form" class="form-horizontal" role="form">
        <div class="row">
            <div class="form-label col-3-half">
                <label>用户名:</label>
            </div>
            <input class="col-7" type="text" ng-model="user.username"
                   validate validate-required validate-max-length="20"/>
        </div>
        <div class="row">
            <div class="form-label col-3-half">
                <label>姓名:</label>
            </div>
            <input class="col-7" type="text" ng-model="user.employeeName"
                   validate validate-required validate-max-length="20"/>
        </div>
        <div class="row">
            <div class="form-label col-3-half">
                <label validate-error="form.password">密码:</label>
            </div>
            <input class="col-7" type="password" ng-model="user.password" name="password"
                   validate validate-required validate-max-length="20"/>
        </div>
        <div class="row">
            <div class="form-label col-3-half">
                <label>确认密码:</label>
            </div>
            <input class="col-7" type="password" ng-model="user.password2"
                   validate validate-required validate-max-length="20"/>
        </div>
        <div class="button-row" style="margin-top: 20px;">
            <button type="button" class="btn btn-default btn-primary"
                    ng-disabled="form.$invalid" ng-click="ok();">
                注册
            </button>
        </div>
    </form>
</div>
</body>
<script type="text/javascript" src="<%=contextPath%>/app/base/user/user.js"></script>
<script type="text/javascript" src="<%=contextPath%>/app/base/user/user_register.js"></script>
</html>