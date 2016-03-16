<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String contextPath = request.getContextPath();
%>
<html lang="en" >
<head >
    <title>${cnName}</title>
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8" />
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/vendor/bootstrap-v3.0/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/style/standard/css/eccrm-common-new.css">
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/jquery-all.js" ></script>
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-all.js" ></script>
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-strap-all.js" ></script>
    <#if listPage.datepicker!false = true>
        <script type="text/javascript" src="<%=contextPath%>/vendor/My97DatePicker/WdatePicker.js" ></script>
    </#if>
    <script>
        window.angular.contextPathURL = '<%=contextPath%>';
    </script>
</head >
<body >
<div class="main <#if listPage.conditionRows??>condition-row-<#if listPage.conditionRows &gt; 3 >3<#else>${listPage.conditionRows}</#if></#if>" ng-app="${name}.${entity}.list" ng-controller="Ctrl" <#if listPage.wholePageScroll == true >style="overflow: auto;"</#if>>
    <#if listPage.queryConditionRows??>
    <div class="list-condition" >
        <div class="block" >
            <div class="block-header" >
                <span class="header-text" >
                    <span class="glyphicons search" ></span >
                    <#if listPage.headerText??>
                        <span >${listPage.headerText}</span >
                    </#if>
                </span >
                <#if listPage.headerButtons?? || listPage.queryBarButtons??>
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
                </span>
                </#if>
            </div >
            <div class="block-content" >
                <div class="content-wrap">
                <#list (listPage.queryConditionRows)! as row>
                    <div class="row" >
                        <#list (row.elements)! as element>
                            <#include "element.ftl">
                        </#list>
                    </div >
                </#list>
                </div>
            </div >
        </div >
    </div >
    </#if>
    <div class="list-result <#if listPage.allowPager==false>no-pagination</#if>" >
        <div class="block" >
            <div class="block-header" >
            <#if listPage.tableHeaderText??>
                <div class="header-text" >
                    <span class="glyphicons list" ></span >
                    <span >${listPage.tableHeaderText}</span >
                </div >
            <#elseif listPage.defaultName! = true>
                <div class="header-text" >
                    <span class="glyphicons list" ></span >
                    <span >${cnName!''}</span >
                </div >
            </#if>
            <#if listPage.tableHeaderButtons?? && (listPage.tableHeaderButtons?size > 0)>
                <span class="header-button" >
                <#list (listPage.tableHeaderButtons)! as button>
                    <#if button.url??>
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
                </span >
            </#if>
            </div >
            <div class="block-content" >
                <div class="content-wrap">
                    <div class="table-responsive panel panel-table" >
                        <table class="table table-striped table-hover" >
                            <thead class="table-header" >
                                <tr >
                                <#if listPage.allowCheckbox! = true>
                                    <td class="width-min">
                                        <div select-all-checkbox checkboxes="beans.data" selected-items="items"
                                             anyone-selected="anyone"></div>
                                    </td>
                                </#if>
                                <#list (listPage.items)! as item>
                                    <td >${item!''}</td >
                                </#list>
                                </tr >
                            </thead >
                            <tbody class="table-body" >
                                <tr ng-show="!beans || !beans.total" >
                                <#if listPage.allowCheckbox! = true>
                                    <td colspan="${listPage.items?size+1}" class="text-center" >没有查询到数据！</td >
                                <#else>
                                    <td colspan="${listPage.items?size}" class="text-center" >没有查询到数据！</td >
                                </#if>
                                </tr >
                                <tr bindonce ng-repeat="foo in beans.data" ng-cloak>
                                    <td><input type="checkbox" ng-model="foo.isSelected"/></td>
                                    <td>
                                        <a class="cp" title="编辑" ng-click="modify(foo.id);">
                                            <i class="icons edit"></i>
                                        </a>
                                        <a class="cp" title="删除" ng-click="remove(foo.id);">
                                            <i class="icons fork"></i>
                                        </a>
                                    </td>
                                </tr>
                            </tbody >
                        </table >
                    </div >
                </div>
                </div >
            </div>
    </div >
    <#if listPage.allowPager==true>
    <div class="list-pagination" eccrm-page="pager" ></div >
    </#if>
</div >
</body >
<script type="text/javascript" src="<%=contextPath%>/app/${path}/${entity}.js" ></script>
<script type="text/javascript" src="<%=contextPath%>/app/${path}/list/${entity}_list.js" ></script>
</html >