/*
 * existing-properties.js: Example of using prompt with predefined properties.
 *
 * (C) 2010, Nodejitsu Inc.
 *
 */

var prompt = require('../lib/prompt');

prompt.properties = {
  email: {
    format: 'email',
    message: 'Must be a valid email address'
  },
  password: {
    hidden: true
  }
};

//
// Start the prompt
//
prompt.start();

//
// Get two properties from the user: email, password
//
prompt.get(['email', 'password'], function (err, result) {
  //
  // Log the results.
  //
  console.log('Command-line input received:');
  console.log('  email: ' + result.email);
  console.log('  password: ' + result.password);
});
