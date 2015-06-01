/*
 * file-maxfiles-test.js: Tests for instances of the File transport setting the max file size,
 * and setting a number for max files created.
 * maxSize * maxFiles = total storage used by winston.
 *
 * (C) 2011 Daniel Aristizabal
 * MIT LICENSE
 *
 */

var assert = require('assert'),
    exec = require('child_process').exec,
    fs = require('fs'),
    path = require('path'),
    vows = require('vows'),
    winston = require('../../lib/winston'),
    helpers = require('../helpers');

var maxfilesTransport = new winston.transports.File({
  timestamp: false,
  json: false,
  filename: path.join(__dirname, '..', 'fixtures', 'logs', 'testmaxfiles.log'),
  maxsize: 4096,
  maxFiles: 3
});
    
vows.describe('winston/transports/file/maxfiles').addBatch({
  "An instance of the File Transport": {
    "when passed a valid filename": {
      topic: maxfilesTransport,
      "should be a valid transporter": function (transportTest) {
        helpers.assertFile(transportTest);
      },
      "should set the maxFiles option correctly": function (transportTest) {
        assert.isNumber(transportTest.maxFiles);
      }
    },
    "when delete old test files": {
      topic: function () {
        exec('rm -rf ' + path.join(__dirname, '..', 'fixtures', 'logs', 'testmaxfiles*'), this.callback);
      },
      "and when passed more files than the maxFiles": {
        topic: function () {
          var that = this,
              created = 0;
              
          function data(ch) {
            return new Array(1018).join(String.fromCharCode(65 + ch));
          };
      
          function logKbytes(kbytes, txt) {
            //
            // With no timestamp and at the info level,
            // winston adds exactly 7 characters: 
            // [info](4)[ :](2)[\n](1)
            //
            for (var i = 0; i < kbytes; i++) {
              maxfilesTransport.log('info', data(txt), null, function () { });
            }
          }
          
          maxfilesTransport.on('logged', function () {
            if (++created === 6) {
              return that.callback();
            }
            
            logKbytes(4, created);
          });
         
          logKbytes(4, created);
        },
        "should be only 3 files called 5.log, 4.log and 3.log": function () {
          for (var num = 0; num < 6; num++) {
            var file = !num ? 'testmaxfiles.log' : 'testmaxfiles' + num + '.log',
                fullpath = path.join(__dirname, '..', 'fixtures', 'logs', file);
            
            // There should be no files with that name
            if (num >= 0 && num < 3) {
              return assert.throws(function () {
                fs.statSync(file);
              }, Error);
            } 

            // The other files should be exist
            assert.doesNotThrow(function () {
              fs.statSync(file);
            }, Error);
          }
        },
        "should have the correct content": function () {
          ['D', 'E', 'F'].forEach(function (name, inx) {
            var counter = inx + 3,
                logsDir = path.join(__dirname, '..', 'fixtures', 'logs'),
                content = fs.readFileSync(path.join(logsDir, 'testmaxfiles' + counter + '.log'), 'utf-8');
            // The content minus the 7 characters added by winston
            assert.lengthOf(content.match(new RegExp(name, 'g')), 4068);
          });
        }
      }
    }
  }
}).export(module);