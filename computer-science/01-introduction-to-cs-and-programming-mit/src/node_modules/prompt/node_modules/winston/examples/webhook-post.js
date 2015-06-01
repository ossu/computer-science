var winston = require('../lib/winston');

//
// Create a new winston logger instance with two tranports: Console, and Webhook
//
//
// The Console transport will simply output to the console screen
// The Webhook tranports will perform an HTTP POST request to an abritrary end-point ( for post/recieve webhooks )
//
var logger = new (winston.Logger)({
  transports: [
    new (winston.transports.Console)(),
    new (winston.transports.Webhook)({ 'host': 'localhost', 'port': 8080, 'path': '/collectdata' })
  ]
});

logger.log('info', 'Hello webhook log files!', { 'foo': 'bar' });
