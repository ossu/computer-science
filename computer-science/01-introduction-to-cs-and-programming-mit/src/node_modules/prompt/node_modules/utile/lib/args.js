/*
 * args.js: function argument parsing helper utility
 *
 * (C) 2012, Nodejitsu Inc.
 * MIT LICENSE
 *
 */

var utile = require('./index');

//
// ### function args(_args)
// #### _args {Arguments} Original function arguments
//
// Top-level method will accept a javascript "arguments" object (the actual keyword
// "arguments" inside any scope), and attempt to return back an intelligent object
// representing the functions arguments
//
module.exports = function (_args) {
  var args = utile.rargs(_args),
      _cb;

  //
  // Find and define the first argument
  //
  Object.defineProperty(args, 'first', { value: args[0] });

  //
  // Find and define any callback
  //
  _cb = args[args.length - 1] || args[args.length];
  if (typeof _cb === "function") {
    Object.defineProperty(args, 'callback', { value: _cb });
    Object.defineProperty(args, 'cb', { value: _cb });
    args.pop();
  }

  //
  // Find and define the last argument
  //
  if (args.length) {
    Object.defineProperty(args, 'last', { value: args[args.length - 1] });
  }

  return args;
};
