var winston = require('../lib/winston');

var logger = new (winston.Logger)({
  transports: [
    new (winston.transports.Console)({ raw: true }),
  ]
});

logger.log('info', 'Hello, this is a raw logging event',   { 'foo': 'bar' });
logger.log('info', 'Hello, this is a raw logging event 2', { 'foo': 'bar' });
