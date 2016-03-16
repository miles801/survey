<%--
Created by IntelliJ IDEA.
User: ${author!}
Date: ${current!}
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en" >
<head >
    <base href="<%=request.getContextPath()%>/" >
    <title >${cnName}列表</title >
    <script type="text/javascript" src="vendor/jquery-v1.8.3/jquery.min.js" ></script >
    <script type="text/javascript" src="vendor/angular-v1.2.9/angular.min.js" ></script >
    <script type="text/javascript" src="vendor/angular-v1.2.9/angular-resource.min.js" ></script >
    <script type="text/javascript" src="vendor/angular-v1.2.9/angular-animate.min.js" ></script >
    <script type="text/javascript" src="vendor/angularstrap-v2.0/angular-strap.min.js" ></script >
    <script type="text/javascript" src="vendor/angularstrap-v2.0/angular-strap.tpl.min.js" ></script >
<#if listPage.highcharts?? && listPage.highcharts == true>
    <script type="text/javascript" src="vendor/highcharts3.0.8/js/highcharts.js" ></script >
</#if>

    <link rel="stylesheet" type="text/css" href="vendor/bootstrap-v3.0/css/bootstrap.min.css" >
    <link rel="stylesheet" type="text/css" href="style/standard/css/eccrm-common-new.css" >
    <link rel="stylesheet" href="vendor/angular-motion-v0.3.2/angular-motion.min.css" />
    <link rel="stylesheet" type="text/css" href="vendor/zTree/css/ztree.css" >
</head >
<body >
<div class="main <#if listPage.conditionRows??>condition-row-<#if listPage.conditionRows &gt; 3 >3<#else>${listPage.conditionRows}</#if></#if>" ng-app="eccrm.${name}.${entity}.list" ng-controller="${className}ListController" >
    <div eccrm-alert="alert" ></div >
    <div class="row panel panel-tree" >
        <%-- 左侧树 --%>
        <div class="tree" >
            <div class="tree-content" >
                <ul id="treeDemo" class="ztree" ></ul >
            </div >
            <div class="scroller" ></div >
        </div >
        <%-- 右侧正文内容 --%>
        <div class="content" >
            <%-- 订单结果列表 --%>
            <div class="list-condition">
                <div class="block" >
                    <div class="block-header" >
                        <span class="header-text" >
                            <span class="glyphicons list" ></span >
                        <#if listPage.headerText??>
                            <span >${listPage.headerText}</span >
                        <#else>
                            <span >${cnName}查询</span >
                        </#if>
                        </span >
                        <span class="header-button" >
                            <#if listPage.headerButtons??>
                                <#list listPage.headerButtons! as btn>
                                    <button type="button" class="btn btn-green btn-min" >
                                        <span class="glyphicons ${btn.icon!}" ></span > ${btn.name!}
                                    </button >
                                </#list>
                            </#if>
                            <#if listPage.queryBarButtons??>
                                <#list listPage.queryBarButtons as button>
                                    <#if button.url?? >
                                        <a type="button" class="btn btn-green btn-min" ng-href="${button.url!'#'}" >
                                            <#if button.icon??>
                                                <span class="glyphicons ${button.icon}" ></span >
                                            </#if>
                                        ${button.name}
                                        </a >
                                    <#else>
                                        <button type="button" class="btn btn-green btn-min" ng-click="${button.click!''}" >
                                            <#if button.icon??>
                                                <span class="glyphicons ${button.icon}" ></span >
                                            </#if>
                                        ${button.name}
                                        </button >
                                    </#if>
                                </#list>
                            </#if>
                        </span >
                    </div >
                    <div class="block-content" >
                    <#if listPage.queryConditionRows??>
                    <#--查询信息栏-->
                        <div class="content-wrap">
                            <#list listPage.queryConditionRows as row>
                                <div class="row" >
                                    <#list (row.elements)! as element>
                                    <#include "element.ftl">
                                </#list>
                                </div >
                            </#list>
                        </div>
                    </#if>
                    </div >
                </div >
            </div>
            <div class="list-result">
                <div class="block">
                <#if listPage.tableHeaderText?? || listPage.tableHeaderButtons??>
                    <div class="block-header" >
                        <#if listPage.tableHeaderText??>
                            <div class="header-text" >
                                <span class="glyphicons list" ></span >
                                <span >${listPage.tableHeaderText}</span >
                            </div >
                        </#if>
                        <#if listPage.tableHeaderButtons??>
                            <div class="header-button" >
                                <#list listPage.tableHeaderButtons as btn>
                                    <a type="button" class="btn btn-green btn-min" ng-href="${btn.url!}" >
                                        <#if btn.icon??>
                                            <span class="glyphicons ${btn.icon}" ></span >
                                        </#if>
                                    ${btn.name}
                                    </a >
                                </#list>
                            </div >
                        </#if>
                    </div >
                </#if>
                    <div class="table-responsive panel panel-table" >
                        <table class="table table-striped table-hover" >
                            <thead class="table-header" >
                            <tr >
                            <#if listPage.allowCheckbox! = true>
                                <td >
                                    <input type="checkbox" style="height: 12px;" ng-model="checkAll" />
                                </td >
                            </#if>
                            <#if listPage.items??>
                                <#list listPage.items as item>
                                    <td >${item}</td >
                                </#list>
                            </#if>
                            </tr >
                            </thead >
                            <tbody class="table-body" >
                            <tr ng-show="!${entity}s || !${entity}s.data || ${entity}s.data.length == 0" >
                            <#if listPage.allowCheckbox! = true>
                                <td colspan="${listPage.items?size+1}" class="text-center" >没有查询到数据！</td >
                            <#else>
                                <td colspan="${listPage.items?size}" class="text-center" >没有查询到数据！</td >
                            </#if>
                            </tr >
                            </tbody >
                        </table >
                    </div >
                </div>
            </div>
        <#if listPage.allowPager?? && listPage.allowPager == true>
            <div eccrm-page="pager" class="list-pagination"></div >
        </#if>
        </div >
    </div >
</div>
</body >
<script type="text/javascript" src="app/${path}/${entity}.js" ></script >
<script type="text/javascript" src="app/${path}/list/${entity}_list.js" ></script >

</html >