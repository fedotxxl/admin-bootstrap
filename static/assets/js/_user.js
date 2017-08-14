angular.module("app")
    .factory("_userApi", function ($http, _router) {
        return {
            getData: function () {
                return $http.get(_router.api.users.loggedIn())
                    .then(function (response) {
                        return response.data;
                    })
                    .catch(function (e) {
                        return null;
                    });
            }
        }
    })
    .factory("_user", function ($rootScope, _userApi, _http) {
        var _user = null;
        var _userPromise = null;

        function reloadData() {
            _userPromise = _userApi.getData().then(function (user) {
                _user = user;
                $rootScope.user = _user;
                $rootScope.$emit("user:reloaded", _user);

                return _user;
            });

            return _userPromise;
        }

        return {
            get: function () {
                return _user
            },
            hasAuthority: function (authority) {
                return _.contains((_user || {}).permissions, authority);
            },
            hasAuthorityPromise: function (authority) {
                return _userPromise.then(function (user) {
                    return _.contains((_user || {}).permissions, authority);
                });
            },
            getPromise: function () {
                return _userPromise;
            },
            reloadData: reloadData
        }
    })
    .run(function (_user) {
        _user.reloadData();
    });