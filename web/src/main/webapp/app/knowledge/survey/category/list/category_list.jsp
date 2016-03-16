<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en" >
<head >
    <title >题库分类列表</title >
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/vendor/bootstrap-v3.0/css/bootstrap.min.css" >
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/style/standard/css/eccrm-common-new.css" >
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/vendor/angular-motion-v0.3.2/angular-motion.css" >
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/vendor/zTree/css/ztree.css" >

    <script type="text/javascript" src="<%=contextPath%>/vendor/jquery-v1.8.3/jquery.min.js" ></script >
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-all.js" ></script >
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-strap-all.js" ></script >
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-ztree-all.js" ></script >

    <script type="text/javascript" src="<%=contextPath%>/app/knowledge/survey/category/category.js" ></script >
    <script type="text/javascript" src="<%=contextPath%>/app/knowledge/survey/category/category-modal.js" ></script >
    <script type="text/javascript" >
        window.angular.contextPathURL = '<%=contextPath%>';
    </script >
</head >
<body >
<div class="main condition-row-1" ng-app="knowledge.survey.category.list"
     ng-controller="SubjectCategoryListController" >
    <div eccrm-alert="alert" ></div >
    <div class="row panel panel-tree" >
        <div class="tree" >
            <div class="tree-content" >
                <ul id="treeDemo" class="ztree" ></ul >
            </div >
            <div class="scroller" ></div >
        </div >
        <div class="content" >
            <div class="list-condition" >
                <div class="block" >
                    <div class="block-header" >
                        <span class="header-text" >
                            <span class="glyphicons search" ></span >
                            <span >题库分类</span >
                        </span >
                        <span class="header-button" >
                                    <button type="button" class="btn btn-green btn-min" ng-click="query();" >
                                        <span class="glyphicons search" ></span >
                                        查询
                                    </button >

                                    <button type="button" class="btn btn-green btn-min" ng-click="condition={}" >
                                        <span class="glyphicons repeat" ></span >
                                        重置
                                    </button >
                        </span >
                    </div >
                    <div class="block-content" >
                        <div class="content-wrap" >
                            <div class="row" >
                                <div class="form-label col-1-half" >
                                    <label >名称:</label >
                                </div >
                                <input class="col-2-half" type="text" ng-model="condition.name" />

                            </div >

                        </div >
                    </div >
                </div >
            </div >
            <div class="list-result" >
                <div class="block" >
                    <div class="block-header" >
                        <div class="header-text" >
                            <span class="glyphicons list" ></span >
                            <span >分类列表</span >
                        </div >
                        <div class="header-button" >
                            <a type="button" class="btn btn-green btn-min" ng-click="add();" >
                                <span class="glyphicons plus" ></span >
                                新增
                            </a >
                            <a type="button" class="btn btn-green btn-min" ng-click="remove();" ng-disabled="!anyone" >
                                <span class="glyphicons remove_2" ></span >
                                删除
                            </a >
                        </div >
                    </div >
                    <div class="block-content">
                        <div class="table-responsive panel panel-table first-min" >
                            <table class="table table-striped table-hover " >
                                <thead class="table-header" >
                                <tr >
                                    <td >
                                        <div select-all-checkbox checkboxes="beans.data" selected-items="items"
                                             anyone-selected="anyone" ></div >
                                    </td >
                                    <td >分类名称</td >
                                    <td style="width: 30px;" >父节点</td >
                                    <td style="width: 30px;" >显示顺序</td >
                                    <td style="width: 20px;" >状态</td >
                                    <td >操作</td >
                                </tr >
                                </thead >
                                <tbody class="table-body" >
                                <tr ng-show="!beans.total" >
                                    <td colspan="6" class="text-center" >没有符合条件的记录！</td >
                                </tr >
                                <tr bindonce ng-repeat="foo in beans.data" ng-cloak >
                                    <td >
                                        <input type="checkbox" ng-model="foo.isSelected" />
                                    </td >
                                    <td title="点击查询明细！" style="cursor: pointer;" >
                                        <a ng-click="detail(foo.id)" bo-text="foo.name | limitTo:40" ></a >
                                    </td >
                                    <td bo-text="foo.parentName" ></td >
                                    <td bo-text="foo.sequenceNo" ></td >
                                    <td bo-text="foo.statusName" ></td >
                                    <td >
                                        <a class="btn btn-tiny" title="编辑" ng-click="modify(foo.id)" >
                                            <i class="icons edit" ></i >
                                        </a >
                                        <a class="btn btn-tiny" title="删除" ng-click="remove(foo.id);" >
                                            <i class="icons fork" ></i >
                                        </a >

                                    </td >
                                </tr >
                                </tbody >
                            </table >
                        </div >
                    </div>
                </div >
            </div >
            <div eccrm-page="pager" class="list-pagination" ></div >
        </div >
    </div >
</div >
</body >
<script type="text/javascript" src="<%=contextPath%>/app/knowledge/survey/category/list/category_list.js" ></script >
</html >