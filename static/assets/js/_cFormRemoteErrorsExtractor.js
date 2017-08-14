angular.module("app")
    .factory("_cFormRemoteErrorsExtractor", function (_i18n) {
        return {
            get: function (data, attrs) {
                if (data.errors && data.errors.field) {
                    return _.map(data.errors.field, function (err) {
                        return {
                            field: err.field,
                            message: _extractMessage(err, attrs)
                        }
                    });
                } else {
                    return [];
                }

                function _extractMessage(err, attrs) {
                    if (err.message) {
                        return err.message;
                    } else if (attrs.messagePrefix && (err.field || err.code)) {
                        var errors = [];

                        if (err.field) errors.push(err.field);
                        if (err.code) errors.push(err.code);

                        return _i18n(attrs.messagePrefix + "." + errors.join("."))
                    } else if (err.field && err.code) {
                        return _i18n(err.code);
                    }
                }
            }
        }
    });