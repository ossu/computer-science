/*
 * pkginfo-test.js: Tests for the pkginfo module.
 *
 * (C) 2011, Charlie Robbins
 *
 */

var assert = require('assert'),
    exec = require('child_process').exec,
    fs = require('fs'),
    path = require('path'),
    vows = require('vows'),
    pkginfo = require('../lib/pkginfo');

function assertProperties (source, target) {
  assert.lengthOf(source, target.length + 1);
  target.forEach(function (prop) {
    assert.isTrue(!!~source.indexOf(prop));
  });
}

function compareWithExample(targetPath) {
  var examplePaths = ['package.json'];
  
  if (targetPath) {
    examplePaths.unshift(targetPath);
  }
  
  return function(exposed) {
    var pkg = fs.readFileSync(path.join.apply(null, [__dirname, '..', 'examples'].concat(examplePaths))).toString(),
        keys = Object.keys(JSON.parse(pkg));
    
    assertProperties(exposed, keys);
  };
}

function testExposes (options) {
  return {
    topic: function () {
      exec('node ' + path.join(__dirname, '..', 'examples', options.script), this.callback);
    },
    "should expose that property correctly": function (err, stdout, stderr) {
      assert.isNull(err);
      
      var exposed = stderr.match(/'(\w+)'/ig).map(function (p) { 
        return p.substring(1, p.length - 1);
      });
      
      return !options.assert 
        ? assertProperties(exposed, options.properties)
        : options.assert(exposed);
    }
  }
}

vows.describe('pkginfo').addBatch({
  "When using the pkginfo module": {
    "and passed a single `string` argument": testExposes({
      script: 'single-property.js',
      properties: ['version']
    }),
    "and passed multiple `string` arguments": testExposes({
      script: 'multiple-properties.js',
      properties: ['version', 'author']
    }),
    "and passed an `object` argument": testExposes({
      script: 'object-argument.js',
      properties: ['version', 'author']
    }),
    "and passed an `array` argument": testExposes({
      script: 'array-argument.js',
      properties: ['version', 'author']
    }),
    "and read from a specified directory": testExposes({
      script: 'target-dir.js',
      assert: compareWithExample('subdir')
    }),
    "and passed no arguments": testExposes({
      script: 'all-properties.js',
      assert: compareWithExample()
    })
  }
}).export(module);
