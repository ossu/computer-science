(function() {
  var assert, vows, util;

  vows = require('vows');

  assert = require('assert');

  util = require('../../lib/util');

  vows.describe('Module core extension String').addBatch({
    'Testing value': {
      topic: 'bullet',
      'join the keys': function(topic) {
        return assert.equal(util.string.value(topic), 'bullet');
      }
    },
    'Testing gsub': {
      topic: 'bullet',
      'when no args': function(topic) {
        return assert.equal(util.string.gsub(topic), 'bullet');
      },
      'when only 1 arg': function(topic) {
        return assert.equal(util.string.gsub(topic, /./), 'bullet');
      },
      'when given proper args': function(topic) {
        return assert.equal(util.string.gsub(topic, /[aeiou]/, '*'), 'b*ll*t');
      },
      'when replacement is a function': {
        'with many groups': function(topic) {
          var str;
          str = util.string.gsub(topic, /([aeiou])(.)/, function($) {
            return "<" + $[1] + ">" + $[2];
          });
          return assert.equal(str, 'b<u>ll<e>t');
        },
        'with no groups': function(topic) {
          var str;
          str = util.string.gsub(topic, /[aeiou]/, function($) {
            return "<" + $[1] + ">";
          });
          return assert.equal(str, 'b<u>ll<e>t');
        }
      },
      'when replacement is special': {
        'with many groups': function(topic) {
          return assert.equal(util.string.gsub(topic, /([aeiou])(.)/, '<$1>$2'), 'b<u>ll<e>t');
        },
        'with no groups': function(topic) {
          return assert.equal(util.string.gsub(topic, /[aeiou]/, '<$1>'), 'b<u>ll<e>t');
        }
      }
    },
    'Testing capitalize': {
      topic: 'employee salary',
      'normal': function(topic) {
        return assert.equal(util.string.capitalize(topic), 'Employee Salary');
      }
    },
    'Testing upcase': {
      topic: 'bullet',
      'only first letter should be upcase': function(topic) {
        return assert.equal(util.string.upcase(topic), 'Bullet');
      },
      'letter after underscore': function(topic) {
        return assert.equal(util.string.upcase('bullet_record'), 'Bullet_Record');
      },
      'letter after slash': function(topic) {
        return assert.equal(util.string.upcase('bullet_record/errors'), 'Bullet_Record/Errors');
      },
      'no letter after space': function(topic) {
        return assert.equal(util.string.upcase('employee salary'), 'Employee salary');
      }
    },
    'Testing downcase': {
      topic: 'BULLET',
      'only first letter should be downcase': function(topic) {
        return assert.equal(util.string.downcase(topic), 'bULLET');
      },
      'letter after underscore': function(topic) {
        return assert.equal(util.string.downcase('BULLET_RECORD'), 'bULLET_rECORD');
      },
      'letter after slash': function(topic) {
        return assert.equal(util.string.downcase('BULLET_RECORD/ERRORS'), 'bULLET_rECORD/eRRORS');
      }
    }
  })["export"](module);

}).call(this);
