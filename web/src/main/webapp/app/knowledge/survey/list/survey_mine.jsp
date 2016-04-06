<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>我的试卷</title>
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
<div class="main condition-row-1" ng-app="eccrm.knowledge.survey.mine" ng-controller="Ctrl">
    <div class="list-condition">
        <div class="block">
            <div class="block-header">
                <span class="header-button">
                        <button type="button" class="btn btn-green btn-min" ng-click="query();">
                            <span class="glyphicons search"></span>
                            查询
                        </button>
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
            </div>
            <div class="block-content">
                <div class="content-wrap">
                    <div class="table-responsive panel panel-table">
                        <table class="table table-striped table-hover text-center">
                            <thead class="table-header">
                            <tr>
                                <td>试卷名称</td>
                                <td>总题数</td>
                                <td>总分数</td>
                                <td>答题人</td>
                                <td>得分</td>
                                <td>开始答题时间</td>
                                <td>结束答题时间</td>
                                <td style="width: 150px;">答题时长(分)</td>
                            </tr>
                            </thead>
                            <tbody class="table-body">
                            <tr ng-show="!beans.total">
                                <td colspan="8" class="text-center">没有符合条件的记录！</td>
                            </tr>

                            <tr bindonce ng-repeat="foo in beans.data" ng-cloak>
                                <td bo-text="foo.surveyName"></td>
                                <td bo-text="foo.totalCounts"></td>
                                <td bo-text="foo.totalScore"></td>
                                <td bo-text="foo.empName"></td>
                                <td bo-text="foo.score"></td>
                                <td bo-text="foo.startDate | eccrmDatetime"></td>
                                <td bo-text="foo.endDate | eccrmDatetime"></td>
                                <td bo-text="(foo.duration/60000)|number:2"></td>
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

<script type="text/javascript" src="<%=contextPath%>/app/knowledge/survey/list/survey_mine.js"></script>

</html>