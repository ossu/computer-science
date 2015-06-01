var assert = require('assert'),
    path = require('path'),
    rimraf = require('rimraf'),
    vows = require('vows'),
    readDirFiles = require('read-dir-files'),
    ncp = require('../').ncp;

var fixtures = path.join(__dirname, 'fixtures'),
    src = path.join(fixtures, 'src'),
    out = path.join(fixtures, 'out');

vows.describe('ncp').addBatch({
  'When copying a directory of files': {
    topic: function () {
      var cb = this.callback;
      rimraf(out, function () {
        ncp(src, out, cb);
      });
    },
    'files should be copied': {
      topic: function () {
        var cb = this.callback;

        readDirFiles(src, 'utf8', function (srcErr, srcFiles) {
          readDirFiles(out, 'utf8', function (outErr, outFiles) {
            cb(outErr, srcFiles, outFiles);
          });
        });
      },
      'and the destination should match the source': function (err, srcFiles, outFiles) {
        assert.isNull(err);
        assert.deepEqual(srcFiles, outFiles);
      }
    }
  }
}).addBatch({
  'When copying files using filter': {
    topic: function() {
      var cb = this.callback;
      var filter = function(name) {
        return name.substr(name.length - 1) != 'a'
      }
      rimraf(out, function () {
        ncp(src, out, {filter: filter}, cb);
      });
    },
    'it should copy files': {
      topic: function () {
        var cb = this.callback;

        readDirFiles(src, 'utf8', function (srcErr, srcFiles) {
          function filter(files) {
            for (var fileName in files) {
              var curFile = files[fileName];
              if (curFile instanceof Object)
                return filter(curFile);
              if (fileName.substr(fileName.length - 1) == 'a')
                delete files[fileName];
            }
          }
          filter(srcFiles);
          readDirFiles(out, 'utf8', function (outErr, outFiles) {
            cb(outErr, srcFiles, outFiles);
          });
        });
      },
      'and destination files should match source files that pass filter': function (err, srcFiles, outFiles) {
        assert.isNull(err);
        assert.deepEqual(srcFiles, outFiles);
      }
    }
  }
}).addBatch({
   'When copying files using transform': {
      'it should pass file descriptors along to transform functions': function() {
         ncp(src, out, {
            transform: function(read,write,file) {
               assert.notEqual(file.name, undefined);
               assert.strictEqual(typeof file.mode,'number');
               read.pipe(write);
            }
         }, function(){});
      }
  }
}).export(module);

