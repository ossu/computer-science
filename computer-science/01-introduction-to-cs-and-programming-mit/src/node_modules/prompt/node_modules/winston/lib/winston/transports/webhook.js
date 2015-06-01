/*
 * webhook.js: Transport for logging to remote http endpoints ( POST / RECEIVE webhooks )
 *
 * (C) 2011 Marak Squires
 * MIT LICENCE
 *
 */

var events = require('events'),
    http = require('http'),
    https = require('https'),
    util = require('util'),
    cycle = require('cycle'),
    common = require('../common'),
    Transport = require('./transport').Transport;

//
// ### function WebHook (options)
// #### @options {Object} Options for this instance.
// Constructor function for the Console transport object responsible
// for making arbitrary HTTP requests whenever log messages and metadata
// are received.
//
var Webhook = exports.Webhook = function (options) {
  Transport.call(this, options);

  this.name   = 'webhook';
  this.host   = options.host   || 'localhost';
  this.port   = options.port   || 8080;
  this.method = options.method || 'POST';
  this.path   = options.path   || '/winston-log';

  if (options.auth) {
    this.auth = {};
    this.auth.username = options.auth.username || '';
    this.auth.password = options.auth.password || '';
  }

  if (options.ssl) {
    this.port     = options.port || 443;
    this.ssl      = {
      key:  options.ssl.key  || null,
      cert: options.ssl.cert || null,
      ca:   options.ssl.ca
    };
  }
};

//
// Inherit from `winston.Transport`.
//
util.inherits(Webhook, Transport);

//
// Expose the name of this Transport on the prototype
//
Webhook.prototype.name = 'webhook';

//
// ### function log (level, msg, [meta], callback)
// #### @level {string} Level at which to log the message.
// #### @msg {string} Message to log
// #### @meta {Object} **Optional** Additional metadata to attach
// #### @callback {function} Continuation to respond to when complete.
// Core logging method exposed to Winston. Metadata is optional.
//
Webhook.prototype.log = function (level, msg, meta, callback) {
  if (this.silent) {
    return callback(null, true);
  }

  var self = this,
      meta = cycle.decycle(meta),
      message = common.clone(meta),
      options,
      req;

  // Prepare options for outgoing HTTP request
  options = {
    host: this.host,
    port: this.port,
    path: this.path,
    method: this.method,
    headers: { 'Content-Type': 'application/json' }
  };

  if (this.ssl) {
    options.ca = this.ssl.ca;
    options.key = this.ssl.key;
    options.cert = this.ssl.cert;

    // Required for the test fixture SSL certificate to be considered valid.
    options.rejectUnauthorized = false;
  }

  if (this.auth) {
    // Encode `Authorization` header used by Basic Auth
    options.headers['Authorization'] = 'Basic ' + new Buffer(
      this.auth.username + ':' + this.auth.password, 'utf8'
    ).toString('base64');
  }

  // Perform HTTP logging request
  req = (self.ssl ? https : http).request(options, function (res) {
    // TODO: emit 'logged' correctly,
    // keep track of pending logs.
    res.on('data', function(data) {
      // Do nothing. We need to read the response, or we run into maxSockets
      // after 5 requests.
    });

    self.emit('logged');
    if (callback) callback(null, true);
    callback = null;
  });

  req.on('error', function (err) {
    //
    // Propagate the `error` back up to the `Logger` that this
    // instance belongs to.
    //
    self.emit('error', err);
    if (callback) callback(err, false);
    callback = null;
  });

  //
  // Write logging event to the outgoing request body
  //
  // jsonMessage is currently conforming to JSON-RPC v1.0,
  // but without the unique id since there is no anticipated response
  // see: http://en.wikipedia.org/wiki/JSON-RPC
  //

  var params = common.clone(meta) || {};
  params.timestamp = new Date();
  params.message = msg;
  params.level = level;

  req.write(JSON.stringify({
    method: 'log',
    params: params
  }));

  req.end();
};
