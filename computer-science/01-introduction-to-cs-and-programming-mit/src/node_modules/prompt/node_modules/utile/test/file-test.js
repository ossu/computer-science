/*
 * file-test.js: Tests for `utile.file` module.
 *
 * (C) 2011, Nodejitsu Inc.
 * MIT LICENSE
 *
 */

var assert = require('assert'),
    path = require('path'),
    vows = require('vows'),
    macros = require('./helpers/macros'),
    utile = require('../');

var fixture = path.join(__dirname, 'fixtures', 'read-json-file', 'config.json');

vows.describe('utile/file').addBatch({
  'When using utile': {
    'the `.file.readJson()` function': {
      topic: function () {
        utile.file.readJson(fixture, this.callback);
      },
      'should return correct JSON structure': macros.assertReadCorrectJson
    },
    'the `.file.readJsonSync()` function': {
      topic: utile.file.readJsonSync(fixture),
      'should return correct JSON structure': macros.assertReadCorrectJson
    }
  }
}).export(module);

