<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html >
<html >
<head >
    <title>即时沟通 </title>
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
    <style>
        .view-animate-container {
            position: relative;
        / / height : auto !important;
            position: relative;
            background: white;
            border: 1px solid #ddd;
            height: 100%;
            overflow-y: hidden;
        }

        .nav.nav-tabs li a {
            cursor: pointer;
        }

        .view-animate {
            padding: 10px;
        }

        /*.view-animate.ng-enter, .view-animate.ng-leave {
            -webkit-transition: all cubic-bezier(0.250, 0.460, 0.450, 0.940) 1s;
            transition: all cubic-bezier(0.250, 0.460, 0.450, 0.940) 1s;
            display: block;
            width: 100%;
            border-left: 1px solid black;
            border-color: #DDDDDD;
            position: absolute;
            top: 0;
            left: 0;
            right: 0;
            bottom: 0;
            padding: 10px;
        }*/

        .view-animate.ng-enter {
            left: 100%;
        }

        .view-animate.ng-enter.ng-enter-active {
            left: 0;
        }

        .view-animate.ng-leave.ng-leave-active {
            left: -100%;
        }
    </style>
</head>
<body>
<div class="main " ng-app="eccrm.home.onlinetalk.edit"
     ng-controller="OnlineTalkEditController">
    <div style="display: none;">
        <input type="hidden" id="id" value="${id}"/>
        <input type="hidden" id="active" value="${param.active}"/>
        <input type="hidden" id="contextPath" value="<%=contextPath%>/" />
    </div>
    <div class="row-10">
        <div eccrm-route="routeOptions" style="height: 100%"></div>
    </div>
</div>


</body>
<script type="text/javascript" src="<%=contextPath%>/app/base/user/user.js"></script>
<script type="text/javascript" src="<%=contextPath%>/app/base/usergroup/usergroup.js"></script>
<script type="text/javascript" src="<%=contextPath%>/app/base/user/user-modal.js"></script>
<script type="text/javascript" src="<%=contextPath%>/app/home/news/onlinetalk/onlinetalk.js"></script>
<script type="text/javascript" src="<%=contextPath%>/app/home/news/onlinetalk/list/onlinetalk_list.js"></script>
<script type="text/javascript" src="<%=contextPath%>/app/home/news/onlinetalk/list/onlinetalk_list2.js"></script>

</html>