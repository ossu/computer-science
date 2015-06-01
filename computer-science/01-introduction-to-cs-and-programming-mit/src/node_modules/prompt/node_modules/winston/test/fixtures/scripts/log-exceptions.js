/*
 * log-exceptions.js: A test fixture for logging exceptions in winston.
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
      filename: path.join(__dirname, '..', 'logs', 'exception.log'),
      handleExceptions: true
    })
  ]
});

logger.handleExceptions();

setTimeout(function () {
  throw new Error('OH NOES! It failed!');
}, 1000);