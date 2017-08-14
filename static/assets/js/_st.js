angular.module("app")
    .directive("cStInit", function ($rootScope, _convert) {
        return {
            scope: {
                cStInit: "="
            },
            require: '^stTable',
            link: function ($scope, $element, attr, ctrl) {
                ctrl.tableState().pagination.itemsByPage = 30;
                ctrl.tableState().pagination.numberOfPages = 0;

                $scope.$watch("cStInit", function (curr) {
                    if (curr) {
                        if (_.isNumeric(curr.itemsByPage)) {
                            ctrl.tableState().pagination.itemsByPage = curr.itemsByPage;
                        }

                        if (_.isNumeric(curr.total) && curr.total >= 0) {
                            ctrl.tableState().pagination.numberOfPages = Math.ceil(curr.total / ctrl.tableState().pagination.itemsByPage);
                        }

                        if (curr.order) {
                            ctrl.tableState().sort = curr.order;
                        }
                    }
                });
            }
        }
    })
    .directive("cStInitV", function ($rootScope, _convert) {
        return {
            require: '^stTable',
            link: function ($scope, $element, attr, ctrl) {
                $rootScope.$watch("$v.f.order", function (order) {
                    if (order) {
                        ctrl.tableState().sort = _convert.stSortFromString(order);
                    }
                });

                $rootScope.$watch("$v.f.page", function (page) {
                    if (page) {
                        ctrl.tableState().pagination.start = (page - 1) * ctrl.tableState().pagination.itemsByPage;
                    }
                });
            }
        }
    });