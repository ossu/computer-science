var path = require('path'),
    vows = require('vows'),
    assert = require('assert'),
    winston = require('../../lib/winston'),
    helpers = require('../helpers');

var npmTransport = new (winston.transports.Memory)(),
    syslogTransport = new (winston.transports.Memory)({ levels: winston.config.syslog.levels });

vows.describe('winston/transports/memory').addBatch({
  "An instance of the Memory Transport": {
    "with npm levels": {
      "should have the proper methods defined": function () {
        helpers.assertMemory(npmTransport);
      },
      "the log() method": helpers.testNpmLevels(npmTransport, "should respond with true", function (ign, err, logged) {
        assert.isNull(err);
        assert.isTrue(logged);
      })
    },
    "with syslog levels": {
      "should have the proper methods defined": function () {
        helpers.assertMemory(syslogTransport);
      },
      "the log() method": helpers.testSyslogLevels(syslogTransport, "should respond with true", function (ign, err, logged) {
        assert.isNull(err);
        assert.isTrue(logged);
      })
    }
  }
}).export(module);