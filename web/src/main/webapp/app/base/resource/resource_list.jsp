<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en" >
<head >
    <title >资源列表</title >
    <meta content="text/html" charset="utf-8" >
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8" />
    <!--[if IE]>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" >
    <![endif]-->
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/vendor/bootstrap-v3.0/css/bootstrap.min.css" >
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/style/standard/css/eccrm-common-new.css" >
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/vendor/angular-motion-v0.3.2/angular-motion.css" >
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/vendor/zTree/css/ztree.css" >
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/jquery-all.js" ></script >
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-all.js" ></script >
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-strap-all.js" ></script >
    <script type="text/javascript" >
        window.angular.contextPathURL = "<%=contextPath%>";
    </script >
</head >
<body >
<div class="main" ng-app="eccrm.base.resource.list" ng-controller="ResourceListCtrl" >
    <div class="row panel panel-tree" >
        <div class="tree"  style="width: 200px;" >
            <ul id="treeDemo" class="ztree" ></ul >
        </div >
        <div class="content"  style="padding-left: 205px;" >
            <div class="list-result" >
                <div class="block" >
                    <div class="block-header" >
                        <span class="header-text" >
                            <span >资源</span >
                        </span >
                        <span class="header-button" >
                            <a class="btn btn-green btn-min" ng-click="add();" >新建 </a >
                        </span >
                    </div >
                    <div class="block-content" >
                        <div class="table-responsive panel panel-table" >
                            <table class="table table-striped table-hover" >
                                <thead class="table-header" >
                                <tr >
                                    <td >名称</td >
                                    <td >编号</td >
                                    <td >类型</td >
                                    <td >显示顺序</td >
                                    <td >URL</td >
                                    <td >上级功能</td >
                                    <td >所属模块</td >
                                    <td >是否显示</td >
                                    <td >是否授权</td >
                                    <td >状态</td >
                                    <td >操作</td >
                                </tr >
                                </thead >
                                <tbody class="table-body" ng-cloak >
                                <tr ng-show="menus.total == 0" >
                                    <td colspan="11" class="text-center" >没有符合条件的记录！</td >
                                </tr >
                                <tr bindonce ng-repeat="menu in menus.data" >
                                    <td title="点击查询明细！" style="cursor: pointer;" >
                                        <a ng-click="view(menu.id);" bo-text="menu.name" ></a >
                                    </td >
                                    <td bo-text="menu.code" ></td >
                                    <td bo-text="menu.typeName" ></td >
                                    <td bo-text="menu.sequenceNo" ></td >
                                    <td bo-text="menu.url" ></td >
                                    <td bo-text="menu.parentName" ></td >
                                    <td bo-text="menu.moduleName" ></td >
                                    <td bo-text="menu.show ? '显示' : '不显示'" ></td >
                                    <td bo-text="menu.authorization? '授权':'不授权'" ></td >
                                    <td bo-text="menu.statusName" ></td >
                                    <td >
                                        <a ng-click="modify(menu.id)" class="btn btn-tiny ph0" title="编辑" >
                                            <i class="icons edit" ></i >
                                        </a >
                                        <a ng-click="remove(menu.id,$index);" class="btn btn-tiny ph0" title="删除！" >
                                            <i class="icons fork" ></i >
                                        </a >
                                    </td >
                                </tr >
                                </tbody >
                            </table >
                        </div >
                    </div >
                </div >
                <div class="list-pagination" eccrm-page="pager" ></div >
            </div >
        </div >
    </div >
</div >
</div >

<script type="text/javascript" src="<%=contextPath%>/vendor/zTree/js/jquery.ztree.core-3.5.min.js" ></script >
<script type="text/javascript" src="<%=contextPath%>/app/base/resource/resource.js" ></script >
<script type="text/javascript" src="<%=contextPath%>/app/base/resource/resource_list.js" ></script >
</body >

</html >