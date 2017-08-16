angular.module("app")
    .component("cFilterInputSimple", {
        templateUrl: "/components/_c-filter-input-simple.html",
        bindings: {
            filter: "=",
            label: "=",
        }
    })
    .component("cFilterInput", {
        templateUrl: "/components/_c-filter-input.html",
        bindings: {
            items: "=",
            selected: "=",
            onSelected: "&",
            label: "@",
            config: "="
        },
        controller: function ($rootScope, $scope, _i18n) {
            var $c = this;
            var config = $c.config || {};
            var configDefaults = {
                maxOptions: 50,
                plugins: ['remove_button'],
                valueField: 'idUnique',
                labelField: 'label',
                searchField: 'text',
                delimiter: '|',
                placeholder: _i18n($c.label),
                hideSelected: true,
                closeAfterSelect: true,
                render: {
                    option: function (data, escape) {
                        return '<div class="item"><div>' + escape(data.label) + '</div><div class="m-mp-filter-type _' + data.type + '">' + data.typeTranslation + '</div></div>';
                    },
                    item: function (data, escape) {
                        return '<div class="item"><div class="m-mp-filter-type _' + data.type + '">' + escape(data.label) + '</div></div>';
                    }
                },
                onChange: function (items) {
                    $rootScope.$safeApply(function () {
                        $c.onSelected({items: items});
                    });
                }
            };

            $c.selectize = {
                options: [],
                config: angular.extend(configDefaults, config)
            };

            $scope.$watch("$ctrl.selected", _refreshSelected);

            $scope.$watch("$ctrl.items", function (items) {
                if (items) {
                    $c.selectize.options.length = 0;
                    $c.selectize.options.pushArray(items);
                    _refreshSelected();
                }
            });

            function _refreshSelected() {
                $c.selectize.model = $c.selected;
            }
        }
    })
    .filter('propsFilter', function () {
        return function (items, props) {
            var out = [];

            if (angular.isArray(items)) {
                var keys = Object.keys(props);

                items.forEach(function (item) {
                    var itemMatches = false;

                    for (var i = 0; i < keys.length; i++) {
                        var prop = keys[i];
                        var text = props[prop].toLowerCase();
                        if (item[prop].toString().toLowerCase().indexOf(text) !== -1) {
                            itemMatches = true;
                            break;
                        }
                    }

                    if (itemMatches) {
                        out.push(item);
                    }
                });
            } else {
                // Let the output be the input untouched
                out = items;
            }

            return out;
        };
    });