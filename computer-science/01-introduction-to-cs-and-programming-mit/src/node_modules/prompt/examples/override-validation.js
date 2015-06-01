/*
 * override-validation.js: Example of using prompt with complex properties and command-line input.
 *
 * (C) 2010, Nodejitsu Inc.
 *
 */

var prompt = require('../lib/prompt'),
    optimist = require('optimist');

var schema = {
  properties: {
    name: {
      pattern: /^[a-zA-Z\s-]+$/,
      message: 'Name must be only letters, spaces, or dashes',
      required: true
    },
    email: {
      name: 'email',
      format: 'email',
      message: 'Must be a valid email address'
    }
  }
};

//
// Set the overrides
//
prompt.override = optimist.argv

//
// Start the prompt
//
prompt.start();

//
// Get two properties from the user: email, password
//
prompt.get(schema, function (err, result) {
  //
  // Log the results.
  //
  console.log('Command-line input received:');
  console.log('  name: ' + result.name);
  console.log('  email: ' + result.email);
});

// try running
// $ node ./override-validation.js --name USER --email EMAIL
// You will only be asked for email becasue it's invalid
// $ node ./override-validation.js --name h$acker --email me@example.com
// You will only be asked for email becasue it's invalid