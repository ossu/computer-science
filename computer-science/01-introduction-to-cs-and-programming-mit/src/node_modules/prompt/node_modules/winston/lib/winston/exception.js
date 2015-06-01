/*
 * exception.js: Utility methods for gathing information about uncaughtExceptions.
 *
 * (C) 2010 Charlie Robbins
 * MIT LICENCE
 *
 */

var os = require('os'),
    stackTrace = require('stack-trace');

var exception = exports;

exception.getAllInfo = function (err) {
  return {
    date:    new Date().toString(),
    process: exception.getProcessInfo(),
    os:      exception.getOsInfo(),
    trace:   exception.getTrace(err),
    stack:   err.stack && err.stack.split('\n')
  };
};

exception.getProcessInfo = function () {
  return {
    pid:         process.pid,
    uid:         process.getuid ? process.getuid() : null,
    gid:         process.getgid ? process.getgid() : null,
    cwd:         process.cwd(),
    execPath:    process.execPath,
    version:     process.version,
    argv:        process.argv,
    memoryUsage: process.memoryUsage()
  };
};

exception.getOsInfo = function () {
  return {
    loadavg: os.loadavg(),
    uptime:  os.uptime()
  };
};

exception.getTrace = function (err) {
  var trace = err ? stackTrace.parse(err) : stackTrace.get();
  return trace.map(function (site) {
    return {
      column:   site.getColumnNumber(),
      file:     site.getFileName(),
      function: site.getFunctionName(),
      line:     site.getLineNumber(),
      method:   site.getMethodName(),
      native:   site.isNative(),
    }
  });
};
