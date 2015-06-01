/*
 * function-args-test.js: Tests for `args` method
 *
 * (C) 2012, Nodejitsu Inc.
 * MIT LICENSE
 *
 */

var assert = require('assert'),
    path = require('path'),
    vows = require('vows'),
    macros = require('./helpers/macros'),
    utile = require('../');

vows.describe('utile/args').addBatch({
  'When using utile': {
    'the `args` function': {
      topic: utile,
      'should be a function': function (_utile) {
        assert.isFunction(_utile.args);
      },
    }
  },
  'utile.rargs()': {
    'with no arguments': {
      topic: utile.rargs(),
      'should return an empty object': function (result) {
        assert.isArray(result);
        assert.lengthOf(result, 0);
      }
    },
    'with simple arguments': {
      topic: function () {
        return (function () {
          return utile.rargs(arguments);
        })('a', 'b', 'c');
      },
      'should return an array with three items': function (result) {
        assert.isArray(result);
        assert.equal(3, result.length);
        assert.equal(result[0], 'a');
        assert.equal(result[1], 'b');
        assert.equal(result[2], 'c');
      }
    },
    'with a simple slice': {
      topic: function () {
        return (function () {
          return utile.rargs(arguments, 1);
        })('a', 'b', 'c');
      },
      'should return an array with three items': function (result) {
        assert.isArray(result);
        assert.equal(2, result.length);
        assert.equal(result[0], 'b');
        assert.equal(result[1], 'c');
      }
    }
  },
  'utile.args()': {
    'with no arguments': {
      topic: utile.args(),
      'should return an empty Array': function (result) {
        assert.isUndefined(result.callback);
        assert.isArray(result);
        assert.lengthOf(result, 0);
      }
    },
    'with simple arguments': {
      topic: function () {
        return (function () {
          return utile.args(arguments);
        })('a', 'b', 'c', function () {
          return 'ok';
        });
      },
      'should return an array with three items': function (result) {
        assert.isArray(result);
        assert.equal(3, result.length);
        assert.equal(result[0], 'a');
        assert.equal(result[1], 'b');
        assert.equal(result[2], 'c');

        //
        // Ensure that the Array returned
        // by `utile.args()` enumerates correctly
        //
        var length = 0;
        result.forEach(function (item) {
          length++;
        });

        assert.equal(length, 3);
      },
      'should return lookup helpers': function (result) {
        assert.isArray(result);
        assert.equal(result.first, 'a');
        assert.equal(result.last, 'c');
        assert.isFunction(result.callback);
        assert.isFunction(result.cb);
      }
    }
  }
}).export(module);