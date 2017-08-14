//https://github.com/moment/moment/issues/529
moment.fn.weeks = function weeks() {
    var m = this;

    var lastOfMonth = m.clone().endOf('month'),
        lastOfMonthDate = lastOfMonth.date(),
        firstOfMonth = m.clone().startOf('month'),
        currentWeek = firstOfMonth.clone().day(1),
        output = [],
        startOfWeek,
        endOfWeek;

    while (currentWeek < lastOfMonth) {
        startOfWeek = sameMonth(currentWeek.clone().day(1), firstOfMonth, 1);
        endOfWeek = sameMonth(currentWeek.clone().day(7), firstOfMonth, lastOfMonthDate);

        output.push({
            start: startOfWeek,
            end: endOfWeek
        });

        currentWeek.add('d', 7);
    }

    return output;

    function sameMonth(a, b, other) {
        if (a.month() !== b.month()) {
            return other;
        }
        return a.date();
    }
};

moment.fn.toStringDate = function () {
    return this.format("YYYY-MM-DD");
};