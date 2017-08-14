// http://stackoverflow.com/a/33176717/716027
angular.module("common")
    .directive("cInputLimitTo", function () {
        return {
            restrict: "A",
            require: 'ngModel',
            link: function (scope, element, attrs, ngModel) {
                attrs.$set("ngTrim", "false");
                var limitLength = parseInt(attrs.cInputLimitTo, 10);
                scope.$watch(attrs.ngModel, function(newValue) {
                    if(ngModel.$viewValue.length>limitLength){
                        ngModel.$setViewValue( ngModel.$viewValue.substring(0, limitLength ) );
                        ngModel.$render();
                    }
                });
            }
        };
    });