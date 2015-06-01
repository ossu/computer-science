module.exports = function (obj) {

  var addProperty = function (method, func) {
    String.prototype.__defineGetter__(method, func);
  }

  var stringPrototypeBlacklist = [
    '__defineGetter__', '__defineSetter__', '__lookupGetter__', '__lookupSetter__', 'charAt', 'constructor',
    'hasOwnProperty', 'isPrototypeOf', 'propertyIsEnumerable', 'toLocaleString', 'toString', 'valueOf', 'charCodeAt',
    'indexOf', 'lastIndexof', 'length', 'localeCompare', 'match', 'replace', 'search', 'slice', 'split', 'substring',
    'toLocaleLowerCase', 'toLocaleUpperCase', 'toLowerCase', 'toUpperCase', 'trim', 'trimLeft', 'trimRight', 'gsub'
  ];

  Object.keys(obj).forEach(function (key) {
    if (key != 'inflect' && key != 'inflections') {
      if (stringPrototypeBlacklist.indexOf(key) !== -1) {
        console.log('warn: You should not override String.prototype.' + key);
      } else {
        addProperty(key, function () {
          return obj[key](this);
        });
      }
    }
  });

}
