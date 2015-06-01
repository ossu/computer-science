var assert = require('assert'),
    colors = require('./colors');

var s = 'string';

function a(s, code) {
  return '\x1B[' + code.toString() + 'm' + s + '\x1B[39m';
}

function aE(s, color, code) {
  assert.equal(s[color], a(s, code));
  assert.equal(colors[color](s), a(s, code));
  assert.equal(s[color], colors[color](s));
  assert.equal(s[color].stripColors, s);
  assert.equal(s[color].stripColors, colors.stripColors(s));
}

function h(s, color) {
  return '<span style="color:' + color + ';">' + s + '</span>';
}

var stylesColors = ['white', 'black', 'blue', 'cyan', 'green', 'magenta', 'red', 'yellow'];
var stylesAll = stylesColors.concat(['bold', 'italic', 'underline', 'inverse', 'rainbow']);

colors.mode = 'console';
assert.equal(s.bold, '\x1B[1m' + s + '\x1B[22m');
assert.equal(s.italic, '\x1B[3m' + s + '\x1B[23m');
assert.equal(s.underline, '\x1B[4m' + s + '\x1B[24m');
assert.equal(s.strikethrough, '\x1B[9m' + s + '\x1B[29m');
assert.equal(s.inverse, '\x1B[7m' + s + '\x1B[27m');
assert.ok(s.rainbow);
aE(s, 'white', 37);
aE(s, 'grey', 90);
aE(s, 'black', 30);
aE(s, 'blue', 34);
aE(s, 'cyan', 36);
aE(s, 'green', 32);
aE(s, 'magenta', 35);
aE(s, 'red', 31);
aE(s, 'yellow', 33);
assert.equal(s, 'string');

colors.setTheme({error:'red'});

assert.equal(typeof("astring".red),'string');
assert.equal(typeof("astring".error),'string');

colors.mode = 'browser';
assert.equal(s.bold, '<b>' + s + '</b>');
assert.equal(s.italic, '<i>' + s + '</i>');
assert.equal(s.underline, '<u>' + s + '</u>');
assert.equal(s.strikethrough, '<del>' + s + '</del>');
assert.equal(s.inverse, '<span style="background-color:black;color:white;">' + s + '</span>');
assert.ok(s.rainbow);
stylesColors.forEach(function (color) {
  assert.equal(s[color], h(s, color));
  assert.equal(colors[color](s), h(s, color));
});

assert.equal(typeof("astring".red),'string');
assert.equal(typeof("astring".error),'string');

colors.mode = 'none';
stylesAll.forEach(function (style) {
  assert.equal(s[style], s);
  assert.equal(colors[style](s), s);
});

assert.equal(typeof("astring".red),'string');
assert.equal(typeof("astring".error),'string');
