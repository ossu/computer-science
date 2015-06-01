/*
 * array-argument.js: Sample of including specific properties from a package.json file
 *                    using Array argument syntax.
 *
 * (C) 2011, Charlie Robbins
 *
 */
 
var util = require('util'),
    pkginfo = require('../lib/pkginfo')(module, ['version', 'author']);

exports.someFunction = function () {
  console.log('some of your custom logic here');
};

console.log('Inspecting module:');
console.dir(module.exports);

console.log('\nAll exports exposed:');
console.error(Object.keys(module.exports));