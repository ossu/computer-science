var winston = require('../');
winston.handleExceptions(new winston.transports.Console({ colorize: true, json: true }));

throw new Error('Hello, winston!');
