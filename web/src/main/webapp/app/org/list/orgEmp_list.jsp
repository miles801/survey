<%--
Created by IntelliJ IDEA.
User: chenl
Date: 2014-10-14 15:56:01
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en" >
<head >
    <base href="<%=request.getContextPath()%>/" >
    <title >机构员工列表</title >
    <link rel="stylesheet" type="text/css" href="vendor/bootstrap-v3.0/css/bootstrap.min.css" >
    <link rel="stylesheet" type="text/css" href="style/standard/css/eccrm-common-new.css" >
    <link rel="stylesheet" type="text/css" href="vendor/angular-motion-v0.3.2/angular-motion.css" >
    <link rel="stylesheet" type="text/css" href="vendor/zTree/css/ztree.css" >
    <meta content="text/html" charset="utf-8" >
</head >
<body>
<div class="main condition-row-3" id="pageTab" ng-app="eccrm.organi.organiEmp.list" ng-controller="OrganiEmpListController" >
 <div class="row panel panel-tree" >
     <%-- 左侧树 --%>
     <div class="tree" style="border: 1px solid #B7C1CB;width: 200px" >
         <div class="tree-content" >
             <ul id="treeDemo" class="ztree" ></ul >
         </div >
         <div class="scroller" ></div >
     </div >
     <%-- 右侧正文内容 --%>
     <div class="content" style="padding-left: 205px" >
         <%-- 订单结果列表 --%>
         <div class="list-condition" >
             <div class="block" >
                 <div class="block-content" >
                     <div class="content-wrap" >
                         <div class="row" >
                             <div class="form-label col-1-half" >
                                 <label >姓名:</label >
                             </div >
                             <input class="col-2-half" type="text" ng-model="positionEmp.type" />

                             <div class="form-label col-1-half" >
                                 <label >登录名:</label >
                             </div >
                             <input class="col-2-half" type="text" ng-model="positionEmp.projectName" />

                             <div class="form-label col-1-half" >
                                 <label >RTX:</label >
                             </div >
                             <input class="col-2-half" type="text" ng-model="positionEmp.type" />
                         </div >
                         <div class="row" >
                             <div class="form-label col-1-half" >
                                 <label >电子邮件:</label >
                             </div >
                             <input class="col-2-half" type="text" ng-model="positionEmp.projectName" />

                             <div class="form-label col-1-half" >
                                 <label >移动电话:</label >
                             </div >
                             <input class="col-2-half" type="text" ng-model="positionEmp.type" />

                             <div class="form-label col-1-half" >
                                 <label >状态:</label >
                             </div >
                             <select ng-model="positionEmp.projectName" class="col-2-half" >
                                 <option value="0" >启用</option >
                                 <option value="2" >未启用</option >
                                 <option value="3" >注销</option >
                             </select >
                         </div >
                         <div class="row" >
                                  <span style="float: right;padding-right: 5px" >
                                        <button type="button" class="btn btn-default btn-min" ng-click="" >
                                            选择员工
                                        </button >
                                        <button type="button" class="btn btn-default btn-min" ng-click="" >
                                            删除
                                        </button >
                                        <button type="button" class="btn btn-default btn-min" ng-click="" >
                                            搜索
                                        </button >
                                        <button type="button" class="btn btn-default btn-min" ng-click="" >
                                            清空
                                        </button >
                                  </span >
                         </div >
                     </div >
                 </div >
             </div >
         </div >
         <div class="list-result" >
             <div class="block" >
                 <div class="table-responsive panel panel-table first-min" >
                     <table class="table table-striped table-hover" >
                         <thead class="table-header" >
                         <tr >
                             <td >
                                 <div select-all-checkbox checkboxes="beans"
                                      selected-items="items" anyone-selected="anyone"></div>
                             </td >
                             <td >姓名</td >
                             <td >工号</td >
                             <td >职务</td >
                             <td >移动电话</td >
                             <td >电子邮件</td >
                             <td >RTX</td >
                             <td >分机</td >
                             <td >工作地点</td >
                             <td >登录名</td >
                             <td >状态</td >
                             <td >操作</td >
                         </tr >
                         </thead >
                         <tbody class="table-body" >
                         <tr ng-show="!beans|| beans.length == 0" >
                             <td colspan="12" class="text-center" >没有查询到数据！</td >
                         </tr >
                         <tr ng-repeat="foo in beans" ng-show="beans.total != 0">
                             <td><span ng-bind-template="{{ foo.employeeName }}"></span></td>
                             <td><span ng-bind-template="{{ foo.employeeCode }}"></span></td>
                             <td><span ng-bind-template="{{ foo.duty }}"></span></td>
                             <td><span ng-bind-template="{{ foo.employeeMobile }}"></span></td>
                             <td><span ng-bind-template="{{ foo.officeEmail }}"></span></td>
                             <td><span ng-bind-template="{{ foo.RtxID }}"></span></td>
                             <td><span ng-bind-template="{{ foo.mobile }}"></span></td>
                             <td><span ng-bind-template="{{ foo.wAddress }}"></span></td>
                             <td >登录名</td >
                             <td><span ng-bind-template="{{ foo.status }}"></span></td>
                             <td><a  class="btnA" ng-click="del(foo.id)">删除</a>|<a class="btnA" ng-click="">调岗</a></td>
                         </tr>
                         </tbody >
                     </table >
                 </div >
             </div >
         </div >
         <div eccrm-page="pager" class="list-pagination" ></div >
     </div >
 </div >
</div >

</body>
<script type="text/javascript" src="vendor/jquery-v1.8.3/jquery.min.js" ></script >
<script type="text/javascript" src="static/ycrl/javascript/angular-all.js" ></script >
<script type="text/javascript" src="static/ycrl/javascript/angular-strap-all.js" ></script >
<script type="text/javascript" src="vendor/zTree/js/jquery.ztree.core-3.5.min.js" ></script >
<script type="text/javascript" src="app/org/orgEmp.js" ></script >
<script type="text/javascript" src="app/org/org.js" ></script >
<script type="text/javascript" src="app/org/emp.js" ></script >
<script type="text/javascript" src="app/org/template/orgModify-modal.js" ></script >
<script type="text/javascript" src="app/org/list/orgEmp_list.js" ></script >

</html >