/*
 * logger-test.js: Tests for instances of the winston Logger
 *
 * (C) 2010 Charlie Robbins
 * MIT LICENSE
 *
 */

var fs = require('fs'),
    path = require('path'),
    vows = require('vows'),
    http = require('http'),
    assert = require('assert'),
    winston = require('../lib/winston'),
    helpers = require('./helpers');

vows.describe('winston').addBatch({
  "The winston module": {
    topic: function () {
      winston.default.transports.console.level = 'silly';
      return null;
    },
    "should have the correct methods defined": function () {
      assert.isObject(winston.transports);
      assert.isFunction(winston.Transport);
      assert.isTrue(!winston.transports.Transport);
      assert.isFunction(winston.transports.Console);
      assert.isFunction(winston.transports.File);
      assert.isFunction(winston.transports.Webhook);
      assert.isObject(winston.default.transports.console);
      assert.isFalse(winston.emitErrs);
      assert.isObject(winston.config);
      ['Logger', 'add', 'remove', 'extend', 'clear']
        .concat(Object.keys(winston.config.npm.levels))
        .forEach(function (key) {
          assert.isFunction(winston[key]);
        });
    },
    "it should": {
      topic: function () {
        fs.readFile(path.join(__dirname, '..', 'package.json'), this.callback);
      },
      "have the correct version set": function (err, data) {
        assert.isNull(err);
        data = JSON.parse(data.toString());
        assert.equal(winston.version, data.version);
      }
    },
    "the log() method": helpers.testNpmLevels(winston, "should respond without an error", function (err) {
      assert.isNull(err);
    }),
    "the extend() method called on an empty object": {
      topic: function (logger) {
        var empty = {};
        winston.extend(empty);
        return empty;
      },
      "should define the appropriate methods": function (extended) {
        ['log', 'profile', 'startTimer'].concat(Object.keys(winston.config.npm.levels)).forEach(function (method) {
          assert.isFunction(extended[method]);
        });
      }
    }
  }
}).addBatch({
  "The winston module": {
    "the setLevels() method": {
      topic: function () {
        winston.setLevels(winston.config.syslog.levels);
        return null;
      },
      "should have the proper methods defined": function () {
        assert.isObject(winston.transports);
        assert.isFunction(winston.transports.Console);
        assert.isFunction(winston.transports.Webhook);
        assert.isObject(winston.default.transports.console);
        assert.isFalse(winston.emitErrs);
        assert.isObject(winston.config);

        var newLevels = Object.keys(winston.config.syslog.levels);
        ['Logger', 'add', 'remove', 'extend', 'clear']
          .concat(newLevels)
          .forEach(function (key) {
            assert.isFunction(winston[key]);
          });


        Object.keys(winston.config.npm.levels)
          .filter(function (key) {
            return newLevels.indexOf(key) === -1;
          })
          .forEach(function (key) {
            assert.isTrue(typeof winston[key] === 'undefined');
          });
      }
    }
  }
}).export(module);
