/*
 * transports.js: Set of all transports Winston knows about
 *
 * (C) 2010 Charlie Robbins
 * MIT LICENCE
 *
 */

var fs = require('fs'),
    path = require('path'),
    common = require('./common');

var transports = exports;

//
// Setup all transports as lazy-loaded getters.
//
fs.readdirSync(path.join(__dirname, 'transports')).forEach(function (file) {
  var transport = file.replace('.js', ''),
      name  = common.capitalize(transport);

  if (transport === 'transport') {
    return;
  }
  else if (~transport.indexOf('-')) {
    name = transport.split('-').map(function (part) {
      return common.capitalize(part);
    }).join('');
  }

  transports.__defineGetter__(name, function () {
    return require('./transports/' + transport)[name];
  });
});