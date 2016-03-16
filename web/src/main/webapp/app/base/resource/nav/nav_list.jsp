<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en" >
<head >
    <title >仪表盘配置列表</title >
    <meta content="text/html" charset="utf-8" >
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8" />
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/vendor/bootstrap-v3.0/css/bootstrap.min.css" >
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/style/standard/css/eccrm-common-new.css" >
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/vendor/angular-motion-v0.3.2/angular-motion.css" >
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/vendor/zTree/css/ztree.css" >
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/jquery-all.js" ></script >
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-all.js" ></script >
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-strap-all.js" ></script >
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-ztree-all.js" ></script >
    <script type="text/javascript" >
        window.angular.contextPathURL = "<%=contextPath%>";
    </script >
</head >
<body >
<div class="main" ng-app="eccrm.base.nav.list" ng-controller="NavListCtrl" >
    <div class="list-result" >
        <div class="block" >
            <div class="block-header" >
            <span class="header-text" >
                <span >仪表盘设置</span >
            </span >
            <span class="header-button" >
                <a class="btn btn-min" ng-click="add();" >新建 </a >
                <a class="btn btn-min" ng-click="remove();" >删除 </a >
                <a class="btn btn-min" ng-click="preview();" style="display: none;" >预览 </a >
                <a class="btn btn-min" ng-click="publish();" style="display: none;" >发布 </a >
            </span >
            </div >
            <div class="block-content" >
                <div class="table-responsive panel panel-table" >
                    <table class="table table-striped table-hover" >
                        <thead class="table-header" >
                        <tr >
                            <td >导航名</td >
                            <td >标签名</td >
                            <td class="length-min" >显示顺序</td >
                            <td >显示样式</td >
                            <td >所属功能</td >
                            <td >上级导航</td >
                            <td >状态</td >
                            <td >操作</td >
                        </tr >
                        </thead >
                        <tbody class="table-body" >
                        <tr ng-show="!beans || !beans.total" >
                            <td colspan="8" class="text-center" >无导航菜单！</td >
                        </tr >
                        <tr ng-repeat="foo in beans.data" ng-cloak >
                            <td title="点击查询明细！" style="cursor: pointer;" >
                                <a ng-click="view(foo.id);" ng-bind-template="{{ foo.name }}" ></a >
                            </td >
                            <td ng-bind-template="{{ foo.showName }}" ></td >
                            <td class="text-center" ng-bind-template="{{ foo.sequenceNo }}" ></td >
                            <td class="text-center" ng-bind-template="{{ foo.rows + ' * ' + foo.cols }}" ></td >
                            <td class="text-center" ng-bind-template="{{ foo.resourceName }}" ></td >
                            <td class="text-center" ng-bind-template="{{ foo.parentName }}" ></td >
                            <td ng-bind-template="{{ foo.statusName }}" ></td >
                            <td >
                                <a ng-click="modify(foo.id)" class="btn btn-tiny ph0" title="编辑" >
                                    <i class="icons edit" ></i >
                                </a >
                            </td >
                        </tr >
                        </tbody >
                    </table >
                </div >
            </div >
        </div >
    </div >
    <div class="list-pagination" eccrm-page="pager" ></div >
</div >

<script type="text/javascript" src="<%=contextPath%>/app/base/resource/nav/nav.js" ></script >
<script type="text/javascript" src="<%=contextPath%>/app/base/resource/resource.js" ></script >
<script type="text/javascript" src="<%=contextPath%>/app/base/resource/nav/nav-modal.js" ></script >
<script type="text/javascript" src="<%=contextPath%>/app/base/resource/nav/nav_list.js" ></script >
</body >

</html >