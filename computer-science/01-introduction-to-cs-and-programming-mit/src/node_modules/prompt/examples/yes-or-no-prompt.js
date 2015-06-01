/*
 * yes-or-no-prompt.js: Simple example of using prompt.
 *
 * (C) 2012, Nodejitsu Inc.
 *
 */

var prompt = require('../lib/prompt');

//
// Start the prompt
//
prompt.start();

var property = {
  name: 'yesno',
  message: 'are you sure?',
  validator: /y[es]*|n[o]?/,
  warning: 'Must respond yes or no',
  default: 'no'
};

//
// Get the simple yes or no property
//
prompt.get(property, function (err, result) {
  //
  // Log the results.
  //
  console.log('Command-line input received:');
  console.log('  result: ' + result.yesno);
});