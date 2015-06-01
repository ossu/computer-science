/*
 * single-property.js: Sample of including a single specific properties from a package.json file
 *
 * (C) 2011, Charlie Robbins
 *
 */
 
var util = require('util'),
    pkginfo = require('../lib/pkginfo')(module, 'version');

exports.someFunction = function () {
  console.log('some of your custom logic here');
};

console.log('Inspecting module:');
console.dir(module.exports);

console.log('\nAll exports exposed:');
console.error(Object.keys(module.exports));