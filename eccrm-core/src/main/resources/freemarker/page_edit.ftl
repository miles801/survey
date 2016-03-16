<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en" >

<head >
    <title>编辑${cnName}</title>
    <meta content="text/html" charset="utf-8" >
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/vendor/bootstrap-v3.0/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/style/standard/css/eccrm-common-new.css">
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/vendor/zTree/css/ztree.css" >
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/jquery-all.js" ></script>
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-all.js" ></script>
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-strap-all.js" ></script>
<#if editPage.tree!false = true>
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-ztree-all.js" ></script>
</#if>
<#if editPage.datepicker!false = true>
    <script type="text/javascript" src="<%=contextPath%>/vendor/My97DatePicker/WdatePicker.js" ></script>
</#if>
    <script type="text/javascript" >
        window.angular.contextPathURL = "<%=contextPath%>";
    </script>
</head >
<body >
<div class="main" ng-app="${name}.${entity}.edit" ng-controller="Ctrl" >
    <div class="block" >
        <div class="block-header" >
                <span class="header-text" >
                    <span class="glyphicons info-sign" ></span >
                <#if editPage.headerText??>
                    <span >${editPage.headerText}</span >
                </#if>
                </span >
                <span class="header-button" >
                <#if editPage.headerButtons?? >
                    <#list editPage.headerButtons! as btn>
                        <button type="button" class="btn btn-green btn-min" >
                            <span class="glyphicons ${btn.icon!}" ></span > ${btn.name!}
                        </button >
                    </#list>
                <#else>
                    <c:if test="${r"${pageType eq 'add'}"}">
                    <button type="button" class="btn btn-green btn-min" ng-click="save()" ng-disabled="!form.$valid" >
                        <span class="glyphicons disk_save" ></span > 保存
                    </button >
                    <#if editPage.saveAndNew! = true>
                        <button type="button" class="btn btn-green btn-min" ng-click="save(true)" ng-disabled="!form.$valid" >
                            <span class="glyphicons disk_open" ></span > 保存并新建
                        </button >
                    </#if>
                    </c:if>
                    <c:if test="${r"${pageType eq 'modify'}"}">
                    <button type="button" class="btn btn-green btn-min" ng-click="update()" ng-disabled="!form.$valid" >
                        <span class="glyphicons claw_hammer" ></span > 更新
                    </button >
                    </c:if>
                </#if>
                    <a type="button" class="btn btn-green btn-min" ng-click="back();" >
                        <span class="glyphicons message_forward" ></span > 返回
                    </a >
                </span >
        </div >
        <div class="block-content" >
            <div class="content-wrap" >
                <form name="form" class="form-horizontal" role="form" >
                    <div style="display: none;" >
                        <input type="hidden" id="pageType" value="${r"${pageType}"}" />
                        <input type="hidden" id="id" value="${r"${id}"}" />
                    </div >
                <#list editPage.formRows as row>
                    <div class="row" >
                        <#list row.elements as element>
                            <#include "element.ftl">
                        </#list>
                    </div >
                </#list>
                </form >
            </div >
        </div >
    </div >
</div >
</body >
<script type="text/javascript" src="<%=contextPath%>/app/${path}/${entity}.js" ></script>
<script type="text/javascript" src="<%=contextPath%>/app/${path}/edit/${entity}_edit.js" ></script>
</html >