angular.module("app")
.factory("_filter", function (_filterItem) {
    function init(extend) {
        var selectedContainer = extend.getSelectedContainer;

        var answer = angular.extend(extend, {
            byType: {},
            getItems: function (type) {
                if (type) {
                    return answer.byType[type];
                } else {
                    return answer.items;
                }
            },
            setItems: function (items) {
                answer.items = items;
                answer.byType = _.groupBy(items, _.property("type"));
            },
            setSelected: function (items) {
                selectedContainer().items = items;
            },
            setSelectedSub: function (type, items) {
                var selectedOther = _.filter(answer.getSelected(), function (item) {
                    return _filterItem.uniqueIdToObject(item).type != type;
                });

                answer.setSelected([].pushArray(selectedOther).pushArray(items));
            },
            getSelected: function (type) {
                var selected = selectedContainer().items;

                if (type) {
                    selected = _.filter(selected, function (item) {
                        return _filterItem.uniqueIdToObject(item).type == type;
                    });
                }

                return selected;
            }
        });

        return answer;
    }

    return {
        init: init
    }
});