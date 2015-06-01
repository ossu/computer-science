/*
 * format-test.js: Tests for `utile.format` module.
 *
 * (C) 2011, Nodejitsu Inc.
 * MIT LICENSE
 *
 */

var vows = require('vows'),
    assert = require('assert'),
    utile = require('../lib');

vows.describe('utile/format').addBatch({

  'Should use the original `util.format` if there are no custom parameters to replace.': function() {
    assert.equal(utile.format('%s %s %s', 'test', 'test2', 'test3'), 'test test2 test3');
  },

  'Should use `utile.format` if custom parameters are provided.': function() {
    assert.equal(utile.format('%a %b %c', [
      '%a',
      '%b',
      '%c'
    ], [
      'test',
      'test2',
      'test3'
    ]), 'test test2 test3');
  }

}).export(module);
