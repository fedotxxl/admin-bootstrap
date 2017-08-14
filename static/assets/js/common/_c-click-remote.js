angular.module("common")
    .directive("cClickRemote", function () {
       return {
           restrict: "A",
           scope: {
               cClickRemote: "&"
           },
           link: function ($scope, $element) {
               $element.click(function (e) {
                   var answer = $scope.cClickRemote({"$event": e});

                   if (answer && answer.finally) {
                        $element.attr("disabled", "1");

                        answer.finally(function () {
                            $element.removeAttr("disabled");
                        });
                   }
               })
           }
       }
    });