(function() {
  var assert, vows, util;

  vows = require('vows');

  assert = require('assert');

  util = require('../../lib/util');

  vows.describe('Module core extension Array').addBatch({
    'Testing del': {
      topic: ['a', 'b', 'c'],
      'element exists': {
        'first element': function(topic) {
          return assert.deepEqual(util.array.del(topic, 'a'), ['b', 'c']);
        },
        'middle element': function(topic) {
          return assert.deepEqual(util.array.del(topic, 'b'), ['a', 'c']);
        },
        'last element': function(topic) {
          return assert.deepEqual(util.array.del(topic, 'c'), ['a', 'b']);
        }
      },
      'element does not exist': function(topic) {
        return assert.deepEqual(util.array.del(topic, 'd'), ['a', 'b', 'c']);
      }
    },
    'Testing utils': {
      topic: ['a', 'b', 'c'],
      'first': function(topic) {
        return assert.equal(util.array.first(topic), 'a');
      },
      'last': function(topic) {
        return assert.equal(util.array.last(topic), 'c');
      }
    }
  })["export"](module);

}).call(this);
