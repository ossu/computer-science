/*
 * file-stress-test.js: Tests for stressing File transport
 *
 * (C) 2014 William Wong
 * MIT LICENSE
 *
 */

var assert = require('assert'),
    fs = require('fs'),
    os  = require('os'),
    path = require('path'),
    vows = require('vows'),
    winston = require('../../lib/winston');

vows.describe('winston/transports/file').addBatch({
    'A stressed instance of the File Transport': {
        topic: function () {
            var callback = this.callback.bind(this),
                logPath = path.resolve(__dirname, '../fixtures/logs/file-stress-test.log');

            try {
                fs.unlinkSync(logPath);
            } catch (ex) {
                if (ex && ex.code !== 'ENOENT') { return callback(ex); }
            }

            var fileTransport = new (winston.transports.File)({
                    filename: logPath
                }),
                logger = new (winston.Logger)({
                    transports: [fileTransport]
                });

            fileTransport.on('open', function () {
                setTimeout(function () {
                    clearInterval(interval);

                    logger.query({ order: 'asc' }, function (err, results) {
                        callback(null, results);
                    });
                }, 100);
            });

            var logIndex = 0,
                interval = setInterval(function () {
                    logger.info(++logIndex);
                    stress(200);
                }, 0);

            logger.info(++logIndex);
            stress(200);

            function stress(duration) {
                var startTime = Date.now();

                while (Date.now() - startTime < duration) {
                    Math.sqrt(Math.PI);
                }
            }
        },
        'should not skip any log lines': function (results) {
            var testIndex = 0;

            results.file.forEach(function (log) {
                if (+log.message !== ++testIndex) {
                    throw new Error('Number skipped');
                }
            });
        }
    }
}).export(module);
