<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en" >
<head >
    <title>试卷预览</title>
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/vendor/bootstrap-v3.0/css/bootstrap.min.css" >
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/style/standard/css/eccrm-common-new.css" >
    <link rel="stylesheet" href="<%=contextPath%>/vendor/angular-motion-v0.3.2/angular-motion.min.css" />

    <script type="text/javascript" src="<%=contextPath%>/vendor/jquery-v1.8.3/jquery.min.js" ></script >
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-all.js" ></script >
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-strap-all.js" ></script >
    <script type="text/javascript" src="<%=contextPath%>/app/knowledge/survey/survey.js" ></script >
    <style >
        input + span {
            margin-left: 8px;
        }

        .survey {
            padding: 5px 15px;
        }

        .survey .title {
            text-indent: 2em;
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
    </style >
    <script type="text/javascript" >
        window.angular.contextPathURL = '<%=contextPath%>';
    </script >
</head >
<body >
<div class="main" ng-app="eccrm.knowledge.survey.preview" ng-controller="SurveyPreviewCtrl" style="overflow: auto;margin: 5px;" >
    <div class="dn" >
        <input type="hidden" id="surveyId" value="${beans.id}" />
        <input type="hidden" id="multiPage" value="${beans.multiPage}" />
        <input type="hidden" id="surveyName" value="${beans.name}" />
        <input type="hidden" id="pageType" value="${pageType}" />
        <input type="hidden" id="businessId" value="${businessId}" />
        <input type="hidden" id="userId" value="${userId}" />
        <input type="hidden" id="userName" value="${userName}" />
        <input type="hidden" id="userType" value="${userType}" />
        <input type="hidden" id="batchId" value="${batchId}" />
    </div >
    <div class="row" >
        <h3 style="text-align: center;" >${beans.name}</h3 >
    </div >
    <div class="row" style="padding: 5px 15px;text-indent:4em" ng-if="${beans.showNavPage}" ng-cloak >
        ${beans.navContent}
    </div >
    <div class="ycrl split" ></div >
    <div class="row" >
        <form role="form" name="form" >
            <div class="row survey" bindonce ng-repeat="b in beans" >

                <div class="title" ng-class="{required:b.isRequired==true}" bo-html="((multiPage?index:$index)+1)+'. '+b.title" ></div >
                <%-- 单选 --%>
                <ul class="subject" ng-if="b.subjectType=='1'" >
                    <li bindonce ng-repeat="i in b.items" ng-class="{oneline:b.showList===true}" >
                        <label ><input type="radio" name="{{b.id}}" ng-model="b.answer" ng-value="i.id" /><span bo-text="i.name" ></span ></label >
                    </li >
                </ul >

                <%-- 多选 --%>
                <ul class="subject" ng-if="b.subjectType=='2'" >
                    <li bindonce ng-repeat="i in b.items" ng-class="{oneline:b.showList===true}" >
                        <label ><input type="checkbox" name="{{b.id}}" ng-model="i.isSelected" /><span bo-text="i.name" ></span ></label >
                    </li >
                </ul >

                <%-- 判断 --%>
                <div class="subject" ng-if="b.subjectType=='3'" >
                <span >
                <label ><input type="radio" name="{{b.id}}" ng-model="b.answer" ng-value="true" /> 正确</label >
                </span >
                <span style="margin-left: 20px;" >
                <label ><input type="radio" name="{{b.id}}" ng-model="b.answer" ng-value="false" /> 错误</label >
                </span >
                </div >

                <%-- 填空 --%>
                <div class="subject" ng-if="b.subjectType=='4'" >
                    <span >答：</span ><input type="text" eccrm-aw style="width: 240px;padding: 2px 8px;" ng-model="b.answer" />
                </div >
            </div >
        </form >
    </div >
    <div class="row" style="padding: 20px;text-align: center;" ng-cloak >
        <button class="btn btn-blue fl" style="height: 30px;" ng-if="multiPage" ng-disabled="!(index>0)" ng-click="prev();" >上一页</button >
        <button class="btn btn-blue" style="height: 30px;margin-left: -30px;" ng-click="commitAnswer()" ng-disabled="form.$invalid" ng-if="pageType=='ANSWER'" >提交答案</button >
        <button class="btn btn-blue fr" style="height: 30px;" ng-if="multiPage" ng-disabled="!(index<total-1)" ng-click="next();" >下一页</button >
    </div >
</div >
<script type="text/javascript" src="<%=contextPath%>/app/knowledge/survey/preview.js" ></script >
</body >
</html >