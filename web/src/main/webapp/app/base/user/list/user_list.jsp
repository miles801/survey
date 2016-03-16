<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en" >
<head >
    <title >用户管理</title >
    <meta content="text/html" charset="utf-8" >
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8" />
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/vendor/artDialog/artDialog.css" />
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/vendor/bootstrap-v3.0/css/bootstrap.min.css" >
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/style/standard/css/eccrm-common-new.css" >
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/vendor/angular-motion-v0.3.2/angular-motion.css" >
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/jquery-all.js" ></script >
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-all.js" ></script >
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-strap-all.js" ></script >
    <script type="text/javascript" >
        window.angular.contextPathURL = "<%=contextPath%>";
    </script >
</head >
<body >
<div class="main condition-row-2" ng-app="eccrm.base.user.list" ng-controller="UserListController" >
    <div class="list-condition" >
        <div class="block" >
            <div class="block-header" >
                <span class="header-text" >
                        <span >用户管理</span >
                </span >
                <span class="header-button" >
                                    <button type="button" class="btn btn-green btn-min" ng-click="query();" >
                                        <span class="glyphicons search" ></span >
                                        查询
                                    </button >
                                    <button type="button" class="btn btn-green btn-min" ng-click="reset();" >
                                        <span class="glyphicons repeat" ></span >
                                        重置
                                    </button >
                </span >
            </div >
            <div class="block-content" >
                <div class="row" >
                    <div class="form-label col-1-half" >
                        <label >用户名:</label >
                    </div >
                    <input class="col-2-half" type="text" ng-model="condition.username" />

                    <div class="form-label col-1-half" >
                        <label >工号:</label >
                    </div >
                    <input class="col-2-half" type="text" ng-model="condition.employeeNo" />

                    <div class="form-label col-1-half" >
                        <label >用户类型:</label >
                    </div >
                    <select ng-model="condition.type" class="col-2" ng-options="foo.value as foo.name for foo in userType" > </select >
                </div >
                <div class="row" >
                    <div class="form-label col-1-half" >
                        <label >姓名:</label >
                    </div >
                    <input class="col-2-half" type="text" ng-model="condition.employeeName" />

                    <div class="form-label col-1-half" >
                        <label >状态:</label >
                    </div >
                    <select ng-model="condition.status" class="col-2-half" ng-options="foo.value as foo.name for foo in userStatus" > </select >
                </div >
            </div >
        </div >
    </div >
    <div class="list-result " >
        <div class="block" >
            <div class="block-header" >
                <div class="header-text" >
                    <span class="glyphicons list" ></span >
                    <span >用户列表</span >
                </div >
                <span class="header-button" >
                        <a type="button" class="btn btn-green btn-min" ng-click="add();" >
                            <span class="glyphicons plus" ></span >
                            增加
                        </a >
                        <button type="button" class="btn btn-green btn-min" ng-click="deleteOrCancel();" ng-disabled="!anyone" >
                            <span class="glyphicons remove" ></span >
                            注销
                        </button >
                        <button type="button" class="btn btn-green btn-min" ng-click="resetPassword();" ng-disabled="!anyone" >
                            <span class="glyphicons repeat" ></span >
                            重置密码
                        </button >
                </span >
            </div >
            <div class="block-content" >
                <div class="content-wrap" >
                    <div class="table-responsive panel panel-table first-min" >
                        <table class="table table-striped table-hover" >
                            <thead class="table-header" >
                            <tr >
                                <td >
                                    <div select-all-checkbox checkboxes="users.data" selected-items="items" anyone-selected="anyone" ></div >
                                </td >
                                <td >姓名</td >
                                <td >职务</td >
                                <td >移动电话</td >
                                <td >RTX</td >
                                <td >用户类型</td >
                                <td >启用时间</td >
                                <td >关闭时间</td >
                                <td >状态</td >
                                <td >操作</td >
                            </tr >
                            </thead >
                            <tbody class="table-body" >
                            <tr ng-show="!users || !users.total" >
                                <td colspan="9" class="text-center" >没有符合条件的记录！</td >
                            </tr >
                            <tr bindonce ng-repeat="foo in users.data" ng-cloak >
                                <td >
                                    <input type="checkbox" ng-model="foo.isSelected" />
                                </td >

                                <td title="点击查询明细！" style="cursor: pointer;" >
                                    <a ng-click="detail(foo.id);" bo-text="foo.employeeName" ></a >
                                </td >
                                <td bo-text="foo.dutyName" ></td >
                                <td bo-text="foo.mobilePhone" ></td >
                                <td bo-text="foo.username"></td >
                                <td bo-text="foo.typeName"></td >
                                <td bo-text="foo.startDate | eccrmDate"></td >
                                <td bo-text="foo.endDate | eccrmDate"></td >
                                <td bo-text="foo.statusName"></td >
                                <td >
                                    <a class="cp" title="编辑" ng-click="update(foo);" >
                                        <i class="icons edit" ></i >
                                    </a >
                                    <a class="cp" title="删除" ng-click="deleteOrCancel(foo);" >
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
</div >

</body >
<script type="text/javascript" src="<%=contextPath%>/app/base/user/user.js" ></script >
<script type="text/javascript" src="<%=contextPath%>/app/base/user/list/user_list.js" ></script >
</html >