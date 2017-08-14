angular.module("common")
    .directive("cOnClickAndDrag", function ($rootScope) {
        return {
            restrict: "A",
            scope: {
                cOnClickAndDrag: "="
            },
            link: function ($scope, $element) {
                var $window = $(window);
                var isMouseDown = false;

                $element.on("mousedown", $scope.cOnClickAndDrag.select, onMouseDown);

                $element.on("mouseenter", $scope.cOnClickAndDrag.select, onMouseEnter);

                $window.on("mouseup", onMouseUp);

                $scope.$on("$destroy", function () {
                    $window.off("mouseup", onMouseUp);
                    $element.off("mousedown", onMouseDown);
                    $element.off("mouseenter", onMouseEnter);
                });

                function onMouseDown(e) {
                    e.preventDefault();

                    isMouseDown = true;
                }

                function onMouseEnter(e) {
                    if (isMouseDown) {
                        trigger(e);
                    }
                }

                function onMouseUp() {
                    isMouseDown = false;
                }

                function trigger(e) {
                    $rootScope.$safeApply(function () {
                        if ($scope.cOnClickAndDrag.on) {
                            $scope.cOnClickAndDrag.on(e);
                        }
                    })
                }
            }
        }
    });