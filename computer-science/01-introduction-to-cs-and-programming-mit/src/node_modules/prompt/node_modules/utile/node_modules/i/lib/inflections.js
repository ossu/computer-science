// A singleton instance of this class is yielded by Inflector.inflections, which can then be used to specify additional
// inflection rules. Examples:
//
//     BulletSupport.Inflector.inflect ($) ->
//       $.plural /^(ox)$/i, '$1en'
//       $.singular /^(ox)en/i, '$1'
//
//       $.irregular 'octopus', 'octopi'
//
//       $.uncountable "equipment"
//
// New rules are added at the top. So in the example above, the irregular rule for octopus will now be the first of the
// pluralization and singularization rules that is runs. This guarantees that your rules run before any of the rules that may
// already have been loaded.

var util = require('./util');

var Inflections = function () {
  this.plurals = [];
  this.singulars = [];
  this.uncountables = [];
  this.humans = [];
  require('./defaults')(this);
  return this;
};

// Specifies a new pluralization rule and its replacement. The rule can either be a string or a regular expression.
// The replacement should always be a string that may include references to the matched data from the rule.
Inflections.prototype.plural = function (rule, replacement) {
  if (typeof rule == 'string') {
    this.uncountables = util.array.del(this.uncountables, rule);
  }
  this.uncountables = util.array.del(this.uncountables, replacement);
  this.plurals.unshift([rule, replacement]);
};

// Specifies a new singularization rule and its replacement. The rule can either be a string or a regular expression.
// The replacement should always be a string that may include references to the matched data from the rule.
Inflections.prototype.singular = function (rule, replacement) {
  if (typeof rule == 'string') {
    this.uncountables = util.array.del(this.uncountables, rule);
  }
  this.uncountables = util.array.del(this.uncountables, replacement);
  this.singulars.unshift([rule, replacement]);
};

// Specifies a new irregular that applies to both pluralization and singularization at the same time. This can only be used
// for strings, not regular expressions. You simply pass the irregular in singular and plural form.
//
//     irregular 'octopus', 'octopi'
//     irregular 'person', 'people'
Inflections.prototype.irregular =  function (singular, plural) {
  this.uncountables = util.array.del(this.uncountables, singular);
  this.uncountables = util.array.del(this.uncountables, plural);
  if (singular[0].toUpperCase() == plural[0].toUpperCase()) {
    this.plural(new RegExp("(" + singular[0] + ")" + singular.slice(1) + "$", "i"), '$1' + plural.slice(1));
    this.plural(new RegExp("(" + plural[0] + ")" + plural.slice(1) + "$", "i"), '$1' + plural.slice(1));
    this.singular(new RegExp("(" + plural[0] + ")" + plural.slice(1) + "$", "i"), '$1' + singular.slice(1));
  } else {
    this.plural(new RegExp("" + (singular[0].toUpperCase()) + singular.slice(1) + "$"), plural[0].toUpperCase() + plural.slice(1));
    this.plural(new RegExp("" + (singular[0].toLowerCase()) + singular.slice(1) + "$"), plural[0].toLowerCase() + plural.slice(1));
    this.plural(new RegExp("" + (plural[0].toUpperCase()) + plural.slice(1) + "$"), plural[0].toUpperCase() + plural.slice(1));
    this.plural(new RegExp("" + (plural[0].toLowerCase()) + plural.slice(1) + "$"), plural[0].toLowerCase() + plural.slice(1));
    this.singular(new RegExp("" + (plural[0].toUpperCase()) + plural.slice(1) + "$"), singular[0].toUpperCase() + singular.slice(1));
    this.singular(new RegExp("" + (plural[0].toLowerCase()) + plural.slice(1) + "$"), singular[0].toLowerCase() + singular.slice(1));
  }
};

// Specifies a humanized form of a string by a regular expression rule or by a string mapping.
// When using a regular expression based replacement, the normal humanize formatting is called after the replacement.
// When a string is used, the human form should be specified as desired (example: 'The name', not 'the_name')
//
//     human /(.*)_cnt$/i, '$1_count'
//     human "legacy_col_person_name", "Name"
Inflections.prototype.human = function (rule, replacement) {
  this.humans.unshift([rule, replacement]);
}

// Add uncountable words that shouldn't be attempted inflected.
//
//     uncountable "money"
//     uncountable ["money", "information"]
Inflections.prototype.uncountable = function (words) {
  this.uncountables = this.uncountables.concat(words);
}

// Clears the loaded inflections within a given scope (default is _'all'_).
// Give the scope as a symbol of the inflection type, the options are: _'plurals'_,
// _'singulars'_, _'uncountables'_, _'humans'_.
//
//     clear 'all'
//     clear 'plurals'
Inflections.prototype.clear = function (scope) {
  if (scope == null) scope = 'all';
  switch (scope) {
    case 'all':
      this.plurals = [];
      this.singulars = [];
      this.uncountables = [];
      this.humans = [];
    default:
      this[scope] = [];
  }
}

// Clears the loaded inflections and initializes them to [default](../inflections.html)
Inflections.prototype.default = function () {
  this.plurals = [];
  this.singulars = [];
  this.uncountables = [];
  this.humans = [];
  require('./defaults')(this);
  return this;
};

module.exports = new Inflections();
