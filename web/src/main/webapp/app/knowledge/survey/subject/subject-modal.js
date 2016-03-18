(function (angular, window) {
    var app = angular.module('knowledge.survey.subject.modal', [
        'eccrm.angular',
        'eccrm.angularstrap',
        'eccrm.angular.ztree',
        'knowledge.survey.subject',
        'knowledge.survey.category'
    ]);


    app.factory('SubjectModal', function ($modal, CommonUtils, Subject, SubjectService, AlertFactory, SubjectCategoryTree, ModalFactory) {
        // UEditor配置
        var editorOptions = {
            items: [
                'source', '|', 'undo', 'redo', '|', 'preview', 'code', 'cut', 'copy', 'paste',
                'plainpaste', 'wordpaste', '|', 'justifyleft', 'justifycenter', 'justifyright',
                'justifyfull', 'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent', 'subscript',
                'superscript', 'clearhtml', 'quickformat', 'selectall', '|', 'fullscreen', '/',
                'formatblock', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold',
                'italic', 'underline', 'strikethrough', 'lineheight', '|', 'image', 'multiimage',
                'insertfile', 'table', 'hr', 'emoticons'
            ]
        };

        // 初始化UEditor
        var titleEditor, descriptionEditor;

        function initUEditor(options) {
            var o = angular.extend({}, editorOptions, options);
            titleEditor = KindEditor.create('.modal #container', o);
            descriptionEditor = KindEditor.create('.modal #container2', o);
        }


        var common = function (pageType, options) {
            var defaults = {
                id: '',     // 在更新、查看页面必须
                callback: null//点击确定后要执行的函数
            };
            options = angular.extend({}, defaults, options);
            var modal = $modal({
                backdrop: 'static',
                template: CommonUtils.contextPathURL('/app/knowledge/survey/subject/template/subject-modal.tpl.html')
            });
            // 初始化ueditor
            var $scope = modal.$scope;
            $scope.beans = {};
            $scope.pageType = pageType = pageType || 'view';

            $scope.ztreeOptions = SubjectCategoryTree.pick({
                position: 'absolute',
                onClick: function (node) {
                    $scope.beans.categoryId = node.id;
                    $scope.beans.categoryName = node.name;
                }
            });

            $scope.clearCategory = function () {
                $scope.beans.categoryId = null;
                $scope.beans.categoryName = null;
            };

            // 状态
            Subject.status(function (data) {
                $scope.status = data;
            });

            // 题型
            Subject.type(function (data) {
                $scope.types = data;
            });


            // 移除题目选项
            $scope.removeItems = function (index) {
                $scope.beans.items.splice(index, 1);
            };


            $scope.validate = function () {
                var subjectType = $scope.beans.subjectType;

                // 检查标题
                var title = $scope.beans.title;
                if (!title) {
                    alert('没有指定标题!');
                    return false;
                }

                // 单选和多选的时候必须指定选项
                if (subjectType === '1' || subjectType === '2') {
                    var items = $scope.beans.items;
                    if (items == null || items.length < 1) {
                        alert('没有指定选项!');
                        return false;
                    }
                    for (var i = 0; i < items.length; i++) {
                        var item = items[i];
                        if (!item.name) {
                            alert('没有指定选项!');
                            return false;
                        }
                    }
                }

                return true;
            };

            ModalFactory.afterShown(modal, function () {
                initUEditor();
                if ('modify|view'.indexOf(pageType) > -1) {
                    load.call($scope, options.id)
                }
                if (pageType == 'view') {
                    titleEditor.readonly(true);
                    descriptionEditor.readonly(true);
                    $('.modal .modal-body').find("input,select,textarea,.btn").attr('disabled', 'disabled');
                }

            });

            return $scope;
        };

        function getEditorText() {
            if (titleEditor.text()) {
                this.title = titleEditor.html();
            }
            if (descriptionEditor.text()) {
                this.description = descriptionEditor.html();
            }
        }

        function setEditorText() {
            titleEditor.html(this.title || '');
            descriptionEditor.html(this.description || '');
        }

        function load(id) {
            if (!id) {
                alert('没有获得ID!');
                return false;
            }
            var context = this;
            var promise = SubjectService.get({id: id});
            context.beans = {};
            CommonUtils.loading(promise, '加载中...', function (data) {
                angular.extend(context.beans, data.data || {});
                setEditorText.call(context.beans);
            });

            var itemPromise = SubjectService.queryItems({id: id});
            CommonUtils.loading(itemPromise, '加载中...', function (data) {
                context.beans.items = data.data || [];
            });
        }

        return {
            add: function (options, callback) {
                var $scope = common('add', options, callback);
                var defaults = {
                    status: 'ACTIVE',
                    subjectType: '1',   // 单选
                    items: [{}, {}, {}, {}]
                };

                // 初始化
                angular.extend($scope.beans, defaults);
                $scope.save = function (createNew) {
                    getEditorText.call($scope.beans);
                    if (!$scope.validate()) {
                        return;
                    }
                    var promise = SubjectService.save($scope.beans);
                    CommonUtils.loading(promise, '保存中...', function (data) {
                        if (data && data.success) {
                            if (createNew == true) {
                                $scope.beans.answer = null;
                                titleEditor.html('');
                                $scope.beans.title = '';
                                $scope.beans.items = [{}, {}, {}, {}];
                                return;
                            }
                            $scope.$hide();
                            callback = options && options.callback || callback;
                            if (angular.isFunction(callback)) {
                                callback();
                            }
                        }
                    });
                }
            },
            modify: function (options, callback) {
                var $scope = common('modify', options);
                $scope.update = function () {
                    getEditorText.call($scope.beans);
                    if (!$scope.validate()) {
                        return;
                    }
                    var promise = SubjectService.update($scope.beans, function (data) {
                        if (data && data.success) {
                            $scope.beans = null;
                            $scope.$hide();
                            callback = options.callback || callback;
                            if (angular.isFunction(callback)) {
                                callback();
                            }
                        }
                    });
                    CommonUtils.loading(promise, '更新中...');
                }
            },
            /**
             * 必须配置参数：id
             * @param options
             */
            view: function (options) {
                var $scope = common('view', options);
            },
            /**
             *
             * @param options 配置项
             * @param callback 回调函数
             */
            pick: function (options, callback) {
                var modal = $modal({
                    backdrop: 'static',
                    template: CommonUtils.contextPathURL('/app/knowledge/survey/subject/template/subject-modal-pick.tpl.html')
                });
                var $scope = modal.$scope;

                // 查询条件
                $scope.condition = {
                    title: null,        // 题目
                    status: 'ACTIVE',    // 状态为有效的
                    surveyId: options.surveyId,        // 排除指定试卷下的题目
                    subjectType: null,  // 题型
                    categoryId: null    // 题库
                };

                // 查询方法
                $scope.query = function () {
                    $scope.pager.query();
                };
                // 确定选择
                $scope.confirm = function () {
                    var items = $scope.items;
                    modal.hide();
                    callback(items);
                };

                $scope.ztreeOptions = SubjectCategoryTree.pick(function (node) {
                    $scope.condition.categoryId = node.id;
                    $scope.categoryName = node.name;
                });

                $scope.clearCategory = function () {
                    $scope.condition.categoryId = null;
                    $scope.categoryName = null;
                };

                // 题型
                Subject.type(function (data) {
                    $scope.types = data;
                });

                $scope.pager = {
                    limit: 10,
                    fetch: function () {
                        var params = {start: this.start, limit: this.limit};
                        return CommonUtils.promise(function (defer) {
                            var promise = SubjectService.pageQuery(angular.extend(params, $scope.condition));
                            CommonUtils.loading(promise, '查询中...', function (data) {
                                data = data.data || {total: 0};
                                defer.resolve(data);
                                $scope.beans = data;
                            });
                            params = null;
                        });
                    }
                };
            },
            setNext: function () {

            }
        };
    });


})(angular, window);