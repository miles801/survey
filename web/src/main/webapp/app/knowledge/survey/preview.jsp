<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>试卷预览</title>
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/vendor/bootstrap-v3.0/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/style/standard/css/eccrm-common-new.css">
    <link rel="stylesheet" href="<%=contextPath%>/vendor/angular-motion-v0.3.2/angular-motion.min.css"/>

    <script type="text/javascript" src="<%=contextPath%>/vendor/jquery-v1.8.3/jquery.min.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-all.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/vendor/angular-v1.2.9/angular-animate.min.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-strap-all.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/app/knowledge/survey/survey.js"></script>
    <style>
        input + span {
            margin-left: 8px;
        }

        .survey {
            padding: 5px 15px;
        }

        .survey .title {
            text-indent: 2em;
        }

        .survey .title > p {
            display: inline-block;
            margin-bottom: 0;
            text-align: left;
            padding-left: 0;
            text-indent: 5px;
            font-size: 14px;
        }

        .survey ul {
            list-style: none;
        }

        .survey li.oneline {
            float: left;
            margin-left: 25px;
        }

        .survey li.oneline:first-child {
            margin-left: 0;
        }

        .survey .subject {
            padding: 8px 8px 8px 40px;
        }

        .survey .required:after {
            content: '(必填)';
            color: #FF0000;
            margin-left: 8px;
        }

        .desc {
            padding: 10px 25px;
            width: 420px;
            float: left;
        }

        .article-box {
            padding: 10px 0 0 10px;
            float: left;
            min-height: 300px;
            position: relative;
            border-left: 1px solid #ddd;
        }

        .desc .row > span {
            display: inline-block;
            width: 120px;
            height: 24px;
            line-height: 24px;
        }

        .item {
            width: 30px;
            height: 30px;
            float: left;
            margin: 8px;
            border: 1px solid #dcdcdc;
            text-align: center;
            vertical-align: middle;
            padding-top: 5px;
            cursor: pointer;
        }

        .item.active {
            border-bottom: 4px solid red;
        }

        .item.hasAnswer {
            color: #fff;
            background-color: #00B83F;
        }

        .button-row .btn {
            width: 120px;
            float: left;
            margin-left: 20px;
        }

        .error {
            font-weight: 700;
            color: red;
        }
    </style>
    <script type="text/javascript">
        window.angular.contextPathURL = '<%=contextPath%>';
    </script>
</head>
<body>
<div class="main" ng-app="eccrm.knowledge.survey.preview" ng-controller="SurveyPreviewCtrl"
     style="overflow: auto;margin: 5px;">
    <div class="dn">
        <input type="hidden" id="surveyId" value="${beans.id}"/>
        <input type="hidden" id="surveyReportId" value="${surveyReportId}"/>
        <input type="hidden" id="surveyName" value="${beans.name}"/>
        <input type="hidden" id="pageType" value="${pageType}"/>
    </div>
    <div class="row">
        <h3 style="text-align: center;">${beans.name}</h3>
    </div>
    <div class="row" style="padding: 5px 15px;text-indent:4em" ng-if="${beans.showNavPage}" ng-cloak>
        ${beans.navContent}
    </div>
    <div class="ycrl split"></div>
    <div class="row">
        <div class="desc" ng-cloak>
            <div class="row">
                <span>总题数：{{beans.totalSubjects}}</span>
                <span>总分数：{{beans.totalScore}}</span>
            </div>
            <div class="row" ng-if="beans.xzCounts>0">
                <div class="row">单选题：(每题{{beans.xzScore}} 分)</div>
            </div>
            <div class="row" ng-if="beans.dxCounts>0">
                <div class="row">多选题：(每题{{beans.dxScore}} 分)</div>
            </div>
            <div class="row" ng-if="beans.pdCounts>0">
                <div class="row">判断题：(每题{{beans.pdScore}} 分)</div>
            </div>
            <div class="row" ng-if="beans.tkCounts>0">
                <div class="row">填空题：(每题{{beans.tkScore}} 分)</div>
            </div>
            <div class="item" ng-class="{active:$index==$parent.index,hasAnswer:!!s.answer}" bindonce
                 ng-repeat="s in subjects" ng-click="changeIndex($index);">
                <span>{{$index+1}}</span></div>

        </div>
        <div class="article-box">
            <form role="form" name="form">
                <div class="row survey" bindonce ng-repeat="subject in subjects"
                     ng-show="$index==$parent.index || subject.error">
                    <span ng-cloak style="position: absolute;">{{$index+1}}. </span>
                    <div class="title" bo-html="subject.title"></div>
                    <%-- 单选 --%>
                    <ul class="subject" ng-if="subject.subjectType=='1'" ng-cloak>
                        <li bindonce ng-repeat="i in subject.items" ng-class="{oneline:subject.showList===true}">
                            <label>
                                <input type="radio" name="{{subject.id}}" ng-model="subject.answer" ng-value="i.id"
                                       validate validate-required/>
                                <span bo-text="i.name"></span>
                            </label>
                            <span ng-show="subject.error && subject.rightAnswer==i.id"><i
                                    class="icons icon yes"></i></span>
                        </li>
                    </ul>

                    <%-- 多选 --%>
                    <ul class="subject" ng-if="subject.subjectType=='2'" ng-cloak>
                        <li bindonce ng-repeat="i in subject.items" ng-class="{oneline:subject.showList===true}">
                            <label>
                                <input type="checkbox" name="{{subject.id}}" ng-model="i.isSelected" validate
                                       validate-required ng-change="changeValue();"/>
                                <span bo-text="i.name"></span>
                            </label>
                            <span ng-show="subject.error && i.isRight"><i class="icons icon yes"></i></span>
                        </li>
                    </ul>

                    <%-- 判断 --%>
                    <div class="subject" ng-if="subject.subjectType=='3'" ng-cloak>
                            <span>
                                <label><input type="radio" name="{{subject.id}}" ng-model="subject.answer" value="true"
                                              validate validate-required/> 正确</label>
                                <span ng-show="subject.error && subject.rightAnswer=='true'"><i
                                        class="icons icon yes"></i></span>
                            </span>
                            <span style="margin-left: 20px;">
                                <label><input type="radio" name="{{subject.id}}" ng-model="subject.answer" value="false"
                                              validate validate-required/> 错误</label>
                                <span ng-show="subject.error && subject.rightAnswer=='false'"><i
                                        class="icons icon yes"></i></span>
                            </span>
                    </div>

                    <%-- 填空 --%>
                    <div class="subject" ng-if="subject.subjectType=='4'" ng-cloak>
                        <span>答： </span>
                        <input type="text" eccrm-aw style="width: 240px;padding: 2px 8px;" ng-model="subject.answer"
                               validate validate-required/>
                        <span style="margin-left:10px;" ng-show="subject.error" class="error">正确答案: {{subject.rightAnswer}}</span>

                    </div>
                    <%-- 简答 --%>
                    <div class="subject" ng-if="subject.subjectType=='5'" ng-cloak>
                        <span>答：</span><textarea eccrm-aw style="width: 240px;padding: 2px 8px;"
                                                 ng-model="subject.answer" validate validate-required></textarea>
                    </div>
                </div>
                <div class="row text-center" ng-if="finish" ng-cloak>
                    <h5 style="color: #E50000; font-size: 20px;">答题结束：总得分 {{score}} 分</h5>
                </div>
                <div class="row button-row" ng-if="!finish" style="padding: 20px;text-align: center;" ng-cloak>
                    <button class="btn btn-blue" ng-show="index>0"
                            ng-click="prev();">上一题
                    </button>
                    <button class="btn btn-blue" ng-click="commitAnswer()"
                            ng-disabled="form.$invalid" ng-if="pageType=='ANSWER'">提交答案
                    </button>
                    <button class="btn btn-blue" ng-show="index<beans.totalSubjects-1"
                            ng-click="next();">下一题
                    </button>
                </div>
            </form>
        </div>
    </div>
</div>
<script type="text/javascript" src="<%=contextPath%>/app/knowledge/survey/preview.js"></script>
</body>
</html>