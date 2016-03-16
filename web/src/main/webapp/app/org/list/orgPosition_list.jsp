<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html >
<html >
<head >
    <title >机构岗位</title >
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8" />
    <meta http-equiv="pragma" content="no-cache" >
    <meta http-equiv="cache-control" content="no-cache" >
    <meta http-equiv="expires" content="0" >
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/vendor/bootstrap-v3.0/css/bootstrap.min.css" >
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/style/standard/css/eccrm-common-new.css" >
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/vendor/angular-motion-v0.3.2/angular-motion.css" >
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/vendor/zTree/css/ztree.css" >
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/jquery-all.js" ></script >
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-all.js" ></script >
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-strap-all.js" ></script >
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-ztree-all.js" ></script >

    <script type="text/javascript" src="<%=contextPath%>/app/org/org.js" ></script >
    <script type="text/javascript" src="<%=contextPath%>/app/position/position.js" ></script >
    <script type="text/javascript" src="<%=contextPath%>/app/org/template/orgPosition-modal.js" ></script >
    <script type="text/javascript" src="<%=contextPath%>/app/org/orgPosition.js" ></script >
    <script type="text/javascript" src="<%=contextPath%>/app/org/list/orgPosition_list.js" ></script >
    <script type="text/javascript" >
        window.angular.contextPathURL = '<%=contextPath%>';
    </script >

</head >
<body id="ng-app" >
<div class="main " ng-app="base.orgPosition.list" ng-controller="OrgPositionListController" >
    <input type="hidden" id="contextPath" value="<%=request.getContextPath()%>" />

    <div class="row panel panel-tree" >

        <div class="tree" style="border: 1px solid #B7C1CB;width: 350px" >
            <div class="tree-content" >
                <ul id="treeDemo" class="ztree" ></ul >
            </div >
            <div class="scroller" ></div >
        </div >

        <div class="content " style="padding-left: 355px" >

            <div class="list-result" >
                <div class="block" >

                    <div class="block-header" >
                        <div class="header-text" >
                            <span class="glyphicons list" ></span > <span >机构岗位</span >
                        </div >
                        <div class="header-button" >
                            <a type="button" class="btn btn-green btn-min" ng-click="addPosition()" >
                                选择
                            </a >
                            <a type="button" class="btn btn-green btn-min" ng-disabled="!anyone"
                               ng-click="del()" >
                                删除
                            </a >

                        </div >
                    </div >
                    <div class="block-content" >
                        <div class="content-wrap" >
                            <div class="table-responsive panel panel-table" >
                                <table class="table table-striped table-hover" >
                                    <thead class="table-header" >
                                    <tr >
                                        <td style="width: 12px" >
                                            <div select-all-checkbox checkboxes="beans.data"
                                                 selected-items="items" anyone-selected="anyone" ></div >
                                        <td style="width: 30%" >岗位名称</td >
                                        <td >简称</td >
                                        <%--<td>拼音全拼</td>--%>
                                        <%--<td>拼音简拼</td>--%>
                                        <td >岗位类型</td >
                                        <td style="white-space: nowrap;text-align:center; width: 10px;" >操作</td >
                                    </tr >
                                    </thead >
                                    <tbody class="table-body" >
                                    <tr ng-show="!beans||beans.length== 0 ||!beans.data.length" >
                                        <td colspan="5" class="text-center" >没有符合条件的记录！</td >
                                    </tr >
                                    <tr ng-repeat="foo in beans.data" ng-show="beans.total != 0" >
                                        <td >
                                            <input type="checkbox" style="height: 12px;" ng-model="foo.isSelected" />
                                        </td >
                                        <td >
                                            <span ng-bind-template="{{ foo.name }}" ></span ></td >
                                        <td ><span ng-bind-template="{{ foo.shortName }}" ></span ></td >
                                        <%--<td><span ng-bind-template="{{ foo.pinyin }}"></span></td>--%>
                                        <%--<td><span ng-bind-template="{{ foo.jianPin }}"></span></td>--%>
                                        <td ><span ng-bind-template="{{ foo.roleTypeName}}" ></span ></td >
                                        <td ><a style="cursor:pointer" ng-click="remove(foo.id)" >
                                            <i class="icons fork" ></i >
                                        </a >
                                            <%-- <a style="cursor:pointer"
                                                ng-href="app/knowledge/knowledgeCollection/operation/knowledge_merge.jsp">
                                                 <i class="icons user" ></i >
                                             </a>--%>
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