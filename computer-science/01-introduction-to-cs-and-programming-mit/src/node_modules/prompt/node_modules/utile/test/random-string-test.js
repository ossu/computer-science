/*
 * common-test.js : testing common.js for expected functionality
 *
 * (C) 2011, Nodejitsu Inc.
 *
 */

var assert = require('assert'),
    vows = require('vows'),
    utile = require('../lib');

vows.describe('utile/randomString').addBatch({
  "When using utile": {
    "the randomString() function": {
      topic: function () {
        return utile.randomString();
      },
      "should return 16 characters that are actually random by default": function (random) {
        assert.isString(random);
        assert.lengthOf(random, 16);
        assert.notEqual(random, utile.randomString());
      },
      "when you can asked for different length strings": {
        topic: function () {
          return [utile.randomString(4), utile.randomString(128)];
        },
        "where they actually are of length 4, 128": function (strings) {
          assert.isArray(strings);
          assert.lengthOf(strings,2);
          assert.isString(strings[0]);
          assert.isString(strings[1]);
          assert.lengthOf(strings[0], 4);
          assert.lengthOf(strings[1], 128);
          assert.notEqual(strings[0], strings[1].substr(0,4));
        }
      }
    }
  }
}).export(module);
