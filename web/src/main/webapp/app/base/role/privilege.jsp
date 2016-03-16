<%--
Created by IntelliJ IDEA.
User: shenbb
Date: 2014-02-01 19:26:53
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en" >
<head >
    <base href="<%=request.getContextPath()%>/" >
    <title >授权设置</title >
    <link rel="stylesheet" type="text/css" href="vendor/bootstrap-v3.0/css/bootstrap.min.css" >
    <link rel="stylesheet" type="text/css" href="style/standard/css/eccrm-common-new.css" >
    <link rel="stylesheet" type="text/css" href="vendor/angular-motion-v0.3.2/angular-motion.css" >
    <link rel="stylesheet" type="text/css" href="vendor/zTree/css/ztree.css" >

</head >
<body >
<div class="main" ng-app="eccrm.base.grant" ng-controller="PrivilegeCtrl" >
    <div eccrm-route="grantRoutes" style="height: 100%;" ></div >
</div >
</body >
<script type="text/javascript" src="vendor/jquery-v1.8.3/jquery.min.js" ></script >
<script type="text/javascript" src="static/ycrl/javascript/angular-all.js" ></script >
<script type="text/javascript" src="static/ycrl/javascript/angular-strap-all.js" ></script >
<script type="text/javascript" src="vendor/zTree/js/jquery.ztree.core-3.5.min.js" ></script >
<script type="text/javascript" src="app/base/usergroup/usergroup.js" ></script >
<script type="text/javascript" src="app/base/role/role.js" ></script >
<script type="text/javascript" src="app/base/role/role-modal.js" ></script >
<script type="text/javascript" src="app/base/user/user.js" ></script >
<script type="text/javascript" src="app/base/role/privilege.js" ></script >

</html >