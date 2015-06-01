/*
 * add-properties.js: Example of how to add properties to an object using prompt.
 *
 * (C) 2010, Nodejitsu Inc.
 *
 */

var prompt = require('../lib/prompt');

//
// Start the prompt
//
prompt.start();

var obj = {
  password: 'lamepassword',
  mindset: 'NY'
}

//
// Log the initial object.
//
console.log('Initial object to be extended:');
console.dir(obj);

//
// Add two properties to the empty object: username and email
//
prompt.addProperties(obj, ['username', 'email'], function (err) {
  //
  // Log the results.
  //
  console.log('Updated object received:');
  console.dir(obj);
});