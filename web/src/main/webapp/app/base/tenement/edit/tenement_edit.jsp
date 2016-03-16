<%--
  Created by IntelliJ IDEA.
  User: miles
  Date: 13-11-22
  Time: 下午3:20
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="isAdd" value="${pageType eq null or pageType eq 'add'}" ></c:set >
<c:set var="isModify" value="${pageType ne null and pageType eq 'modify'}" ></c:set >
<c:set var="isDetail" value="${pageType ne null and pageType eq 'detail'}" ></c:set >
<!DOCTYPE html>
<html lang="en" >
<head >
    <base href="<%=request.getContextPath()%>/" >
    <title >编辑租户</title >
    <link rel="stylesheet" type="text/css" href="vendor/bootstrap-v3.0/css/bootstrap.min.css" >
    <link rel="stylesheet" type="text/css" href="style/standard/css/eccrm-common-new.css" >
    <link rel="stylesheet" type="text/css" href="vendor/angular-motion-v0.3.2/angular-motion.min.css" >
</head >
<body >
<div class="main" ng-app="eccrm.base.tenement.edit" ng-controller="TenementEditController"
     style="overflow: auto;" >
    <div style="display: none;" >
        <input type="hidden" id="id" value="${id}" />
        <input type="hidden" id="pageType" value="${pageType}" />
    </div >
    <div class="row" >
        <div class="block" >
            <div class="block-header" >
            <span class="header-text" >
                <span class="glyphicons circle_info" ></span >
                <span >
                    <c:if test="${isAdd}" >新建租户</c:if >
                    <c:if test="${isModify}" >更新租户信息</c:if >
                    <c:if test="${isDetail}" >查看租户信息</c:if >
                </span >
            </span >

                <div class="header-button" >
                    <c:if test="${isAdd}" >
                        <button type="button" class="btn btn-green btn-min" ng-show="!tenement.id" ng-cloak
                                ng-click="save()" ng-disabled="!form.$valid" >
                            <span class="glyphicons floppy_saved" ></span > 保存
                        </button >
                        <button type="button" class="btn btn-green btn-min" ng-show="tenement.id" ng-cloak
                                ng-click="update()" ng-disabled="!form.$valid" >
                            <span class="glyphicons floppy_saved" ></span > 更新
                        </button >
                    </c:if >
                    <c:if test="${isModify}" >
                        <button type="button" class="btn btn-green btn-min"
                                ng-click="update()" ng-disabled="!form.$valid" >
                            <span class="glyphicons claw_hammer" ></span > 更新
                        </button >
                    </c:if >
                    <a type="button" class="btn btn-sm btn-green btn-min" ng-click="back();" >
                        <span class="glyphicons message_forward" ></span > 返回
                    </a >
                </div >
            </div >
            <div class="block-content" >
                <div class="content-wrap" >
                    <form name="form" class="form-horizontal" role="form" >
                        <div class="row" >
                            <div class="form-label col-1-half" >
                                <label >租户名称:</label >
                            </div >
                            <input type="text" name="name" class="col-6-half" placeholder="请输入租户名称" ng-model="tenement.name"
                                   validate validate-required
                                   validate-min-length="4" validate-max-length="200" />

                            <div class="form-label col-1-half" >
                                <label >租户编号:</label >
                            </div >
                            <input type="text" name="code" class="col-2" ng-model="tenement.code"
                                   validate validate-required validate-max-length="40" />
                        </div >
                        <div class="row" >
                            <div class="form-label col-1-half" >
                                <label >企业规模:</label >
                            </div >
                            <select class="col-2-half" ng-model="tenement.companyScale" >
                                <option value="1" >1-29人</option >
                                <option value="2" >30-100人</option >
                                <option value="3" >100-200人</option >
                                <option value="4" >200-500人</option >
                                <option value="5" >500人以上</option >
                            </select >

                            <div class="form-label col-1-half" >
                                <label >公司网址:</label >
                            </div >
                            <input type="text" class="col-2-half" ng-model="tenement.url" />

                            <div class="form-label col-1-half" >
                                <label >所在行业:</label >
                            </div >
                            <select ng-model="tenement.industry" class="col-2"
                                    ng-options="foo.code as foo.name for foo in industries" >
                            </select >
                        </div >
                        <div class="row" >
                            <div class="form-label col-1-half" >
                                <label >所在地区:</label >
                            </div >
                            <div class="col-6-half" >
                                <div class="col-4" >
                                    <select class="col-10" ng-model="tenement.province" ng-options="foo.id as foo.name for foo in provinces" ng-change="changeProvince(tenement.province)" ></select >
                                    <span class="col-2 text-center" >省</span >
                                </div >
                                <div class="col-4" >
                                    <select class="col-10" ng-model="tenement.city" ng-options="foo.id as foo.name for foo in cities" ng-change="changeCity(tenement.city);" ></select >
                                    <span class="col-2 text-center" >市</span >
                                </div >
                                <div class="col-4" >
                                    <select class="col-12" ng-model="tenement.county" ng-options="foo.id as foo.name for foo in disticts" ></select >
                                </div >
                            </div >
                            <span class="col" style="padding-left: 10px;" >区/县</span >
                        </div >
                        <div class="row" >
                            <div class="form-label col-1-half" >
                                <label >详细地址:</label >
                            </div >
                            <input type="text" class="col-10 label-2" ng-model="tenement.address" />
                        </div >
                        <div class="row" >
                            <div class="form-label col-1-half" >
                                <label >租用方式:</label >
                            </div >
                            <select ng-model="tenement.rentType" class="col-2-half"
                                    ng-options="foo.code as foo.name for foo in rentTypes" >
                            </select >

                            <div class="form-label col-1-half" >
                                <label >用户规模:</label >
                            </div >
                            <select ng-model="tenement.userScale" class="col-2-half"
                                    ng-options="foo.code as foo.name for foo in scales" >
                            </select >

                            <div class="form-label col-1-half" >
                                <label for="singleResource" >资源要求:</label >
                            </div >
                            <div class="col-2" >
                                <input type="checkbox" id="singleResource" ng-model="tenement.singleResource" ng-value="true" />
                                <label for="singleResource" >独占</label >
                            </div >
                        </div >
                        <div class="row" >
                            <div class="form-label col-1-half" >
                                <label >开始日期:</label >
                            </div >
                            <input type="text" class="col-2-half" ng-model="tenement.startDate"
                                   bs-datepicker readonly data-max-date="{{tenement.endDate}}" />
                            <span class="add-on" >
                                <i class="glyphicons calendar icon" title="清除时间" ng-click="tenement.startDate=null;" ></i >
                            </span >

                            <div class="form-label col-1-half" >
                                <label >结束日期:</label >
                            </div >
                            <input class="col-2-half" type="text" ng-model="tenement.endDate"
                                   bs-datepicker readonly data-min-date="{{tenement.startDate}}" />
                            <span class="add-on" >
                                <i class="glyphicons calendar icon" title="清除时间" ng-click="tenement.endDate=null;" ></i >
                            </span >
                        </div >
                        <div class="row" >
                            <div class="form-label col-1-half" >
                                <label >租用说明:</label >
                            </div >
                            <textarea rows="3" class="col-10" ng-model="tenement.description" ></textarea >
                        </div >
                        <div class="row" >
                            <div class="form-label col-1-half" >
                                <label >备注:</label >
                            </div >
                            <textarea rows="3" class="col-10" ng-model="tenement.remark" ></textarea >
                        </div >
                        <div class="row" >
                            <div class="form-label col-1-half" >
                                <label >负责人:</label >
                            </div >
                            <input type="hidden" ng-model="tenement.principalId" />
                            <input class="col-2-half" type="text" ng-model="tenement.principalEmployee.name" />
                <span class="add-on" >
                    <i class="glyphicons user icon" ></i >
                </span >
                        </div >
                        <div class="row" >
                            <div class="form-label col-1-half" >
                                <label >采集人:</label >
                            </div >
                            <span class="col-2-half" ng-bind-template="{{tenement.createdUser.name}}" ></span >

                            <div class="form-label col-1-half" >
                                <label >采集时间:</label >
                            </div >
                            <span class="col-2-half" ng-bind-template="{{tenement.createdDatetime | eccrmDatetime }}" ></span >

                            <div class="form-label col-1-half" >
                                <label >状态:</label >
                            </div >
                            <select ng-model="tenement.status" class="col-2" ng-options="foo.code as foo.name for foo in status" > </select >
                        </div >
                    </form >
                </div >
            </div >
        </div >
    </div >
    <div eccrm-route="routeOptions" ></div >

</div >
</body >
<script type="text/javascript" src="vendor/jquery-v1.8.3/jquery.min.js" ></script >
<script type="text/javascript" src="static/ycrl/javascript/angular-all.js" ></script >
<script type="text/javascript" src="static/ycrl/javascript/angular-strap-all.js" ></script >
<script type="text/javascript" src="app/base/region/region.js" ></script >
<script type="text/javascript" src="vendor/angular-file-upload/angular-file-upload.js" ></script >
<script type="text/javascript" src="js/attachment.js" ></script >
<script type="text/javascript" src="app/base/tenement/tenement.js" ></script >
<script type="text/javascript" src="app/base/tenement/document-modal.js" ></script >
<script type="text/javascript" src="app/base/tenement/edit/tenement_edit.js" ></script >
</html >