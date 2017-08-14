angular.module('app')
    .factory('_http403ResponseInterceptor', function($q) {
        return {
            responseError: function(rejection) {
                if (rejection.status == 403) {
                    window.location.href = "/";
                }

                // Return the promise rejection.
                return $q.reject(rejection);
            }
        }
    })
    .config(function($httpProvider) {
        $httpProvider.interceptors.push('_http403ResponseInterceptor');
    });