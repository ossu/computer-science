/*
 * webhook-test.js: Tests for instances of the Webhook transport
 *
 * (C) 2011 Marak Squires
 * MIT LICENSE
 *
 */

var path = require('path'),
    vows = require('vows'),
    fs = require('fs'),
    http = require('http'),
    https = require('https'),
    assert = require('assert'),
    winston = require('../../lib/winston'),
    helpers = require('../helpers');

var webhookTransport = new (winston.transports.Webhook)({
  "host": "localhost",
  "port": 8080,
  "path": "/winston-test"
});

var httpsWebhookTransport = new (winston.transports.Webhook)({
  "host": "localhost",
  "port": 8081,
  "path": "/winston-test",
  "ssl": true
});

var authWebhookTransport = new (winston.transports.Webhook)({
  "host": "localhost",
  "port": 8080,
  "path": "/winston-auth-test",
  "auth": {
    "username": "winston",
    "password": "churchill"
  }
});

var requestsAuthenticated = true;

var server = http.createServer(function (req, res) {
  if (req.url == '/winston-auth-test') {
    //
    // Test if request has been correctly authenticated
    //
    // Strip 'Basic' from Authorization header
    var signature = req.headers['authorization'].substr(6);
    requestsAuthenticated = requestsAuthenticated &&
      new Buffer(signature, 'base64').toString('utf8') == 'winston:churchill';
  }
  res.end();
});

server.listen(8080);


var httpsServer = https.createServer({
  cert: fs.readFileSync(path.join(__dirname, '..', 'fixtures', 'keys', 'agent2-cert.pem')),
  key: fs.readFileSync(path.join(__dirname, '..', 'fixtures', 'keys', 'agent2-key.pem'))
}, function (req, res) {
  res.end();
});

httpsServer.listen(8081);

vows.describe('winston/transports/webhook').addBatch({
  "An instance of the Webhook Transport": {
    "when passed valid options": {
      "should have the proper methods defined": function () {
        helpers.assertWebhook(webhookTransport);
      },
      "the log() method": helpers.testNpmLevels(webhookTransport, "should respond with true", function (ign, err, logged) {
        assert.isNull(err);
        assert.isTrue(logged);
      })
    }
  },
  "An https instance of the Webhook Transport": {
    "when passed valid options": {
      "should have the proper methods defined": function () {
        helpers.assertWebhook(httpsWebhookTransport);
      },
      "the log() method": helpers.testNpmLevels(httpsWebhookTransport, "should respond with true", function (ign, err, logged) {
        assert.isNull(err);
        assert.isTrue(logged);
      })
    }
  },
  "An http Basic Auth instance of the Webhook Transport": {
    "when passed valid options": {
      "should have the proper methods defined": function () {
        helpers.assertWebhook(authWebhookTransport);
      },
      "the log() method": helpers.testNpmLevels(authWebhookTransport, "should respond with true", function (ign, err, logged) {
        assert.isNull(err);
        assert.isTrue(logged);
      })
    }
  }
}).addBatch({
  "When the tests are over": {
    topic: function () {
      //
      // Delay destruction of the server since the
      // WebHook transport responds before the request
      // has actually be completed.
      //
      setTimeout(this.callback, 1000);
    },
    "the server should cleanup": function () {
      server.close();
    },
    "requests have been correctly authenticated": function () {
      assert.ok(requestsAuthenticated);
    }
  }
}).addBatch({
  // "An instance of the Webhook Transport": transport(winston.transports.Webhook, {
  //   "host": "localhost",
  //   "port": 8080,
  //   "path": "/winston-test"
  // })
}).export(module);
