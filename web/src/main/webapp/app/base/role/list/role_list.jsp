<%--
  Created by IntelliJ IDEA.
  User: chenl
  Date: 13-12-05
  Time: 下午3:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en" >
<head >
    <base href="<%=request.getContextPath()%>/" >
    <title >角色管理</title >
    <link rel="stylesheet" type="text/css" href="vendor/bootstrap-v3.0/css/bootstrap.min.css" >
    <link rel="stylesheet" type="text/css" href="style/standard/css/eccrm-common-new.css" >
    <link rel="stylesheet" type="text/css" href="vendor/angular-motion-v0.3.2/angular-motion.css" >
    <link rel="stylesheet" type="text/css" href="vendor/zTree/css/ztree.css" >
</head >
<body >
<div class="main condition-row-1" ng-app="eccrm.base.role.list" ng-controller="RoleListController" >
    <div class="list-condition" >
        <div class="block" >
            <div class="block-header" >
                <span class="header-text" >
                    <span class="glyphicons search" ></span >
                    <span >角色查询</span >
                </span >

                <div class="header-button" >
                    <button type="button" class="btn btn-green btn-min" ng-click="query();" >
                        <span class="glyphicons search" ></span > 查询
                    </button >
                    <button type="reset" class="btn btn-green btn-min" ng-click="reset();" >
                        <span class="glyphicons repeat" ></span > 重置
                    </button >
                </div >
            </div >
            <div class="block-content" >
                <div class="content-wrap" >
                    <div class="row" >
                        <div class="form-label col-1-half" >
                            <label >角色名称:</label >
                        </div >
                        <div class="inline" >
                            <input type="text" class="col-2-half" placeholder="请输入角色名称..." ng-model="condition.name" />
                        </div >
                        <div class="form-label col-1-half" >
                            <label class="control-label" >状态:</label >
                        </div >
                        <select class="col-2-half" ng-model="condition.status" ng-options="foo.value as foo.name for foo in RoleStatus" > </select >
                    </div >
                </div >
            </div >
        </div >
    </div >
    <div class="list-result" >
        <div class="block" >
            <div class="block-header" >
           <span class="header-text" >
               <span class="glyphicons list" ></span >
               <span >角色列表</span >
           </span >
            <span class="header-button" >
                <a class="btn btn-green btn-min" ng-click="add();" >
                    <span class="glyphicons plus" ></span > 增加
                </a >

                <a ng-click="remove();" class="btn btn-sm btn-green btn-min " title="单击删除/注销多条记录！" ng-disabled="!anyOne" >
                    <i class="glyphicons remove_2" ></i >
                    <span >注销</span >
                </a >
            </span >
            </div >
            <div class="block-content" >
                <div class="content-wrap" >
                    <div class="table-responsive panel panel-table first-min" >
                        <table class="table table-striped table-hover" >
                            <thead class="table-header" >
                            <tr >
                                <td >
                                    <div select-all-checkbox checkboxes="roles.data" selected-items="items" anyone-selected="anyOne" ></div >
                                </td >
                                <td >角色</td >
                                <td >编号</td >
                                <td >启用时间</td >
                                <td >失效时间</td >
                                <td >创建人</td >
                                <td >创建时间</td >
                                <td >状态</td >
                                <td >操作</td >
                            </tr >
                            </thead >
                            <tbody class="table-body" >
                            <tr ng-show="!roles || !roles.total" >
                                <td colspan="10" class="text-center" >没有符合条件的记录！</td >
                            </tr >
                            <tr ng-repeat="foo in roles.data" ng-cloak>
                                <td >
                                    <input type="checkbox" ng-model="foo.isSelected" />
                                </td >
                                <td title="点击查询明细！" style="cursor: pointer;" >
                                    <a ng-click="view(foo.id);" ng-bind-template="{{foo.name}}" ></a >
                                </td >
                                <td ng-bind-template="{{ foo.code }}" ></td >
                                <td ng-bind-template="{{ foo.startDate | eccrmDate }}" ></td >
                                <td ng-bind-template="{{ foo.endDate | eccrmDate }}" ></td >
                                <td ng-bind-template="{{ foo.creatorName }}" ></td >
                                <td ng-bind-template="{{ foo.createdDatetime | eccrmDatetime }}" ></td >
                                <td ng-bind-template="{{ foo.statusName }}" ></td >
                                <td >

                                    <a ng-click="grant(foo.id)" class="btn btn-tiny" title="授权" >
                                        <i class="glyphicons crown" ></i >
                                    </a >
                                    <a ng-click="modify(foo.id)" class="btn btn-tiny" title="编辑" >
                                        <i class="glyphicons pencil" ></i >
                                    </a >
                                    <a ng-click="remove(foo.id);" class="btn btn-tiny" title="删除" ng-disabled="foo.roleStatus>2" >
                                        <i class="icons fork" ></i >
                                    </a >
                                </td >
                            </tr >
                            </tbody >
                        </table >
                    </div >
                </div >
            </div >
        </div >
    </div >
    <div class="list-pagination" eccrm-page="pager" ></div >
</div >
</body >
<script type="text/javascript" src="vendor/jquery-v1.8.3/jquery.min.js" ></script >
<script type="text/javascript" src="static/ycrl/javascript/angular-all.js" ></script >
<script type="text/javascript" src="static/ycrl/javascript/angular-strap-all.js" ></script >
<script type="text/javascript" src="vendor/zTree/js/jquery.ztree.core-3.5.min.js" ></script >
<script type="text/javascript" src="vendor/zTree/js/jquery.ztree.excheck-3.5.min.js" ></script >
<script type="text/javascript" src="app/base/parameter/parameter.js" ></script >
<script type="text/javascript" src="app/base/menu/menu.js" ></script >
<script type="text/javascript" src="app/base/menu/menu-modal.js" ></script >
<script type="text/javascript" src="app/base/role/role.js" ></script >
<script type="text/javascript" src="app/base/role/role-modal.js" ></script >
<script type="text/javascript" src="app/base/role/list/role_list.js" ></script >
</html >