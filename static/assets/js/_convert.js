angular.module("app")
    .constant("_convert", (function () {
        var answer = {
            momentMonthToInt: function (month) {
                if (!month) {
                    return -1;
                } else {
                    return month.year() * 100 + (month.month() + 1);
                }
            },
            momentMonthFromInt: function (month) {
                if (!month) {
                    return null;
                } else {
                    return moment({year: month / 100, month: (month % 100) - 1, day: 01});
                }
            },
            dateToInt: function (year, month, day) {
                return year * 10000 + month * 100 + day;
            },
            momentToInt: function (moment) {
                if (!moment) {
                    return -1;
                } else {
                    return answer.dateToInt(moment.year(), moment.month() + 1, moment.date());
                }
            },
            momentFromInt: function (date) {
                if (!date) {
                    return null;
                } else {
                    return moment({year: date / 10000, month: (date % 10000) / 100 - 1, day: date % 100});
                }
            },
            secondsWithDayCorrection: function (secondsOfDay) {
                var day = 24 * 60 * 60;
                var isNextDay = false;
                var issTime = secondsOfDay;

                if (issTime >= day) {
                    issTime -= day;
                    isNextDay = true;
                }

                return {
                    seconds: issTime,
                    isNextDay: isNextDay
                }
            },
            hourFromSecondsOfADay: function (seconds) {
                return Math.floor(seconds / (60 * 60))
            },
            monthDayToInt: function (month, day) {
                return month * 100 + day;
            },
            monthDayFromInt: function (date) {
                return {
                    month: parseInt(date / 100),
                    day: date % 100
                }
            },
            toArray: function (item) {
                if (_.isUndefined(item) || _.isNull(item)) {
                    return [];
                } else if (_.isArray(item)) {
                    return item;
                } else {
                    return [item];
                }
            },
            stSortToString: function (sort) {
                return ((sort.reverse) ? "-" : "") + sort.predicate;
            },
            stSortFromString: function (sort) {
                if (!sort) {
                    return undefined;
                } else {
                    if (sort[0] == '-') {
                        return {
                            predicate: sort.substring(1),
                            reverse: true
                        }
                    } else {
                        return {
                            predicate: sort
                        }
                    }
                }
            }
        };

        return answer;
    }()));