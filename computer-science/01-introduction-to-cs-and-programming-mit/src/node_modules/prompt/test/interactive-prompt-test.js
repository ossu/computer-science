/*
 * prompt-test.js: Tests for prompt.
 *
 * (C) 2010, Nodejitsu Inc.
 *
 */

var assert = require('assert'),
    vows = require('vows'),
    prompt = require('../lib/prompt'),
    winston = require('winston').cli(),
    helpers = require('./helpers');

vows.describe('prompt/interactive').addBatch({
  "When using prompt": {
    topic: function () {
      //
      // Reset the prompt for interactive testing
      //
      prompt.started = false;
      prompt.start();
      winston.info('These prompt tests are interactive');
      winston.info('Not following instructions will result in test failure');
      return null;
    },
    "the getInput() method": {
      "when passed a complex property with `hidden: true`": {
        topic: function () {
          winston.info('When prompted, enter: 12345 [backspace] [backspace] [enter]');
          prompt.getInput({ path: ['password'], schema: helpers.schema.properties.password }, this.callback);
        },
        "should respond with `123`": function (err, result) {
          assert.isNull(err);
          assert.equal(result, '123');
        },
        "and then when passed a complex property expecting a number": {
          topic: function () {
            winston.info('When prompted, enter: 123 [enter]');
            prompt.getInput({ path: ['number'], schema: helpers.schema.properties.number }, this.callback);
          },
          "should respond with `123` (as a number)": function (err, result) {
            assert.isNull(err);
            assert.equal(result, 123);
          }
        }
      }
    }
  }
}).export(module);
