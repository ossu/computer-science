/*
 * file.js: Simple utilities for working with the file system.
 *
 * (C) 2011, Nodejitsu Inc.
 * MIT LICENSE
 *
 */

var fs = require('fs');

exports.readJson = exports.readJSON = function (file, callback) {
  if (typeof callback !== 'function') {
    throw new Error('utile.file.readJson needs a callback');
  }

  fs.readFile(file, 'utf-8', function (err, data) {
    if (err) {
      return callback(err);
    }

    try {
      var json = JSON.parse(data);
      callback(null, json);
    }
    catch (err) {
      return callback(err);
    }
  });
};

exports.readJsonSync = exports.readJSONSync = function (file) {
  return JSON.parse(fs.readFileSync(file, 'utf-8'));
};
