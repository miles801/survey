<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en" >
<head >
    <title >员工信息</title >
    <meta content="text/html" charset="utf-8" >
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8" />
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/vendor/bootstrap-v3.0/css/bootstrap.min.css" >
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/style/standard/css/eccrm-common-new.css" >
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/jquery-all.js" ></script >
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-all.js" ></script >
    <script type="text/javascript" src="<%=contextPath%>/app/employee/employee.js" ></script >
    <script type="text/javascript" src="<%=contextPath%>/app/org/PositionEmp.js" ></script >
    <script type="text/javascript" >
        window.angular.contextPathURL = "<%=contextPath%>";
        (function () {
            var app = angular.module('eccrm.base.employee.info', [
                'eccrm.angular',
                'eccrm.base.employee',
                'base.position.emp'
            ]);
            app.controller('EmpCtrl', function ($scope, EmployeeService, PositionEmpService, CommonUtils) {
                var id = $('#id').val();
                // 查询基本信息
                var promise = EmployeeService.get({id: id});
                CommonUtils.loading(promise, '正在加载员工信息...', function (data) {
                    $scope.info = data.data || {};
                });

                // 查询岗位信息
                var promise2 = PositionEmpService.queryByEmpId({empId: id});
                CommonUtils.loading(promise2, '加载机构岗位信息...', function (data) {
                    $scope.po = data.data || [];
                })
            })
        })();
    </script >
</head >
<body >
<div class="main" ng-app="eccrm.base.employee.info" ng-controller="EmpCtrl" style="overflow: auto;" >
    <input type="hidden" id="id" value="${userId}" />

    <div class="row" >
        <div class="form-label col-1-half" >
            <label >姓名:</label >
        </div >
        <span class="col" >{{info.employeeName}}</span >
    </div >
    <div class="row dn" >
        <div class="form-label col-1-half" >
            <label >工号:</label >
        </div >
        <span class="col" >{{info.employeeCode}}</span >
    </div >
    <div class="row" >
        <div class="form-label col-1-half" >
            <label >RTX:</label >
        </div >
        <span class="col" >{{info.extensionNumber}}</span >
    </div >
    <div class="row" >
        <div class="form-label col-1-half" >
            <label >岗位:</label >
        </div >
        <div class="block" >
            <div class="row" bindonce ng-repeat="foo in po" >
                <span class="col" style="text-overflow: ellipsis;white-space: nowrap;overflow: hidden;width: 90%;display: inline-block;" bo-title="foo.positionName + '-->' + foo.orgName" bo-text="foo.positionName + '-->' + foo.orgName" ></span >
            </div >
        </div >
    </div >
</div >
</body >
</html >