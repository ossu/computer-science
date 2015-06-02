"use strict";

// Returns an array of integers starting at a, incrementing by step, ending before b.
//
// Example:
//
// > var range = require("range").range;
// > range(0, 10);
// [0, 1, 2, 3, 4, 5, 6, 7, 8, 9]

function range(a, b, step) {
  if (arguments.length === 1) {
      b = a;
      a = 0;
  }

  step = step || 1;

  var x, r = [];

  for (x = a; (b - x) * step > 0; x += step) {
    r.push(x);
  }

  return r;
}

exports.range = range;
