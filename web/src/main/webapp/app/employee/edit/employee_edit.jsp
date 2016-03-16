<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<%
    String contextPath = request.getContextPath();
%>
<head>
    <base href="<%=request.getContextPath()%>/">
    <title>编辑员工</title>
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8"/>
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/vendor/bootstrap-v3.0/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/style/standard/css/eccrm-common-new.css">
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/vendor/zTree/css/ztree.css">

    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/jquery-all.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-all.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-strap-all.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-ztree-all.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-upload.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/vendor/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/app/org/org.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/app/employee/employee.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/app/employee/edit/employee_edit.js"></script>

    <script type="text/javascript">
        window.angular.contextPathURL = '<%=contextPath%>';
    </script>
</head>
<body id="ng-app">
<div class="main tab" ng-app="eccrm.base.employee.edit" ng-controller="Ctrl">
    <div class="row-10">
        <div class="block">
            <div class="block-header">
                    <span class="header-text">
                        <span class="glyphicons info-sign"></span>
                        <span>个人档案</span>
                    </span>
                    <span class="header-button">
                        <button type="button" class="btn btn-sm btn-green btn-min"
                                ng-show="!page.type || page.type=='add'" ng-click="save()" ng-disabled="!form.$valid">
                            <span class="glyphicons disk_save"></span> 保存
                        </button>
                        <button type="button" class="btn btn-sm btn-green btn-min" ng-show="page.type=='modify'"
                                ng-click="update()" ng-disabled="!form.$valid && !canUpdate">
                            <span class="glyphicons claw_hammer"></span> 更新
                        </button>
                        <a type="button" class="btn btn-sm btn-green btn-min" ng-click="back();">
                            <span class="glyphicons message_forward"></span> 返回
                        </a>
                    </span>
            </div>
            <div class="block-content">
                <div class="content-wrap">
                    <form name="form" class="form-horizontal" role="form">
                        <div style="display: none;">
                            <input type="hidden" ng-model="page.type" ng-init="page.type='${pageType}'"/>
                            <input type="hidden" id="contextPath" value="<%=request.getContextPath()%>"/>
                            <input type="hidden" id="id" value="${id}"/>
                            <input type="hidden" id="pageType" value="${pageType}"/>
                        </div>
                        <div id="picture" style="position: absolute;width: 200px;height: 100px;left: 70%;">
                            <%-- 头像 --%>
                            <div eccrm-upload="uploadOptions">
                            </div>
                            <div style="position:absolute;top:5px;left: 45px;width: 180px;height: 80px;">
                                <div id="imageId" class="col" ng-show="employee.picture"
                                     style="border: 1px dashed #DAF3F5;padding: 5px 10px;"></div>
                                <i class="icons icon fork cp col" ng-show="employee.picture" ng-click="removePicture()"
                                   ng-cloak ng-if="pageType!=='detail'" style="margin-left: 8px;"></i>
                            </div>
                        </div>
                        <div class="row">
                            <div class="form-label col-1-half">
                                <label validate-error="form.employeeName">姓名:</label>
                            </div>
                            <input class="col-2-half" type="text" name="employeeName" validate validate-required
                                   validate-max-length="15" ng-model="employee.employeeName"/>

                            <div class="form-label col-1-half">
                                <label>性别:</label>
                            </div>
                            <div class="col-2-half">
                                <select ng-model="employee.gender" ng-options="foo.value as foo.name for foo in sex"
                                        class="col-12">
                                </select>
                            </div>
                        </div>


                        <div class="row">
                            <div class="form-label col-1-half">
                                <label>所属县区:</label>
                            </div>
                            <div class="col-2-half">
                                <input type="text" class="col-12" ztree-single="OrgztreeOptions"
                                       ng-model="employee.orgName" validate validate-required/>
                                    <span class="add-on">
                                       <i class="icons circle_fork icon" title="清空"
                                          ng-click="employee..organization.id=null;employee.organization.name=null;"></i>
                                    </span>
                            </div>

                            <div class="form-label col-1-half">
                                <label>手机号码:</label>
                            </div>
                            <input class="col-2-half" type="text" validate validate-int validate-max-length="20"
                                   ng-model="employee.mobile"/>
                        </div>
                        <div class="row">

                            <div class="form-label col-1-half">
                                <label>身份证号:</label>
                            </div>
                            <input class="col-2-half" type="text" validate validate-max-length="20" validate-int
                                   ng-model="employee.tel"/>

                            <div class="form-label col-1-half">
                                <label>职务:</label>
                            </div>
                            <input class="col-2-half" type="text" validate validate-max-length="40"
                                   ng-model="employee.duty"/>

                        </div>

                        <div class="row">
                            <div class="form-label col-1-half">
                                <label>民族:</label>
                            </div>
                            <select ng-model="employee.nation" class="col-2-half"
                                    ng-options="foo.value as foo.name for foo in nation">
                            </select>
                            <div class="form-label col-1-half">
                                <label>政治面貌:</label>
                            </div>
                            <select ng-model="employee.zzmm" class="col-2-half"
                                    ng-options="foo.value as foo.name for foo in zzmm">
                            </select>

                        </div>
                        <div class="row">
                            <div class="form-label col-1-half">
                                <label>邮箱:</label>
                            </div>
                            <input class="col-6-half" type="text" validate validate-max-length="100"
                                   ng-model="employee.email"/>

                        </div>
                        <div class="row">
                            <div class="form-label col-1-half">
                                <label>工作单位:</label>
                            </div>
                            <input class="col-6-half" type="text" validate validate-max-length="100"
                                   ng-model="employee.company"/>
                        </div>
                        <div class="row">
                            <div class="form-label col-1-half">
                                <label>地址:</label>
                            </div>
                            <input class="col-6-half" type="text" validate validate-max-length="100"
                                   ng-model="employee.address"/>
                        </div>
                        <div class="row">
                            <div class="form-label col-1-half">
                                <label>基本情况:</label>
                            </div>
                            <textarea rows="6" class="col-10-half" ng-model="employee.description"
                                      validate validate-max-length="500"></textarea>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>