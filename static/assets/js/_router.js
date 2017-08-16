angular.module("app")
    .factory("_router", function (_v) {
        return {
            open: function (url) {
                _v.change({href: url})
            },
            api: {
                users: {
                    list: function () {
                        return "/api/users/";
                    },
                    filterItems: function () {
                        return "/api/users/filter-items"
                    },
                    loggedIn: function () {
                        return "/api/auth/logged-in";
                    },
                    item: {
                        get: function (id) {
                            return "/api/users/" + id;
                        },
                        save: function (id) {
                            if (id) {
                                return "/api/users/" + id;
                            } else {
                                return "/api/users/new";
                            }
                        },
                        delete: function (id) {
                            return "/api/users/" + id;
                        },
                        enable: function (id) {
                            return "/api/users/" + id + "/enable";
                        },
                        disable: function (id) {
                            return "/api/users/" + id + "/disable";
                        }
                    }
                }
            }
        }
    })
    .constant("_routes", {
        //copied from vRouter.js
        auth: {
            login: "/auth/login.html"
        },
        app: {
            root: "/app/",
            settings: {
                users: {
                    list: "/app/settings/users/",
                    item: {
                        create: "/app/settings/users/new",
                        edit: "/app/settings/users/:id"
                    }
                }
            }
        }
    })
    .factory("_vParamTransformers", function (_filterItem) {
        function momentMonthToInt(month) {
            if (!month) {
                return -1;
            } else {
                return month.year() * 100 + (month.month() + 1);
            }
        }

        return {
            "app.settings.users.list": function ($v) {
                $v.f = $v.f || {};
                $v.f.page = _.toIntOr($v.f.page, 1);
                $v.f.order = $v.f.order || "-mail";
                $v.f.items = ($v.f.items) ? _.toMyArray($v.f.items) : [];

                return $v;
            }
        }
    })
    .config(function (_vProvider, _routes, _vParamTransformersProvider) {
        _vProvider.setRoutes(_routes);
        _vProvider.setParamTransformers(_vParamTransformersProvider.$get());
    })
    .run(function ($rootScope, _router) {
        $rootScope._router = _router;
    });