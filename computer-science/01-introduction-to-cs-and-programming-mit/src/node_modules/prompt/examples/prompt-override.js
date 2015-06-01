var prompt = require('../lib/prompt'),
    optimist;

try {
  optimist = require('optimist');
} catch (err) {
  throw new Error([
    'You need to install optimist before this example will work!',
    'Try: `npm install optimist`.'
  ].join('\n'));
}

//
// Set the overrides
//
prompt.override = optimist.argv

//
// Start the prompt
//
prompt.start();

//
// Get two properties from the user: username and email
//
prompt.get(['username', 'email'], function (err, result) {
  //
  // Log the results.
  //
  console.log('Command-line input received:');
  console.log('  username: ' + result.username);
  console.log('  email: ' + result.email);
  prompt.pause();
})

// $ node ./prompt-override.js --username USER --email EMAIL
