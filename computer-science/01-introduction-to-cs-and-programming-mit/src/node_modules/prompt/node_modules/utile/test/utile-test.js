/*
 * utile-test.js: Tests for `utile` module.
 *
 * (C) 2011, Nodejitsu Inc.
 * MIT LICENSE
 *
 */

var assert = require('assert'),
    vows = require('vows'),
    utile = require('../lib');

var obj1, obj2;

obj1 = {
  foo: true,
  bar: {
    bar1: true,
    bar2: 'bar2'
  }
};

obj2 = {
  baz: true,
  buzz: 'buzz'
};

Object.defineProperties(obj2, {

  'bazz': {
    get: function() {
      return 'bazz';
    },

    set: function() {
      return 'bazz';
    }
  },

  'wat': {
    set: function() {
      return 'wat';
    }
  }

});

vows.describe('utile').addBatch({
  "When using utile": {
    "it should have the same methods as the `util` module": function () {
      Object.keys(require('util')).forEach(function (fn) {
        assert.isFunction(utile[fn]);
      });
    },
    "it should have the correct methods defined": function () {
      assert.isFunction(utile.mixin);
      assert.isFunction(utile.clone);
      assert.isFunction(utile.rimraf);
      assert.isFunction(utile.mkdirp);
      assert.isFunction(utile.cpr);
    },
    "the mixin() method": function () {
      var mixed = utile.mixin({}, obj1, obj2);
      assert.isTrue(mixed.foo);
      assert.isObject(mixed.bar);
      assert.isTrue(mixed.baz);
      assert.isString(mixed.buzz);
      assert.isTrue(!!Object.getOwnPropertyDescriptor(mixed, 'bazz').get);
      assert.isTrue(!!Object.getOwnPropertyDescriptor(mixed, 'bazz').set);
      assert.isTrue(!!Object.getOwnPropertyDescriptor(mixed, 'wat').set);
      assert.isString(mixed.bazz);
    },
    "the clone() method": function () {
      var clone = utile.clone(obj1);
      assert.isTrue(clone.foo);
      assert.isObject(clone.bar);
      assert.notStrictEqual(obj1, clone);
    },
    "the createPath() method": function () {
      var x = {},
          r = Math.random();

      utile.createPath(x, ['a','b','c'], r)
      assert.equal(x.a.b.c, r)
    },
    "the capitalize() method": function () {
      assert.isFunction(utile.capitalize);
      assert.equal(utile.capitalize('bullet'), 'Bullet');
      assert.equal(utile.capitalize('bullet_train'), 'BulletTrain');
    },
    "the escapeRegExp() method": function () {
      var ans = "\\/path\\/to\\/resource\\.html\\?search=query";
      assert.isFunction(utile.escapeRegExp);
      assert.equal(utile.escapeRegExp('/path/to/resource.html?search=query'), ans);
    },
    "the underscoreToCamel() method": function () {
      var obj = utile.underscoreToCamel({
        key_with_underscore: {
          andNested: 'values',
          several: [1, 2, 3],
          nested_underscores: true
        },
        just_one: 'underscore'
      });

      assert.isObject(obj.keyWithUnderscore);
      assert.isString(obj.justOne);
      assert.isTrue(obj.keyWithUnderscore.nestedUnderscores);
    },
    "the camelToUnderscore() method": function () {
      var obj = utile.camelToUnderscore({
        keyWithCamel: {
          andNested: 'values',
          several: [1, 2, 3],
          nestedCamel: true
        },
        justOne: 'camel'
      });

      assert.isObject(obj.key_with_camel);
      assert.isString(obj.just_one);
      assert.isTrue(obj.key_with_camel.nested_camel);
    }
  }
}).export(module);

