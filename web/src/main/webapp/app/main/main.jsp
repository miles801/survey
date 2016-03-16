<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>银川市“红黄蓝”闲散青少年结对帮扶数据库</title>
    <link rel="stylesheet" href="<%=contextPath%>/vendor/bootstrap-v3.0/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="<%=contextPath%>/style/standard/css/eccrm-common-new.css"/>
    <link rel="stylesheet" href="<%=contextPath%>/app/main/css/main.css"/>

    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/jquery-all.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-all.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-strap-all.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/app/main/js/nav.js"></script>
    <script>
        window.angular.contextPathURL = "<%=contextPath%>";
    </script>
    <style>
        .aside {
            top: inherit !important;
        }
    </style>
</head>
<body id="ng-app" ng-app="eccrm.main">
<div id="container" ng-controller="MainController">
    <input type="hidden" id="contextPath" value="<%=contextPath%>/"/>
    <div id="header">
        <div class="top">
            <div class="logo"></div>
            <span style=" color: #fff; font-size: 26px; margin-left: 65px; height: 60px; display: inline-block; line-height: 60px; ">
                | 银川市“红黄蓝”闲散青少年结对帮扶数据库
            </span>
            <div class="tool" style="width: 100px;">
                <a href="<%=contextPath%>/logout">
                    <img src="<%=contextPath%>/app/main/images/icon/h13.png" width="24" height="24" title="退出">
                </a>
                <a ng-click="updatePwd();">
                    <img src="<%=contextPath%>/app/main/images/icon/h7.png" width="24" height="24" title="更改密码">
                </a>
            </div>
            <span style="font-size: 16px;position: absolute;right: 100px;color:#fff;top:30px;">共青团银川市委员会</span>
        </div>
    </div>
    <div id="main">
        <div class="leftbar">
            <div class="LB_container">
                <a title="首页" ng-click="showHome();">
                    <img src="<%=contextPath%>/app/main/images/home.png" alt="首页"/>
                </a>
                <a bindonce bo-title="menu.name" ng-repeat="menu in menus" ng-repeat-finish
                   ng-click="showChildren(menu);">
                    <img ng-src="<%=contextPath%>/attachment/download?id={{menu.icon}}" ng-cloak
                         bo-if="menu.icon"
                    />
                </a>
            </div>
            <div class="btnT"></div>
            <div class="btnB"></div>
        </div>
        <div class="mainRight">
            <div id="accordian">
                <ul>
                    <li bindonce ng-repeat="level1 in subMenus" ng-repeat-finish="subFinish">
                        <h3 nav-click-slide=".nav_menus">
							<span class="menu-text">
                                <i class="icons-sj"></i>
								<a ng-click="addTab(level1.name,level1.url,level1)" bo-text="level1.name"></a>
							</span>
							<span class="menu-children" bo-show="level1.children.length>0">
								<span class="menu-children" style="position:relative">
                                    <span style="position:absolute;color:#ffffff;">&#9660;</span>
                                </span>
							</span>
                        </h3>
                        <ul class="nav_menus">
                            <li bindonce ng-repeat="level2 in level1.children" bindonce>
                                <div bo-if="level2.children && level2.children.length>0">
                                    <a nav-click-slide="div" style="cursor: pointer;"
                                       ng-click="addTab(level2.name,level2.url,level2)">
                                        <span bo-text="level2.name" class="menu-text"></span>
										<span class="menu-children">
											<span style="color:#1893dd;">&#9660;</span>
										</span>
                                    </a>

                                    <div style="margin-left: 10px;display: none;"
                                         bo-if="level2.children && level2.children.length>0">
                                        <a ng-click="addTab(level3.name,level3.url,level3)" style="cursor: pointer;"
                                           bindonce ng-repeat="level3 in level2.children">
                                            <span style="margin-right:3px;color:#1893dd;">&#8627;</span>
                                            <span bo-text="level3.name" class="menu-text"></span>
                                        </a>
                                    </div>
                                </div>
                                <a bo-if="!level2.children || level2.children.length<1"
                                   ng-click="addTab(level2.name,level2.url)">
                                    <span bo-text="level2.name" class="menu-text"></span>
                                </a>
                            </li>
                        </ul>
                    </li>
                </ul>
            </div>
            <div id="colbar">
                <div id="arrow">
                    <i class="arrow-left" id="fold" title="收起"></i>
                    <i class="arrow-right" id="expand" title="展开" style="display: none;"></i>
                </div>
            </div>
            <div class="content-iframe">
                <iframe id="iframe" style="display: none;" name="iframe" frameborder="0"></iframe>
                <div id="tab" style="height: 100%;width: 100%;overflow: hidden;"></div>
            </div>
        </div>
    </div>
    <div class="footer" id="footer">
        <div class="left">
            <span><i class="icons user" title="当前用户"
                     style="top:3px;"></i><span>${sessionScope.employeeName}</span></span>
            <span style="margin-left: 15px;"><i class="icons clock" title="登录时间"></i><span
                    ng-cloak>{{${sessionScope.loginDatetime} | date:'yyyy-MM-dd HH:mm'}}</span></span>
            <span style="margin-left: 15px;font-weight: 700;" ng-cloak eccrm-previlege="PMD">消息：</span>
        </div>

        <div class="center">
            <marquee direction="left" onmouseover="this.stop()" onmouseout="this.start()">
                *** 内部资料,注意保密! ***
            </marquee>
        </div>
        <div class="right" style="width: 100px;">
            <span title="便签">
                <i class="icons note" ng-click="showNote();"></i>
            </span>
            <span title="消息">
                <i class="icons message"></i>
                <span style="position: relative;top:-2px;">(<span><a style="color: #fff;cursor: pointer" title="查看消息"
                                                                     ng-click="showMessages();"
                                                                     ng-cloak>{{messages}}</a></span>)</span>
            </span>
        </div>
    </div>
</div>
</body>
<script type="text/javascript" src="<%=contextPath%>/app/base/im/news/news.js"></script>
<script type="text/javascript" src="<%=contextPath%>/app/base/user/user.js"></script>
<script type="text/javascript" src="<%=contextPath%>/app/base/user/passwordpolicy.js"></script>
<script type="text/javascript" src="<%=contextPath%>/vendor/jquery-v1.8.3/jquery.md5.js"></script>
<script type="text/javascript" src="<%=contextPath%>/app/base/user/password-modal.js"></script>
<script src="<%=contextPath%>/app/main/js/main.js" type="text/javascript"></script>
</html>