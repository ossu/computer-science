# prompt [![Build Status](https://secure.travis-ci.org/flatiron/prompt.svg)](http://travis-ci.org/flatiron/prompt)

A beautiful command-line prompt for node.js

## Features

* prompts the user for input
* supports validation and defaults
* hides passwords

## Usage
Using prompt is relatively straight forward. There are two core methods you should be aware of: `prompt.get()` and `prompt.addProperties()`. There methods take strings representing property names in addition to objects for complex property validation (and more). There are a number of [examples][0] that you should examine for detailed usage.

### Getting Basic Prompt Information
Getting started with `prompt` is easy. Lets take a look at `examples/simple-prompt.js`:

``` js
  var prompt = require('prompt');

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
  });
```

This will result in the following command-line output:

```
  $ node examples/simple-prompt.js
  prompt: username: some-user
  prompt: email: some-user@some-place.org
  Command-line input received:
    username: some-user
    email: some-user@some-place.org
```

### Prompting with Validation, Default Values, and More (Complex Properties)
In addition to prompting the user with simple string prompts, there is a robust API for getting and validating complex information from a command-line prompt. Here's a quick sample:

``` js
  var schema = {
    properties: {
      name: {
        pattern: /^[a-zA-Z\s\-]+$/,
        message: 'Name must be only letters, spaces, or dashes',
        required: true
      },
      password: {
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
    console.log('  password: ' + result.password);
  });
```

Pretty easy right? The output from the above script is:

```
  $ node examples/property-prompt.js
  prompt: name: nodejitsu000
  error:  Invalid input for name
  error:  Name must be only letters, spaces, or dashes
  prompt: name: Nodejitsu Inc
  prompt: password:
  Command-line input received:
    name: Nodejitsu Inc
    password: some-password
```

## Valid Property Settings
`prompt` understands JSON-schema with a few extra parameters and uses [revalidator](https://github.com/flatiron/revalidator) for validation.

Here's an overview of the properties that may be used for validation and prompting controls:

``` js
  {
    description: 'Enter your password',     // Prompt displayed to the user. If not supplied name will be used.
    type: 'string',                 // Specify the type of input to expect.
    pattern: /^\w+$/,                  // Regular expression that input must be valid against.
    message: 'Password must be letters', // Warning message to display if validation fails.
    hidden: true,                        // If true, characters entered will not be output to console.
    default: 'lamepassword',             // Default value to use if no value is entered.
    required: true                        // If true, value entered must be non-empty.
    before: function(value) { return 'v' + value; } // Runs before node-prompt callbacks. It modifies user's input
  }
```

Alternatives to `pattern` include `format` and `conform`, as documented in [revalidator](https://github.com/flatiron/revalidator).

Using `type: 'array'` has some special cases.

- `description` will not work in the schema if `type: 'array'` is defined.
- `maxItems` takes precedence over `minItems`.
- Arrays that do not have `maxItems` defined will require users to `SIGINT` (`^C`) before the array is ended.
- If `SIGINT` (`^C`) is triggered before `minItems` is met, a validation error will appear. This will require users to `SIGEOF` (`^D`) to end the input.

For more information on things such as `maxItems` and `minItems`, refer to the [revalidator](https://github.com/flatiron/revalidator) repository.

### Alternate Validation API:

Prompt, in addition to iterating over JSON-Schema properties, will also happily iterate over an array of validation objects given an extra 'name' property:

```js
  var prompt = require('../lib/prompt');

  //
  // Start the prompt
  //
  prompt.start();

  //
  // Get two properties from the user: username and password
  //
  prompt.get([{
      name: 'username',
      required: true
    }, {
      name: 'password',
      hidden: true,
      conform: function (value) {
        return true;
      }
    }], function (err, result) {
    //
    // Log the results.
    //
    console.log('Command-line input received:');
    console.log('  username: ' + result.username);
    console.log('  password: ' + result.password);
  });
```

### Backward Compatibility

Note that, while this structure is similar to that used by prompt 0.1.x, that the object properties use the same names as in JSON-Schema. prompt 0.2.x is backward compatible with prompt 0.1.x except for asynchronous validation.

### Skipping Prompts

Sometimes power users may wish to skip promts and specify all data as command line options.
if a value is set as a property of `prompt.override` prompt will use that instead of
prompting the user.

``` js
  //prompt-override.js

  var prompt = require('prompt'),
      optimist = require('optimist')

  //
  // set the overrides
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
  })

  //: node prompt-override.js --username USER --email EMAIL
```


### Adding Properties to an Object
A common use-case for prompting users for data from the command-line is to extend or create a configuration object that is passed onto the entry-point method for your CLI tool. `prompt` exposes a convenience method for doing just this:

``` js
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
```

### Prompt history
You can use the `prompt.history()` method to get access to previous prompt input.

``` js
  prompt.get([{
    name: 'name',
    description: 'Your name',
    type: 'string',
    required: true
  }, {
    name: 'surname',
    description: 'Your surname',
    type: 'string',
    required: true,
    message: 'Please dont use the demo credentials',
    conform: function(surname) {
      var name = prompt.history('name').value;
      return (name !== 'John' || surname !== 'Smith');
    }
  }], function(err, results) {
    console.log(results);
  });
```

## Customizing your prompt
Aside from changing `property.message`, you can also change `prompt.message`
and `prompt.delimiter` to change the appearance of your prompt.

The basic structure of a prompt is this:

``` js
prompt.message + prompt.delimiter + property.message + prompt.delimiter;
```

The default `prompt.message` is "prompt," the default `prompt.delimiter` is
": ", and the default `property.message` is `property.name`.
Changing these allows you to customize the appearance of your prompts! In
addition, prompt supports ANSI color codes via the
[colors module](https://github.com/Marak/colors.js) for custom colors. For a
very colorful example:

``` js
  var prompt = require("prompt");

  //
  // Setting these properties customizes the prompt.
  //
  prompt.message = "Question!".rainbow;
  prompt.delimiter = "><".green;

  prompt.start();

  prompt.get({
    properties: {
      name: {
        description: "What is your name?".magenta
      }
    }
  }, function (err, result) {
    console.log("You said your name is: ".cyan + result.name.cyan);
  });
```

If you don't want colors, you can set

```js
var prompt = require('prompt');

prompt.colors = false;
```

## Installation

``` bash
  $ [sudo] npm install prompt
```

## Running tests

``` bash
  $ npm test
```

#### License: MIT
#### Author: [Charlie Robbins](http://github.com/indexzero)
#### Contributors: [Josh Holbrook](http://github.com/jesusabdullah), [Pavan Kumar Sunkara](http://github.com/pksunkara)

[0]: https://github.com/flatiron/prompt/tree/master/examples
