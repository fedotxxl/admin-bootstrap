angular.module("common")
    .directive("cTranslate", function (_i18n) {
        return {
            restrict: "A",
            link: function ($scope, $element, attr) {
                $element.html(_i18n(attr.cTranslate))
            }
        }
    });