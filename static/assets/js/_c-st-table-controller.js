angular.module("app")
    .directive("cStTableController", function () {
        return {
            restrict: "A",
            require: '^stTable',
            link: function ($scope, $element, attrs, ctrl) {
                var prefix= attrs.cStTableController;

                $scope.$on(prefix + ":refresh", function () {
                    ctrl.pipe();
                });

                $scope.$on(prefix + ":set-page", function (e, page) {
                    ctrl.tableState().pagination.start = (page - 1) * ctrl.tableState().pagination.itemsByPage;
                });
            }
        }
    });