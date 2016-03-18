<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>试卷</title>
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/vendor/bootstrap-v3.0/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/style/standard/css/eccrm-common-new.css">
    <link rel="stylesheet" href="<%=contextPath%>/vendor/angular-motion-v0.3.2/angular-motion.min.css"/>

    <script type="text/javascript" src="<%=contextPath%>/vendor/jquery-v1.8.3/jquery.min.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-all.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-strap-all.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-ztree-all.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/app/knowledge/survey/survey.js"></script>
    <script type="text/javascript">
        window.angular.contextPathURL = '<%=contextPath%>';
    </script>
</head>
<body>
<div class="main condition-row-1" ng-app="eccrm.knowledge.survey.list"
     ng-controller="SurveyListCtrl"
>
    <div eccrm-alert="alert"></div>
    <div class="list-condition">
        <div class="block">
            <div class="block-header">
                <span class="header-button">
                            <button type="button" class="btn btn-green btn-min" ng-click="query();">
                                <span class="glyphicons search"></span>
                                查询
                            </button>

                            <button type="button" class="btn btn-green btn-min" ng-click="condition={}">
                                <span class="glyphicons repeat"></span>
                                重置
                            </button>
                </span>
            </div>
            <div class="block-content">
                <div class="content-wrap">
                    <div class="row">
                        <div class="form-label col-1-half">
                            <label>试卷名称:</label>
                        </div>
                        <input class="col-2-half" type="text" ng-model="condition.name"/>

                        <div class="form-label col-1-half">
                            <label>状态:</label>
                        </div>
                        <select class="col-2-half" ng-model="condition.status"
                                ng-options="foo.value as foo.name for foo in status"> </select>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="list-result ">
        <div class="block">
            <div class="block-header">
                <div class="header-text">
                    <span class="glyphicons list"></span>
                    <span>试卷列表</span>
                </div>
                <span class="header-button">
                        <a type="button" class="btn btn-green btn-min" ng-click="add();">
                            <span class="glyphicons plus"></span>
                            新增
                        </a>
                       <a type="button" class="btn btn-green btn-min" ng-click="remove();" ng-disabled="!anyone">
                           <span class="glyphicons remove_2"></span>
                           删除
                       </a>
                </span>
            </div>
            <div class="block-content">
                <div class="content-wrap">
                    <div class="table-responsive panel panel-table">
                        <table class="table table-striped table-hover text-center">
                            <thead class="table-header">
                            <tr>
                                <td>
                                    <div select-all-checkbox checkboxes="beans.data" selected-items="items"
                                         anyone-selected="anyone"></div>
                                </td>
                                <td>试卷名称</td>
                                <td>总题数</td>
                                <td>总分数</td>
                                <td>单选</td>
                                <td>多选</td>
                                <td>判断</td>
                                <td>填空</td>
                                <td>简答</td>
                                <td>生效时间</td>
                                <td>截止时间</td>
                                <td>状态</td>
                                <td>操作</td>
                            </tr>
                            </thead>
                            <tbody class="table-body">
                            <tr ng-show="!beans.total">
                                <td colspan="13" class="text-center">没有符合条件的记录！</td>
                            </tr>

                            <tr bindonce ng-repeat="foo in beans.data" ng-cloak>
                                <td>
                                    <input type="checkbox" ng-model="foo.isSelected"/>
                                </td>
                                <td title="点击查询明细！" style="cursor: pointer;">
                                    <a ng-click="view(foo.id)" bo-text=" foo.name"></a>
                                </td>
                                <td bo-text="foo.totalSubjects"></td>
                                <td bo-text="foo.totalScore"></td>
                                <td bo-text="foo.xzCounts"></td>
                                <td bo-text="foo.dxCounts"></td>
                                <td bo-text="foo.pdCounts"></td>
                                <td bo-text="foo.tkCounts"></td>
                                <td bo-text="foo.jdCounts"></td>
                                <td bo-text="foo.startTime | eccrmDatetime"></td>
                                <td bo-text="foo.endTime | eccrmDatetime"></td>
                                <td bo-text="foo.statusName"></td>
                                <td>
                                    <a class="btn btn-tiny" title="编辑" ng-click="modify(foo.id,foo.status)">
                                        <span class="icons edit"></span>
                                    </a>
                                    <a class="btn btn-tiny" title="发布" ng-click="publish(foo.id,foo.status)">
                                        <span class="icons yes"></span>
                                    </a>

                                    <a class="btn btn-tiny" title="预览/模拟考试"
                                       ng-click="preview(foo.id);">
                                        <span class="icons view"></span>
                                    </a>
                                </td>
                            </tr>

                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="list-pagination" eccrm-page="pager"></div>
</div>
</div >
</body>

<script type="text/javascript" src="<%=contextPath%>/app/knowledge/survey/list/survey_list.js"></script>

</html>