angular.module("common").
    factory("_http", function() {
        return {
            getData: function(promise) {
                return function() {
                    return promise.apply(this, arguments).then(function (response) {
                        return response.data;
                    })
                }
            },
            getDataAndTransform: function (promise, transformer) {
                return function() {
                    return promise.apply(this, arguments).then(function (response) {
                        return transformer(response.data);
                    })
                }
            },
            getListDataAndTransform: function (promise, transformer) {
                return function() {
                    return promise.apply(this, arguments).then(function (response) {
                        var data = response.data;

                        if (data && data.items) {
                            data.items = _.map(data.items, function (item) {
                                return transformer(item);
                            });
                        }

                        return data;
                    })
                };
            }
        }
    });