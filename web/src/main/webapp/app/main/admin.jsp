<%--
  Created by IntelliJ IDEA.
  User: miles
  Date: 13-11-25
  Time: 下午12:56
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en" >
<head >
    <title >客诉中心服务平台</title >
    <link rel="stylesheet" href="<%=contextPath%>/vendor/bootstrap-v3.0/css/bootstrap.min.css" />
    <link rel="stylesheet" href="<%=contextPath%>/style/standard/css/eccrm-common-new.css" />
    <link rel="stylesheet" href="<%=contextPath%>/app/main/css/main.css" />
    <link rel="stylesheet" href="<%=contextPath%>/static/wanda/styles/wanda-comm.css" />
    <link rel="stylesheet" href="<%=contextPath%>/vendor/angular-motion-v0.3.2/angular-motion.min.css" />
    <link rel="stylesheet" href="<%=contextPath%>/vendor/angular-motion-v0.3.2/angular-addition.css" />
    <link rel="stylesheet" href="<%=contextPath%>/vendor/zTree/css/ztree.css" type="text/css" >

    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/jquery-all.js" ></script >
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-all.js" ></script >
    <script type="text/javascript" src="<%=contextPath%>/app/main/js/nav.js" ></script >
    <script >
        window.angular.contextPathURL = "<%=contextPath%>";
    </script >
</head >
<body id="ng-app" ng-app="eccrm.main" >
<div id="container" ng-controller="MainController" >
    <%--顶部导航条--%>
    <div id="header" >
        <div class="top" >
            <div class="logo" ></div >
            <div class="tool" >
                <div >
                    <a href="<%=contextPath%>/home/tools/notebook" target="iframe" >

                    </a >
                    <a href="<%=contextPath%>/logout" >
                        <img src="<%=contextPath%>/app/main/images/icon/h13.png" width="24" height="24" title="退出" >
                    </a >
                </div >
            </div >
        </div >
    </div >
    <div id="main" >
        <div class="leftbar" >
            <div class="LB_container" >
                <a href="#" title="{{menu.name}}" ng-repeat="menu in menus" ng-click="showChildren(menu);" >
                    <img ng-if="$index==0" src="<%=contextPath%>/app/main/images/icon/l9.png" />
                    <img ng-if="$index==1" src="<%=contextPath%>/app/main/images/icon/l11.png" />
                </a >
            </div >
            <div class="btnT" ></div >
            <div class="btnB" ></div >
        </div >
        <div class="mainRight" >
            <%--左侧导航菜单--%>
            <div id="accordian" >
                <ul >
                    <li ng-repeat="level1 in subMenus" >
                        <h3 nav-click-slide=".nav_menus" >
							<span class="menu-text" >
								<a ng-href="{{level1.url}}" ng-bind-template="{{level1.name}}" target="iframe" ></a >
							</span >
							<span class="menu-children" ng-show="level1.children.length>0" >
								<span class="menu-children" style="position:relative" >
                                    <span style="position:absolute;color:#1893dd;" >&#9660;</span >
                                </span >
							</span >
                        </h3 >
                        <ul class="nav_menus" >
                            <li ng-repeat="level2 in level1.children" >
                                <div ng-if="level2.children && level2.children.length>0" >
                                    <a nav-click-slide="div" style="cursor: pointer;" ng-href="{{level2.url}}" target="iframe" >
                                        <span ng-bind-template="{{level2.name}}" class="menu-text" ></span >
										<span class="menu-children" >
											<span style="color:#1893dd;" >&#9660;</span >
										</span >
                                    </a >

                                    <div style="margin-left: 10px;display: none;"
                                         ng-if="level2.children && level2.children.length>0" ng-repeat="level3 in level2.children" >
                                        <a ng-href="{{level3.url}}" target="iframe" style="cursor: pointer;" >
                                            <span style="margin-right:3px;color:#1893dd;" >&#8627;</span ><span ng-bind-template="{{level3.name}}" class="menu-text" ></span >
                                        </a >
                                    </div >
                                </div >
                                <%--没有子节点--%>
                                <a ng-if="!level2.children || level2.children.length<1" ng-href="{{level2.url}}" target="iframe" >

                                    <span ng-bind-template="{{level2.name}}" class="menu-text" ></span >
									<span ng-if="level2.children && level2.children.length>0" class="menu-children"
                                          ng-show="level1.children.length>0" >
										<i class="glyphicons expand" ></i >
									</span >
                                </a >
                            </li >
                        </ul >
                    </li >
                </ul >
            </div >
            <div id="colbar" >
                <div id="arrow" >
                    <i class="arrow-left" id="fold" title="收起" ></i >
                    <i class="arrow-right" id="expand" title="展开" style="display: none;" ></i >
                </div >
            </div >
            <div class="content-iframe" >
                <iframe id="iframe" name="iframe" frameborder="0" ></iframe >
            </div >
        </div >
    </div >
    <div class="footer" >
        <%--<div ><img src="<%=contextPath%>/app/main/images/icon/f1.png" align="absmiddle" alt="等待" /> 等待 <span >12</span >
        </div >
        <div ><img src="<%=contextPath%>/app/main/images/icon/f2.png" align="absmiddle" alt="空闲" /> 空闲 <span >8</span >
        </div >
        <div ><img src="<%=contextPath%>/app/main/images/icon/f3.png" align="absmiddle" alt="小休" /> 小休 <span >25</span >
        </div >
        <div ><img src="<%=contextPath%>/app/main/images/icon/f4.png" align="absmiddle" alt="签出" /> 签出 <span >11</span >
        </div >
        <div ><img src="<%=contextPath%>/app/main/images/icon/f5.png" align="absmiddle" alt="服务水平" />
            <span >服务水平 80%</span ></div >
        <hr />
        <div >姓名：<span >李四</span ></div >
        <div >工号：<span >00001</span ></div >
        <div >
            <span >客户满意度：</span >
            <span >
                <img src="<%=contextPath%>/app/main/images/star.png" align="absmiddle" />
                <img src="<%=contextPath%>/app/main/images/star.png" align="absmiddle" />
                <img src="<%=contextPath%>/app/main/images/star.png" align="absmiddle" />
                <img src="<%=contextPath%>/app/main/images/star_half.png" align="absmiddle" />
                <img src="<%=contextPath%>/app/main/images/star_empty.png" align="absmiddle" />
            </span >
        </div >--%>
        <a href="#" class="corner" ></a >
    </div >
</div >
</body >

<script src="<%=contextPath%>/static/ycrl/javascript/angular-ztree-all.js" type="text/javascript" ></script >
<script src="<%=contextPath%>/app/base/resource/menu/menu.js" type="text/javascript" ></script >
<script src="<%=contextPath%>/app/main/js/admin.js" type="text/javascript" ></script >
</html >