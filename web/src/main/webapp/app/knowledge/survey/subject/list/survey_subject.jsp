<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en" >
<head >
    <title >问卷题目</title >
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/vendor/bootstrap-v3.0/css/bootstrap.min.css" >
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/style/standard/css/eccrm-common-new.css" >
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/vendor/zTree/css/ztree.css" >
    <script type="text/javascript" src="<%=contextPath%>/vendor/jquery-v1.8.3/jquery.min.js" ></script >
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-all.js" ></script >
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-strap-all.js" ></script >
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-ztree-all.js" ></script >

    <script type="text/javascript" src="<%=contextPath%>/vendor/kindeditor-4.1.10/kindeditor-min.js" charset="utf-8" ></script >
    <script type="text/javascript" src="<%=contextPath%>/vendor/kindeditor-4.1.10/lang/zh_CN.js" charset="utf-8" ></script >

    <script type="text/javascript" src="<%=contextPath%>/app/knowledge/survey/survey.js" ></script >
    <script type="text/javascript" src="<%=contextPath%>/app/knowledge/survey/category/category.js" ></script >
    <script type="text/javascript" src="<%=contextPath%>/app/knowledge/survey/subject/subject.js" ></script >
    <script type="text/javascript" src="<%=contextPath%>/app/knowledge/survey/subject/subject-modal.js" ></script >
    <script type="text/javascript" >
        window.angular.contextPathURL = '<%=contextPath%>';
    </script >
</head >
<body >
<div class="main" ng-app="knowledge.survey.subject.list" ng-controller="SubjectListCtrl" >
    <div class="dn" >
        <input type="hidden" id="surveyId" value="${param.surveyId}" />
        <input type="hidden" id="pageType" value="${param.pageType}" />
    </div >
    <div class="block" >
        <div class="block-header" >
            <div class="header-text" >
                <span >题目列表</span >
            </div >
            <div class="header-button" >
                <c:if test="${param.pageType eq 'modify' or param.pageType eq 'add'}" >
                    <a type="button" class="btn btn-green btn-min" ng-click="save();" ng-cloak ng-show="sequenceChanged" >
                        保存
                    </a >
                    <a type="button" class="btn btn-green btn-min" ng-click="add();" >
                        添加
                    </a >
                    <a type="button" class="btn btn-green btn-min" ng-click="remove();" ng-disabled="!anyone" >
                        移除
                    </a >
                </c:if >
            </div >
        </div >
        <div class="block-content" >
            <div class="table-responsive panel panel-table first-min" >
                <table class="table table-striped table-hover " >
                    <thead class="table-header" >
                    <tr >
                        <td >
                            <div select-all-checkbox checkboxes="beans" selected-items="items"
                                 anyone-selected="anyone" ></div >
                        </td >
                        <td style="width: 150px;" >分类</td >
                        <td >题目</td >
                        <td style="width: 100px;" >题型</td >
                        <td style="width: 100px;" >排序号</td >
                        <td >操作</td >
                    </tr >
                    </thead >
                    <tbody class="table-body" >
                    <tr ng-show="!beans.length" >
                        <td colspan="6" class="text-center" >没有符合条件的记录！</td >
                    </tr >
                    <tr bindonce ng-repeat="foo in beans" >
                        <td >
                            <input type="checkbox" ng-model="foo.isSelected" ng-disabled="foo.status==1" />
                        </td >
                        <td bo-text="foo.categoryName" ></td >
                        <td title="点击查询明细！" style="cursor: pointer;" >
                            <a ng-click="view(foo.subjectId)" bo-text="foo.subjectName | limitTo:40" ></a >
                        </td >
                        <td bo-text="foo.subjectTypeName" ></td >
                        <td ng-bind-template="{{foo.sequenceNo}}" class="ta-c" >
                        </td >
                        <td >
                            <c:if test="${param.pageType eq 'modify' or param.pageType eq 'add'}" >
                                <a class="btn btn-tiny" title="上调序号" ng-click="up($index);" >
                                    <i class="icons upload" ></i >
                                </a >
                                <a class="btn btn-tiny" title="下调序号" ng-click="down($index);" >
                                    <i class="icons download" ></i >
                                </a >
                                <a class="btn btn-tiny" title="移除" ng-click="remove(foo.subjectId);" >
                                    <i class="icons fork" ></i >
                                </a >
                            </c:if >
                        </td >
                    </tr >
                    </tbody >
                </table >
            </div >
        </div >
    </div >
</div >
</body >

<script type="text/javascript" src="<%=contextPath%>/app/knowledge/survey/subject/list/survey_subject.js" ></script >
</html >