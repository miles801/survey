<%--
Created by IntelliJ IDEA.
User: chenl
Date: 2014-01-24 11:05:40
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="isAdd" value="${pageType eq null or pageType eq 'add'}"></c:set>
<c:set var="isModify" value="${pageType ne null and pageType eq 'modify'}"></c:set>
<c:set var="isDetail" value="${pageType ne null and pageType eq 'detail'}"></c:set>
<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html >
<html>
<head>
    <title>消息查看</title>
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8"/>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/vendor/artDialog/artDialog.css"/>
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/vendor/bootstrap-v3.0/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/style/standard/css/eccrm-common-new.css">
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/vendor/angular-motion-v0.3.2/angular-motion.css">
    <link rel="stylesheet" href="<%=contextPath%>/vendor/ueditor/themes/default/css/ueditor.css"/>
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/jquery-all.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-all.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-strap-all.js"></script>

    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-ztree-all.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/app/home/news/onlinetalk/onlinetalk-modal.js"></script>
    <script src="<%=contextPath%>/static/ycrl/javascript/upload.js"></script>
    <script src="<%=contextPath%>/vendor/ueditor/ueditor.config.js"></script>
    <script src="<%=contextPath%>/vendor/ueditor/ueditor.all.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/app/base/user/user.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/app/base/usergroup/usergroup.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/app/home/news/onlinetalk/list/onlinetalk_list2.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/app/home/news/onlinetalk/onlinetalk.js"></script>
    <script type="text/javascript">
        window.angular.contextPathURL = '<%=contextPath%>';
    </script>
</head>
<body>

<div class="main tab" ng-app="eccrm.home.onlinetalk.list"
     ng-controller="OnlinetalkListController">
    <div eccrm-alert="alert"></div>
    <div class="row-10">
        <div class="block">
            <div class="block-header">
					<span class="header-text"> <span
                            class="glyphicons circle_info"></span> <span>查看消息</span> </span> <span
                    class="header-button">
						<a type="button" class="btn btn-sm btn-green btn-min"
                           ng-click="back();"> <span class="glyphicons message_forward"></span>
                            返回 </a> </span>
            </div>
            <div class="block-content">
                <%--表单按照1.5:2.5:1.5:2.5:2的比例进行排列--%>
                <div class="content-wrap">
                    <form name="form" class="form-horizontal" role="form">
                        <div style="display: none;">
                            <input type="hidden" id="id" value="${id}"/>
                            <input type="hidden" id="pageType" value="${pageType}"/>
                        </div>

                        <div class="row">
                            <div class="form-label col-1-half">
                                <label>发件人:</label>
                            </div>
                            <span class="col-2-half" ng-bind-template="{{beans.creatorName}}"></span>

                            <div class="form-label col-1-half">
                                <label>发件时间:</label>
                            </div>
                            <span class="col-2"
                                  ng-bind-template="{{beans.createdDatetime | date:'yyyy-MM-dd HH:mm:ss'}}"></span>

                        </div>


                        <div class="row">
                            <div class="form-label col-1-half">
                                <label>内容:</label>
                            </div>
                            <div class="col-10">
                                <script id="container" ng-bind-template="{{beans.content}}" ng-cloak
                                        type="text/plain"></script>
                            </div>
                        </div>
                        <div class="row">
                            <div class="form-label col-1-half">
                                <label>附件:</label>
                            </div>
                            <div class="col-10">
                                <div class="row">
                                    <table class="tableGrid" id="fileTable">
                                        <tr>
                                            <th style="display:none;"></th>
                                            <th style="width: 40%;">附件名称</th>
                                            <th style="width: 20%;">附件大小</th>
                                            <th style="width: 25%;">上传时间</th>
                                            <th>操作</th>
                                        </tr>
                                        <tr ng-cloak ng-show="!attachments || !attachments.length">
                                            <td colspan="4" style="text-align: center;">无附件!</td>
                                        </tr>
                                        <tr ng-repeat="at in attachments">
                                            <td style="display: none;">{{at.id}}</td>
                                            <td>{{at.fileName}}</td>
                                            <td>{{(at.size/1000 || 0)|number:3}}KB</td>
                                            <td>{{at.uploadTime | eccrmDatetime}}</td>
                                            <td>
                                                <a title="删除附件" ng-click="deleteAttachment($index,at.id)"
                                                   style="cursor: pointer;">删除</a>
                                                <a title="下载"
                                                   ng-href="<%=contextPath%>/attachment/download?id={{at.id}}"
                                                   target="_blank" style="cursor: pointer;">下载</a>
                                            </td>
                                        </tr>
                                    </table>
                                </div>
                            </div>


                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

</div>
</body>
</html>