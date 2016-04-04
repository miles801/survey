<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String contextPath = request.getContextPath();
%>
<html>
<head>
    <base href="<%=request.getContextPath()%>/">
    <title>试卷导入</title>
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/vendor/bootstrap-v3.0/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/style/standard/css/eccrm-common-new.css">
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/vendor/angular-motion-v0.3.2/angular-motion.min.css">

    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/jquery-all.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-all.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-strap-all.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-upload.js"></script>

    <script>
        window.angular.contextPathURL = '<%=contextPath%>';
    </script>
    <style>
        .row > label {
            margin-right: 8px;
            padding: 5px;
        }

        .row > label input {
            margin-right: 5px;
        }
    </style>
</head>
<body>
<div class="main" ng-app="spec.survey.import" ng-controller="Ctrl">
    <div class="block">
        <div class="block-header">
                <span class="header-text">
                    <span class="glyphicons info-sign"></span>
                    <span></span>
                </span>
        </div>
        <div class="block-content">
            <div class="content-wrap">
                <form name="form" class="form-horizontal" role="form">
                    <div class="row">
                        <div class="form-label col-1-half">
                            <label validate-error="form.subjectType">题型:</label>
                        </div>
                        <label>
                            <input type="radio" name="subjectType" ng-model="subjectType" ng-value="1" validate
                                   validate-required/>单选题
                        </label>
                        <label>
                            <input type="radio" name="subjectType" ng-model="subjectType" ng-value="2" validate
                                   validate-required/>多选题
                        </label>
                        <label>
                            <input type="radio" name="subjectType" ng-model="subjectType" ng-value="3" validate
                                   validate-required/>判断题
                        </label>
                        <label>
                            <input type="radio" name="subjectType" ng-model="subjectType" ng-value="4" validate
                                   validate-required/>填空题
                        </label>
                    </div>
                    <div class="row" eccrm-upload="fileUpload" ng-cloak></div>
                    <div class="row" style="margin-left: 10.5%;margin-top:8px;">
                        <p style="font-size: 14px;font-weight: 700;">注意：</p>

                        <p>1. 附件不支持多页签(只会读取sheet1的数据)!</p>

                        <p>2. 如果数据不正确，将会全部失败!</p>

                    </div>
                    <div class="button-row" ng-cloak>
                        <a class="btn" ng-href="<%=contextPath%>/survey/template?type={{subjectType}}" target="_blank"
                           ng-disabled="!subjectType"
                           style="width: 180px;height: 50px;line-height: 50px;">下载<span>{{subjectType|subjectType}}</span>模板</a>
                        <button class="btn" ng-click="importData();" ng-disabled="!subjectType || !canImport"
                                style="margin-left:80px;width: 150px;">执行导入
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
<script type="text/javascript" src="<%=contextPath%>/app/knowledge/survey/survey.js"></script>
<script type="text/javascript" src="<%=contextPath%>/app/knowledge/survey/list/survey_import.js"></script>
</html>
