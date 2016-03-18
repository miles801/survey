<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en" >
<head >
    <title >题目管理列表</title >
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/vendor/bootstrap-v3.0/css/bootstrap.min.css" >
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/style/standard/css/eccrm-common-new.css" >
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/vendor/angular-motion-v0.3.2/angular-motion.css" >
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/vendor/zTree/css/ztree.css" >

    <script type="text/javascript" src="<%=contextPath%>/vendor/kindeditor-4.1.10/kindeditor-min.js" charset="utf-8" ></script >
    <script type="text/javascript" src="<%=contextPath%>/vendor/kindeditor-4.1.10/lang/zh_CN.js" charset="utf-8" ></script >

    <script type="text/javascript" src="<%=contextPath%>/vendor/jquery-v1.8.3/jquery.min.js" ></script >
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-all.js" ></script >
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-strap-all.js" ></script >
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-ztree-all.js" ></script >

    <script type="text/javascript" src="<%=contextPath%>/app/knowledge/survey/category/category.js" ></script >
    <script type="text/javascript" src="<%=contextPath%>/app/knowledge/survey/subject/subject.js" ></script >
    <script type="text/javascript" src="<%=contextPath%>/app/knowledge/survey/subject/subject-modal.js" ></script >
    <script type="text/javascript" >
        window.angular.contextPathURL = '<%=contextPath%>';
    </script >
</head >
<body >
<div class="main condition-row-1" ng-app="knowledge.survey.subject.list" ng-controller="SubjectListCtrl" >
    <div class="row panel panel-tree" >
        <div class="tree" style="width: 200px;">
            <div class="tree-content" >
                <ul id="treeDemo" class="ztree" ></ul >
            </div >
            <div class="scroller" ></div >
        </div >
        <div class="content" style="padding-left: 205px;">
            <div class="list-condition" >
                <div class="block" >
                    <div class="block-header" >
                        <span class="header-text" >
                            <span >题目管理</span >
                        </span >
                        <span class="header-button" >
                                    <button type="button" class="btn btn-green btn-min" ng-click="query();" >
                                        查询
                                    </button >

                                    <button type="button" class="btn btn-green btn-min" ng-click="condition={}" >
                                        重置
                                    </button >
                        </span >
                    </div >
                    <div class="block-content" >
                        <div class="content-wrap" >
                            <div class="row" >
                                <div class="form-label col-1-half" >
                                    <label >题目:</label >
                                </div >
                                <input class="col-2-half" type="text" ng-model="condition.title" />

                                <div class="form-label col-1-half" >
                                    <label >题型:</label >
                                </div >
                                <select ng-model="condition.subjectType" class="col-2-half" ng-options="foo.value as foo.name for foo in type" > </select >

                                <div class="form-label col-1-half" >
                                    <label >状态:</label >
                                </div >
                                <select ng-model="condition.status" class="col-2-half" ng-options="foo.value as foo.name for foo in status" > </select >
                            </div >

                        </div >
                    </div >
                </div >
            </div >
            <div class="list-result" >
                <div class="block" >
                    <div class="block-header" >
                        <div class="header-text" >
                            <span >题目列表</span >
                        </div >
                        <div class="header-button" >
                            <a type="button" class="btn btn-green btn-min" ng-click="add();" >
                                新增
                            </a >
                            <a type="button" class="btn btn-green btn-min" ng-click="remove();" ng-disabled="!anyone" >
                                删除
                            </a >
                        </div >
                    </div >
                    <div class="block-content" >
                        <div class="table-responsive panel panel-table">
                            <table class="table table-striped table-hover text-center">
                                <thead class="table-header" >
                                <tr >
                                    <td class="width-min">
                                        <div select-all-checkbox checkboxes="beans.data" selected-items="items"
                                             anyone-selected="anyone" ></div >
                                    </td >
                                    <td >题目</td >
                                    <td style="width: 120px;">题型</td>
                                    <td style="width: 120px;">分类</td>
                                    <td style="width: 120px;">状态</td>
                                    <td style="width: 100px;">操作</td>
                                </tr >
                                </thead >
                                <tbody class="table-body" >
                                <tr ng-show="!beans.total" >
                                    <td colspan="6" class="text-center" >没有符合条件的记录！</td >
                                </tr >
                                <tr bindonce ng-repeat="foo in beans.data" ng-cloak>
                                    <td >
                                        <input type="checkbox" ng-model="foo.isSelected" ng-disabled="{{foo.status==1}}" />
                                    </td >
                                    <td title="点击查询明细！" style="cursor: pointer;" class="text-left">
                                        <a ng-click="view(foo.id)" bo-text="foo.title | removeHTMLTag | limitTo:40" ></a >
                                    </td >
                                    <td bo-text="foo.subjectTypeName" ></td >
                                    <td bo-text="foo.categoryName" ></td >
                                    <td bo-text="foo.statusName" ></td >
                                    <td >
                                        <a class="btn btn-tiny" title="编辑" ng-click="modify(foo.id,foo.status)" >
                                            <i class="icons edit" ></i >
                                        </a >
                                        <a class="btn btn-tiny" title="删除" ng-click="remove(foo.id,foo.status);" >
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
            <div eccrm-page="pager" class="list-pagination" ></div >
        </div >
    </div >
</div >
</body >

<script type="text/javascript" src="<%=contextPath%>/app/knowledge/survey/subject/list/subject_list.js" ></script >
</html >