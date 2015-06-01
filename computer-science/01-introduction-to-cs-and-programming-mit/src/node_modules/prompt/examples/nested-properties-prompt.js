/*
 * property-prompt.js: Example of using prompt with complex properties.
 *
 * (C) 2010, Nodejitsu Inc.
 *
 */

var prompt = require('../lib/prompt');

var schema = {
  properties: {
    url: {
      required: true,
      format: 'url'
    },
    auth: {
      properties: {
        username: {
          required: true
        },
        password: {
          required: true,
          hidden: true
        }
      }
    }
  }
};

prompt.start();

prompt.get(schema, function (err, result) {
  console.log('Command-line input received:');
  console.log('  url: ' + result.url);
  console.log('  auth:username: ' + result.auth.username);
  console.log('  auth:password: ' + result.auth.password);
});
