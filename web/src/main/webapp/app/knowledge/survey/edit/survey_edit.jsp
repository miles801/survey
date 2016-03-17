<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>编辑试卷</title>
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/vendor/bootstrap-v3.0/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/style/standard/css/eccrm-common-new.css">
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/vendor/angular-motion-v0.3.2/angular-motion.css">
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/vendor/zTree/css/ztree.css">

    <script type="text/javascript" src="<%=contextPath%>/vendor/kindeditor-4.1.10/kindeditor-min.js"
            charset="utf-8"></script>
    <script type="text/javascript" src="<%=contextPath%>/vendor/kindeditor-4.1.10/lang/zh_CN.js"
            charset="utf-8"></script>

    <script type="text/javascript" src="<%=contextPath%>/vendor/jquery-v1.8.3/jquery.min.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-all.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-strap-all.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-ztree-all.js"></script>

    <script type="text/javascript" src="<%=contextPath%>/vendor/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/app/knowledge/survey/survey.js"></script>
    <script type="text/javascript">
        window.angular.contextPathURL = '<%=contextPath%>';
    </script>
</head>
<body>
<div class="main" ng-app="eccrm.knowledge.survey.edit" ng-controller="SurveyEditCtrl" style="overflow: auto;">
    <div style="display: none;">
        <input type="hidden" id="pageType" value="${pageType}"/>
        <input type="hidden" id="id" value="${id}"/>
    </div>
    <div class="row">
        <div class="block">
            <div class="block-header">
            <span class="header-text">
            </span>

                <div class="header-button">
                    <button type="button" class="btn btn-green btn-min" ng-if="pageType=='add'" ng-cloak
                            ng-click="save()" ng-disabled="!form.$valid">
                        <span class="glyphicons floppy_saved"></span> 保存
                    </button>
                    <button type="button" class="btn btn-green btn-min" ng-if="pageType=='modify'" ng-cloak
                            ng-click="update()" ng-disabled="!form.$valid">
                        <span class="glyphicons floppy_saved"></span> 更新
                    </button>
                    <button type="button" class="btn btn-sm btn-green btn-min dn"
                            ng-if="pageType=='add'|| pageType=='modify'"
                            ng-click="save(true)" ng-disabled="!form.$valid && beans.status=='ACTIVE'">
                        <span class="glyphicons send"></span> 发布
                    </button>
                    <a type="button" class="btn btn-sm btn-green btn-min" ng-click="back();">
                        <span class="glyphicons message_forward"></span> 返回
                    </a>
                </div>
            </div>
            <div class="block-content">
                <div class="content-wrap">
                    <form name="form" class="form-horizontal" role="form">
                        <div class="row">
                            <div class="form-label col-1-half">
                                <label>试卷名称:</label>
                            </div>
                            <input class="col-10-half" type="text" ng-model="beans.name" validate validate-required
                                   validate-max-length="100"/>

                            <div class="form-label col-1-half dn">
                                <label>试卷类型:</label>
                            </div>
                        </div>
                        <div class="row">
                            <div class="form-label col-1-half">
                                <label>生效时间:</label>
                            </div>
                            <div class="col-2-half">
                                <input type="text" class="col-12" ng-model="beans.startTime" readonly
                                       validate validate-required
                                       eccrm-my97="{el:'startTime',dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'endTime\')}'}"
                                       id="startTime"/>
                                <span class="add-on">
                                    <i class="icons clock icon" title="清除时间" ng-click="beans.startDate=undefined;"></i>
                                </span>
                            </div>

                            <div class="form-label col-1-half">
                                <label>截止时间:</label>
                            </div>
                            <div class="col-2-half">
                                <input class="col-12" type="text" ng-model="beans.endTime"
                                       validate validate-required
                                       readonly
                                       eccrm-my97="{el:'endTime',dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'startTime\')}'}"
                                       id="endTime"/>
                                <span class="add-on">
                                    <i class="icons clock icon" title="清除时间" ng-click="beans.endDate=undefined;"></i>
                                </span>
                            </div>

                        </div>
                        <div class="row">
                            <div class="form-label col-1-half"></div>
                            <div class="col-10-half">
                                <label style="cursor: pointer;margin-right: 10px;">
                                    <input ng-model="beans.showNavPage" type="checkbox"/> 显示前导页
                                </label>
                                <label style="cursor: pointer;margin-right: 10px;">
                                    <input ng-model="beans.multiPage" type="checkbox"/> 多页显示试卷
                                </label>
                                <label style="cursor: pointer;margin-right: 10px;">
                                    <input ng-model="beans.showResult" type="checkbox"/> 允许查看结果
                                </label>

                            </div>
                        </div>
                        <div class="row">
                            <div class="form-label col-1-half">
                                <label>单选题个数:</label>
                            </div>
                            <input class="col-2-half" type="text" ng-model="beans.xzCounts" validate validate-int/>

                            <div class="form-label col-1-half">
                                <label>单题分数:</label>
                            </div>
                            <input class="col-2-half" type="text" ng-model="beans.xzScore" validate validate-int/>
                            <div class="form-label col-1-half">
                                <label>单选题总分数:</label>
                            </div>
                            <span class="col-2-half" ng-cloak>{{beans.xzCounts*beans.xzScore}}</span>
                        </div>
                        <div class="row">
                            <div class="form-label col-1-half">
                                <label>多选题个数:</label>
                            </div>
                            <input class="col-2-half" type="number" ng-model="beans.dxCounts" validate validate-int/>

                            <div class="form-label col-1-half">
                                <label>单题分数:</label>
                            </div>
                            <input class="col-2-half" type="number" ng-model="beans.dxScore" validate validate-int/>
                            <div class="form-label col-1-half">
                                <label>多选题总分数:</label>
                            </div>
                            <span class="col-2-half" ng-cloak>{{beans.dxCounts*beans.dxScore}}</span>
                        </div>
                        <div class="row">
                            <div class="form-label col-1-half">
                                <label>判断题个数:</label>
                            </div>
                            <input class="col-2-half" type="number" ng-model="beans.pdCounts" validate validate-int/>

                            <div class="form-label col-1-half">
                                <label>单题分数:</label>
                            </div>
                            <input class="col-2-half" type="number" ng-model="beans.pdScore" validate validate-int/>
                            <div class="form-label col-1-half">
                                <label>判断题总分数:</label>
                            </div>
                            <span class="col-2-half" ng-cloak>{{beans.pdCounts*beans.pdScore}}</span>
                        </div>
                        <div class="row">
                            <div class="form-label col-1-half">
                                <label>填空题个数:</label>
                            </div>
                            <input class="col-2-half" type="number" ng-model="beans.tkCounts" validate validate-int/>

                            <div class="form-label col-1-half">
                                <label>单题分数:</label>
                            </div>
                            <input class="col-2-half" type="number" ng-model="beans.tkScore" validate validate-int/>
                            <div class="form-label col-1-half">
                                <label>填空题总分数:</label>
                            </div>
                            <span class="col-2-half" ng-cloak>{{beans.tkCounts*beans.tkScore}}</span>
                        </div>
                        <div class="row">
                            <div class="form-label col-1-half">
                                <label>简答题个数:</label>
                            </div>
                            <input class="col-2-half" type="number" ng-model="beans.jdCounts" validate validate-int/>

                            <div class="form-label col-1-half">
                                <label>单题分数:</label>
                            </div>
                            <input class="col-2-half" type="number" ng-model="beans.jdScore" validate validate-int/>
                            <div class="form-label col-1-half">
                                <label>简答题总分数:</label>
                            </div>
                            <span class="col-2-half" ng-cloak>{{beans.jdCounts*beans.jdScore}}</span>
                        </div>
                        <div class="row">
                            <div class="form-label col-1-half">
                                <label>总题数:</label>
                            </div>
                            <span class="col-2-half" ng-cloak>{{beans.xzCounts+beans.dxCounts+beans.pdCounts+beans.tkCounts+beans.jdCounts}} 题</span>
                            <div class="form-label col-1-half">
                                <label>总分数:</label>
                            </div>
                            <span class="col-2-half" ng-cloak>{{beans.xzTotalScore+beans.dxTotalScore+beans.pdTotalScore+beans.tkTotalScore+beans.jdTotalScore}} 分</span>
                        </div>
                        <div class="row" ng-cloak>
                            <div class="form-label col-1-half">
                                <label for="navPageContent">前导页内容:</label>
                            </div>
                            <div class="col-10-half">
                                <textarea id="navPageContent" class="col-12" rows="10"></textarea>
                            </div>
                        </div>
                        <div class="row">
                            <div class="form-label col-1-half">
                                <label>采集人:</label>
                            </div>
                            <span ng-bind-template="{{ beans.creatorName }}" class="col-2-half"></span>

                            <div class="form-label col-1-half">
                                <label>采集时间:</label>
                            </div>
                            <span ng-bind-template="{{ beans.createdDatetime | date:'yyyy-MM-dd HH:mm:ss' }}"
                                  class="col-2-half"></span>

                            <div class="form-label col-1-half">
                                <label>状态:</label>
                            </div>
                            <div class="col-2-half">
                                <select ng-model="beans.status"
                                        ng-options="foo.value as foo.name for foo in status"> </select>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <div class="row dn" style="height: 600px;margin-top:20px;">
        <iframe id="iframe" style="display: none;" name="iframe" frameborder="0"></iframe>
        <div id="tab" style="height: 100%;width: 100%;overflow: hidden;"></div>
    </div>
</div>
</body>

<script type="text/javascript" src="<%=contextPath%>/app/knowledge/survey/edit/survey_edit.js"></script>
</html>