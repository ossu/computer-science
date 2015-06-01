(function() {
  var assert, vows;

  vows = require('vows');

  assert = require('assert');

  vows.describe('Module Inflector inflections').addBatch({
    'Test inflector inflections': {
      topic: require('../../lib/inflections'),
      'clear': {
        'single': function(topic) {
          topic.uncountables = [1, 2, 3];
          topic.humans = [1, 2, 3];
          topic.clear('uncountables');
          assert.isEmpty(topic.uncountables);
          return assert.deepEqual(topic.humans, [1, 2, 3]);
        },
        'all': function(topic) {
          assert.deepEqual(topic.humans, [1, 2, 3]);
          topic.uncountables = [1, 2, 3];
          topic.clear();
          assert.isEmpty(topic.uncountables);
          return assert.isEmpty(topic.humans);
        }
      },
      'uncountable': {
        'one item': function(topic) {
          topic.clear();
          assert.isEmpty(topic.uncountables);
          topic.uncountable('money');
          return assert.deepEqual(topic.uncountables, ['money']);
        },
        'many items': function(topic) {
          topic.clear();
          assert.isEmpty(topic.uncountables);
          topic.uncountable(['money', 'rice']);
          return assert.deepEqual(topic.uncountables, ['money', 'rice']);
        }
      },
      'human': function(topic) {
        topic.clear();
        assert.isEmpty(topic.humans);
        topic.human("legacy_col_person_name", "Name");
        return assert.deepEqual(topic.humans, [["legacy_col_person_name", "Name"]]);
      },
      'plural': function(topic) {
        topic.clear();
        assert.isEmpty(topic.plurals);
        topic.plural('ox', 'oxen');
        assert.deepEqual(topic.plurals, [['ox', 'oxen']]);
        topic.uncountable('money');
        assert.deepEqual(topic.uncountables, ['money']);
        topic.uncountable('monies');
        topic.plural('money', 'monies');
        assert.deepEqual(topic.plurals, [['money', 'monies'], ['ox', 'oxen']]);
        return assert.isEmpty(topic.uncountables);
      },
      'singular': function(topic) {
        topic.clear();
        assert.isEmpty(topic.singulars);
        topic.singular('ox', 'oxen');
        assert.deepEqual(topic.singulars, [['ox', 'oxen']]);
        topic.uncountable('money');
        assert.deepEqual(topic.uncountables, ['money']);
        topic.uncountable('monies');
        topic.singular('money', 'monies');
        assert.deepEqual(topic.singulars, [['money', 'monies'], ['ox', 'oxen']]);
        return assert.isEmpty(topic.uncountables);
      },
      'irregular': function(topic) {
        topic.clear();
        topic.uncountable(['octopi', 'octopus']);
        assert.deepEqual(topic.uncountables, ['octopi', 'octopus']);
        topic.irregular('octopus', 'octopi');
        assert.isEmpty(topic.uncountables);
        assert.equal(topic.singulars[0][0].toString(), /(o)ctopi$/i.toString());
        assert.equal(topic.singulars[0][1], '$1ctopus');
        assert.equal(topic.plurals[0][0].toString(), /(o)ctopi$/i.toString());
        assert.equal(topic.plurals[0][1], '$1ctopi');
        assert.equal(topic.plurals[1][0].toString(), /(o)ctopus$/i.toString());
        return assert.equal(topic.plurals[1][1].toString(), '$1ctopi');
      }
    }
  })["export"](module);

}).call(this);
