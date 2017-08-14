angular.module("app")
    .filter("_momentToDate", function () {
        return function (date) {
            if (date) {
                var answer = date._momentToDate;

                if (!answer) {
                    answer = date.format("DD.MM.YYYY");
                    date._momentToDate = answer;
                }

                return answer;
            } else {
                return "";
            }
        }
    })
    .filter("_momentToDateTime", function () {
        return function (date) {
            if (date) {
                var answer = date._momentToDateTime;

                if (!answer) {
                    answer = date.format("DD.MM.YYYY HH:mm");
                    date._momentToDateTime = answer;
                }

                return answer;
            } else {
                return "";
            }
        }
    })
    .filter("_momentToMonth", function () {
        return function (date) {
            if (date) {
                var answer = date._momentToMonth;

                if (!answer) {
                    answer = date.format("MMMM YYYY");
                    date._momentToMonth = answer;
                }

                return answer;
            } else {
                return "";
            }
        }
    })
    .factory("_moment", function (_convert) {
        function getMonthsBetweenDates(from, to) {
            if (from) from = from.clone().startOf("month");
            if (to) to = to.clone().startOf("month");

            if (from && to && to >= from) {
                var months = [];
                var current = from.clone();

                while (to >= current) {
                    months.push(_convert.momentMonthToInt(current));
                    current.add(1,'month');
                }

                return months;
            } else {
                return undefined;
            }
        }

        function toUtcDate(date) {
            return moment.utc([date.year(), date.month(), date.date(), 0, 0, 0])
        }

        return {
            getMonthsBetweenDates: getMonthsBetweenDates,
            toUtcDate: toUtcDate
        }
    });