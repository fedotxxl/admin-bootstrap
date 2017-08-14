angular.module("app")
    .factory("_notify", function ($timeout, notify, _i18n) {
        function _notify(message, classes, closeInMillis) {
            var templateUrl = "/components/_notify.html";
            var msg;

            if (_.isObject(message)) {
                msg = _i18n(message.key, message.params);
            } else {
                msg = _i18n(message);
            }

            var n = notify({
                message: msg,
                classes: classes,
                templateUrl: templateUrl
            });

            if (closeInMillis > 0) {
                $timeout(function () {
                    n.close();
                }, closeInMillis);
            }

            return n;
        }

        return {
            info: function (message, closeInMillis) {
                return _notify(message, "alert-info", closeInMillis || 5000)
            },
            success: function (message, closeInMillis) {
                return _notify(message, "alert-success", closeInMillis || 5000)
            },
            warn: function (message, closeInMillis) {
                return _notify(message, "alert-warning", closeInMillis || 7000)
            },
            danger: function (message, closeInMillis) {
                return _notify(message, "alert-danger", closeInMillis || -1)
            },
            closeAll: _notify.closeAll
        }
    });