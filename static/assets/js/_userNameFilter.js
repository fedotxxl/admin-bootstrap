angular.module("app")
    .filter("_userName", function () {
        return function (user) {
            if (!user) return "";

            var names = [];

            if (user.first_name) names.push(user.first_name);
            if (user.last_name) names.push(user.last_name);

            if (!names.length) {
                return user.mail;
            } else {
                return names.join(" ")
            }
        }
    });