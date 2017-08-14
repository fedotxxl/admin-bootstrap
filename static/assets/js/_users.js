angular.module("app")
    .factory("_usersApi", function ($http, _router) {
        return {
            list: function (request) {
                return $http.post(_router.api.users.list(), request)
            },
            getFilterItems: function (request) {
                return $http.get(_router.api.users.filterItems(), request)
            },
            get: function (id) {
                return $http.get(_router.api.users.item.get(id))
            },
            save: function (id) {
                return $http.post(_router.api.users.item.save(id))
            },
            enable: function (id) {
                return $http.post(_router.api.users.item.enable(id))
            },
            disable: function (id) {
                return $http.post(_router.api.users.item.disable(id))
            },
            delete: function (id) {
                return $http.delete(_router.api.users.item.delete(id))
            }
        }
    })
    .factory("_users", function (_usersApi, _http, _log, _filterItem) {
        return {
            api: {
                list: _http.getData(_usersApi.list),
                get: _http.getData(_usersApi.get),
                save: _http.getData(_usersApi.save),
                items: _http.getData(_usersApi.items),
                enable: _http.getData(_usersApi.enable),
                disable: _http.getData(_usersApi.disable),
                delete: _http.getData(_usersApi.delete),
                getFilterItems: _filterItem.getDataAndPostProcess(_usersApi.getFilterItems),
            }
        }
    });