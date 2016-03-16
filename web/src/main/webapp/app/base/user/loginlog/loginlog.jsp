<%--
Created by IntelliJ IDEA.
User:
Date: 2014-03-07 13:49:36
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en" >
<head >
    <base href="<%=request.getContextPath()%>/" >
    <title >登录日志</title >
    <link rel="stylesheet" type="text/css" href="vendor/bootstrap-v3.0/css/bootstrap.min.css" >
    <link rel="stylesheet" type="text/css" href="style/standard/css/eccrm-common-new.css" >
    <link rel="stylesheet" href="vendor/angular-motion-v0.3.2/angular-motion.min.css" />

</head >
<body >
<div class="main condition-row-2" ng-app="eccrm.user.loginlog" ng-controller="LoginLogCtrl" >
    <div class="list-condition" >
        <div class="block" >
            <div class="block-header" >
                <span class="header-text" >
                    <span class="glyphicons search" ></span >
                        <span >登录日志</span >
                </span >
                <span class="header-button" >
                            <button type="button" class="btn btn-green btn-min" ng-click="query();" >
                                <span class="glyphicons search" ></span > 查询
                            </button >
                            <button type="button" class="btn btn-green btn-min" ng-click="condition={}" >
                                <span class="glyphicons repeat" ></span > 重置
                            </button >
                </span >
            </div >
            <div class="block-content" >
                <div class="content-wrap" >
                    <div class="row" >
                        <div class="form-label col-1-half" >
                            <label >登录时间:</label >
                        </div >
                        <div class="col-2-half" >

                            <input type="text" class="col-5-half" bs-datepicker
                                   ng-model="condition.startDate" readonly
                                   placeholder="选择日期" name="start" data-max-date="{{condition.endDate}}" />

                            <div class="add-on col" >
                                <i class="glyphicons calendar icon" title="清除时间"
                                   ng-click="condition.startDate=undefined;" ></i >
                            </div >
                            <span class="col-1" style="text-align: center" >-</span >
                            <input type="text" class="col-5-half" bs-datepicker
                                   ng-model="condition.endDate" readonly
                                   placeholder="选择日期" data-min-date="{{condition.startDate}}" />

                            <div class="add-on col" >
                                <i class="glyphicons calendar icon" title="清除时间" ng-click="condition.endDate=undefined;" ></i >
                            </div >
                        </div >
                        <div class="form-label col-1-half" >
                            <label >用户名:</label >
                        </div >
                        <input class="col-2-half" type="text" ng-model="condition.username" />
                        <%--<div class="form-label col-1-half" >
                            <label >部门:</label >
                        </div >
                        <input class="col-2-half" type="text" ng-model="condition.dept" />

                        <div class="form-label col-1-half" >
                            <label >用户组:</label >
                        </div >
                        <input class="col-2-half" type="text" ng-model="condition.usergroup" />--%>
                    </div >
                    <div class="row" >
                        <div class="form-label col-1-half" >
                            <label >员工姓名:</label >
                        </div >
                        <input class="col-2-half" type="text" ng-model="condition.employeeName" />
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
                    <span >登录日志</span >
                </div >
            </div >
            <div class="block-content" >
                <div class="content-wrap" >
                    <div class="table-responsive panel panel-table" >
                        <table class="table table-striped table-hover" >
                            <thead class="table-header" >
                            <tr >
                                <td >用户</td >
                                <td >姓名</td >
                                <td >工号</td >
                                <td >职位</td >
                                <td >登录时间</td >
                                <td >退出时间</td >
                                <td >退出方式</td >
                                <td >IP地址</td >
                                <td >状态</td >
                            </tr >
                            </thead >
                            <tbody class="table-body" ng-cloak >
                            <tr ng-show="!logs || !logs.data.length" >
                                <td colspan="9" class="text-center" >没有符合条件的记录！</td >
                            </tr >
                            <tr ng-repeat="foo in logs.data" >
                                <td >{{ foo.username }}</td >
                                <td >{{ foo.employeeName }}</td >
                                <td >{{ foo.employeeNo }}</td >
                                <td >{{ foo.position }}</td >
                                <td >{{ foo.createdDatetime | eccrmDatetime }}</td >
                                <td >{{ foo.logoutDatetime | eccrmDatetime }}</td >
                                <td >{{ foo.logoutType | logoutType }}</td >
                                <td >{{ foo.ip }}</td >
                                <td >{{ foo.logoutDatetime | loginLogStatus }}</td >
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
<script type="text/javascript" src="vendor/jquery-v1.8.3/jquery.min.js" ></script >
<script type="text/javascript" src="static/ycrl/javascript/angular-all.js" ></script >
<script type="text/javascript" src="static/ycrl/javascript/angular-strap-all.js" ></script >
<script type="text/javascript" src="app/base/user/loginlog/loginlog.js" ></script >

</html >