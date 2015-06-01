/*
 * index.js: Top-level include for the `utile` module.
 *
 * (C) 2011, Nodejitsu Inc.
 * MIT LICENSE
 *
 */

var fs = require('fs'),
    path = require('path'),
    util = require('util');

var utile = module.exports;

//
// Extend the `utile` object with all methods from the
// core node `util` methods.
//
Object.keys(util).forEach(function (key) {
  utile[key] = util[key];
});

Object.defineProperties(utile, {

  //
  // ### function async
  // Simple wrapper to `require('async')`.
  //
  'async': {
    get: function() {
      return utile.async = require('async');
    }
  },

  //
  // ### function inflect
  // Simple wrapper to `require('i')`.
  //
  'inflect': {
    get: function() {
      return utile.inflect = require('i')();
    }
  },

  //
  // ### function mkdirp
  // Simple wrapper to `require('mkdirp')`
  //
  'mkdirp': {
    get: function() {
      return utile.mkdirp = require('mkdirp');
    }
  },

  //
  // ### function deepEqual
  // Simple wrapper to `require('deep-equal')`
  // Remark: deepEqual is 4x faster then using assert.deepEqual
  //         see: https://gist.github.com/2790507
  //
  'deepEqual': {
    get: function() {
      return utile.deepEqual = require('deep-equal');
    }
  },

  //
  // ### function rimraf
  // Simple wrapper to `require('rimraf')`
  //
  'rimraf': {
    get: function() {
      return utile.rimraf = require('rimraf');
    }
  },

  //
  // ### function cpr
  // Simple wrapper to `require('ncp').ncp`
  //
  'cpr': {
    get: function() {
      return utile.cpr = require('ncp').ncp;
    }
  },

  //
  // ### @file {Object}
  // Lazy-loaded `file` module
  //
  'file': {
    get: function() {
      return utile.file = require('./file');
    }
  },

  //
  // ### @args {Object}
  // Lazy-loaded `args` module
  //
  'args': {
    get: function() {
      return utile.args = require('./args');
    }
  },

  //
  // ### @base64 {Object}
  // Lazy-loaded `base64` object
  //
  'base64': {
    get: function() {
      return utile.base64 = require('./base64');
    }
  },

  //
  // ### @format {Object}
  // Lazy-loaded `format` object
  //
  'format': {
    get: function() {
      return utile.format = require('./format');
    }
  }

});


//
// ### function rargs(_args)
// #### _args {Arguments} Original function arguments
//
// Top-level method will accept a javascript "arguments" object
// (the actual keyword "arguments" inside any scope) and return
// back an Array.
//
utile.rargs = function (_args, slice) {
  if (!slice) {
    slice = 0;
  }

  var len = (_args || []).length,
      args = new Array(len - slice),
      i;

  //
  // Convert the raw `_args` to a proper Array.
  //
  for (i = slice; i < len; i++) {
    args[i - slice] = _args[i];
  }

  return args;
};

//
// ### function each (obj, iterator)
// #### @obj {Object} Object to iterate over
// #### @iterator {function} Continuation to use on each key. `function (value, key, object)`
// Iterate over the keys of an object.
//
utile.each = function (obj, iterator) {
  Object.keys(obj).forEach(function (key) {
    iterator(obj[key], key, obj);
  });
};

//
// ### function find (o)
//
//
utile.find = function (obj, pred) {
  var value, key;

  for (key in obj) {
    value = obj[key];
    if (pred(value, key)) {
      return value;
    }
  }
};

//
// ### function pad (str, len, chr)
// ### @str {String} String to pad
// ### @len {Number} Number of chars to pad str with
// ### @chr {String} Optional replacement character, defaults to empty space
// Appends chr to str until it reaches a length of len
//
utile.pad = function pad(str, len, chr) {
  var s;
  if (!chr) {
    chr = ' ';
  }
  str = str || '';
  s = str;
  if (str.length < len) {
    for (var i = 0; i < (len - str.length); i++) {
      s += chr;
    }
  }
  return s;
}

//
// ### function path (obj, path, value)
// ### @obj {Object} Object to insert value into
// ### @path {Array} List of nested keys to insert value at
// Retreives a value from given Object, `obj`, located at the
// nested keys, `path`.
//
utile.path = function (obj, path) {
  var key, i;

  for (i in path) {
    if (typeof obj === 'undefined') {
      return undefined;
    }

    key = path[i];
    obj = obj[key];
  }

  return obj;
};

//
// ### function createPath (obj, path, value)
// ### @obj {Object} Object to insert value into
// ### @path {Array} List of nested keys to insert value at
// ### @value {*} Value to insert into the object.
// Inserts the `value` into the given Object, `obj`, creating
// any keys in `path` along the way if necessary.
//
utile.createPath = function (obj, path, value) {
  var key, i;

  for (i in path) {
    key = path[i];
    if (!obj[key]) {
      obj[key] = ((+i + 1 === path.length) ? value : {});
    }

    obj = obj[key];
  }
};

//
// ### function mixin (target [source0, source1, ...])
// Copies enumerable properties from `source0 ... sourceN`
// onto `target` and returns the resulting object.
//
utile.mixin = function (target) {
  utile.rargs(arguments, 1).forEach(function (o) {
    Object.getOwnPropertyNames(o).forEach(function(attr) {
      var getter = Object.getOwnPropertyDescriptor(o, attr).get,
          setter = Object.getOwnPropertyDescriptor(o, attr).set;

      if (!getter && !setter) {
        target[attr] = o[attr];
      }
      else {
        Object.defineProperty(target, attr, {
          get: getter,
          set: setter
        });
      }
    });
  });

  return target;
};


//
// ### function capitalize (str)
// #### @str {string} String to capitalize
// Capitalizes the specified `str`.
//
utile.capitalize = utile.inflect.camelize;

//
// ### function escapeRegExp (str)
// #### @str {string} String to be escaped
// Escape string for use in Javascript regex
//
utile.escapeRegExp = function (str) {
  return str.replace(/[\-\[\]\/\{\}\(\)\*\+\?\.\\\^\$\|]/g, "\\$&");
};

//
// ### function randomString (length)
// #### @length {integer} The number of bits for the random base64 string returned to contain
// randomString returns a pseude-random ASCII string (subset)
// the return value is a string of length ⌈bits/6⌉ of characters
// from the base64 alphabet.
//
utile.randomString = function (length) {
  var chars, rand, i, ret, mod, bits;

  chars = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789_-';
  ret = '';
  // standard 4
  mod = 4;
  // default is 16
  bits = length * mod || 64;

  // in v8, Math.random() yields 32 pseudo-random bits (in spidermonkey it gives 53)
  while (bits > 0) {
    // 32-bit integer
    rand = Math.floor(Math.random() * 0x100000000);
    //we use the top bits
    for (i = 26; i > 0 && bits > 0; i -= mod, bits -= mod) {
      ret += chars[0x3F & rand >>> i];
    }
  }

  return ret;
};

//
// ### function filter (object, test)
// #### @obj {Object} Object to iterate over
// #### @pred {function} Predicate applied to each property. `function (value, key, object)`
// Returns an object with properties from `obj` which satisfy
// the predicate `pred`
//
utile.filter = function (obj, pred) {
  var copy;
  if (Array.isArray(obj)) {
    copy = [];
    utile.each(obj, function (val, key) {
      if (pred(val, key, obj)) {
        copy.push(val);
      }
    });
  }
  else {
    copy = {};
    utile.each(obj, function (val, key) {
      if (pred(val, key, obj)) {
        copy[key] = val;
      }
    });
  }
  return copy;
};

//
// ### function requireDir (directory)
// #### @directory {string} Directory to require
// Requires all files and directories from `directory`, returning an object
// with keys being filenames (without trailing `.js`) and respective values
// being return values of `require(filename)`.
//
utile.requireDir = function (directory) {
  var result = {},
      files = fs.readdirSync(directory);

  files.forEach(function (file) {
    if (file.substr(-3) === '.js') {
      file = file.substr(0, file.length - 3);
    }
    result[file] = require(path.resolve(directory, file));
  });
  return result;
};

//
// ### function requireDirLazy (directory)
// #### @directory {string} Directory to require
// Lazily requires all files and directories from `directory`, returning an
// object with keys being filenames (without trailing `.js`) and respective
// values (getters) being return values of `require(filename)`.
//
utile.requireDirLazy = function (directory) {
  var result = {},
      files = fs.readdirSync(directory);

  files.forEach(function (file) {
    if (file.substr(-3) === '.js') {
      file = file.substr(0, file.length - 3);
    }
    Object.defineProperty(result, file, {
      get: function() {
        return result[file] = require(path.resolve(directory, file));
      }
    });
  });
  
  return result;
};

//
// ### function clone (object, filter)
// #### @object {Object} Object to clone
// #### @filter {Function} Filter to be used
// Shallow clones the specified object.
//
utile.clone = function (object, filter) {
  return Object.keys(object).reduce(filter ? function (obj, k) {
    if (filter(k)) obj[k] = object[k];
    return obj;
  } : function (obj, k) {
    obj[k] = object[k];
    return obj;
  }, {});
};

//
// ### function camelToUnderscore (obj)
// #### @obj {Object} Object to convert keys on.
// Converts all keys of the type `keyName` to `key_name` on the
// specified `obj`.
//
utile.camelToUnderscore = function (obj) {
  if (typeof obj !== 'object' || obj === null) {
    return obj;
  }

  if (Array.isArray(obj)) {
    obj.forEach(utile.camelToUnderscore);
    return obj;
  }

  Object.keys(obj).forEach(function (key) {
    var k = utile.inflect.underscore(key);
    if (k !== key) {
      obj[k] = obj[key];
      delete obj[key];
      key = k;
    }
    utile.camelToUnderscore(obj[key]);
  });

  return obj;
};

//
// ### function underscoreToCamel (obj)
// #### @obj {Object} Object to convert keys on.
// Converts all keys of the type `key_name` to `keyName` on the
// specified `obj`.
//
utile.underscoreToCamel = function (obj) {
  if (typeof obj !== 'object' || obj === null) {
    return obj;
  }

  if (Array.isArray(obj)) {
    obj.forEach(utile.underscoreToCamel);
    return obj;
  }

  Object.keys(obj).forEach(function (key) {
    var k = utile.inflect.camelize(key, false);
    if (k !== key) {
      obj[k] = obj[key];
      delete obj[key];
      key = k;
    }
    utile.underscoreToCamel(obj[key]);
  });

  return obj;
};
