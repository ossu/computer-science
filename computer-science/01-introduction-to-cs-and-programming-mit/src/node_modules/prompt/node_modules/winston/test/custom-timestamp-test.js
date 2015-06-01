/*
 * custom-timestamp-test.js: Test function as timestamp option for transport `{ timestamp: function () {} }`
 *
 * (C) 2011 Charlie Robbins, Tom Shinnick
 * MIT LICENSE
 *
 */

var assert = require('assert'),
    events = require('events'),
    fs = require('fs'),
    path = require('path'),
    vows = require('vows'),
    winston = require('../lib/winston'),
    helpers = require('./helpers');

function assertTimestamp (basename, options) {
  var filename = path.join(__dirname, 'fixtures', 'logs', basename + '.log');
  
  try { fs.unlinkSync(filename) }
  catch (ex) { }
  
  return {
    topic: function () {
      options.filename = filename;
      var transport = new (winston.transports.File)(options);

      // We must wait until transport file has emitted the 'flush' 
      // event to be sure the file has been created and written
      transport.once('flush', this.callback.bind(this, null, filename));
      transport.log('info', 'When a fake tree falls in the forest...', null, function () {});
    },
    "should log with the appropriate timestamp": function (_, filename) {
      var data = fs.readFileSync(filename, 'utf8');
      assert.isNotNull(data.match(options.pattern));
    }
  }
}

vows.describe('winston/transport/timestamp').addBatch({
  "When timestamp option is used": {
    "with file transport": {
      "with value set to false": assertTimestamp('noTimestamp', { 
        pattern: /^info\:/, 
        json: false,
        timestamp: false 
      }),
      "with value set to true ": assertTimestamp('defaultTimestamp', { 
        pattern: /^\d{4}-\d{2}-\d{2}T\d{2}:\d{2}:\d{2}\.\d{3}Z/,
        json: false,
        timestamp: true 
      }),
      "and function value": assertTimestamp('customTimestamp', {
        pattern: /^\d{8}\./,
        json: false,
        timestamp: function () {
          return '20110803.171657';
        }
      })
    }
  }
}).export(module);