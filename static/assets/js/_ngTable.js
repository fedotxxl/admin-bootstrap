angular.module("app")
    .factory("_ngTable", function () {
        return {
            sortingTo$v: function (sorting) {
                var property = _.keys(sorting || {})[0];

                if (property) {
                    return ((sorting[property] == "desc") ? "-" : "") + property;
                } else {
                    return "";
                }
            },
            sortingFrom$v: function (sorting) {
                var answer = {};

                if (sorting) {
                    if (sorting[0] == "-") {
                        answer[sorting.substring(1)] = "desc";
                    } else {
                        answer[sorting] = "asc";
                    }
                }

                return answer;
            }
        }
    });