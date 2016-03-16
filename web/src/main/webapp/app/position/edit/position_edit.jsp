<%--
Created by IntelliJ IDEA.
User: chenl
Date: 2014-10-14 16:01:46
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <base href="<%=request.getContextPath()%>/">
    <title>编辑新增岗位</title>
    <link rel="stylesheet" type="text/css" href="vendor/bootstrap-v3.0/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="style/standard/css/eccrm-common-new.css">
    <link rel="stylesheet" href="vendor/angular-motion-v0.3.2/angular-motion.min.css"/>
    <link rel="stylesheet" href="vendor/angular-motion-v0.3.2/angular-motion.min.css"/>
</head>
<body>
<div class="main" ng-app="eccrm..position.edit" ng-controller="PositionEditController">
    <div eccrm-alert="alert"></div>
    <%--表单按照1.5:2.5:1.5:2.5:2的比例进行排列--%>
    <div class="row-10 block">
        <div class="block-header">
                <span class="header-text">
                    <span>新增岗位</span>
                </span>
                <span class="header-button">
                    <button type="button" class="btn btn-green btn-min" ng-if="!page.type || page.type=='add'"
                            ng-click="save()" ng-disabled="!form.$valid">
                        <span class="glyphicons disk_save"></span> 保存
                    </button>
                    <button type="button" class="btn btn-green btn-min" ng-if="page.type && page.type=='modify'"
                            ng-click="update()" ng-disabled="!form.$valid && !canUpdate">
                        <span class="glyphicons claw_hammer"></span> 更新
                    </button>
                    <a type="button" class="btn btn-green btn-min"
                       ng-click="back();">
                        <span class="glyphicons message_forward"></span> 返回
                    </a>
                </span>
        </div>
        <div class="block-content">
            <div class="content-wrap">
                <form name="form" class="form-horizontal" role="form">
                    <div style="display: none;">
                        <input type="hidden" id="pageType" value="${pageType}"/>
                        <input type="hidden" id="id" value="${id}"/>
                    </div>
                    <div class="row">
                        <div class="form-label col-2">
                            <label>名称(全拼):</label>
                        </div>
                        <input class="col-4" type="text" ng-model="position.project"/>

                        <div class="form-label col-1-half">
                            <label>简称(简拼):</label>
                        </div>
                        <input class="col-4" type="text" ng-model="position.activity"/>
                    </div>
                    <div class="row">
                        <div class="form-label col-2">
                            <label>拼音(全拼):</label>
                        </div>
                        <input class="col-4" type="text" ng-model="position.project"/>

                        <div class="form-label col-1-half">
                            <label>拼音(简拼):</label>
                        </div>
                        <input class="col-4" type="text" ng-model="position.list"/>
                    </div>
                    <div class="row">
                        <div class="form-label col-2">
                            <label>所属系统:</label>
                        </div>
                        <select ng-model="position.project" class="col-4">
                            <option value="">请选择</option>
                        </select>

                        <div class="form-label col-1-half">
                            <label>所属分类:</label>
                        </div>
                        <input class="col-4" type="text" ng-model="position.list"/>
                    </div>
                    <div class="row">
                        <div class="form-label col-2">
                            <label>所属机构:</label>
                        </div>
                        <input class="col-4" type="text" ng-model="position.project"/>

                        <div class="form-label col-1-half">
                            <label>岗位类型:</label>
                        </div>
                        <select ng-model="position.list" class="col-4">
                            <option value="">请选择</option>
                        </select>
                    </div>
                    <div class="row">
                        <div class="form-label col-2">
                            <label>人员编制：:</label>
                        </div>
                        <input class="col-4" type="number" ng-model="position.project"/>

                        <div class="form-label col-1-half">
                            <label>最小人员:</label>
                        </div>
                        <input class="col-4" type="number" ng-model="position.project"/>
                    </div>
                    <div class="row">
                        <div class="form-label col-2">
                            <label>是否预警:</label>
                        </div>
                        <div class="col-2-half">
                            <input name="distributionData" type="checkbox" ng-model="position.distributionData"/>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-label col-2">
                            <label>岗位描述:</label>
                        </div>
                        <textarea class="col-9-half" rows="3" ng-model="position.distributionData"></textarea>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
<script type="text/javascript" src="vendor/jquery-v1.8.3/jquery.min.js"></script>
<script type="text/javascript" src="static/ycrl/javascript/angular-all.js"></script>
<script type="text/javascript" src="static/ycrl/javascript/angular-strap-all.js"></script>
<script type="text/javascript" src="app/base/position.js"></script>
<script type="text/javascript" src="app/base/position/edit/position_edit.js"></script>
</html>