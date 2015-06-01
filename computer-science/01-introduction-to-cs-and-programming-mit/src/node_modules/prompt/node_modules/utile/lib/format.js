/*
 * format.js: `util.format` enhancement to allow custom formatting parameters.
 *
 * (C) 2012, Nodejitsu Inc.
 * MIT LICENSE
 *
 */

var util = require('util');

exports = module.exports = function(str) {
  var formats = [].slice.call(arguments, 1, 3);

  if (!(formats[0] instanceof Array && formats[1] instanceof Array) || arguments.length > 3)
    return util.format.apply(null, arguments);

  var replacements = formats.pop(),
      formats = formats.shift();

  formats.forEach(function(format, id) {
    str = str.replace(new RegExp(format), replacements[id]);
  });

  return str;
};
