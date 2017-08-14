angular.module("app")
    .component('cFormRemoteErrorSimple', {
        bindings: {
            message: "="
        },
        template:
        '<div class="m-t-xs" ng-show="$ctrl.isDisplayed()">' +
        '<span class="text-danger" ng-bind-html="$ctrl.message"></span>' +
        '</div>',
        controller: function () {
            var $c = this;

            $c.isDisplayed = function() {
                return !!$c.message;
            };
        }
    });
