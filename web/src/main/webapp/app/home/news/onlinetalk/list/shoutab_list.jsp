<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html >
<html >
<head >
    <title>收到的消息</title>
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


<div class="main condition-row-2" ng-app="eccrm.home.onlinetalk.edit"
     ng-controller="ShouController">

    <div class="list-condition">
        <div class="block">
            <div class="block-header">
                <div class="header-text">
                    <span class="glyphicons list"></span> <span>收到的消息</span>
                </div>
					<span class="header-button">
						<a type="button" class="btn btn-green btn-min" ng-click="query();">
                            <span class="glyphicons search"></span> 查询
                        </a>
						<button type="button" class="btn btn-green btn-min" ng-click="condition={}">
                            <span class="glyphicons repeat"></span> 重置
                        </button>
                    </span>
            </div>
            <div class="block-content">
                <div class="content-wrap">
                    <div class="row">
                        <div class="form-label col-1-half">
                            <label>发送人:</label>
                        </div>

                        <input class="col-2-half" type="hidden" ng-model="condition.creatorId"/>
                        <input class="col-2-half" type="text" ng-model="condition.creatorName"
                               ng-click="single()"/>
                        <span class="add-on">
                            <i class="glyphicons user icon" ng-click="condition.creatorName=null"></i>
                        </span>

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
                            <label></label>
                        </div>

							<span class="col-2-half">
                                <label style="cursor: pointer;margin-right: 10px;">
                                    <input type="checkbox" style="height: 12px;" ng-model="condition.isread"
                                            />已阅读
                                </label>
                                <label style="cursor: pointer;margin-right: 10px;">
                                    <input type="checkbox" style="height: 12px;" ng-model="condition.isReply"
                                            />已回复
                                </label>
                            </span>
                    </div>
                    <div class="row">
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
                    <span class="glyphicons list"></span> <span>收信箱列表</span>
                </div>
                <div class="header-button">

                    <!-- <a type="button" class="btn btn-green btn-min" ng-click="send()">
                         <span class="glyphicons message_out"></span> 发送 </a>-->
                </div>
            </div>
            <div class="table-responsive panel panel-table ">
                <table class="table table-striped table-hover ">
                    <thead class="table-header">
                    <tr>
                        <td>标题</td>
                        <td style=" width: 10px; white-space: nowrap;text-align: center;vertical-align: middle;">发送人
                        </td>
                        <td style=" width: 10px; white-space: nowrap;text-align: center;vertical-align: middle;">发送时间
                        </td>
                        <td style=" width: 10px; white-space: nowrap;text-align: center;vertical-align: middle;">已阅读
                        </td>
                        <td style=" width: 10px; white-space: nowrap;text-align: center;vertical-align: middle;">已回复
                        </td>
                        <td style=" width: 10px; white-space: nowrap;text-align: center;vertical-align: middle;">操作</td>
                    </tr>
                    </thead>
                    <tbody class="table-body">
                    <tr ng-show="!onlineTalks || !onlineTalks.data || !onlineTalks.data.length">
                        <td colspan="7" class="text-center">没有符合条件的记录！</td>
                    </tr>
                    <tr ng-repeat="foo in onlineTalks.data" ng-click="$parent.selected=foo">
                        <td>
                            <a style="cursor: pointer"
                               ng-bind-template="{{foo.subject}}"
                               ng-href="<%=contextPath%>/operating/operatingOnlineContact/inboxDetail/{{foo.id}}"
                               title="查看">
                            </a>
                            <!--ng-bind-html="foo.content | limitTo:45"-->
                        </td>
                        <td ng-bind-template="{{ foo.creatorName}}"></td>
                        <td ng-bind-template="{{ foo.createdDatetime | date:'yyyy-MM-dd HH:mm:ss'}}"
                            style="width: 12%"></td>
                        <td ng-bind-template="{{foo.isread?'是':'否'}}"></td>
                        <td ng-bind-template="{{ foo.isReply?'是':'否'}}"></td>
                        <td>
                            <a type="button" class="btn btn-tiny" ng-click="reply(foo.id)" title="回复"
                               ng-disabled="foo.needReply ==false || foo.status =='2'">
                                <span class="icons logout"></span>
                            </a>
                            <!--<a type="button" class="btn btn-tiny"
                               ng-click="forwarding('222222')" title="转发">
                                <span class="glyphicons redo"></span>
                            </a>-->
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
    <div eccrm-page="pager" class="list-pagination"></div>


</div>

</body>
<script type="text/javascript" src="<%=contextPath%>/app/base/user/user.js"></script>
<script type="text/javascript" src="<%=contextPath%>/app/base/usergroup/usergroup.js"></script>
<script type="text/javascript" src="<%=contextPath%>/app/base/user/user-modal.js"></script>
<script type="text/javascript" src="<%=contextPath%>/app/home/news/onlinetalk/onlinetalk.js"></script>
<script type="text/javascript" src="<%=contextPath%>/app/home/news/onlinetalk/list/onlinetalk_list.js"></script>
<script type="text/javascript" src="<%=contextPath%>/app/home/news/onlinetalk/list/onlinetalk_list2.js"></script>

</html>