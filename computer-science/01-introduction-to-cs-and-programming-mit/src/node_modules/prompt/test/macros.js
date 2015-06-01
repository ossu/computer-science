/*
 * macros.js: Test macros for prompt.
 *
 * (C) 2010, Nodejitsu Inc.
 *
 */
 
var assert = require('assert'),
    helpers = require('./helpers'),
    prompt = require('../lib/prompt');

exports.shouldConfirm = function (options, mixin) {
  var message = options.response.toString().replace(/\n/g, ''),
      messages = ["When using prompt", "the confirm() method"],
      context = {},
      last = context;
      
  messages = messages.concat(options.messages || []);
  
  while (messages.length) {
    var text = messages.shift();
    last[text] = {};
    last = last[text]; 
  }
  
  last['responding with ' + message] = {
    topic: function () {
      if(!mixin)
        prompt.confirm(options.prop, this.callback);
      else
        prompt.confirm(options.prop, mixin, this.callback);

      if (!Array.isArray(options.response)) {
        helpers.stdin.writeNextTick(options.response + '\n');
      }
      else {
        helpers.stdin.writeSequence(options.response);
      }
    },
    "should respond with true" : function(err, result) {
      assert.isNull(err);
      assert.isTrue(result);
    }
  }
  
  return context;
};

exports.shouldNotConfirm = function (options) {
  var message = options.response.toString().replace(/\n/g, ''),
      messages = ["When using prompt", "the confirm() method"],
      context = {},
      last = context;
      
  messages = messages.concat(options.messages || []);
  
  while (messages.length) {
    var text = messages.shift();
    last[text] = {};
    last = last[text]; 
  }
  
  last['responding with ' + message] = {
    topic: function () {
      prompt.confirm(options.prop, this.callback);
      
      if (!Array.isArray(options.response)) {
        helpers.stdin.writeNextTick(options.response + '\n');
      }
      else {
        helpers.stdin.writeSequence(options.response);
      }
    },
    "should respond with false" : function(err, result) {
      assert.isNull(err);
      assert.isFalse(result);
    }
  };
  
  return context;
};

