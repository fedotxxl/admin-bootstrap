angular.module("app")
    .factory("_modal", function ($q, $rootScope, $uibModal, _i18n) {
        function yesNo(params) {
            return $q(function (resolve, reject) {
                var ctrl = function($scope, $uibModalInstance) {
                    $scope.ok = function () {
                        $uibModalInstance.close();

                        resolve(arguments);
                    };

                    $scope.cancel = function () {
                        $uibModalInstance.close();

                        reject(arguments)
                    };
                };

                var $scope = $rootScope.$new();
                var templateUrl;
                var size;

                if (_.isString(params) || _.isArray(params)) {
                    $scope.message = _i18n(params);
                    templateUrl = "/components/_c-modal-yes-no.html";
                } else {
                    templateUrl = params.templateUrl;
                    $scope = angular.extend($scope, params.scope);
                    size = params.size;
                }

                $uibModal.open({
                    templateUrl: templateUrl,
                    size: size || "sm",
                    scope: $scope,
                    controller: ctrl
                });
            });
        }

        return {
            yesNo: yesNo
        }
    });