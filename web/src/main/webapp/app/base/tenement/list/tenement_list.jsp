<%--
  Created by IntelliJ IDEA.
  User: miles
  Date: 13-11-22
  Time: 下午3:21
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en" >
<head >
    <base href="<%=request.getContextPath()%>/" >
    <title >租户列表</title >
    <link rel="stylesheet" type="text/css" href="vendor/bootstrap-v3.0/css/bootstrap.min.css" >
    <link rel="stylesheet" type="text/css" href="style/standard/css/eccrm-common-new.css" >
    <link rel="stylesheet" href="vendor/angular-motion-v0.3.2/angular-motion.min.css" />
</head >
<body >
<div class="main condition-row-2" ng-app="eccrm.base.tenement.list" ng-controller="TenementListController" >
    <div class="list-condition" >
        <div class="block" >
            <div class="block-header" >
                <span class="header-text" >
                    <span class="glyphicons search" ></span >
                    <span >租户管理</span >
                </span >
                <span class="header-button" >
                    <button type="button" class="btn btn-green btn-min" ng-click="query();" >
                        <span class="glyphicons search" ></span > 查询
                    </button >
                    <button type="reset" class="btn btn-green btn-min" ng-click="reset();" >
                        <span class="glyphicons repeat" ></span > 重置
                    </button >
                </span >
            </div >
            <div class="block-content" >
                <div class="content-wrap" >
                    <div class="row" >
                        <div class="form-label col-1-half" >
                            <label >租户名称:</label >
                        </div >
                        <input class="col-2-half" type="text" ng-model="condition.name" />

                        <div class="form-label col-1-half" >
                            <label >租户编号:</label >
                        </div >
                        <input class="col-2-half" type="text" ng-model="condition.code" />

                        <div class="form-label col-1-half" >
                            <label >状态:</label >
                        </div >
                        <select class="col-2" ng-model="condition.status" ng-options="foo.code as foo.name for foo in TenementStatus" > </select >
                    </div >
                    <div class="row" >
                        <div class="form-label col-1-half" >
                            <label >到期时间:</label >
                        </div >
                        <div class="col-2-half" >
                            <div class="col-5-half" >
                                <input type="text" class="col-12"
                                       bs-datepicker ng-model="condition.invalidStartDate" data-max-date="{{condition.invalidEndDate}}"
                                       readonly />
                                    <span class="add-on col" >
                                        <i class="glyphicons calendar icon" title="清除时间" ng-click="condition.invalidStartDate=undefined;" ></i >
                                    </span >
                            </div >
                            <span class="col-1 text-center" >-</span >

                            <div class="col-5-half" >
                                <input type="text" class="col-12"
                                       bs-datepicker ng-model="condition.invalidEndDate" data-min-date="{{condition.invalidStartDate}}"
                                       readonly />
                                    <span class="add-on col" >
                                        <i class="glyphicons calendar icon" title="清除时间" ng-click="condition.invalidEndDate=undefined;" ></i >
                                    </span >
                            </div >
                        </div >
                    </div >
                </div >
            </div >
        </div >
    </div >
    <%-- 订单查询条件 --%>
    <div class="list-result" >
        <div class="block" >
            <div class="block-header" >
                <span class="header-text" >
                    <span class="glyphicons list" ></span >
                    <span >租户列表</span >
                </span >
                <span class="header-button" >
                    <a class="btn btn-green btn-min" ng-click="add();" >
                        <span class="glyphicons plus" ></span > 新建
                    </a >
                    <a class="btn btn-green btn-min" ng-click="remove();" ng-disabled="!anyone" >
                        <span class="glyphicons remove_2" ></span > 删除/注销
                    </a >
                </span >
            </div >
            <div class="block-content" >
                <div class="content-wrap" >
                    <div class="table-responsive panel panel-table first-min" >
                        <table class="table table-striped table-hover" >
                            <thead class="table-header" >
                            <tr sortable="sortable" >
                                <td >
                                    <div select-all-checkbox checkboxes="tenements.data" selected-items="items" anyone-selected="anyone" ></div >
                                </td >
                                <td >租户名称</td >
                                <td >租户编号</td >
                                <td >所在地区</td >
                                <td >租用方式</td >
                                <td >开始时间</td >
                                <td >结束时间</td >
                                <td >用户规模</td >
                                <td >资源要求</td >
                                <td >备注</td >
                                <td >状态</td >
                                <td >操作</td >
                            </tr >
                            </thead >
                            <tbody class="table-body" ng-cloak >
                            <tr ng-show="!tenements.total|| !tenements.data.length" >
                                <td colspan="12" class="text-center" >没有符合条件的记录！</td >
                            </tr >
                            <tr ng-repeat="foo in tenements.data" >
                                <td ><input type="checkbox" ng-model="foo.isSelected" /></td >
                                <td title="点击查询明细！" style="cursor: pointer;" >
                                    <a ng-href="base/tenement/detail?id={{foo.id}}" ng-bind-template="{{ foo.name }}" ></a >
                                </td >
                                <td ng-bind-template="{{ foo.code }}" ></td >
                                <td ng-bind-template="{{foo.provinceName}}-{{foo.cityName}}-{{ foo.countyName}}" ></td >
                                <td ng-bind-template="{{ foo.rentTypeName }}" ></td >
                                <td >{{ foo.startDate | eccrmDate }}</td >
                                <td >{{ foo.endDate | eccrmDate }}</td >
                                <td ng-bind-template="{{ foo.userScaleName }}" ></td >
                                <td ng-bind-template="{{ foo.singleResource?'独占':'共享' }}" ></td >
                                <td ng-bind-template="{{ foo.remark }}" ></td >
                                <td ng-bind-template="{{ foo.status | tenementStatusFilter}}" ></td >
                                <td >
                                    <a class="btn btn-tiny" title="编辑" ng-href="base/tenement/modify?id={{foo.id}}" >
                                        <i class="glyphicons pencil" ></i >
                                    </a >
                                    <a class="btn btn-tiny" title="暂停" ng-click="pause(foo.id);" ng-if="foo.status<6" >
                                        <i class="glyphicons pause" ></i >
                                    </a >
                                    <a class="btn btn-tiny" title="关闭" ng-click="close(foo.id);" ng-if="foo.status<7" >
                                        <i class="glyphicons remove_2" ></i >
                                    </a >
                                    <a class="btn btn-tiny" title="删除" ng-click="remove(foo.id);" ng-if="!foo.status || foo.status==1" >
                                        <i class="glyphicons remove" ></i >
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
<script type="text/javascript" src="app/base/tenement/tenement.js" ></script >
<script type="text/javascript" src="app/base/tenement/list/tenement_list.js" ></script >

</html >