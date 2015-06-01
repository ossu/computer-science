/*
 * property-prompt.js: Example of using prompt with complex properties.
 *
 * (C) 2010, Nodejitsu Inc.
 *
 */

var prompt = require('../lib/prompt');

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
    },
    password: {
      required: true,
      hidden: true
    }
  }
};

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
  console.log('  password: ' + result.password);
});
