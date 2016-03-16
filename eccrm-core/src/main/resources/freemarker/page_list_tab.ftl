<%--
Created by IntelliJ IDEA.
<#if author??>
User: ${author}
</#if>
<#if current??>
Date: ${current}
</#if>
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en" >
<head >
    <base href="<%=request.getContextPath()%>/" >
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

</head >
<body >
<div class="main <#if listPage.conditionRows??>condition-row-<#if listPage.conditionRows &gt; 3 >3<#else>${listPage.conditionRows}</#if></#if>"
     ng-app="eccrm.${name}.${entity}.list"
     ng-controller="${className}ListController">
    <div class="tab-wrap" >
    <#if listPage.queryConditionRows??>
        <div class="list-condition">
            <#list listPage.queryConditionRows as row>
            <div class="row" >
                <#list (row.elements)! as element>
                    <#include "element.ftl">
                </#list>
            </div >
            </#list>
        </div>
    </#if>
        <div class="list-result">
            <div class="block" >
            <#if listPage.headerText?? || listPage.headerButtons??>
                <div class="block-header" >
                    <#if listPage.headerText??>
                        <div class="header-text" >
                            <span class="glyphicons list" ></span >
                            <span >${listPage.headerText}</span >
                        </div >
                    </#if>
                    <div class="header-button" >
                        <#if listPage.tableHeaderButtons??>
                            <#list listPage.tableHeaderButtons as btn>
                                <a type="button" class="btn btn-green btn-min" ng-href="${btn.url!}" >
                                    <#if btn.icon??>
                                        <span class="glyphicons ${btn.icon}" ></span >
                                    </#if>
                                ${btn.name}
                                </a >
                            </#list>
                        </#if>
                        <#if listPage.headerButtons??>
                            <#list listPage.headerButtons as btn>
                                <a class="btn btn-min btn-green" <#if btn.url??>ng-href="${btn.url}"</#if> <#if btn.click??> ng-click="${btn.click}" </#if> >
                                    <i class="glyphicons ${btn.icon}" ></i > ${btn.name}
                                </a >
                            </#list>
                        </#if>
                    </div >
                </div >
            </#if>
                <div class="block-content">
                    <div class="content-wrap">
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
        </div >
        </div>
    <#if listPage.allowPager?? && listPage.allowPager == true>
        <div class="list-pagination" eccrm-page="pager" ></div >
    </#if>
    </div >
    </div >
</div >

</body >
<script type="text/javascript" src="app/${path}/${entity}.js" ></script >
<script type="text/javascript" src="app/${path}/list/${entity}_list.js" ></script >

</html >