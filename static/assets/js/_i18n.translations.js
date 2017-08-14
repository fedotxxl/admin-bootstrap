angular.module("app")
    .config(function ($translateProvider) {

        var en = {

        };

        $translateProvider
            .useSanitizeValueStrategy('sanitize')
            .translations("en", en)
            .fallbackLanguage("en");

        $translateProvider.preferredLanguage("en");
    });