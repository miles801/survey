<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html >
<html >
<head >
    <title >岗位管理列表</title >
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8" />
    <meta http-equiv="pragma" content="no-cache" >
    <meta http-equiv="cache-control" content="no-cache" >
    <meta http-equiv="expires" content="0" >
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/vendor/artDialog/artDialog.css" />
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/vendor/bootstrap-v3.0/css/bootstrap.min.css" >
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/style/standard/css/eccrm-common-new.css" >
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/vendor/angular-motion-v0.3.2/angular-motion.css" >
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/vendor/zTree/css/ztree.css" >
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/jquery-all.js" ></script >
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-all.js" ></script >
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-strap-all.js" ></script >

    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-ztree-all.js" ></script >
    <script type="text/javascript" src="<%=contextPath%>/vendor/zTree/js/jquery.ztree.exedit-3.5.min.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/app/position/position.js" ></script >
    <script type="text/javascript" src="<%=contextPath%>/app/org/org.js" ></script >
    <script type="text/javascript" src="<%=contextPath%>/app/position/position-modal.js" ></script >
    <script type="text/javascript" src="<%=contextPath%>/app/position/list/position_list.js" ></script >
    <script type="text/javascript" >
        window.angular.contextPathURL = '<%=contextPath%>';
    </script >

</head >
<body id="ng-app" >
<div class="main " ng-app="eccrm..position.list" ng-controller="PositionListController" >
    <input type="hidden" id="contextPath" value="<%=request.getContextPath()%>" />

    <div class="row panel panel-tree" >
        <%-- 左侧树 --%>
        <div class="tree" style="border: 1px solid #B7C1CB;width: 200px" >
            <div class="tree-content" >
                <ul id="treeDemo" class="ztree" ></ul >
            </div >
            <div class="scroller" ></div >
        </div >
        <%-- 右侧正文内容 --%>
        <div class="content" style="padding-left: 210px" >
            <%-- 订单结果列表 --%>
            <div class="list-result" >
                <div class="block" >
                    <div class="block-header" >
                        <span class="header-text" >
                            <span class="glyphicons search" ></span >
                                <span >岗位列表</span >
                        </span >
                        <span class="header-button" >
                             <button type="button" class="btn btn-green btn-min" ng-click="addAllot()" >
                                 添加分类
                             </button >
                            <button type="button" class="btn btn-green btn-min" ng-click="add()" >
                                添加岗位
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
                                            <div select-all-checkbox checkboxes="holidays.data" selected-items="items"
                                                 anyone-selected="anyone" ></div >
                                        </td >
                                        <td >岗位名称</td >
                                        <td >简称</td >
                                        <td >岗位类型</td >
                                        <td >状态</td >
                                        <td >操作</td >
                                    </tr >
                                    </thead >
                                    <tbody class="table-body" >
                                    <tr ng-show="!positions || !positions.total || positions.data.length==0" >
                                        <td colspan="6" class="text-center" >没有查询到数据！</td >
                                    </tr >
                                    <tr bindonce ng-repeat="foo in positions.data" >
                                        <td >
                                            <input type="checkbox" ng-model="foo.isSelected" />
                                        </td >
                                        <td title="点击查询明细！" style="cursor: pointer;text-align: left" >
                                            <a ng-click="detail(foo.id)" bo-text="foo.name" ></a >
                                        </td >
                                        <td bo-text="foo.jianPin" ></td >
                                        <td bo-text="foo.roleTypeName" ></td >
                                        <td bo-text="foo.statusName" ></td >
                                        <td >
                                            <a style="cursor:pointer" title="修改" ng-click="edit(foo.id)" >
                                                <i class="icons edit" ></i >
                                            </a >
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
    </div >
</div >
</body >
</html >