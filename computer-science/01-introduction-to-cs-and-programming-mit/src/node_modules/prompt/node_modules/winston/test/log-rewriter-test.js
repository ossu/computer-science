/*
 * log-rewriter-test.js: Tests for rewriting metadata in winston.
 *
 * (C) 2010 Charlie Robbins
 * MIT LICENSE
 *
 */

var assert = require('assert'),
    vows = require('vows'),
    winston = require('../lib/winston'),
    helpers = require('./helpers');

vows.describe('winston/logger/rewriter').addBatch({
  "An instance of winston.Logger": {
    topic: new (winston.Logger)({transports: [
      new (winston.transports.Console)({ level: 'info' })
    ]}),
    "the addRewriter() method": {
      topic: function (logger) {
        logger.addRewriter(function (level, msg, meta) {
          meta.level = level;
          meta.msg = msg;
          meta.foo = 'bar';
          return meta;
        });
        return logger;
      },
      "should add the rewriter": function (logger) {
        assert.equal(helpers.size(logger.rewriters), 1);
      },
      "the log() method": {
        topic: function (logger) {
          logger.once('logging', this.callback);
          logger.log('info', 'test message', {"a": "b"});
        },
        "should run the rewriter": function (transport, level, msg, meta) {
          assert.equal(meta.a, 'b');
          assert.equal(meta.level, 'info');
          assert.equal(meta.msg, 'test message');
          assert.equal(meta.foo, 'bar');
        }
      }
    }
  }
}).addBatch({
  "An instance of winston.Logger with explicit rewriter": {
    topic: new (winston.Logger)({transports: [
      new (winston.transports.Console)({ level: 'info'})
    ], rewriters: [
      function (level, msg, meta) {
        meta.level = level;
        meta.msg = msg;
        meta.foo = 'bar';
        return meta;
      }
    ]}),
    "should add the rewriter": function (logger) {
      assert.equal(helpers.size(logger.rewriters), 1);
    },
    "the log() method": {
      topic: function (logger) {
        logger.once('logging', this.callback);
        logger.log('info', 'test message', {"a": "b"});
      },
      "should run the rewriter": function (transport, level, msg, meta) {
        assert.equal(meta.a, 'b');
        assert.equal(meta.level, 'info');
        assert.equal(meta.msg, 'test message');
        assert.equal(meta.foo, 'bar');
      }
    }
  }
}).addBatch({
  "An instance of winston.Logger with rewriters": {
    topic: new (winston.Logger)({transports: [
      new (winston.transports.Console)({ level: 'info' })
    ], rewriters: [
      function (level, msg, meta) {
        meta.numbers.push(1);
        return meta;
      },
      function (level, msg, meta) {
        meta.numbers.push(2);
        return meta;
      }
    ]}),
    "the log() method": {
      topic: function (logger) {
        logger.once('logging', this.callback);
        logger.log('info', 'test message', {"numbers": [0]});
      },
      "should run the rewriters in correct order": function (transport, level, msg, meta) {
        assert.deepEqual(meta.numbers, [0, 1, 2]);
      }
    }
  }
}).export(module);
