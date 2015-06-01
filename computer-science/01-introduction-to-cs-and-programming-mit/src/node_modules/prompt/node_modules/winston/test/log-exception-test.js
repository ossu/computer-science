/*
 * exception-test.js: Tests for exception data gathering in winston.
 *
 * (C) 2010 Charlie Robbins
 * MIT LICENSE
 *
 */

var assert = require('assert'),
    path = require('path'),
    fs = require('fs'),
    spawn = require('child_process').spawn,
    vows = require('vows'),
    winston = require('../lib/winston'),
    helpers = require('./helpers'),
    exists = (fs.exists || path.exists);

vows.describe('winston/logger/exceptions').addBatch({
  "When using winston": {
    "the handleException() method": {
      "with a custom winston.Logger instance": helpers.assertHandleExceptions({
        script: path.join(__dirname, 'fixtures', 'scripts', 'log-exceptions.js'),
        logfile: path.join(__dirname, 'fixtures', 'logs', 'exception.log')
      }),
      "with the default winston logger": helpers.assertHandleExceptions({
        script: path.join(__dirname, 'fixtures', 'scripts', 'default-exceptions.js'),
        logfile: path.join(__dirname, 'fixtures', 'logs', 'default-exception.log')
      }),
      "when strings are thrown as errors": helpers.assertHandleExceptions({
        script: path.join(__dirname, 'fixtures', 'scripts', 'log-string-exception.js'),
        logfile: path.join(__dirname, 'fixtures', 'logs', 'string-exception.log'),
        message: 'OMG NEVER DO THIS STRING EXCEPTIONS ARE AWFUL'
      }),
      "when a custom exitOnError function is set": {
        topic: function () {
          var that = this,
              scriptDir = path.join(__dirname, 'fixtures', 'scripts');

          that.child = spawn('node', [path.join(scriptDir, 'exit-on-error.js')]);
          setTimeout(this.callback.bind(this), 1500);
        },
        "should not exit the process": function () {
          assert.isFalse(this.child.killed);
          this.child.kill();
        }
      }
    },
    "the unhandleException() method": {
      topic: function () {
        var that = this,
            child = spawn('node', [path.join(__dirname, 'fixtures', 'scripts', 'unhandle-exceptions.js')]),
            exception = path.join(__dirname, 'fixtures', 'logs', 'unhandle-exception.log');

        helpers.tryUnlink(exception);
        child.on('exit', function () {
          exists(exception, that.callback.bind(this, null));
        });
      },
      "should not write to the specified error file": function (err, exists) {
        assert.isTrue(!err);
        assert.isFalse(exists);
      }
    }
  }
}).export(module);