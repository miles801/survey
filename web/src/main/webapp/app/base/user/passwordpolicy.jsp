<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en" >
<head >
    <title >编辑密码策略</title >
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8" />
    <!--[if IE]>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" >
    <![endif]-->
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/vendor/bootstrap-v3.0/css/bootstrap.min.css" >
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/style/standard/css/eccrm-common-new.css" >
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/vendor/angular-motion-v0.3.2/angular-motion.css" >
    <script type="text/javascript" src="<%=contextPath%>/vendor/jquery-v1.8.3/jquery.min.js" ></script >
    <script type="text/javascript" src="<%=contextPath%>/vendor/jquery-v1.8.3/jquery.md5.js" ></script >
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-all.js" ></script >
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-strap-all.js" ></script >
    <script type="text/javascript" >
        window.angular.contextPathURL = "<%=contextPath%>";
    </script >
</head >
<body style="overflow-y: auto;" >
<div class="main" ng-app="eccrm.base.passwordpolicy.edit" ng-controller="PasswordPolicyController" >
    <div class="block" >
        <div class="block-header" >
                <span class="header-text" >
                    <span class="glyphicons circle_info" ></span >
                    <span >密码策略</span >
                </span >
                <span class="header-button" ng-cloak >
                    <button ng-show="!pwd.id" class="btn btn-green btn-min" ng-disabled="form.$invalid" ng-click="save();" >
                        <span class="glyphicons floppy_saved" ></span > 保存
                    </button >

                    <button ng-show="pwd.id" class="btn btn-green btn-min" ng-disabled="form.$invalid" ng-click="update()" >
                        <span class="glyphicons claw_hammer" ></span > 更新
                    </button >
                </span >
        </div >
        <div class="block-content" >
            <form name="form" >
                <div class="row" >
                    <div class="form-label col-1-half" >
                        <label >密码限制:</label >
                    </div >
                    <div class="col-10" >
                        <input type="radio" id="noLimit" ng-model="pwd.limitType" ng-value="1" />
                        <label for="noLimit" style="padding-right: 15px;" >不限制</label >
                        <input type="radio" id="requireLetters" ng-model="pwd.limitType" ng-value="2" />
                        <label for="requireLetters" style="padding-right: 15px;" >必须包括字母</label >
                    </div >
                </div >
                <div class="row" >
                    <div class="form-label col-1-half" >
                        <label >密码长度:</label >
                    </div >
                    <div class="col-2-half" >
                        <input type="number" class="col-5" name="minLength" ng-model="pwd.minLength"
                               validate validate-required validate-int validate-max-length="2"
                               validate-min-value="1" validate-max-value="16" />
                        <span class="col-2 text-center" > -- </span >
                        <input type="number" class="col-5" name="maxLength" ng-model="pwd.maxLength"
                               validate validate-required validate-int validate-max-length="2"
                               validate-min-value="1" validate-max-value="16" />
                    </div >

                    <div class="form-label col-1-half" >
                        <label >默认密码:</label >
                    </div >
                    <input type="text" class="col-2-half" ng-model="pwd.defaultPassword" name="defaultPassword"
                           validate validate-required />

                </div >
                <div class="row" >
                    <div class="form-label col-1-half" >
                        <label >密码有效期:</label >
                    </div >
                    <select class="col-2-half" ng-model="pwd.effectivePeriod"
                            ng-options="foo.code as foo.name for foo in effectivePeriod" >
                    </select >

                    <div class="form-label col-1-half" >
                        <label >过期处理方式:</label >
                    </div >
                    <select ng-model="pwd.expireHandler" class="col-2-half"
                            ng-options="foo.code as foo.name for foo in expireHandler" >
                    </select >

                </div >
                <div class="row" >
                    <div class="form-label col-1-half" >
                        <label >备注:</label >
                    </div >
                    <textarea rows="6" class="col-10" ng-model="pwd.description" ></textarea >
                </div >
                <div class="row" ng-show="pwd.id" ng-cloak >
                    <div class="form-label col-1-half" >
                        <label >最后修改人:</label >
                    </div >
                    <span class="col-2-half" ng-bind-template="{{ pwd.modifierName || pwd.creatorName }}" ></span >

                    <div class="form-label col-1-half" >
                        <label >最后修改时间:</label >
                    </div >
                    <span class="col-2-half" ng-bind-template="{{pwd.modifiedDatetime | eccrmDatetime }}" ></span >
                </div >
            </form >
        </div >
    </div >
</div >
</body >
<script type="text/javascript" src="<%=contextPath%>/app/base/user/passwordpolicy.js" ></script >
<script type="text/javascript" src="<%=contextPath%>/app/base/user/passwordpolicy-edit.js" ></script >
</html >