//http://stackoverflow.com/questions/4825812/clean-way-to-remove-element-from-javascript-array-with-jquery-coffeescript
Array.prototype.remove = function(elem) {
    var removed = false;
    var match = -1;

    while( (match = this.indexOf(elem)) > -1 ) {
        this.splice(match, 1);
        removed = true;
    }

    return removed;
};

//http://stackoverflow.com/questions/4156101/javascript-push-array-values-into-another-array
Array.prototype.pushArray = function(arr) {
    this.push.apply(this, arr);

    return this;
};

//http://stackoverflow.com/a/17153457/716027
Array.prototype.addOrRemove = function(value) {
    var index = this.indexOf(value);

    if (index === -1) {
        this.push(value);
    } else {
        this.splice(index, 1);
    }
};