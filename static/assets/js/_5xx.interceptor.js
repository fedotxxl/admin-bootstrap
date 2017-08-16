angular.module("app")
    .factory("_http5xxResponseInterceptor", function ($q, $injector, _log) {
        return {
            responseError: function (rejection) {
                if (rejection.status >= 500 && rejection.status < 600) {
                    $injector.get("_notify").danger($injector.get("_i18n")("notification.5xx.server"));
                    _log.warn(rejection);
                }

                // Return the promise rejection.
                return $q.reject(rejection);
            }
        }
    })
    .config(function (_notifyProvider, _i18nProvider) {
        window.onerror = function() {
            _notifyProvider.$get().danger(_i18nProvider.$get()("notification.5xx.client"));

            return false;
        };
    })
    .config(function ($httpProvider) {
        $httpProvider.interceptors.push("_http5xxResponseInterceptor");
    });