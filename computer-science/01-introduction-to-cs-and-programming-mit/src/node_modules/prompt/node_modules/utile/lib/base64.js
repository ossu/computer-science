/*
 * base64.js: An extremely simple implementation of base64 encoding / decoding using node.js Buffers
 *
 * (C) 2010, Nodejitsu Inc.
 *
 */

var base64 = exports;

//
// ### function encode (unencoded)
// #### @unencoded {string} The string to base64 encode
// Encodes the specified string to base64 using node.js Buffers.
//
base64.encode = function (unencoded) {
  var encoded;

  try {
    encoded = new Buffer(unencoded || '').toString('base64');
  }
  catch (ex) {
    return null;
  }

  return encoded;
};

//
// ### function decode (encoded)
// #### @encoded {string} The string to base64 decode
// Decodes the specified string from base64 using node.js Buffers.
//
base64.decode = function (encoded) {
  var decoded;

  try {
    decoded = new Buffer(encoded || '', 'base64').toString('utf8');
  }
  catch (ex) {
    return null;
  }

  return decoded;
};