/*
 * simple-prompt.js: Simple example of using prompt.
 *
 * (C) 2010, Nodejitsu Inc.
 *
 */

var prompt = require('../lib/prompt');

//
// Start the prompt
//
prompt.start();

//
// Get two properties from the user: username and email
//
prompt.get([
  {
    name: 'username',
    validator: /^[a-z]+$/,
    warning: 'Username should consist only lowercase alphabets',
    empty: false
  },
  {
    name: 'email',
    message: 'Email Address'
  }
], function (err, result) {
  //
  // Log the results.
  //
  console.log('Command-line input received:');
  console.log('  username: ' + result.username);
  console.log('  email: ' + result.email);
});
