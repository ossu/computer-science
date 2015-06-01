// Requiring modules

module.exports = function (attach) {
  var methods = require('./methods');

  if (attach) {
    require('./native')(methods);
  }

  return methods
};
