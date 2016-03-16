<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html >
<html >
<head >
    <title >岗位分配列表</title >
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8" />
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/vendor/bootstrap-v3.0/css/bootstrap.min.css" >
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/style/standard/css/eccrm-common-new.css" >
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/vendor/angular-motion-v0.3.2/angular-motion.css" >
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/vendor/zTree/css/ztree.css" >
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/jquery-all.js" ></script >
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-all.js" ></script >
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-strap-all.js" ></script >
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-ztree-all.js" ></script >
    <script type="text/javascript" src="<%=contextPath%>/app/position/positionAllot/positionAllot.js" ></script >
    <script type="text/javascript" src="<%=contextPath%>/app/position/positionAllot-modal.js" ></script >
    <script type="text/javascript" src="<%=contextPath%>/app/position/position.js" ></script >
    <script type="text/javascript" src="<%=contextPath%>/app/position/positionAllot/list/positionAllot_list.js" ></script >
    <script type="text/javascript" >
        window.angular.contextPathURL = '<%=contextPath%>';
    </script >
</head >
<body >
<div class="main condition-row-1" ng-app="eccrm.position.positionAllot.list" ng-controller="PositionAllotListController" >
    <input type="hidden" id="contextPath" value="<%=request.getContextPath()%>" />

    <div class="list-condition" >
        <div class="block" <%--style="border: 1px solid #B7C1CB; "--%>>
            <div class="block-header" >
                <div class="header-text" >
                    <span class="glyphicons list" ></span >
                    <span >岗位分配规则</span >
                </div >
                        <span class="header-button" >
                                <button type="button" class="btn btn-green btn-min" ng-click="query()" >
                                    <span class="glyphicons plus" ></span >
                                    查询
                                </button >
                                <button type="button" class="btn btn-green btn-min" ng-click="reset()" >
                                    <span class="glyphicons remove" ></span >
                                    清空
                                </button >
                        </span >
            </div >
            <div class="block-content" >
                <div class="content-wrap" >
                    <div class="row" >
                        <div class="form-label col-1-half" >
                            <label >系统:</label >
                        </div >
                        <select class="col-2-half" ng-model="condition.busiType" ng-options="foo.value as foo.name for foo in busiTypes" class="col-2-half" >
                            <option value="" >请选择</option >
                        </select >

                        <div class="form-label col-1-half" >
                            <label >机构类型:</label >
                        </div >
                        <select class="col-2-half" ng-model="condition.orgType" ng-options="foo.value as foo.name for foo in orgTypes" class="col-2-half" >
                            <option value="" >请选择</option >
                        </select >

                        <div class="form-label col-1-half" >
                            <label >岗位名称:</label >
                        </div >
                        <input class="col-2-half" type="text" ng-model="condition.positionName" />
                    </div >
                </div >
            </div >
        </div >
    </div >
    <div class="list-result" >
        <div class="block" >
            <div class="block-header" >
                <div class="header-text" >
                    <span class="glyphicons list" ></span >
                    <span >规则列表</span >
                </div >
                <span class="header-button" >
                        <button type="button" class="btn btn-green btn-min" ng-click="add()" >
                            <span class="glyphicons plus" ></span >
                            增加
                        </button >
                        <button type="button" class="btn btn-green btn-min" ng-click="remove()" ng-disabled="!anyone" >
                            <span class="glyphicons remove" ></span >
                            删除
                        </button >
                </span >
            </div >
            <div class="block-content" >
                <div class="content-wrap" >
                    <div class="table-responsive panel panel-table first-min" >
                        <table class="table table-striped table-hover" >
                            <thead class="table-header" >
                            <tr >
                                <td >
                                    <div select-all-checkbox checkboxes="positionAllots.data" selected-items="items"
                                         anyone-selected="anyone" ></div >
                                </td >
                                <td >系统</td >
                                <td >机构类型</td >
                                <td >岗位</td >
                                <td >岗位类型</td >
                                <td >状态</td >
                                <td style="white-space: nowrap;text-align:center; width: 10px;" >操作</td >
                            </tr >
                            </thead >
                            <tbody class="table-body" >
                            <tr ng-show="!positionAllots || !positionAllots.total || positionAllots.data.length==0" >
                                <td colspan="7" class="text-center" >没有查询到数据！</td >
                            </tr >
                            <tr ng-repeat="foo in positionAllots.data" >
                                <td >
                                    <input type="checkbox" ng-model="foo.isSelected" />
                                </td >
                                <td ng-bind-template="{{foo.busiTypeName}}" ></td >
                                <td ng-bind-template="{{foo.orgTypeName}}" ></td >
                                <td ng-bind-template="{{ foo.positionVo.name }}" >
                                </td >
                                <td ng-bind-template="{{foo.positionVo.roleTypeName}}" ></td >
                                <td >{{foo.positionVo.statusName}}</td >
                                <td >
                                    <a style="cursor:pointer" title="删除" ng-click="remove(foo.id)" >
                                        <i class="icons fork" ></i >
                                    </a >
                                </td >
                            </tr >
                            </tbody >
                        </table >
                    </div >
                </div >
            </div >
        </div >
    </div >
    <div eccrm-page="pager" class="list-pagination" ></div >
</div >
</body >
</html >
