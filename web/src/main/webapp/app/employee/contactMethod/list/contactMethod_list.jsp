<%--
Created by IntelliJ IDEA.
User: chenl
Date: 2014-10-21 11:44:21
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<%
    String contextPath = request.getContextPath();
%>
<head>
    <base href="<%=request.getContextPath()%>/">
    <title>联络方式</title>
    <link rel="stylesheet" type="text/css"
          href="<%=contextPath%>/vendor/bootstrap-v3.0/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/style/standard/css/eccrm-common-new.css">
    <link rel="stylesheet" href="<%=contextPath%>/vendor/angular-motion-v0.3.2/angular-motion.min.css"/>

</head>
<body>
<div class="main " ng-app="eccrm.base.contactMethod.list"
     ng-controller="ContactMethodListController"
        >
    <div eccrm-alert="alert"></div>
    <input type="hidden" id="contextPath" value="<%=request.getContextPath()%>"/>
    <div class="list-result ">
        <div class="block">
            <div class="block-header">
                <div class="header-text">
                    <span class="glyphicons list"></span>
                    <span>联络方式</span>
                </div>
            </div>
            <div class="block-content">
                <div class="content-wrap">
                    <div class="table-responsive panel panel-table  first-min">
                        <table class="table table-striped table-hover">
                            <thead class="table-header">
                            <tr>
                                <td>
                                    <input type="checkbox" style="height: 12px;" ng-model="checkAll"/>
                                </td>
                                <td>类型</td>
                                <td>类别</td>
                                <td>号码/地址</td>
                                <td>默认</td>
                                <td>状态</td>
                            </tr>
                            </thead>
                            <tbody class="table-body">
                            <tr ng-show="!contactMethods || !contactMethods.total || contactMethods.data.length==0">
                                <td colspan="6" class="text-center">没有查询到数据！</td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="list-pagination" eccrm-page="pager"></div>
</div>
</div >

</body>
<script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/jquery-all.js" ></script >
<script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-all.js" ></script >
<script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-strap-all.js"></script>
<script type="text/javascript" src="<%=contextPath%>/app/employee/contactMethod/contactMethod.js"></script>
<script type="text/javascript"
        src="<%=contextPath%>/app/employee/contactMethod/list/contactMethod_list.js"></script>

</html>