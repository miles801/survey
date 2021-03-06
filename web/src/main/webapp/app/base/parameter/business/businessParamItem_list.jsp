<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en" >
<%
    String contextPath = request.getContextPath();
%>
<head >
    <title >参数列表</title >
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8" />
    <!--[if IE]>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" >
    <![endif]-->
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/vendor/artDialog/artDialog.css" />
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/vendor/bootstrap-v3.0/css/bootstrap.min.css" >
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/style/standard/css/eccrm-common-new.css" >
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/vendor/angular-motion-v0.3.2/angular-motion.css" >
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/vendor/zTree/css/ztree.css" >
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/jquery-all.js" ></script >
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-all.js" ></script >
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-strap-all.js" ></script >
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-ztree-all.js" ></script >
    <script >
        window.angular.contextPathURL = '<%=contextPath%>';
    </script >
</head >

<body >
<div class="main" ng-app="eccrm.base.parameter.item.list" ng-controller="ParameterItemListController" >

    <div class="row panel panel-tree" >
        <div class="tree" style="width: 160px;" >
            <ul id="treeDemo" class="ztree" ></ul >
        </div >
        <div class="content" style="padding-left: 165px;" >
            <div class="list-result" >
                <div class="block " >
                    <div class="block-header" >
                        <div class="header-text" >
                            <span >参数信息</span >
                        </div >
                        <span class="header-button" >
                            <a class="btn btn-green btn-min" ng-click="add();" ng-disabled="current.status!='ACTIVE'" >
                                <span class="glyphicons plus" ></span > 新增
                            </a >

                            <a class="btn btn-green btn-min" ng-click='publish()' ng-disabled="!anyOne" >
                                <span class="glyphicons play" ></span > 启用
                            </a >
                            <a class="btn btn-green btn-min" ng-click='cancelOrDelete()' ng-disabled="!anyOne" >
                                <span class="glyphicons remove_2" ></span > 删除/注销
                            </a >
                        </span >
                    </div >
                    <div class="block-content" >
                        <div class="table-responsive panel panel-table first-min" >
                            <table class="table table-striped table-hover" >
                                <thead class="table-header" >
                                <tr >
                                    <td >
                                        <div select-all-checkbox checkboxes="parameters.data" selected-items="items" anyone-selected="anyOne" ></div >
                                    </td >
                                    <td >参数名</td >
                                    <td >参数值</td >
                                    <td >所属类型</td >
                                    <td >类型编号</td >
                                    <td >级联类型</td >
                                    <td >级联参数</td >
                                    <td class="length-min" >显示顺序</td >
                                    <td class="length-min" >状态</td >
                                    <td >操作</td >
                                </tr >
                                </thead >

                                <tbody class="table-body" ng-cloak >
                                <tr ng-show="!parameters || !parameters.total" >
                                    <td colspan="10" class="text-center" >没有符合条件的记录！</td >
                                </tr >

                                <tr ng-repeat="foo in parameters.data " >
                                    <td >
                                        <input type="checkbox" ng-model="foo.isSelected" />
                                    </td >

                                    <td title="点击查询明细！" style="cursor: pointer;color: #0000ff;text-decoration: underline;" ng-click="view(foo.id);" >
                                        {{ foo.name | substr:20}}
                                    </td >
                                    <td ng-bind-template="{{ foo.value | substr:20}}" ></td >
                                    <td ng-bind-template="{{ foo.typeName }}" ></td >
                                    <td ng-bind-template="{{ foo.type }}" ></td >
                                    <td ng-bind-template="{{ foo.cascadeTypeName }}" ></td >
                                    <td ng-bind-template="{{ foo.cascadeItemName }}" ></td >
                                    <td class="text-center" ng-bind-template="{{ foo.sequenceNo }}" ></td >
                                    <td ng-bind-template="{{ foo.statusName }}" ></td >
                                    <td >
                                        <a class="btn btn-tiny ph0" title="编辑" ng-click="modify(foo.id);" >
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
            <div eccrm-page="pager" class="list-pagination" ></div >
        </div >
    </div >
</div >
</body >
<script type="text/javascript" src="<%=contextPath%>/app/base/parameter/parameter-modal.js" ></script >
<script type="text/javascript" src="<%=contextPath%>/app/base/parameter/business/businessParamItem_list.js" ></script >
</html >
