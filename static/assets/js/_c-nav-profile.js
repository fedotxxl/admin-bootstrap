angular.module("app")
    .component("cNavProfile", {
        templateUrl: "/components/_c-nav-profile.html",
        controller: function (_user) {
            var user = null;
            var $c = this;

            $c.getProfileImg = function () {
                return "/img/profile_anonymous.png";
            };

            $c.getUsername = function () {
                if (user) {
                    if (user.first_name && user.last_name) {
                        return user.first_name + " " + user.last_name;
                    } else {
                        return user.mail;
                    }
                } else {
                    return "Anonymous"
                }
            };

            _user.getPromise().then(function (u) {
                user = u;
            })
        }
    });