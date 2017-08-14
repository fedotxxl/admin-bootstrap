angular.module("app")
    .directive('cFormRemoteError', function ($sce, _i18n) {
        return {
            restrict: "A",
            replace: true,
            scope: true,
            template:
            '<div class="m-t-xs" ng-show="isDisplayed()">' +
                '<span class="text-danger" ng-bind-html="message"></span>' +
            '</div>',
            link: function ($scope, $element, attrs) {
                $scope.isDisplayed = function() {
                    return !!$scope.message;
                };

                $scope.$watch('serverValidationError.' + attrs.cFormRemoteError, function (data) {
                    if (data) {
                        $scope.message = data;
                    }
                });
            }
        }
    });
