var util = require('util'),
    winston = require('../../winston'),
    http = require('http'),
    https = require('https'),
    Stream = require('stream').Stream,
    Transport = require('./transport').Transport;

//
// ### function Http (options)
// #### @options {Object} Options for this instance.
// Constructor function for the Http transport object responsible
// for persisting log messages and metadata to a terminal or TTY.
//
var Http = exports.Http = function (options) {
  Transport.call(this, options);
  options = options || {};

  this.name = 'http';
  this.ssl = !!options.ssl;
  this.host = options.host || 'localhost';
  this.port = options.port;
  this.auth = options.auth;
  this.path = options.path || '';

  if (!this.port) {
    this.port = this.ssl ? 443 : 80;
  }
};

util.inherits(Http, winston.Transport);

//
// Expose the name of this Transport on the prototype
//
Http.prototype.name = 'http';

//
// ### function _request (options, callback)
// #### @callback {function} Continuation to respond to when complete.
// Make a request to a winstond server or any http server which can
// handle json-rpc.
//
Http.prototype._request = function (options, callback) {
  options = options || {};

  var auth = options.auth || this.auth,
      path = options.path || this.path || '';

  delete options.auth;
  delete options.path;

  // Prepare options for outgoing HTTP request
  req = (this.ssl ? https : http).request({
    host: this.host,
    port: this.port,
    path: path.replace(/^\//, ''),
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    auth: (auth) ? auth.username + ':' + auth.password : ''
  });

  req.end(new Buffer(JSON.stringify(options), 'utf8'));

  req.on('error', callback);
  req.on('response', function (res) {
    res.on('end', function () {
      callback(null, res);
    });
  });
};

//
// ### function log (level, msg, [meta], callback)
// #### @level {string} Level at which to log the message.
// #### @msg {string} Message to log
// #### @meta {Object} **Optional** Additional metadata to attach
// #### @callback {function} Continuation to respond to when complete.
// Core logging method exposed to Winston. Metadata is optional.
//
Http.prototype.log = function (level, msg, meta, callback) {
  var self = this;

  if (typeof meta === 'function') {
    callback = meta;
    meta = {};
  }

  var options = {
    method: 'collect',
    params: {
      level: level,
      message: msg,
      meta: meta
    }
  };

  if (meta) {
    if (meta.path) {
      options.path = meta.path;
      delete meta.path;
    }

    if (meta.auth) {
      options.auth = meta.auth;
      delete meta.auth;
    }
  }

  this._request(options, function (err, res) {
    if (res && res.statusCode !== 200) {
      err = new Error('HTTP Status Code: ' + res.statusCode);
    }

    if (err) return callback(err);

    // TODO: emit 'logged' correctly,
    // keep track of pending logs.
    self.emit('logged');

    if (callback) callback(null, true);
  });
};

//
// ### function query (options, callback)
// #### @options {Object} Loggly-like query options for this instance.
// #### @callback {function} Continuation to respond to when complete.
// Query the transport. Options object is optional.
//
Http.prototype.query = function (options, callback) {
  if (typeof options === 'function') {
    callback = options;
    options = {};
  }

  var self = this,
      options = this.normalizeQuery(options);

  options = {
    method: 'query',
    params: options
  };

  if (options.params.path) {
    options.path = options.params.path;
    delete options.params.path;
  }

  if (options.params.auth) {
    options.auth = options.params.auth;
    delete options.params.auth;
  }

  this._request(options, function (err, res, body) {
    if (res && res.statusCode !== 200) {
      err = new Error('HTTP Status Code: ' + res.statusCode);
    }

    if (err) return callback(err);

    if (typeof body === 'string') {
      try {
        body = JSON.parse(body);
      } catch (e) {
        return callback(e);
      }
    }

    callback(null, body);
  });
};

//
// ### function stream (options)
// #### @options {Object} Stream options for this instance.
// Returns a log stream for this transport. Options object is optional.
//
Http.prototype.stream = function (options) {
  var self = this,
      options = options || {},
      stream = new Stream,
      req,
      buff;

  stream.destroy = function () {
    req.destroy();
  };

  options = {
    method: 'stream',
    params: options
  };

  if (options.params.path) {
    options.path = options.params.path;
    delete options.params.path;
  }

  if (options.params.auth) {
    options.auth = options.params.auth;
    delete options.params.auth;
  }

  req = this._request(options);
  buff = '';

  req.on('data', function (data) {
    var data = (buff + data).split(/\n+/),
        l = data.length - 1,
        i = 0;

    for (; i < l; i++) {
      try {
        stream.emit('log', JSON.parse(data[i]));
      } catch (e) {
        stream.emit('error', e);
      }
    }

    buff = data[l];
  });

  req.on('error', function (err) {
    stream.emit('error', err);
  });

  return stream;
};
