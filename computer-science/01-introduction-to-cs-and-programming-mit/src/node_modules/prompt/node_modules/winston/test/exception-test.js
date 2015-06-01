/*
 * exception-test.js: Tests for exception data gathering in winston.
 *
 * (C) 2010 Charlie Robbins
 * MIT LICENSE
 *
 */

var path = require('path'),
    vows = require('vows'),
    assert = require('assert'),
    winston = require('../lib/winston'),
    helpers = require('./helpers');

vows.describe('winston/exception').addBatch({
  "When using the winston exception module": {
    "the getProcessInfo() method": {
      topic: winston.exception.getProcessInfo(),
      "should respond with the appropriate data": function (info) {
        helpers.assertProcessInfo(info);
      }
    },
    "the getOsInfo() method": {
      topic: winston.exception.getOsInfo(),
      "should respond with the appropriate data": function (info) {
        helpers.assertOsInfo(info);
      }
    },
    "the getTrace() method": {
      topic: winston.exception.getTrace(new Error()),
      "should have the appropriate info": function (trace) {
        helpers.assertTrace(trace);
      }
    },
    "the getAllInfo() method": {
      topic: winston.exception.getAllInfo(new Error()),
      "should have the appropriate info": function (info) {
        assert.isObject(info);
        assert.isArray(info.stack);
        helpers.assertDateInfo(info.date);
        helpers.assertProcessInfo(info.process);
        helpers.assertOsInfo(info.os);
        helpers.assertTrace(info.trace);
      }
    }
  }
}).export(module);
