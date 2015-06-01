/*
 * npm-config.js: Config that conform to npm logging levels.
 *
 * (C) 2010 Charlie Robbins
 * MIT LICENCE
 *
 */

var npmConfig = exports;

npmConfig.levels = {
  silly: 0,
  debug: 1,
  verbose: 2,
  info: 3,
  warn: 4,
  error: 5
};

npmConfig.colors = {
  silly: 'magenta',
  verbose: 'cyan',
  debug: 'blue',
  info: 'green',
  warn: 'yellow',
  error: 'red'
};