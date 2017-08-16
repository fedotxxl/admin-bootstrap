angular.module("app")
    .controller("UserPage", function ($scope, _users, _v, _notify, _filterItem, _modal, _router, _i18n) {
        var $c = $scope.$c = this;
        var mail;

        (function configureForm() {
            $c.form = {
                getTargetUri: function () {
                    return _router.api.users.item.save($c.user.id);
                },
                onSuccess: function (response) {
                    $c.form.errors = null;
                    _notify.info(_i18n("user.notification.saved", {mail: $c.user.mail}));
                    _v.change({route: {path: "app.settings.users.item.edit", params: {id: response.id}}});
                },
                onFailure: function (response, status) {
                    if (status == 422 && response.errors) {
                        $c.form.errors = response.errors;
                    }
                }
            }
        }());

        (function loadUser() {
            var id = _v.getParam("id");

            if (id) {
                _users.api.get(id).then(function (user) {
                    $c.user = user;
                    mail = user.mail;
                })
            } else {
                $c.user = {};
            }
        }());

        (function configureBreadcrumb() {
            $c.breadcrumb = {
                before: [
                    {
                        path: "app.root"
                    },
                    {
                        path: "app.settings.users.list"
                    }
                ]
            };
        }());

        (function configureButtons() {
            $c.save = function () {
                _users.api.save($c.user).then(function () {
                    _notify.info("user.notification.saved", {mail: mail})
                })
            };

            $c.delete = function () {
                _modal
                    .yesNo(["user.modal.delete", {mail: mail}])
                    .then(function () {
                        return _users.api.delete($c.user.id);
                    })
                    .then(function () {
                        _notify.info(_i18n("user.notification.deleted", {mail: mail}))
                        _v.change({route: "app.settings.users.list"});
                    });
            };
        }());

    });