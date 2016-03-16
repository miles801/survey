<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html >
<html >
<head >
    <title>发送的消息</title>
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8" />
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/vendor/artDialog/artDialog.css" />
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/vendor/bootstrap-v3.0/css/bootstrap.min.css" >
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/style/standard/css/eccrm-common-new.css" >
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/vendor/angular-motion-v0.3.2/angular-motion.css" >
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/vendor/zTree/css/ztree.css" >
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/jquery-all.js" ></script >
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-all.js" ></script >
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-strap-all.js" ></script >
    <link rel="stylesheet" href="<%=contextPath%>/vendor/ueditor/themes/default/css/ueditor.css"/>

    <script type="text/javascript" src="<%=contextPath%>/vendor/moment/moment.min.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/vendor/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-ztree-all.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/app/home/news/onlinetalk/onlinetalk-modal.js"></script>
    <%--<script src="<%=contextPath%>/vendor/angular-file-upload/angular-file-upload.js"></script>--%>
    <%--<script src="<%=contextPath%>/js/attachment.js"></script>--%>
    <script src="<%=contextPath%>/static/ycrl/javascript/upload.js"></script>
    <script src="<%=contextPath%>/vendor/ueditor/ueditor.config.js"></script>
    <script src="<%=contextPath%>/vendor/ueditor/ueditor.all.js"></script>
    <script type="text/javascript">
        window.angular.contextPathURL = '<%=contextPath%>';
    </script>
</head>
<body>
<div class="main condition-row-1" ng-app="eccrm.home.onlinetalk.edit"
     ng-controller="FaController">

    <div class="list-condition">
        <div class="block">
            <div class="block-header">
                <div class="header-text">
                    <span class="glyphicons list"></span> <span>发送的消息</span>
                </div>
					<span class="header-button">
						<button type="button" class="btn btn-green btn-min" ng-click="queryLetter()">
                            <span class="glyphicons search"></span> 查询
                        </button>
						<button type="button" class="btn btn-green btn-min" ng-click="conditionSend={}">
                            <span class="glyphicons repeat"></span> 重置
                        </button>
                    </span>
            </div>
            <div class="block-content">
                <div class="content-wrap">
                    <div class="row">
                        <div class="form-label col-1-half">
                            <label>发送时间:</label>
                        </div>
                        <div class="col-2-half">
                            <div class="col-5-half">
                                <input type="text" class="col-12"
                                       id="_invalidStartDate"
                                       ng-model="condition.invalidStartDate"
                                       onclick="WdatePicker({el:'_invalidStartDate',autoPickDate:false,dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'_invalidEndDate\')}'})"/>
                            <span class="add-on">
                                 <i class="icons clock icon" ng-click="cleanInvalidStartDate()"> </i>
                            </span>
                            </div>
                            <span class="col-1" style="text-align: center"> - </span>
                            <input type="text" class="col-5-half"
                                   id="_invalidEndDate"
                                   ng-model="condition.invalidEndDate"
                                   onclick="WdatePicker({el:'_invalidEndDate',autoPickDate:false,dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'_invalidStartDate\')}'})"/>
                        <span class="add-on">
                             <i class="icons clock icon" ng-click="cleanInvalidEndDate()"> </i>
                        </span>
                        </div>
                        <div class="form-label col-1-half">
                        <label>接收人:</label>
                        </div>
                        <input class="col-2-half" type="hidden" ng-model="condition.creatorId "/>
                        <input class="col-2-half" type="text" ng-model="condition.creatorName "
                               ng-click="single()"/>
                        <span class="add-on">
                            <i class="glyphicons user icon" ng-click="condition.creatorName = null"></i>
                        </span>

                        <div class="form-label col-1-half">
                            <label>类型:</label>
                        </div>
                        <select ng-model="condition.type" ng-options="foo.value as foo.name  for foo in types"
                                class="col-2-half">
                            <option value="">请选择</option>
                        </select>

                    </div>


                </div>
            </div>

        </div>
    </div>
    <div class="list-result">
        <div class="block">
            <div class="block-header">
                <div class="header-text">
                    <span class="glyphicons list"></span> <span>发件箱列表</span>
                </div>
                <div class="header-button">
                    <%--<a type="button" class="btn btn-green btn-min" ng-click="send()">
                        <span class="glyphicons message_out"></span> 发送
                    </a>--%>
                    <a type="button" class="btn btn-green btn-min" ng-click="updateStatus()" ng-disabled="!anyOne">
                        <span class="glyphicons remove"></span> 注销
                    </a>
                </div>
            </div>
            <div class="table-responsive panel panel-table first-min">
                <table class="table table-striped table-hover ">
                    <thead class="table-header">
                    <tr>
                        <td>
                            <div select-all-checkbox checkboxes="onlineTalks.data" selected-items="items"
                                 anyone-selected="anyOne"></div>
                        </td>
                        <td>标题</td>
                        <td style="width: 10px;white-space: nowrap">发送时间</td>
                        <td style=" width: 10px; white-space: nowrap;text-align: center;vertical-align: middle;">操作</td>
                    </tr>
                    </thead>
                    <tbody class="table-body">
                    <tr ng-show="!onlineTalks || !onlineTalks.data || !onlineTalks.data.length">
                        <td colspan="5" class="text-center">没有符合条件的记录！</td>
                    </tr>
                    <tr ng-repeat="foo in onlineTalks.data" ng-click="$parent.selected=foo">
                        <td>
                            <input type="checkbox" ng-model="foo.isSelected"/>
                        </td>
                        <td>
                            <a style="cursor: pointer"
                               ng-bind-template="{{foo.subject}}"
                               ng-href="<%=contextPath%>/operating/operatingOnlineContact/letterDetail/{{foo.id}}"
                               title="查看明细">
                            </a>
                        </td>
                        <td>
                            {{ foo.createdDatetime | date:'yyyy-MM-dd HH:mm:ss' }}
                        </td>
                        <td>
                            <a type="button" class="btn btn-tiny" ng-click="addRead(foo.id)" title="回复记录" ng-disabled="foo.isReply== false||foo.isread== false">
                                <span class="icons book"></span>
                            </a>

                            <!--<a type="button" class="btn btn-tiny" ng-click="forwarding(foo.id)" title="转发">-->
                            <!--<span class="glyphicons redo"></span>-->
                            <!--</a>-->
                        </td>
                    </tr>

                    </tbody>
                </table>
            </div>
        </div>
    </div>
    <div eccrm-page="pagerLetter" class="list-pagination"></div>
</div>

</body>
<script type="text/javascript" src="<%=contextPath%>/app/base/user/user.js"></script>
<script type="text/javascript" src="<%=contextPath%>/app/base/usergroup/usergroup.js"></script>
<script type="text/javascript" src="<%=contextPath%>/app/base/user/user-modal.js"></script>
<script type="text/javascript" src="<%=contextPath%>/app/home/news/onlinetalk/onlinetalk.js"></script>
<script type="text/javascript" src="<%=contextPath%>/app/home/news/onlinetalk/list/onlinetalk_list.js"></script>
<script type="text/javascript" src="<%=contextPath%>/app/home/news/onlinetalk/list/onlinetalk_list2.js"></script>

</html>