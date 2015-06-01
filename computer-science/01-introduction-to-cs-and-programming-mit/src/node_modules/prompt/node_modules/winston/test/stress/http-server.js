//
// Run ab -c 10 -n 100 localhost:4444/ | wc - l
// Nothing is created in http-stress.log
//

var http = require('http'),
    path = require('path'),
    winston = require('../../lib/winston');

var logger = new (winston.Logger)({
  transports: [
    new (winston.transports.Console)(),
    new (winston.transports.File)({ filename: path.join(__dirname, '..', 'fixtures', 'logs', 'http-stress.log') })
  ]
});

var server = http.createServer(function(request, response){
  response.writeHead(200, { 'Content-Type': 'text/plain' });
  var rd = Math.random() * 500;
  logger.info("hello " + rd);
  response.write('hello ');
  if (Math.floor(rd) == 10) {
    process.exit(1);
  }

  response.end();
}).listen(4444);