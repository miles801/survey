<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en" >
<head >
    <title >编辑用户信息</title >
    <meta content="text/html" charset="utf-8" >
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8" />
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/vendor/artDialog/artDialog.css" />
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/vendor/bootstrap-v3.0/css/bootstrap.min.css" >
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/style/standard/css/eccrm-common-new.css" >
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/vendor/angular-motion-v0.3.2/angular-motion.css" >
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/vendor/zTree/css/ztree.css" >
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/jquery-all.js" ></script >
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-all.js" ></script >
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-strap-all.js" ></script >
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-ztree-all.js" ></script >
    <script type="text/javascript" src="<%=contextPath%>/vendor/My97DatePicker/WdatePicker.js" ></script >
    <script type="text/javascript" >
        window.angular.contextPathURL = "<%=contextPath%>";
    </script >
</head >
<body >
<div class="main" ng-app="eccrm.base.user.edit" ng-controller="UserEditController" >
    <div class="block" >
        <div class="block-header" >
                <span class="header-text" >
                    <span class="glyphicons circle_info" ></span >
                    <span ng-bind-template="{{pageType=='add'?'新增':(pageType =='modify'?'编辑':'查看')}}用户" ng-cloak ></span >
                </span >
                <span class="header-button" ng-cloak >
                    <button type="button" class="btn btn-green btn-min"
                            ng-click="save()" ng-disabled="form.$invalid" ng-if="pageType=='add'" >
                        <span class="glyphicons floppy_saved" ></span > 保存
                    </button >
                    <button type="button" class="btn btn-green btn-min"
                            ng-click="save(true)" ng-disabled="form.$invalid" ng-if="pageType=='add'" >
                        <span class="glyphicons folder_plus" ></span > 保存并新建
                    </button >
                    <button type="button" class="btn btn-green btn-min"
                            ng-click="update()" ng-disabled="form.$invalid" ng-if="pageType=='modify'" >
                        <span class="glyphicons claw_hammer" ></span > 更新
                    </button >
                    <a type="button" class="btn btn-sm btn-green btn-min" ng-click="back();" >
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
                            <label >用户名:</label >
                        </div >
                        <input class="col-2-half" type="text" name="username" ng-model="user.username" validate validate-required autofocus
                               validate validate-min-length="2" validate-max-length="20" maxlength="20" />

                        <div class="form-label col-1-half" >
                            <label >编号:</label >
                        </div >
                        <input class="col-2-half" type="text" ng-model="user.code" name="code"
                               validate-min-length="4" validate-max-length="20" maxlength="20" />

                    </div >
                    <div class="row" >
                        <div class="form-label col-1-half" >
                            <label > 类型:</label >
                        </div >
                        <select ng-model="user.type" class="col-2-half" ng-options="foo.value as foo.name for foo in types" > </select >

                    </div >
                    <div class="row" >
                        <div class="form-label col-1-half" >
                            <label >启用时间:</label >
                        </div >
                        <input type="text" class="col-2-half" ng-model="user.startDate" id="startDate" readonly
                               eccrm-my97="{el:'startDate',autoPickDate:false,dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'endDate\')}'}" />
                        <span class="add-on" >
                            <i class="icons clock icon" ng-click="user.startDate=null;" title="清除" > </i >
                        </span >

                        <div class="form-label col-1-half" >
                            <label >关闭时间:</label >
                        </div >
                        <input type="text" class="col-2-half" ng-model="user.endDate" readonly id="endDate"
                               eccrm-my97="{el:'endDate',autoPickDate:false,dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'startDate\')}'}" />
                        <span class="add-on" >
                            <i class="icons clock icon" ng-click="user.endDate=null;" title="清除" > </i >
                        </span >
                    </div >

                    <div class="row" >
                        <div class="form-label col-1-half" >
                            <label > 员工姓名:</label >
                        </div >
                        <input class="col-2-half" type="text" data-ng-model="user.employeeName" maxlength="20" readonly ng-click="pickEmp();"
                               validate validate-required />
                        <span class="add-on" >
                            <i class="glyphicons user icon" ng-click="clearEmployee();" ></i >
                        </span >

                        <div class="form-label col-1-half" >
                            <label >工号:</label >
                        </div >
                        <input class="col-2-half" type="text" ng-model="user.employeeNo" maxlength="10" />
                    </div >

                    <div class="row" >
                        <div class="form-label col-1-half" >
                            <label >性别:</label >
                        </div >
                        <select ng-model="user.gender" ng-options="foo.value as foo.name for foo in genders" class="col-2-half" ></select >

                        <div class="form-label col-1-half" >
                            <label >所属部门:</label >
                        </div >
                        <input data-ng-model="user.deptName" class="col-2-half" type="text" readonly />
                    </div >
                    <div class="row" >
                        <div class="form-label col-1-half" >
                            <label > EMAIL:</label >
                        </div >
                        <input class="col-6-half" type="text" ng-model="user.email" placeholder="例如:example@163.com"
                               validate validate-email validate-max-length="100" />
                    </div >
                    <div class="row" >
                        <div class="form-label col-1-half" >
                            <label > 手机:</label >
                        </div >
                        <input class="col-2-half" type="text" ng-model="user.mobilePhone" maxlength="20" />
                        <span class="add-on" >
                            <i class="glyphicon glyphicon-remove icon" ></i >
                        </span >

                        <div class="form-label col-1-half" >
                            <label >RTX:</label >
                        </div >
                        <input class="col-2-half" type="text" ng-model="user.officePhone" maxlength="20" />
                    </div >
                    <div class="row" >
                        <div class="form-label col-1-half" >
                            <label > 备注:</label >
                        </div >
                        <textarea class="col-10" rows="6" ng-model="user.description" name="description" maxlength="1000" validate validate-max-length="1000" ></textarea >
                    </div >

                    <div class="row" >
                        <div class="form-label col-1-half" >
                            <label >采集人:</label >
                        </div >
                        <span class="col-2-half" >{{ user.creatorName }}</span >

                        <div class="form-label col-1-half" >
                            <label >采集时间:</label >
                        </div >
                        <span ng-bind-template="{{ user.createdDatetime | eccrmDatetime }}" class="col-2-half" ></span >

                        <div class="form-label col-1-half" >
                            <label >状态:</label >
                        </div >
                        <select ng-model="user.status" class="col-2" ng-options="foo.value as foo.name for foo in status" > </select >
                    </div >
                </form >
            </div >
        </div >
    </div >
</div >
</body >
<script type="text/javascript" src="<%=contextPath%>/app/base/user/user.js" ></script >
<script type="text/javascript" src="<%=contextPath%>/app/employee/employee-modal.js" ></script >
<script type="text/javascript" src="<%=contextPath%>/app/base/user/edit/user_edit.js" ></script >
</html >