angular.module("common")
    .directive("cNoUiSlider", function () {
        return {
            restrict: "E",
            scope: {
                options: "=",
                on: "="
            },
            link: function ($scope, $element) {
                var currentValue;

                $scope.$watch("options", function () {
                    if ($element[0].noUiSlider) $element[0].noUiSlider.destroy();

                    noUiSlider.create($element[0], $scope.options);

                    $element[0].noUiSlider.on("update", function (values, handle, unencoded) {
                        currentValue = parseInt(unencoded[0]);
                    });

                    _.each($scope.on, function (value, key) {
                        $element[0].noUiSlider.on(key, value);
                    });
                });

                $scope.$on("$destroy", function () {
                    $element[0].noUiSlider.off();
                });

                $scope.$watch("options.value", function (curr) {
                    if (!_.isUndefined(curr) && curr != currentValue) {
                        $element[0].noUiSlider.set(curr);
                    }
                })
            }
        }
    })
    .factory("_noUiSlider", function () {
        return {
            percentageToValues: function (percentages, totalValues) {
                var offset = 0;
                var fixRatio = totalValues / 100;

                var answer = _.map(percentages, function (percentage) {
                    var currentValue = percentage * fixRatio;
                    var answer = currentValue + offset;

                    offset += currentValue;

                    return answer;
                });

                answer.pop();

                return answer;
            },
            valuesToPercentages: function (values, totalValues) {
                var fixRatio = 100 / totalValues;
                var prev = 0;
                var totalPercentages = 0;

                var answer = _.map(values, function (value) {
                    var currentPercentage = Math.round(value * fixRatio);
                    var answer = currentPercentage - prev;

                    prev = currentPercentage;
                    totalPercentages += answer;

                    return answer;
                });

                answer.push(100 - totalPercentages);

                return answer;
            }
        }
    });