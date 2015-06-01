//
// Eyes.js - a customizable value inspector for Node.js
//
//   usage:
//
//       var inspect = require('eyes').inspector({styles: {all: 'magenta'}});
//       inspect(something); // inspect with the settings passed to `inspector`
//
//     or
//
//       var eyes = require('eyes');
//       eyes.inspect(something); // inspect with the default settings
//
var eyes = exports,
    stack = [];

eyes.defaults = {
    styles: {                 // Styles applied to stdout
        all:     'cyan',      // Overall style applied to everything
        label:   'underline', // Inspection labels, like 'array' in `array: [1, 2, 3]`
        other:   'inverted',  // Objects which don't have a literal representation, such as functions
        key:     'bold',      // The keys in object literals, like 'a' in `{a: 1}`
        special: 'grey',      // null, undefined...
        string:  'green',
        number:  'magenta',
        bool:    'blue',      // true false
        regexp:  'green',     // /\d+/
    },
    pretty: true,             // Indent object literals
    hideFunctions: false,
    showHidden: false,
    stream: process.stdout,
    maxLength: 2048           // Truncate output if longer
};

// Return a curried inspect() function, with the `options` argument filled in.
eyes.inspector = function (options) {
    var that = this;
    return function (obj, label, opts) {
        return that.inspect.call(that, obj, label,
            merge(options || {}, opts || {}));
    };
};

// If we have a `stream` defined, use it to print a styled string,
// if not, we just return the stringified object.
eyes.inspect = function (obj, label, options) {
    options = merge(this.defaults, options || {});

    if (options.stream) {
        return this.print(stringify(obj, options), label, options);
    } else {
        return stringify(obj, options) + (options.styles ? '\033[39m' : '');
    }
};

// Output using the 'stream', and an optional label
// Loop through `str`, and truncate it after `options.maxLength` has been reached.
// Because escape sequences are, at this point embeded within
// the output string, we can't measure the length of the string
// in a useful way, without separating what is an escape sequence,
// versus a printable character (`c`). So we resort to counting the
// length manually.
eyes.print = function (str, label, options) {
    for (var c = 0, i = 0; i < str.length; i++) {
        if (str.charAt(i) === '\033') { i += 4 } // `4` because '\033[25m'.length + 1 == 5
        else if (c === options.maxLength) {
           str = str.slice(0, i - 1) + '…';
           break;
        } else { c++ }
    }
    return options.stream.write.call(options.stream, (label ?
        this.stylize(label, options.styles.label, options.styles) + ': ' : '') +
        this.stylize(str,   options.styles.all, options.styles) + '\033[0m' + "\n");
};

// Apply a style to a string, eventually,
// I'd like this to support passing multiple
// styles.
eyes.stylize = function (str, style, styles) {
    var codes = {
        'bold'      : [1,  22],
        'underline' : [4,  24],
        'inverse'   : [7,  27],
        'cyan'      : [36, 39],
        'magenta'   : [35, 39],
        'blue'      : [34, 39],
        'yellow'    : [33, 39],
        'green'     : [32, 39],
        'red'       : [31, 39],
        'grey'      : [90, 39]
    }, endCode;

    if (style && codes[style]) {
        endCode = (codes[style][1] === 39 && styles.all) ? codes[styles.all][0]
                                                         : codes[style][1];
        return '\033[' + codes[style][0] + 'm' + str +
               '\033[' + endCode + 'm';
    } else { return str }
};

// Convert any object to a string, ready for output.
// When an 'array' or an 'object' are encountered, they are
// passed to specialized functions, which can then recursively call
// stringify().
function stringify(obj, options) {
    var that = this, stylize = function (str, style) {
        return eyes.stylize(str, options.styles[style], options.styles)
    }, index, result;

    if ((index = stack.indexOf(obj)) !== -1) {
        return stylize(new(Array)(stack.length - index + 1).join('.'), 'special');
    }
    stack.push(obj);

    result = (function (obj) {
        switch (typeOf(obj)) {
            case "string"   : obj = stringifyString(obj.indexOf("'") === -1 ? "'" + obj + "'"
                                                                            : '"' + obj + '"');
                              return stylize(obj, 'string');
            case "regexp"   : return stylize('/' + obj.source + '/', 'regexp');
            case "number"   : return stylize(obj + '',    'number');
            case "function" : return options.stream ? stylize("Function", 'other') : '[Function]';
            case "null"     : return stylize("null",      'special');
            case "undefined": return stylize("undefined", 'special');
            case "boolean"  : return stylize(obj + '',    'bool');
            case "date"     : return stylize(obj.toUTCString());
            case "array"    : return stringifyArray(obj,  options, stack.length);
            case "object"   : return stringifyObject(obj, options, stack.length);
        }
    })(obj);

    stack.pop();
    return result;
};

// Escape invisible characters in a string
function stringifyString (str, options) {
    return str.replace(/\\/g, '\\\\')
              .replace(/\n/g, '\\n')
              .replace(/[\u0001-\u001F]/g, function (match) {
                  return '\\0' + match[0].charCodeAt(0).toString(8);
              });
}

// Convert an array to a string, such as [1, 2, 3].
// This function calls stringify() for each of the elements
// in the array.
function stringifyArray(ary, options, level) {
    var out = [];
    var pretty = options.pretty && (ary.length > 4 || ary.some(function (o) {
        return (o !== null && typeof(o) === 'object' && Object.keys(o).length > 0) ||
               (Array.isArray(o) && o.length > 0);
    }));
    var ws = pretty ? '\n' + new(Array)(level * 4 + 1).join(' ') : ' ';

    for (var i = 0; i < ary.length; i++) {
        out.push(stringify(ary[i], options));
    }

    if (out.length === 0) {
        return '[]';
    } else {
        return '[' + ws
                   + out.join(',' + (pretty ? ws : ' '))
                   + (pretty ? ws.slice(0, -4) : ws) +
               ']';
    }
};

// Convert an object to a string, such as {a: 1}.
// This function calls stringify() for each of its values,
// and does not output functions or prototype values.
function stringifyObject(obj, options, level) {
    var out = [];
    var pretty = options.pretty && (Object.keys(obj).length > 2 ||
                                    Object.keys(obj).some(function (k) { return typeof(obj[k]) === 'object' }));
    var ws = pretty ? '\n' + new(Array)(level * 4 + 1).join(' ') : ' ';

    var keys = options.showHidden ? Object.keys(obj) : Object.getOwnPropertyNames(obj);
    keys.forEach(function (k) {
        if (Object.prototype.hasOwnProperty.call(obj, k) 
          && !(obj[k] instanceof Function && options.hideFunctions)) {
            out.push(eyes.stylize(k, options.styles.key, options.styles) + ': ' +
                     stringify(obj[k], options));
        }
    });

    if (out.length === 0) {
        return '{}';
    } else {
        return "{" + ws
                   + out.join(',' + (pretty ? ws : ' '))
                   + (pretty ? ws.slice(0, -4) : ws) +
               "}";
   }
};

// A better `typeof`
function typeOf(value) {
    var s = typeof(value),
        types = [Object, Array, String, RegExp, Number, Function, Boolean, Date];

    if (s === 'object' || s === 'function') {
        if (value) {
            types.forEach(function (t) {
                if (value instanceof t) { s = t.name.toLowerCase() }
            });
        } else { s = 'null' }
    }
    return s;
}

function merge(/* variable args */) {
    var objs = Array.prototype.slice.call(arguments);
    var target = {};

    objs.forEach(function (o) {
        Object.keys(o).forEach(function (k) {
            if (k === 'styles') {
                if (! o.styles) {
                    target.styles = false;
                } else {
                    target.styles = {}
                    for (var s in o.styles) {
                        target.styles[s] = o.styles[s];
                    }
                }
            } else {
                target[k] = o[k];
            }
        });
    });
    return target;
}

