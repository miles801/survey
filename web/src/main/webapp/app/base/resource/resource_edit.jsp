<%--
  Created by IntelliJ IDEA.
  User: miles
  Date: 13-11-22
  Time: 下午3:20
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String contextPath = request.getContextPath();
%>
<c:set var="isAdd" value="${pageType eq null or pageType eq 'add'}" ></c:set >
<c:set var="isModify" value="${pageType ne null and pageType eq 'modify'}" ></c:set >
<c:set var="isDetail" value="${pageType ne null and pageType eq 'detail'}" ></c:set >
<!DOCTYPE html>
<html lang="en" >
<head >
    <title >编辑资源</title >
    <meta content="text/html" charset="utf-8" >
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8" />
    <!--[if IE]>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" >
    <![endif]-->
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/vendor/bootstrap-v3.0/css/bootstrap.min.css" >
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/style/standard/css/eccrm-common-new.css" >
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/vendor/angular-motion-v0.3.2/angular-motion.css" >
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/vendor/zTree/css/ztree.css" >
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/jquery-all.js" ></script >
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-all.js" ></script >
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-strap-all.js" ></script >
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-ztree-all.js" ></script >
    <script >
        window.angular.contextPathURL = "<%=contextPath%>";
    </script >
</head >
<body >
<div class="main" ng-app="eccrm.base.resource.edit" ng-controller="ResourceEditCtrl" >
    <div class="row-10 block" >
        <div class="block-header" >
                <span class="header-text" >
                    <span class="glyphicons circle_info" ></span >
                    <span >
                        <c:if test="${isAdd}" >新增资源</c:if >
                        <c:if test="${isModify}" >更新资源</c:if >
                        <c:if test="${isDetail}" >查看资源</c:if >
                    </span >
                </span >
                <span class="header-button" >
                    <c:if test="${isAdd}" >
                        <button type="button" class="btn btn-green btn-min"
                                ng-click="save()" ng-disabled="form.$invalid" ng-cloak >
                            <span class="glyphicons floppy_saved" ></span > 保存
                        </button >
                    </c:if >
                    <c:if test="${isModify}" >
                        <button type="button" class="btn btn-green btn-min"
                                ng-click="update()" ng-disabled="form.$invalid" ng-cloak >
                            <span class="glyphicons claw_hammer" ></span > 更新
                        </button >
                    </c:if >
                    <a type="button" class="btn btn-green btn-min"
                       ng-click="back();" >
                        <span class="glyphicons message_forward" ></span > 返回
                    </a >
                </span >
        </div >
        <div class="block-content" >
            <div class="content-wrap" >
                <form name="form" class="form-horizontal" role="form" >
                    <div style="display: none;" >
                        <input type="hidden" id="pageType" value="${pageType}" />
                        <input type="hidden" id="id" value="${id}" />
                    </div >
                    <div class="row" >
                        <div class="form-label col-1-half" >
                            <label >资源名称:</label >
                        </div >
                        <input type="text" name="name" class="col-2-half" placeholder="请输入资源名称" ng-model="resource.name"
                               validate validate-required validate-min-length="2" validate-max-length="20" maxlength="20" data-placement="right" />

                        <div class="form-label col-1-half" >
                            <label >资源编号:</label >
                        </div >
                        <input type="text" name="code" class="col-2-half" ng-model="resource.code"
                               validate validate-required validate-max-length="40" maxlength="40" />

                        <div class="form-label col-1-half" >
                            <label >资源类型:</label >
                        </div >
                        <select id="type" ng-model="resource.type" ng-options="foo.value as foo.name for foo in types" class="col-2" > </select >
                    </div >
                    <div class="row" >
                        <div class="form-label col-1-half" >
                            <label >显示顺序:</label >
                        </div >
                        <input type="number" name="sequenceNo" class="col-2-half" placeholder="请输入一个正整数..." ng-model="resource.sequenceNo"
                               validate validate-int validate-min-value="0" validate-max-value="999" />

                        <div class="form-label col-1-half" >
                            <label for="authorization" >需要授权:</label >
                        </div >
                        <span class="col-2-half" >
                            <input type="checkbox" class="col" id="authorization" ng-model="resource.authorization" value="true" />
                        </span >

                        <div class="form-label col-1-half" >
                            <label for="show" >显示:</label >
                        </div >
                        <input class="col" type="checkbox" id="show" ng-model="resource.show" value="true" />
                    </div >
                    <div class="row" ng-cloak ng-if="resource.type==='1'" >
                        <div class="form-label col-1-half" >
                            <label for="url" >URL:</label >
                        </div >
                        <input type="text" id="url" class="col-10" ng-model="resource.url" />
                    </div >
                    <div class="row" >
                        <div class="form-label col-1-half" >
                            <label >上级资源:</label >
                        </div >
                        <div class="col-2-half" >
                            <input type="text" class="col-12" ztree-single="ztreeOptions" ng-model="resource.parent.name" />
                            <span class="add-on" >
                                <i class="icons circle_fork icon" ng-click="resource.parent=null" title="清除" ></i >
                            </span >
                        </div >
                        <div class="form-label col-1-half" >
                            <label >所属模块:</label >
                        </div >
                        <select class="col-2-half" ng-model="resource.module" ng-options="foo.value as foo.name for foo in modules" ></select >
                    </div >
                    <div class="row" >
                        <div class="form-label col-1-half" >
                            <label >备注:</label >
                        </div >
                        <textarea rows="6" class="col-10" ng-model="resource.description" maxlength="2000" ></textarea >
                    </div >
                    <div class="row" style="margin-top: 5px;" >
                        <div class="form-label col-1-half" >
                            <label >采集人:</label >
                        </div >
                        <span class="col-2-half" ng-bind-template="{{resource.creatorName}}" ></span >

                        <div class="form-label col-1-half" >
                            <label >采集时间:</label >
                        </div >
                        <span class="col-2-half" ng-bind-template="{{resource.createdDatetime | eccrmDatetime}}" ></span >

                        <div class="form-label col-1-half" >
                            <label >状态:</label >
                        </div >
                        <div class="col-2" >
                            <select ng-model="resource.status" ng-options="foo.value as foo.name for foo in status" > </select >
                        </div >
                    </div >
                </form >
            </div >
        </div >
    </div >
</div >
</body >

<script type="text/javascript" src="<%=contextPath%>/app/base/resource/resource.js" ></script >
<script type="text/javascript" src="<%=contextPath%>/app/base/resource/resource_edit.js" ></script >
</html >