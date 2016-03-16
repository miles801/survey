<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en" >
<head >
    <title >岗位员工</title >
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8" />
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/vendor/bootstrap-v3.0/css/bootstrap.min.css" >
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/style/standard/css/eccrm-common-new.css" >
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/vendor/angular-motion-v0.3.2/angular-motion.css" >
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/vendor/zTree/css/ztree.css" >

    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/jquery-all.js" ></script >
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-all.js" ></script >
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-strap-all.js" ></script >
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-ztree-all.js" ></script >
    <script type="text/javascript" src="<%=contextPath%>/app/org/orgEmp.js" ></script >
    <script type="text/javascript" src="<%=contextPath%>/app/org/org.js" ></script >
    <script type="text/javascript" src="<%=contextPath%>/app/org/emp.js" ></script >
    <script type="text/javascript" src="<%=contextPath%>/app/employee/employee-modal.js" ></script >
    <script type="text/javascript" src="<%=contextPath%>/app/org/orgPosition.js" ></script >
    <script type="text/javascript" src="<%=contextPath%>/app/org/PositionEmp.js" ></script >
    <script type="text/javascript" src="<%=contextPath%>/app/org/list/positionEmp_list.js" ></script >

    <script >
        window.angular.contextPathURL = '<%=contextPath%>';
    </script >

    <meta content="text/html" charset="utf-8" >
</head >
<body id="ng-app" >
<div class="main " id="pageTab" ng-app="eccrm.positoin.positoinEmp.list" ng-controller="PositionEmpListController" >
    <div class="row panel panel-tree" >
        <input type="hidden" id="contextPath" value="<%=request.getContextPath()%>" />
        <%-- 左侧树 --%>
        <div class="tree" style="border: 1px solid #B7C1CB;width: 350px" >
            <div class="tree-content" >
                <ul id="treeDemo" class="ztree" ></ul >
            </div >
            <div class="scroller" ></div >
        </div >
        <%-- 右侧正文内容 --%>
        <div class="content" style="padding-left: 355px" >
            <%-- 订单结果列表 --%>
            <div class="list-result" >
                <div class="block" >
                    <div class="block-header" >
                        <div class="header-text" >
                            <span >员工列表</span >
                        </div >
                        <div class="header-button" >
                            <button type="button" class="btn btn-default btn-min" ng-click="addEmp()" data-ng-disabled="!condition.positionId" >
                                选择
                            </button >
                            <button type="button" class="btn btn-default btn-min" ng-disabled="!anyone" ng-click="remove()" >
                                删除
                            </button >

                        </div >
                    </div >
                    <div class="table-responsive panel panel-table first-min" >
                        <table class="table table-striped table-hover" >
                            <thead class="table-header" >
                            <tr >
                                <td >
                                    <div select-all-checkbox checkboxes="beans.data"
                                         selected-items="items" anyone-selected="anyone" ></div >
                                </td >
                                <td >姓名</td >
                                <%--<td >工号</td >--%>
                                <td >职务</td >
                                <td >移动电话</td >
                                <td >电子邮件</td >
                                <td >RTX</td >
                                <%--<td >工作地点</td >--%>
                                <%--<td >类型</td >--%>
                                <td >状态</td >
                                <td >操作</td >
                            </tr >
                            </thead >
                            <tbody class="table-body" >
                            <tr ng-show="!beans|| beans.data.length == 0 || !beans.data" >
                                <td colspan="8" class="text-center" >没有查询到数据！</td >
                            </tr >
                            <tr ng-repeat="foo in beans.data" ng-show="beans.total != 0" >
                                <td >
                                    <input type="checkbox" style="height: 12px;" ng-model="foo.isSelected" />
                                </td >
                                <td ><span ng-bind-template="{{ foo.employeeName }}" ></span ></td >
                                <%--<td><span ng-bind-template="{{ foo.employeeCode }}"></span></td>--%>
                                <td ><span ng-bind-template="{{ foo.duty }}" ></span ></td >
                                <td ><span ng-bind-template="{{ foo.mobile }}" ></span ></td >
                                <td ><span ng-bind-template="{{ foo.email }}" ></span ></td >
                                <td ><span ng-bind-template="{{ foo.extensionNumber }}" ></span ></td >
                                <td ><span ng-bind-template="{{ foo.wAddress }}" ></span ></td >
                                <%--<td><span ng-bind-template="{{ foo.workTypeName }}"></span></td>--%>
                                <%--<td><span ng-bind-template="{{ foo.statusName }}"></span></td>--%>
                                <td >
                                    <a style="cursor:pointer" ng-click="remove(foo.id)" >
                                        <i class="icons fork" ></i >
                                    </a >
                                    <%--<a  style="cursor:pointer"  ng-click="">--%>
                                    <%--<i class="icons search" ></i >--%>
                                    <%--</a>--%>
                                </td >
                            </tr >
                            </tbody >
                        </table >
                    </div >
                </div >
            </div >
            <div eccrm-page="pager" class="list-pagination" ></div >
        </div >
    </div >
</div >
</body >
</html >