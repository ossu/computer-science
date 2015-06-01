/*
 * prompt.js: Simple prompt for prompting information from the command line
 *
 * (C) 2010, Nodejitsu Inc.
 *
 */

var events = require('events'),
    readline = require('readline'),
    utile = require('utile'),
    async = utile.async,
    read = require('read'),
    validate = require('revalidator').validate,
    winston = require('winston');

//
// Monkey-punch readline.Interface to work-around
// https://github.com/joyent/node/issues/3860
//
readline.Interface.prototype.setPrompt = function(prompt, length) {
  this._prompt = prompt;
  if (length) {
    this._promptLength = length;
  } else {
    var lines = prompt.split(/[\r\n]/);
    var lastLine = lines[lines.length - 1];
    this._promptLength = lastLine.replace(/\u001b\[(\d+(;\d+)*)?m/g, '').length;
  }
};

//
// Expose version using `pkginfo`
//
require('pkginfo')(module, 'version');

var stdin, stdout, history = [];
var prompt = module.exports = Object.create(events.EventEmitter.prototype);
var logger = prompt.logger = new winston.Logger({
  transports: [new (winston.transports.Console)()]
});

prompt.started    = false;
prompt.paused     = false;
prompt.allowEmpty = false;
prompt.message    = 'prompt';
prompt.delimiter  = ': ';
prompt.colors     = true;

//
// Create an empty object for the properties
// known to `prompt`
//
prompt.properties = {};

//
// Setup the default winston logger to use
// the `cli` levels and colors.
//
logger.cli();

//
// ### function start (options)
// #### @options {Object} **Optional** Options to consume by prompt
// Starts the prompt by listening to the appropriate events on `options.stdin`
// and `options.stdout`. If no streams are supplied, then `process.stdin`
// and `process.stdout` are used, respectively.
//
prompt.start = function (options) {
  if (prompt.started) {
    return;
  }

  options = options        || {};
  stdin   = options.stdin  || process.stdin;
  stdout  = options.stdout || process.stdout;

  //
  // By default: Remember the last `10` prompt property /
  // answer pairs and don't allow empty responses globally.
  //
  prompt.memory     = options.memory     || 10;
  prompt.allowEmpty = options.allowEmpty || false;
  prompt.message    = options.message    || prompt.message;
  prompt.delimiter  = options.delimiter  || prompt.delimiter;
  prompt.colors     = options.colors     || prompt.colors;

  if (process.platform !== 'win32') {
    // windows falls apart trying to deal with SIGINT
    process.on('SIGINT', function () {
      stdout.write('\n');
      process.exit(1);
    });
  }

  prompt.emit('start');
  prompt.started = true;
  return prompt;
};

//
// ### function pause ()
// Pauses input coming in from stdin
//
prompt.pause = function () {
  if (!prompt.started || prompt.paused) {
    return;
  }

  stdin.pause();
  prompt.emit('pause');
  prompt.paused = true;
  return prompt;
};

//
// ### function resume ()
// Resumes input coming in from stdin
//
prompt.resume = function () {
  if (!prompt.started || !prompt.paused) {
    return;
  }

  stdin.resume();
  prompt.emit('resume');
  prompt.paused = false;
  return prompt;
};

//
// ### function history (search)
// #### @search {Number|string} Index or property name to find.
// Returns the `property:value` pair from within the prompts
// `history` array.
//
prompt.history = function (search) {
  if (typeof search === 'number') {
    return history[search] || {};
  }

  var names = history.map(function (pair) {
    return typeof pair.property === 'string'
      ? pair.property
      : pair.property.name;
  });

  if (!~names.indexOf(search)) {
    return null;
  }

  return history.filter(function (pair) {
    return typeof pair.property === 'string'
      ? pair.property === search
      : pair.property.name === search;
  })[0];
};

//
// ### function get (schema, callback)
// #### @schema {Array|Object|string} Set of variables to get input for.
// #### @callback {function} Continuation to pass control to when complete.
// Gets input from the user via stdin for the specified message(s) `msg`.
//
prompt.get = function (schema, callback) {
  //
  // Transforms a full JSON-schema into an array describing path and sub-schemas.
  // Used for iteration purposes.
  //
  function untangle(schema, path) {
    var results = [];
    path = path || [];

    if (schema.properties) {
      //
      // Iterate over the properties in the schema and use recursion
      // to process sub-properties.
      //
      Object.keys(schema.properties).forEach(function (key) {
        var obj = {};
        obj[key] = schema.properties[key];

        //
        // Concat a sub-untangling to the results.
        //
        results = results.concat(untangle(obj[key], path.concat(key)));
      });

      // Return the results.
      return results;
    }

    //
    // This is a schema "leaf".
    //
    return {
      path: path,
      schema: schema
    };
  }

  //
  // Iterate over the values in the schema, represented as
  // a legit single-property object subschemas. Accepts `schema`
  // of the forms:
  //
  //    'prop-name'
  //
  //    ['string-name', { path: ['or-well-formed-subschema'], properties: ... }]
  //
  //    { path: ['or-well-formed-subschema'], properties: ... ] }
  //
  //    { properties: { 'schema-with-no-path' } }
  //
  // And transforms them all into
  //
  //    { path: ['path', 'to', 'property'], properties: { path: { to: ...} } }
  //
  function iterate(schema, get, done) {
    var iterator = [],
        result = {};

    if (typeof schema === 'string') {
      //
      // We can iterate over a single string.
      //
      iterator.push({
        path: [schema],
        schema: prompt.properties[schema.toLowerCase()] || {}
      });
    }
    else if (Array.isArray(schema)) {
      //
      // An array of strings and/or single-prop schema and/or no-prop schema.
      //
      iterator = schema.map(function (element) {
        if (typeof element === 'string') {
          return {
            path: [element],
            schema: prompt.properties[element.toLowerCase()] || {}
          };
        }
        else if (element.properties) {
          return {
            path: [Object.keys(element.properties)[0]],
            schema: element.properties[Object.keys(element.properties)[0]]
          };
        }
        else if (element.path && element.schema) {
          return element;
        }
        else {
          return {
            path: [element.name || 'question'],
            schema: element
          };
        }
      });
    }
    else if (schema.properties) {
      //
      // Or a complete schema `untangle` it for use.
      //
      iterator = untangle(schema);
    }
    else {
      //
      // Or a partial schema and path.
      // TODO: Evaluate need for this option.
      //
      iterator = [{
        schema: schema.schema ? schema.schema : schema,
        path: schema.path || [schema.name || 'question']
      }];
    }

    //
    // Now, iterate and assemble the result.
    //
    async.forEachSeries(iterator, function (branch, next) {
      get(branch, function assembler(err, line) {
        if (err) {
          return next(err);
        }

        function build(path, line) {
          var obj = {};
          if (path.length) {
            obj[path[0]] = build(path.slice(1), line);
            return obj;
          }

          return line;
        }

        function attach(obj, attr) {
          var keys;
          if (typeof attr !== 'object' || attr instanceof Array) {
            return attr;
          }

          keys = Object.keys(attr);
          if (keys.length) {
            if (!obj[keys[0]]) {
              obj[keys[0]] = {};
            }
            obj[keys[0]] = attach(obj[keys[0]], attr[keys[0]]);
          }

          return obj;
        }

        result = attach(result, build(branch.path, line));
        next();
      });
    }, function (err) {
      return err ? done(err) : done(null, result);
    });
  }

  iterate(schema, function get(target, next) {
    prompt.getInput(target, function (err, line) {
      return err ? next(err) : next(null, line);
    });
  }, callback);

  return prompt;
};

//
// ### function confirm (msg, callback)
// #### @msg {Array|Object|string} set of message to confirm
// #### @callback {function} Continuation to pass control to when complete.
// Confirms a single or series of messages by prompting the user for a Y/N response.
// Returns `true` if ALL messages are answered in the affirmative, otherwise `false`
//
// `msg` can be a string, or object (or array of strings/objects).
// An object may have the following properties:
//
//    {
//      description: 'yes/no' // message to prompt user
//      pattern: /^[yntf]{1}/i // optional - regex defining acceptable responses
//      yes: /^[yt]{1}/i // optional - regex defining `affirmative` responses
//      message: 'yes/no' // optional - message to display for invalid responses
//    }
//
prompt.confirm = function (/* msg, options, callback */) {
  var args     = Array.prototype.slice.call(arguments),
      msg      = args.shift(),
      callback = args.pop(),
      opts     = args.shift(),
      vars     = !Array.isArray(msg) ? [msg] : msg,
      RX_Y     = /^[yt]{1}/i,
      RX_YN    = /^[yntf]{1}/i;

  function confirm(target, next) {
    var yes = target.yes || RX_Y,
      options = utile.mixin({
        description: typeof target === 'string' ? target : target.description||'yes/no',
        pattern: target.pattern || RX_YN,
        name: 'confirm',
        message: target.message || 'yes/no'
      }, opts || {});


    prompt.get([options], function (err, result) {
      next(err ? false : yes.test(result[options.name]));
    });
  }

  async.rejectSeries(vars, confirm, function(result) {
    callback(null, result.length===0);
  });
};


// Variables needed outside of getInput for multiline arrays.
var tmp = [];


// ### function getInput (prop, callback)
// #### @prop {Object|string} Variable to get input for.
// #### @callback {function} Continuation to pass control to when complete.
// Gets input from the user via stdin for the specified message `msg`.
//
prompt.getInput = function (prop, callback) {
  var schema = prop.schema || prop,
      propName = prop.path && prop.path.join(':') || prop,
      storedSchema = prompt.properties[propName.toLowerCase()],
      delim = prompt.delimiter,
      defaultLine,
      against,
      hidden,
      length,
      valid,
      name,
      raw,
      msg;

  //
  // If there is a stored schema for `propName` in `propmpt.properties`
  // then use it.
  //
  if (schema instanceof Object && !Object.keys(schema).length &&
    typeof storedSchema !== 'undefined') {
    schema = storedSchema;
  }

  //
  // Build a proper validation schema if we just have a string
  // and no `storedSchema`.
  //
  if (typeof prop === 'string' && !storedSchema) {
    schema = {};
  }

  schema = convert(schema);
  defaultLine = schema.default;
  name = prop.description || schema.description || propName;
  raw = prompt.colors
    ? [prompt.message, delim + name.grey, delim.grey]
    : [prompt.message, delim + name, delim];

  prop = {
    schema: schema,
    path: propName.split(':')
  };

  //
  // If the schema has no `properties` value then set
  // it to an object containing the current schema
  // for `propName`.
  //
  if (!schema.properties) {
    schema = (function () {
      var obj = { properties: {} };
      obj.properties[propName] = schema;
      return obj;
    })();
  }

  //
  // Handle overrides here.
  // TODO: Make overrides nestable
  //
  if (prompt.override && prompt.override[propName]) {
    if (prompt._performValidation(name, prop, prompt.override, schema, -1, callback)) {
      return callback(null, prompt.override[propName]);
    }

    delete prompt.override[propName];
  }

  var type = (schema.properties && schema.properties[propName] &&
              schema.properties[propName].type || '').toLowerCase().trim(),
      wait = type === 'array';

  if (type === 'array') {
    length = prop.schema.maxItems;
    if (length) {
      msg = (tmp.length + 1).toString() + '/' + length.toString();
    }
    else {
      msg = (tmp.length + 1).toString();
    }
    msg += delim;
    raw.push(prompt.colors ? msg.grey : msg);
  }

  //
  // Calculate the raw length and colorize the prompt
  //
  length = raw.join('').length;
  raw[0] = raw[0];
  msg = raw.join('');

  if (schema.help) {
    schema.help.forEach(function (line) {
      logger.help(line);
    });
  }

  //
  // Emit a "prompting" event
  //
  prompt.emit('prompt', prop);

  //
  // If there is no default line, set it to an empty string
  //
  if(typeof defaultLine === 'undefined') {
    defaultLine = '';
  }

  //
  // set to string for readline ( will not accept Numbers )
  //
  defaultLine = defaultLine.toString();

  //
  // Make the actual read
  //
  read({
    prompt: msg,
    silent: prop.schema && prop.schema.hidden,
    default: defaultLine,
    input: stdin,
    output: stdout
  }, function (err, line) {
    if (err && wait === false) {
      return callback(err);
    }

    var against = {},
        numericInput,
        isValid;

    if (line !== '') {

      if (schema.properties[propName]) {
        var type = (schema.properties[propName].type || '').toLowerCase().trim() || undefined;

        //
        // Attempt to parse input as a float if the schema expects a number.
        //
        if (type == 'number') {
          numericInput = parseFloat(line, 10);
          if (!isNaN(numericInput)) {
            line = numericInput;
          }
        }

        //
        // Attempt to parse input as a boolean if the schema expects a boolean
        //
        if (type == 'boolean') {
          if(line === "true") {
            line = true;
          }
          if(line === "false") {
            line = false;
          }
        }

        //
        // If the type is an array, wait for the end. Fixes #54
        //
        if (type == 'array') {
          var length = prop.schema.maxItems;
          if (err) {
            if (err.message == 'canceled') {
              wait = false;
              stdout.write('\n');
            }
          }
          else {
            if (length) {
              if (tmp.length + 1 < length) {
                isValid = false;
                wait = true;
              }
              else {
                isValid = true;
                wait = false;
              }
            }
            else {
              isValid = false;
              wait = true;
            }
            tmp.push(line);
          }
          line = tmp;
        }
      }

      against[propName] = line;
    }

    if (prop && prop.schema.before) {
      line = prop.schema.before(line);
    }

    // Validate
    if (isValid === undefined) isValid = prompt._performValidation(name, prop, against, schema, line, callback);

    if (!isValid) {
      return prompt.getInput(prop, callback);
    }

    //
    // Log the resulting line, append this `property:value`
    // pair to the history for `prompt` and respond to
    // the callback.
    //
    logger.input(line.yellow);
    prompt._remember(propName, line);
    callback(null, line);

    // Make sure `tmp` is emptied
    tmp = [];
  });
};

//
// ### function performValidation (name, prop, against, schema, line, callback)
// #### @name {Object} Variable name
// #### @prop {Object|string} Variable to get input for.
// #### @against {Object} Input
// #### @schema {Object} Validation schema
// #### @line {String|Boolean} Input line
// #### @callback {function} Continuation to pass control to when complete.
// Perfoms user input validation, print errors if needed and returns value according to validation
//
prompt._performValidation = function (name, prop, against, schema, line, callback) {
  var numericInput, valid, msg;

  try {
    valid = validate(against, schema);
  }
  catch (err) {
    return (line !== -1) ? callback(err) : false;
  }

  if (!valid.valid) {
    msg = line !== -1 ? 'Invalid input for ' : 'Invalid command-line input for ';

    if (prompt.colors) {
      logger.error(msg + name.grey);
    }
    else {
      logger.error(msg + name);
    }

    if (prop.schema.message) {
      logger.error(prop.schema.message);
    }

    prompt.emit('invalid', prop, line);
  }

  return valid.valid;
};

//
// ### function addProperties (obj, properties, callback)
// #### @obj {Object} Object to add properties to
// #### @properties {Array} List of properties to get values for
// #### @callback {function} Continuation to pass control to when complete.
// Prompts the user for values each of the `properties` if `obj` does not already
// have a value for the property. Responds with the modified object.
//
prompt.addProperties = function (obj, properties, callback) {
  properties = properties.filter(function (prop) {
    return typeof obj[prop] === 'undefined';
  });

  if (properties.length === 0) {
    return callback(obj);
  }

  prompt.get(properties, function (err, results) {
    if (err) {
      return callback(err);
    }
    else if (!results) {
      return callback(null, obj);
    }

    function putNested (obj, path, value) {
      var last = obj, key;

      while (path.length > 1) {
        key = path.shift();
        if (!last[key]) {
          last[key] = {};
        }

        last = last[key];
      }

      last[path.shift()] = value;
    }

    Object.keys(results).forEach(function (key) {
      putNested(obj, key.split('.'), results[key]);
    });

    callback(null, obj);
  });

  return prompt;
};

//
// ### @private function _remember (property, value)
// #### @property {Object|string} Property that the value is in response to.
// #### @value {string} User input captured by `prompt`.
// Prepends the `property:value` pair into the private `history` Array
// for `prompt` so that it can be accessed later.
//
prompt._remember = function (property, value) {
  history.unshift({
    property: property,
    value: value
  });

  //
  // If the length of the `history` Array
  // has exceeded the specified length to remember,
  // `prompt.memory`, truncate it.
  //
  if (history.length > prompt.memory) {
    history.splice(prompt.memory, history.length - prompt.memory);
  }
};

//
// ### @private function convert (schema)
// #### @schema {Object} Schema for a property
// Converts the schema into new format if it is in old format
//
function convert(schema) {
  var newProps = Object.keys(validate.messages),
      newSchema = false,
      key;

  newProps = newProps.concat(['description', 'dependencies']);

  for (key in schema) {
    if (newProps.indexOf(key) > 0) {
      newSchema = true;
      break;
    }
  }

  if (!newSchema || schema.validator || schema.warning || typeof schema.empty !== 'undefined') {
    schema.description = schema.message;
    schema.message = schema.warning;

    if (typeof schema.validator === 'function') {
      schema.conform = schema.validator;
    } else {
      schema.pattern = schema.validator;
    }

    if (typeof schema.empty !== 'undefined') {
      schema.required = !(schema.empty);
    }

    delete schema.warning;
    delete schema.validator;
    delete schema.empty;
  }

  return schema;
}
