angular.module("app")
    .controller("UsersPage", function ($q, $scope, NgTableParams, _users, _i18n, _notify, _convert, _filter) {
        var $c = $scope.$c = this;

        $c._i18n = _i18n;

        (function configureFilters() {
            $c.filter = _filter.init({
                getSelectedContainer: function() {
                    return $scope.$v.f;
                }
            });

            _users.api.getFilterItems().then(function (items) {
                $c.filter.setItems(items);
            })
        }());

        (function resetPageOnFilterChange() {
            $scope.$watch("$v.f.items", function (curr, prev) {
                if (!angular.equals(curr, prev)) $scope.$v.f.page = 1;
            });
        }());

        (function configureBreadcrumb() {
            $c.breadcrumb = {
                before: [
                    {
                        path: "app.root"
                    }
                ]
            };
        }());

        (function configureButtons() {
            $c.enable = function (user) {
                _users.api.enable(user.id)
                    .then(function () {
                        user.disabled = false;
                    })
                    .catch(function (response) {
                        if (response.status == 422) {
                            _notify.warn(_i18n("users.notification.invalid"))
                        }
                    })
            };

            $c.disable = function (user) {
                _users.api.disable(user.id).then(function () {
                    user.disabled = true;
                })
            };
        }());

        (function configureTable() {
            $c.table = {
                callServer: function (tableState) {
                    if (tableState.sort.predicate) {
                        $scope.$v.f.order = _convert.stSortToString(tableState.sort);
                    }

                    if (tableState.pagination) {
                        $scope.$v.f.page = (tableState.pagination.start / tableState.pagination.itemsByPage) + 1;
                    }
                },
                getName: function (user) {
                    var names = [];

                    if (user.first_name) names.push(user.first_name);
                    if (user.last_name) names.push(user.last_name);

                    return names.join(" ")
                }
            };
        }());

        (function watchAndRefreshUsers() {
            $scope.$watch("$v.f", _refreshUsers, true);
        }());

        function _refreshUsers() {
            _users.api.list(getListFilterWithPagination()).then(function (answer) {
                $c.data = answer;
            });
        }

        function getListFilterBody() {
            return {
                items: $scope.$v.f.items
            }
        }

        function getListFilterWithPagination() {
            var filter = getListFilterBody();
            var f = $scope.$v.f;

            return {
                page: f.page,
                order: f.order,
                filter: filter
            }
        }
    });