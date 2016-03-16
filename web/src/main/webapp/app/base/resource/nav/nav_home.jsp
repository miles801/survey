<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en" >
<head >
    <title >菜单导航</title >
    <meta content="text/html" charset="utf-8" >
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8" />
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/vendor/bootstrap-v3.0/css/bootstrap.min.css" >
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/style/standard/css/eccrm-common-new.css" >
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/vendor/angular-motion-v0.3.2/angular-motion.css" >
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/jquery-all.js" ></script >
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-all.js" ></script >
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-strap-all.js" ></script >
    <script type="text/javascript" >
        window.angular.contextPathURL = "<%=contextPath%>";
    </script >
    <style >
        .main, .main > div {
            overflow: auto !important;
        }
    </style >
</head >
<body >
<div class="main" ng-app="eccrm.base.nav.home" ng-controller="NavHomeCtrl" style="overflow: auto;" >
    <div eccrm-box="beans" style="height: 100%;width: 100%;padding: 0;margin: 0;" ></div >
</div >

<script type="text/javascript" src="<%=contextPath%>/app/base/resource/nav/nav.js" ></script >
<script type="text/javascript" src="<%=contextPath%>/app/base/resource/nav/nav_home.js" ></script >
</body >

</html >