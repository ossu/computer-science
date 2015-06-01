/*
 * log-string-exceptions.js: A test fixture for logging string exceptions in winston.
 *
 * (C) 2011 Charlie Robbins
 * MIT LICENCE
 *
 */

var path = require('path'),
    winston = require('../../../lib/winston');

var logger = new (winston.Logger)({
  transports: [
    new (winston.transports.File)({
      filename: path.join(__dirname, '..', 'logs', 'string-exception.log'),
      handleExceptions: true
    })
  ]
});

logger.handleExceptions();

setTimeout(function () {
  throw 'OMG NEVER DO THIS STRING EXCEPTIONS ARE AWFUL';
}, 1000);