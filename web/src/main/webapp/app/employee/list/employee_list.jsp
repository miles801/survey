<%--
Created by IntelliJ IDEA.
User: chenl
Date: 2014-10-15 14:47:37
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<%
    String contextPath = request.getContextPath();
%>
<html lang="en" >
<head >
    <base href="<%=request.getContextPath()%>/" >
    <title >人员管理</title >
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
    <script type="text/javascript" src="<%=contextPath%>/vendor/moment/moment.min.js" ></script >
    <script type="text/javascript" src="<%=contextPath%>/app/org/org.js" ></script >
    <script type="text/javascript" src="<%=contextPath%>/app/employee/employee.js" ></script >
    <script type="text/javascript" src="<%=contextPath%>/app/employee/list/employee_list.js" ></script >
    <script type="text/javascript" >
        window.angular.contextPathURL = '<%=contextPath%>';
    </script >
</head >
<body id="ng-app" >
<div class="main condition-row-2 " ng-app="eccrm.base.employee.list"
     ng-controller="EmployeeListController"
        >
    <input type="hidden" id="contextPath" value="<%=request.getContextPath()%>" />

    <div class="list-condition" >
        <div class="block" >
            <div class="block-header" >
                <div class="header-text" >
                    <span class="glyphicons list" ></span >
                    <span >员工查询</span >
                </div >
                <span class="header-button" >
                    <button type="button" class="btn btn-green btn-min" ng-click="query()" >
                        <span class="glyphicons binoculars" ></span >查询
                    </button >
                    <button type="button" class="btn btn-green btn-min" ng-click="reset()" >
                        <span class="glyphicons repeat" ></span >清空
                    </button >
                </span >
            </div >
            <div class="block-content" >
                <div class="content-wrap" style="padding-top: 0px" >
                    <div class="row" >
                        <div class="form-label col-1-half" >
                            <label >姓名:</label >
                        </div >
                        <input class="col-2-half" type="text" ng-model="condition.employeeName" />

                        <div class="form-label col-1-half" >
                            <label >工号:</label >
                        </div >
                        <input class="col-2-half" type="text" ng-model="condition.employeeCode" />

                        <div class="form-label col-1-half" >
                            <label >移动电话:</label >
                        </div >
                        <input class="col-2-half" type="text" ng-model="condition.mobile" />
                    </div >
                    <div class="row" >

                        <div class="form-label col-1-half" >
                            <label >职务:</label >
                        </div >
                        <select ng-model="condition.duty" ng-options="foo.value as foo.name for foo in dutys" class="col-2-half" >
                            <option value="" >请选择</option >
                        </select >

                        <div class="form-label col-1-half" >
                            <label >状态:</label >
                        </div >
                        <select ng-model="condition.status" ng-options="foo.value as foo.name for foo in EmpStatus" class="col-2-half" >
                            <option value="" >请选择</option >
                        </select >
                    </div >
                </div >
            </div >
        </div >
    </div >
    <div class="list-result " >
        <div class="block" >
            <div class="block-header" >
                <div class="header-text" >
                    <span class="glyphicons list" ></span >
                    <span >员工管理</span >
                </div >
                <span class="header-button" >
                    <a type="button" class="btn btn-green btn-min" ng-click="add();" >
                        <span class="glyphicons plus" ></span >添加
                    </a >
                </span >
            </div >
            <div class="block-content" >
                <div class="content-wrap" >
                    <div class="table-responsive panel panel-table  first-min" >
                        <table class="table table-striped table-hover" >
                            <thead class="table-header" >
                            <tr >
                                <td >
                                    <div select-all-checkbox checkboxes="employees.data" selected-items="items"
                                         anyone-selected="anyone" ></div >
                                </td >
                                <td >姓名</td >
                                <td >机构</td >
                                <td >移动电话</td >
                                <td >账号</td >
                                <td >状态</td >
                                <td style="white-space: nowrap;text-align:center; width: 10px;" >操作</td >
                            </tr >
                            </thead >
                            <tbody class="table-body" >
                            <tr ng-show="!employees || !employees.total || employees.data.length==0" >
                                <td colspan="7" class="text-center" >没有查询到数据！</td >
                            </tr >
                            <tr bindonce ng-repeat="foo in employees.data" >
                                <td >
                                    <input type="checkbox" ng-model="foo.isSelected" />
                                </td >
                                <td title="点击查询明细！" style="cursor: pointer;" >
                                    <a ng-click="detail(foo.id)"
                                       bo-text="foo.employeeName" ></a >
                                </td >
                                <td bo-text="foo.orgName" ></td >
                                <td bo-text="foo.mobile" ></td >
                                <td bo-text="foo.extensionNumber" ></td >
                                <td bo-text="foo.statusName" ></td >
                                <td >
                                    <a ng-click="modify(foo.id)" title="编辑" >
                                        <i class="icons edit" ></i >
                                    </a >
                                    <a ng-click="remove(foo.id)" title="删除！" >
                                        <i class="icons fork" ></i >
                                    </a >
                            </tr >
                            </tbody >
                        </table >
                    </div >
                </div >
            </div >
        </div >
    </div >
    <div class="list-pagination" eccrm-page="pager" ></div >
</div >
</div >

</body >

</html >