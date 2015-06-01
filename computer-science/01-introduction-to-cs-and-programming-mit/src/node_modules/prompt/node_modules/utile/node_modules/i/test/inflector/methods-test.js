(function() {
  var assert, cases, vows, util;

  vows = require('vows');

  assert = require('assert');

  util = require('../../lib/util');

  cases = require('./cases');

  vows.describe('Module Inflector methods').addBatch({
    'Test inflector method': {
      topic: require('../../lib/methods'),
      'camelize': {
        'word': function(topic) {
          var i, words, _i, _len, _ref, _results;
          words = cases.CamelToUnderscore;
          _ref = Object.keys(words);
          _results = [];
          for (_i = 0, _len = _ref.length; _i < _len; _i++) {
            i = _ref[_i];
            _results.push(assert.equal(topic.camelize(words[i]), i));
          }
          return _results;
        },
        'word with first letter lower': function(topic) {
          var i, words, _i, _len, _ref, _results;
          words = cases.UnderscoreToLowerCamel;
          _ref = Object.keys(words);
          _results = [];
          for (_i = 0, _len = _ref.length; _i < _len; _i++) {
            i = _ref[_i];
            _results.push(assert.equal(topic.camelize(i, false), words[i]));
          }
          return _results;
        },
        'path': function(topic) {
          var i, words, _i, _len, _ref, _results;
          words = cases.CamelWithModuleToUnderscoreWithSlash;
          _ref = Object.keys(words);
          _results = [];
          for (_i = 0, _len = _ref.length; _i < _len; _i++) {
            i = _ref[_i];
            _results.push(assert.equal(topic.camelize(words[i]), i));
          }
          return _results;
        },
        'path with first letter lower': function(topic) {
          return assert.equal(topic.camelize('bullet_record/errors', false), 'bulletRecord.Errors');
        }
      },
      'underscore': {
        'word': function(topic) {
          var i, words, _i, _j, _len, _len2, _ref, _ref2, _results;
          words = cases.CamelToUnderscore;
          _ref = Object.keys(words);
          for (_i = 0, _len = _ref.length; _i < _len; _i++) {
            i = _ref[_i];
            assert.equal(topic.underscore(i), words[i]);
          }
          words = cases.CamelToUnderscoreWithoutReverse;
          _ref2 = Object.keys(words);
          _results = [];
          for (_j = 0, _len2 = _ref2.length; _j < _len2; _j++) {
            i = _ref2[_j];
            _results.push(assert.equal(topic.underscore(i), words[i]));
          }
          return _results;
        },
        'path': function(topic) {
          var i, words, _i, _len, _ref, _results;
          words = cases.CamelWithModuleToUnderscoreWithSlash;
          _ref = Object.keys(words);
          _results = [];
          for (_i = 0, _len = _ref.length; _i < _len; _i++) {
            i = _ref[_i];
            _results.push(assert.equal(topic.underscore(i), words[i]));
          }
          return _results;
        },
        'from dasherize': function(topic) {
          var i, words, _i, _len, _ref, _results;
          words = cases.UnderscoresToDashes;
          _ref = Object.keys(words);
          _results = [];
          for (_i = 0, _len = _ref.length; _i < _len; _i++) {
            i = _ref[_i];
            _results.push(assert.equal(topic.underscore(topic.dasherize(i)), i));
          }
          return _results;
        }
      },
      'dasherize': {
        'underscored_word': function(topic) {
          var i, words, _i, _len, _ref, _results;
          words = cases.UnderscoresToDashes;
          _ref = Object.keys(words);
          _results = [];
          for (_i = 0, _len = _ref.length; _i < _len; _i++) {
            i = _ref[_i];
            _results.push(assert.equal(topic.dasherize(i), words[i]));
          }
          return _results;
        }
      },
      'demodulize': {
        'module name': function(topic) {
          return assert.equal(topic.demodulize('BulletRecord.CoreExtensions.Inflections'), 'Inflections');
        },
        'isolated module name': function(topic) {
          return assert.equal(topic.demodulize('Inflections'), 'Inflections');
        }
      },
      'foreign_key': {
        'normal': function(topic) {
          var i, words, _i, _len, _ref, _results;
          words = cases.ClassNameToForeignKeyWithoutUnderscore;
          _ref = Object.keys(words);
          _results = [];
          for (_i = 0, _len = _ref.length; _i < _len; _i++) {
            i = _ref[_i];
            _results.push(assert.equal(topic.foreign_key(i, false), words[i]));
          }
          return _results;
        },
        'with_underscore': function(topic) {
          var i, words, _i, _len, _ref, _results;
          words = cases.ClassNameToForeignKeyWithUnderscore;
          _ref = Object.keys(words);
          _results = [];
          for (_i = 0, _len = _ref.length; _i < _len; _i++) {
            i = _ref[_i];
            _results.push(assert.equal(topic.foreign_key(i), words[i]));
          }
          return _results;
        }
      },
      'ordinalize': function(topic) {
        var i, words, _i, _len, _ref, _results;
        words = cases.OrdinalNumbers;
        _ref = Object.keys(words);
        _results = [];
        for (_i = 0, _len = _ref.length; _i < _len; _i++) {
          i = _ref[_i];
          _results.push(assert.equal(topic.ordinalize(i), words[i]));
        }
        return _results;
      }
    }
  }).addBatch({
    'Test inflector inflection methods': {
      topic: function() {
        var Inflector;
        Inflector = require('../../lib/methods');
        Inflector.inflections["default"]();
        return Inflector;
      },
      'pluralize': {
        'empty': function(topic) {
          return assert.equal(topic.pluralize(''), '');
        },
        'uncountable': function(topic) {
          return assert.equal(topic.pluralize('money'), 'money');
        },
        'normal': function(topic) {
          topic.inflections.irregular('octopus', 'octopi');
          return assert.equal(topic.pluralize('octopus'), 'octopi');
        },
        'cases': function(topic) {
          var i, words, _i, _j, _len, _len2, _ref, _ref2, _results;
          words = cases.SingularToPlural;
          _ref = Object.keys(words);
          for (_i = 0, _len = _ref.length; _i < _len; _i++) {
            i = _ref[_i];
            assert.equal(topic.pluralize(i), words[i]);
          }
          _ref2 = Object.keys(words);
          _results = [];
          for (_j = 0, _len2 = _ref2.length; _j < _len2; _j++) {
            i = _ref2[_j];
            _results.push(assert.equal(topic.pluralize(util.string.capitalize(i)), util.string.capitalize(words[i])));
          }
          return _results;
        },
        'cases plural': function(topic) {
          var i, words, _i, _j, _len, _len2, _ref, _ref2, _results;
          words = cases.SingularToPlural;
          _ref = Object.keys(words);
          for (_i = 0, _len = _ref.length; _i < _len; _i++) {
            i = _ref[_i];
            assert.equal(topic.pluralize(words[i]), words[i]);
          }
          _ref2 = Object.keys(words);
          _results = [];
          for (_j = 0, _len2 = _ref2.length; _j < _len2; _j++) {
            i = _ref2[_j];
            _results.push(assert.equal(topic.pluralize(util.string.capitalize(words[i])), util.string.capitalize(words[i])));
          }
          return _results;
        }
      },
      'singuralize': {
        'empty': function(topic) {
          return assert.equal(topic.singularize(''), '');
        },
        'uncountable': function(topic) {
          return assert.equal(topic.singularize('money'), 'money');
        },
        'normal': function(topic) {
          topic.inflections.irregular('octopus', 'octopi');
          return assert.equal(topic.singularize('octopi'), 'octopus');
        },
        'cases': function(topic) {
          var i, words, _i, _j, _len, _len2, _ref, _ref2, _results;
          words = cases.SingularToPlural;
          _ref = Object.keys(words);
          for (_i = 0, _len = _ref.length; _i < _len; _i++) {
            i = _ref[_i];
            assert.equal(topic.singularize(words[i]), i);
          }
          _ref2 = Object.keys(words);
          _results = [];
          for (_j = 0, _len2 = _ref2.length; _j < _len2; _j++) {
            i = _ref2[_j];
            _results.push(assert.equal(topic.singularize(util.string.capitalize(words[i])), util.string.capitalize(i)));
          }
          return _results;
        }
      },
      'uncountablility': {
        'normal': function(topic) {
          var i, words, _i, _j, _k, _len, _len2, _len3, _results;
          words = topic.inflections.uncountables;
          for (_i = 0, _len = words.length; _i < _len; _i++) {
            i = words[_i];
            assert.equal(topic.singularize(i), i);
          }
          for (_j = 0, _len2 = words.length; _j < _len2; _j++) {
            i = words[_j];
            assert.equal(topic.pluralize(i), i);
          }
          _results = [];
          for (_k = 0, _len3 = words.length; _k < _len3; _k++) {
            i = words[_k];
            _results.push(assert.equal(topic.singularize(i), topic.pluralize(i)));
          }
          return _results;
        },
        'greedy': function(topic) {
          var countable_word, uncountable_word;
          uncountable_word = "ors";
          countable_word = "sponsor";
          topic.inflections.uncountable(uncountable_word);
          assert.equal(topic.singularize(uncountable_word), uncountable_word);
          assert.equal(topic.pluralize(uncountable_word), uncountable_word);
          assert.equal(topic.pluralize(uncountable_word), topic.singularize(uncountable_word));
          assert.equal(topic.singularize(countable_word), 'sponsor');
          assert.equal(topic.pluralize(countable_word), 'sponsors');
          return assert.equal(topic.singularize(topic.pluralize(countable_word)), 'sponsor');
        }
      },
      'humanize': {
        'normal': function(topic) {
          var i, words, _i, _len, _ref, _results;
          words = cases.UnderscoreToHuman;
          _ref = Object.keys(words);
          _results = [];
          for (_i = 0, _len = _ref.length; _i < _len; _i++) {
            i = _ref[_i];
            _results.push(assert.equal(topic.humanize(i), words[i]));
          }
          return _results;
        },
        'with rule': function(topic) {
          topic.inflections.human(/^(.*)_cnt$/i, '$1_count');
          topic.inflections.human(/^prefix_(.*)$/i, '$1');
          assert.equal(topic.humanize('jargon_cnt'), 'Jargon count');
          return assert.equal(topic.humanize('prefix_request'), 'Request');
        },
        'with string': function(topic) {
          topic.inflections.human('col_rpted_bugs', 'Reported bugs');
          assert.equal(topic.humanize('col_rpted_bugs'), 'Reported bugs');
          return assert.equal(topic.humanize('COL_rpted_bugs'), 'Col rpted bugs');
        },
        'with _id': function(topic) {
          return assert.equal(topic.humanize('author_id'), 'Author');
        }
      },
      'titleize': {
        'normal': function(topic) {
          var i, words, _i, _len, _ref, _results;
          words = cases.MixtureToTitleCase;
          _ref = Object.keys(words);
          _results = [];
          for (_i = 0, _len = _ref.length; _i < _len; _i++) {
            i = _ref[_i];
            _results.push(assert.equal(topic.titleize(i), words[i]));
          }
          return _results;
        },
        'with hyphens': function(topic) {
          return assert.equal(topic.titleize('x-men: the last stand'), 'X Men: The Last Stand');
        }
      },
      'tableize': function(topic) {
        var i, words, _i, _len, _ref, _results;
        words = cases.ClassNameToTableName;
        _ref = Object.keys(words);
        _results = [];
        for (_i = 0, _len = _ref.length; _i < _len; _i++) {
          i = _ref[_i];
          _results.push(assert.equal(topic.tableize(i), words[i]));
        }
        return _results;
      },
      'classify': {
        'underscore': function(topic) {
          var i, words, _i, _j, _len, _len2, _ref, _ref2, _results;
          words = cases.ClassNameToTableName;
          _ref = Object.keys(words);
          for (_i = 0, _len = _ref.length; _i < _len; _i++) {
            i = _ref[_i];
            assert.equal(topic.classify(words[i]), i);
          }
          _ref2 = Object.keys(words);
          _results = [];
          for (_j = 0, _len2 = _ref2.length; _j < _len2; _j++) {
            i = _ref2[_j];
            _results.push(assert.equal(topic.classify('table_prefix.' + words[i]), i));
          }
          return _results;
        },
        'normal': function(topic) {
          topic.inflections.irregular('octopus', 'octopi');
          return assert.equal(topic.classify('octopi'), 'Octopus');
        }
      }
    }
  })["export"](module);

}).call(this);
