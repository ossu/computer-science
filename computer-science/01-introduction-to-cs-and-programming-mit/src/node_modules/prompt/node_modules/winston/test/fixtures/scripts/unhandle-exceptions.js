/*
 * unhandle-exceptions.js: A test fixture for using `.unhandleExceptions()` winston.
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
      filename: path.join(__dirname, '..', 'logs', 'unhandle-exception.log'),
      handleExceptions: true
    })
  ]
});

logger.handleExceptions();
logger.unhandleExceptions();

setTimeout(function () {
  throw new Error('OH NOES! It failed!');
}, 1000);