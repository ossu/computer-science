var winston = require('../lib/winston');

//
// Create a new winston logger instance with two tranports: Console, and Couchdb
//
//
// The Console transport will simply output to the console screen
// The Couchdb tranport will perform an HTTP POST request to the specified CouchDB instance
//
var logger = new (winston.Logger)({
  transports: [
    new (winston.transports.Console)(),
    new (winston.transports.Couchdb)({ 'host': 'localhost', 'db': 'logs' })
    // if you need auth do this: new (winston.transports.Couchdb)({ 'user': 'admin', 'pass': 'admin', 'host': 'localhost', 'db': 'logs' })
  ]
});

logger.log('info', 'Hello webhook log files!', { 'foo': 'bar' });
