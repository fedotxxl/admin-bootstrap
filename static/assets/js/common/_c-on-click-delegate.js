angular.module("common")
    .directive("cOnClickDelegate", function ($rootScope) {
        return {
            restrict: "A",
            scope: {
                cOnClickDelegate: "="
            },
            link: function ($scope, $element) {
                $element.on("click", $scope.cOnClickDelegate.select, function (e) {
                    $rootScope.$safeApply(function () {
                        $scope.cOnClickDelegate.on(e);
                    })
                });
            }
        }
    });