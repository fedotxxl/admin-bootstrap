_.remove = function(array, element) {
    //http://stackoverflow.com/questions/5767325/remove-specific-element-from-an-array
    var index = array.indexOf(element);
    if (index > -1) {
        array.splice(index, 1);
    }
};

_.empty = function(array) {
    //http://stackoverflow.com/questions/1232040/how-to-empty-an-array-in-javascript
    array.length = 0;
};

_.emptyObject = function(object) {
    _.each(_.keys(object), function(key) {
        delete object[key];
    });
};

_.getProperty = function(o, s) {
    //http://stackoverflow.com/questions/6491463/accessing-nested-javascript-objects-with-string-key
    s = s.replace(/\[(\w+)\]/g, '.$1'); // convert indexes to properties
    s = s.replace(/^\./, '');           // strip a leading dot
    var a = s.split('.');
    for (var i = 0, n = a.length; i < n; ++i) {
        var k = a[i];
        if (k in o) {
            o = o[k];
        } else {
            return;
        }
    }
    return o;
};

_.isNumeric = function(object) {
    return !_.isNaN(parseFloat(object))
};

_.toMyArray = function (item) {
    if (_.isUndefined(item) || _.isNull(item)) {
        return [];
    } else if (_.isArray(item)) {
        return item;
    } else {
        return [item];
    }
};

_.toMyBoolean = function (value) {
    if (!value) {
        return false;
    } else if (value == "false" || value == "0") {
        return false;
    } else {
        return !!value;
    }
}

_.toIntOr = function (item, or) {
    var answer = parseInt(item);

    if (_.isNaN(answer)) {
        answer = or;
    }

    return answer;
};

//http://stackoverflow.com/a/10073788/716027
_.pad = function (n, width, z) {
    z = z || '0';
    n = n + '';
    return n.length >= width ? n : new Array(width - n.length + 1).join(z) + n;
};

_.sum = function (array) {
    return _.reduce(array, function (a, b) {
        return a + b;
    }, 0);
};

_.getOrInit = function (object, key, defaultValue) {
    var answer = object[key];

    if (_.isUndefined(answer)) {
        answer = defaultValue;
        object[key] = answer;
    }

    return answer;
};

_.isNullOrUndefined = function (object) {
    return _.isNull(object) || _.isUndefined(object);
};

_.checkAndSet = function (object, property, value) {
    if (_.isUndefined(object[property])) {
        object[property] = value;
    }

    return object[property];
};