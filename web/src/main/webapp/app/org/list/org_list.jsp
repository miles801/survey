<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en" >
    <%
    String contextPath = request.getContextPath();
%>
<html lang="en" >
<head >
    <%--<base href="<%=request.getContextPath()%>/" >--%>
    <title >机构列表</title >
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8" />
    <%--<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/wanda/styles/wanda-comm.css" />--%>
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
    <script type="text/javascript" src="<%=contextPath%>/app/base/region/region.js" ></script >
    <script type="text/javascript" src="<%=contextPath%>/app/org/orgConstant.js" ></script >
    <script type="text/javascript" src="<%=contextPath%>/app/org/org.js" ></script >
    <script type="text/javascript" src="<%=contextPath%>/app/org/org-modal.js" ></script >
    <script type="text/javascript" src="<%=contextPath%>/app/org/orgTemp.js" ></script >

    <script >
        window.angular.contextPathURL = '<%=contextPath%>';
    </script >
</head >
<body id="ng-app" >
<div class="main " ng-app="base.org.list" ng-controller="OrgListController" >
    <div eccrm-alert="alert" ></div >
    <input type="hidden" id="contextPath" value="<%=request.getContextPath()%>" />

    <div class="row panel panel-tree" >
        <div class="tree" style="border: 1px solid #B7C1CB;width: 400px" >
            <div class="tree-content" >
                <ul id="treeDemo" class="ztree" ></ul >
            </div >
            <div class="scroller" ></div >
        </div >
        <div class="content " style="padding-left: 410px" >
            <div class="list-result no-pagination" >
                <div class="block" >

                    <div class="block-header" >
                        <div class="header-text" >
                            <span class="glyphicons list" ></span > <span >组织机构</span >
                        </div >
                        <div class="header-button" >
                            <a type="button" class="btn btn-green btn-min" ng-click="update()" ng-if="id" >
                                更新
                            </a >
                            <a type="button" class="btn btn-green btn-min" ng-click="addOrg()" >
                                添加
                            </a >
                        </div >
                    </div >
                    <div class="block-content" >
                        <div class="content-wrap" >
                            <form name="form" class="form-horizontal" role="form" >
                                <div style="display: none;" >
                                    <input type="hidden" id="pageType" value="${pageType}" />
                                    <input type="hidden" id="id" value="${id}" />
                                </div >
                                <div class="row" >
                                    <div class="form-label col-2" >
                                        <label ><span style="color: red;" > * </span >名称:</label >
                                    </div >
                                    <input class="col-4" type="text" ng-model="beans.longName"
                                           validate
                                           validate-required autofocus validate-max-length="32"
                                            />

                                    <div class="form-label col-1-half" >
                                        <label ><span style="color: red;" > * </span >简称:</label >
                                    </div >
                                    <input class="col-4" type="text"
                                           validate validate-required autofocus validate-max-length="16" ng-model="beans.name" />
                                </div >
                                <div class="row" >
                                    <div class="form-label col-2" >
                                        <label >拼音(全拼):</label >
                                    </div >
                                    <input class="col-4" type="text"
                                           validate validate-max-length="25" autofocus ng-model="beans.pinyin" />

                                    <div class="form-label col-1-half" >
                                        <label >拼音(简拼):</label >
                                    </div >
                                    <input class="col-4" type="text"
                                           validate validate-max-length="20" ng-model="beans.jianpin" />
                                </div >
                                <div class="row" >
                                    <div class="form-label col-2" >
                                        <label >GroupID:</label >
                                    </div >
                                    <span ng-bind-template="{{ beans.id }}" class="col-4" ></span >

                                    <div class="form-label col-1-half" >
                                        <label >是否直属:</label >
                                    </div >
                                    <input type="checkbox" ng-model="beans.isDirectly" />
                                </div >
                                <div class="row" >
                                    <div class="form-label col-2" >
                                        <label >上级机构:</label >
                                    </div >
                                    <div class="pr col-4" >
                                        <input class="col-12" ztree-single="ztreeOptions" ng-model="beans.parentName" />

                                        <input type="hidden" ng-model="beans.parentId" />
                                    <span class="add-on" style="position: fixed;" >
                                           <i class="icons circle_fork icon" ng-click="clearOrg()" title="清除" ></i >
                                    </span >
                                    </div >
                                    <div class="form-label col-1-half" >
                                        <label >排序:</label >
                                    </div >
                                    <input class="col-4" type="number" ng-model="beans.sequenceNo" validate validate-int
                                           validate-max-length="4" />
                                </div >

                                <div class="row" >
                                    <div class="form-label col-2" >
                                        <label ><span style="color: red;" > * </span >所在城市:</label >
                                    </div >
                                    <div class="col-4" >
                                        <input type="text" class="col-12" validate validate-required ztree-single="regionOptions"
                                               ng-model="beans.businessAreaName" />
                                    <span class="add-on" >
                                        <i class="icons circle_fork icon" ng-click="clearRegion()" title="清除" ></i >
                                      </span >
                                    </div >

                                    <div class="form-label col-1-half" >
                                        <label >所属系统:</label >
                                    </div >
                                    <select ng-model="beans.busiTypeId" class="col-4" ng-options="foo.value as foo.name for foo in busiTypeIds" >
                                    </select >
                                </div >
                                <div class="row" >
                                    <div class="form-label col-2" >
                                        <label >机构类型:</label >
                                    </div >
                                    <select ng-model="beans.orgType" class="col-4" ng-options="foo.value as foo.name for foo in orgTypes" >
                                    </select >

                                    <div class="form-label col-1-half" >
                                        <label ></label >
                                    </div >
                                    <input type="checkbox" style="height: 12px;" ng-model="beans.isOrgs" />是机构
                                    <input type="checkbox" style="height: 12px;margin-left: 8px;" ng-model="beans.isStatus" />启用
                                </div >
                                <div class="row" >
                                    <div class="form-label col-2" >
                                        <label >备注:</label >
                                    </div >
                                <textarea class="col-9-half" rows="3"
                                          validate validate-max-length="1000" ng-model="beans.description" ></textarea >

                                </div >
                                <div class="row" >
                                    <div class="form-label col-2" >
                                        <label >创建时间:</label >
                                    </div >
                                    <span ng-bind-template="{{beans.createdDatetime | eccrmDatetime}}" class="col-4" ></span >

                                    <div class="form-label col-1-half" >
                                        <label >修改时间:</label >
                                    </div >
                                    <span ng-bind-template="{{beans.modifiedDatetime | eccrmDatetime}}" class="col-4" ></span >
                                </div >
                            </form >
                        </div >
                    </div >
                </div >
            </div >
        </div >

    </div >
</div >
<script type="text/javascript" src="<%=contextPath%>/app/org/list/org_list.js" ></script >
</body >
</html >