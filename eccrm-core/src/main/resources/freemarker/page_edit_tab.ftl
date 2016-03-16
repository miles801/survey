<%--
Created by IntelliJ IDEA.
<#if author??>User: ${author}</#if>
<#if current??>Date: ${current}</#if>
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en" >
<head >
    <base href="<%=request.getContextPath()%>/" >
    <title >编辑${cnName}</title >
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

<#if editPage.tree!false = true>
    <link rel="stylesheet" type="text/css" href="vendor/zTree/css/ztree.css" >
    <script type="text/javascript" src="vendor/zTree/js/jquery.ztree.core-3.5.min.js" ></script >
</#if>
</head >
<body >
<div class="main tab" ng-app="eccrm.${name}.${entity}.edit" ng-controller="${className}EditController" >
    <div eccrm-alert="alert" ></div >
    <div class="row-6" >
        <div class="block" >
            <div class="block-header" >
                    <span class="header-text" >
                        <span class="glyphicons info-sign" ></span >
                    <#if editPage.headerText??>
                        <span >${editPage.headerText}</span >
                    <#else>
                        <span >${cnName}基本信息</span >
                    </#if>
                    </span >
                    <span class="header-button" >
                    <#if editPage.headerButtons?? >
                        <#list editPage.headerButtons! as btn>
                            <#if btn.url??>
                                <a type="button" class="btn btn-green btn-min" ng-href="${btn.url}" >
                                    <span class="glyphicons ${btn.icon!}" ></span > ${btn.name!}
                                </a >
                            <#elseif btn.click??>
                                <button type="button" class="btn btn-green btn-min" ng-click="${btn.click}" >
                                    <span class="glyphicons ${btn.icon!}" ></span > ${btn.name!}
                                </button >
                            <#else>
                                <button type="button" class="btn btn-green btn-min" >
                                    <span class="glyphicons ${btn.icon!}" ></span > ${btn.name!}
                                </button >
                            </#if>
                        </#list>
                    <#else>
                        <button type="button" class="btn btn-sm btn-green btn-min" ng-show="!page.type || page.type=='add'" ng-click="save()" ng-disabled="!form.$valid" >
                            <span class="glyphicons disk_save" ></span > 保存
                        </button >
                        <#if editPage.saveAndNew! = true>
                            <button type="button" class="btn btn-sm btn-green btn-min" ng-show="!page.type || page.type=='add'" ng-click="saveAndNew()" ng-disabled="!form.$valid" >
                                <span class="glyphicons disk_open" ></span > 保存并新建
                            </button >
                        </#if>
                        <button type="button" class="btn btn-sm btn-green btn-min" ng-show="page.type=='modify'" ng-click="update()" ng-disabled="!form.$valid && !canUpdate" >
                            <span class="glyphicons claw_hammer" ></span > 更新
                        </button >
                    </#if>
                        <a type="button" class="btn btn-sm btn-green btn-min" ng-click="back();" >
                            <span class="glyphicons message_forward" ></span > 返回
                        </a >
                    </span >
            </div >
            <div class="block-content" >
                <%--表单按照1.5:2.5:1.5:2.5:2的比例进行排列--%>
                <div class="content-wrap" >
                    <form name="form" class="form-horizontal" role="form" >
                        <div style="display: none;" >
                            <input type="hidden" ng-model="page.type" ng-init="page.type='${r"${pageType}"}'" />
                            <input type="hidden" ng-model="menu.id" ng-init="menu.id=('${r"${id}"}') || null" />
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
    <div class="row-4" style="margin-top: 15px;padding-bottom: 15px;" >
        <div class="block noborder" >
            <div class="block-content panel panel-tab" >
                <!-- Nav tabs -->
                <ul class="nav nav-tabs" >
                <#list editPage.tabs as tab>
                    <#if tab_index==0>
                    <li class="active" >
                    <#else>
                    <li >
                    </#if>
                        <a href="#${tab.name}" data-toggle="tab" >${tab.label}</a >
                    </li >
                </#list>
                </ul >
                <div class="tab-content" >
                <#list editPage.tabs as tab>
                    <#if tab_index==0>
                    <div class="tab-pane fade in active" id="${tab.name}" >
                    <#else>
                    <div class="tab-pane fade in" id="${tab.name}" >
                    </#if>
                    <#if tab.url??>
                        <iframe src="${tab.url}" ></iframe ></#if>
                    </div >
                </#list>
                </div >
                </div >
            </div >
        </div >
    </div >
</div >
</body >
<script type="text/javascript" src="app/${path}/${entity}.js" ></script >
<script type="text/javascript" src="app/${path}/edit/${entity}_edit.js" ></script >
</html >