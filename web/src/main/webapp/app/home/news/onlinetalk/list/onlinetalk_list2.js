/**
 * Created by lzm on 2014-03-06 10:16:03.
 */
(function (window, angular, $) {
    var app = angular.module('eccrm.home.onlinetalk.list', ['eccrm.home.onlinetalk', 'eccrm.angular', 'eccrm.angularstrap', 'eccrm.base.onlinetalk.modal']);
    app.controller('OnlinetalkListController', function ($http, CommonUtils, $scope, $window, OnlinetalkService, OnlineTalkModal) {
        var pageType = $('#pageType').val();
        var id = $('#id').val();
        var defaults;
        $scope.back = function () {
            $window.history.back();
//            window.location.href = "home/news/onlinetalk?active=1";
        }
        function loadAttachment() {
            var url = CommonUtils.contextPathURL('/attachment/query?btype=step3&bid=' + $scope.beans.id + "&_tmp=" + new Date().getTime);
            $http.get(url)
                .success(function (data) {
                    $scope.attachments = data.data || [];
                });
        }

        if (pageType == 'detail') {//查看页面
            OnlinetalkService.get({id: id}, function (data) {
                $scope.beans = data;
                loadAttachment()
                if ($scope.beans.content != null) {
                    UE.getEditor('container').ready(function () {
                        UE.getEditor('container').setContent($scope.beans.content);
                    });
                }
                $('input,textarea,select').attr('disabled', 'disabled');
            });
        }
        var ueditor_config = {
            initialFrameHeight: 240,
            autoHeightEnabled: false,
            readonly: pageType == 'detail' ? true : false,
            toolbars: [
                ["bold", "italic", "underline", "fontsize", "|", "insertorderedlist", "insertunorderedlist", "|", "insertimage", "|", 'removeformat', 'forecolor', 'backcolor']
            ],
            //关闭字数统计
            wordCount: false,
            //关闭elementPath
            elementPathEnabled: false
        };
        UE.getEditor('container', ueditor_config);
        $('#container .edui-editor.edui-default').css('width', '100%');
//        $scope.uploadOptions = {
//            disabled: true,
//            businessId: id
//        }
        //回复alert
        $scope.reply = function (id) {
            OnlineTalkModal.replyModal({scope: $scope, id: id}, function (data) {
            })
        };
    });
})(window, angular, jQuery);