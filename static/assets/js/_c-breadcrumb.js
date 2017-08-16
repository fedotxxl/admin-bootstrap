angular.module("app")
    .component("cBreadcrumb", {
        templateUrl: "/components/_c-breadcrumb.html",
        bindings: {
            active: "=",
            before: "="
        },
        controller: function (_i18n) {
            var $ctrl = this;
            
            $ctrl.getBeforeVHrefParams = _.memoize(function (item) {
                if (item.href) {
                    return {href: item.href}
                } else if (item.path) {
                    return {route: item.path}
                }
            }, function (item) {
                return JSON.stringify(item);
            });
            
            $ctrl.labelActive = _i18n("breadcrumb." + $ctrl.active);

            $ctrl.getBeforeLabel = function (item) {
                if (item.path) {
                    return _i18n("breadcrumb." + item.path);
                }
            };
        }
    });