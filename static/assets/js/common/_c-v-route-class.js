angular.module("common")
    .directive("cVRouteClass", function (_v) {
        return {
            restrict: "A",
            scope: {
                cVRouteClass: "="
            },
            link: function ($scope, $element, attr) {
                var data = $scope.cVRouteClass;
                var route = data.route;
                var clazz = data.class;

                $scope.$watch(_v.getRoute, function (curr) {
                    if (curr) {
                        $element.toggleClass(clazz, curr.path == route);
                    }
                });
            }
        }
    });