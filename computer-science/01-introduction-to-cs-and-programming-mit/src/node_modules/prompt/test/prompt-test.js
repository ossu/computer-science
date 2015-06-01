/*
 * prompt-test.js: Tests for prompt.
 *
 * (C) 2010, Nodejitsu Inc.
 *
 */

var assert = require('assert'),
    vows = require('vows'),
    prompt = require('../lib/prompt'),
    helpers = require('./helpers'),
    macros = require('./macros'),
    schema = helpers.schema;

// A helper to pass fragments of our schema into prompt as full schemas.
function grab () {
  var names = [].slice.call(arguments),
      complete = { schema: {} };

  names.forEach(function (name) {
    complete.path = [name],
    complete.schema = schema.properties[name];
  });
  return complete;
};

//
// Reset the prompt for mock testing
//
prompt.started = false;
prompt.start({
  stdin: helpers.stdin,
  stdout: helpers.stdout
});

vows.describe('prompt').addBatch({
  "When using prompt": {
    "the getInput() method": {
      "with a simple string prompt": {
        topic: function () {
          var that = this;
          helpers.stdout.once('data', function (msg) {
            that.msg = msg;
          })

          prompt.getInput('test input', this.callback);
          helpers.stdin.writeNextTick('test value\n');
        },
        "should prompt to stdout and respond with data": function (err, input) {
          assert.isNull(err);
          assert.equal(input, 'test value');
          assert.isTrue(this.msg.indexOf('test input') !== -1);
        }
      },
    }
  }
}).addBatch({
  "When using prompt": {
    "the getInput() method": {
      "with any field that is not supposed to be empty": {
        "and we don't provide any input": {
          topic: function () {
            var that = this;
            helpers.stdout.once('data', function (msg) {
              that.msg = msg;
            });

            helpers.stderr.once('data', function (msg) {
              that.errmsg = msg;
            });

            prompt.getInput(grab('notblank'), function () {});
            prompt.once('invalid', this.callback.bind(null, null))
            helpers.stdin.writeNextTick('\n');
          },
          "should prompt with an error": function (_, prop, input) {
            assert.isObject(prop);
            assert.equal(input, '');
            assert.isTrue(this.errmsg.indexOf('Invalid input') !== -1);
            assert.isTrue(this.msg.indexOf('notblank') !== -1);
          }
        }
      }
    }
  }
}).addBatch({
  "When using prompt": {
    "the getInput() method": {
      "with a hidden field that is not supposed to be empty": {
        "and we provide valid input": {
          topic: function () {
            var that = this;
            helpers.stdout.once('data', function (msg) {
              that.msg = msg;
            });

            prompt.getInput('password', this.callback);
            helpers.stdin.writeNextTick('trustno1\n');
          },

          "should prompt to stdout and respond with data": function (err, input) {
            assert.isNull(err);
            assert.equal(input, 'trustno1');
            assert.isTrue(this.msg.indexOf('password') !== -1);
          }
        },
      }
    }
  }
}).addBatch({
  "When using prompt": {
    "the getInput() method": {
      "with a hidden field that is not supposed to be empty": {
        "and we don't provide an input": {
          topic: function () {
            var that = this;
            helpers.stdout.once('data', function (msg) {
              that.msg = msg;
            });

            helpers.stderr.once('data', function (msg) {
              that.errmsg = msg;
            });

            prompt.getInput(grab('password'), function () {} );
            prompt.once('invalid', this.callback.bind(null, null))
            helpers.stdin.writeNextTick('\n');
          },
          "should prompt with an error": function (ign, prop, input) {
            assert.isObject(prop);
            assert.equal(input, '');
            assert.isTrue(this.errmsg.indexOf('Invalid input') !== -1);
            assert.isTrue(this.msg.indexOf('password') !== -1);
          }
        }
      }
    }
  }
}).addBatch({
  "When using prompt": {
    "the getInput() method": {
      "with a complex property prompt": {
        "and a valid input": {
          topic: function () {
            var that = this;
            helpers.stdout.once('data', function (msg) {
              that.msg = msg;
            });

            prompt.getInput(grab('username'), this.callback);
            helpers.stdin.writeNextTick('some-user\n');
          },
          "should prompt to stdout and respond with data": function (err, input) {
            assert.isNull(err);
            assert.equal(input, 'some-user');
            assert.isTrue(this.msg.indexOf('username') !== -1);
          }
        }
      }
    }
  }
}).addBatch({
  "When using prompt": {
    "the getInput() method": {
      "with a complex property prompt": {
        "and an invalid input": {
          topic: function () {
            var that = this;
            helpers.stdout.once('data', function (msg) {
              that.msg = msg;
            });

            helpers.stderr.once('data', function (msg) {
              that.errmsg = msg;
            })

            prompt.getInput(grab('username'), this.callback);

            prompt.once('invalid', function () {
              prompt.once('prompt', function () {
                process.nextTick(function () {
                  helpers.stdin.writeNextTick('some-user\n');
                })
              })
            });

            helpers.stdin.writeNextTick('some -user\n');
          },
          "should prompt with an error before completing the operation": function (err, input) {
            assert.isNull(err);
            assert.equal(input, 'some-user');
            assert.isTrue(this.errmsg.indexOf('Invalid input') !== -1);
            assert.isTrue(this.msg.indexOf('username') !== -1);
          }
        }
      }
    }
  }
}).addBatch({
  "When using prompt": {
    "the getInput() method": {
      "with a complex property prompt": {
        "with an invalid validator (array)": {
          topic: function () {
            var that = this,
                called;

            prompt.getInput(grab('badValidator'), function (err) {
              if (!called) {
                called = true;
                that.callback(err);
              }
            });
            helpers.stdin.writeNextTick('some-user\n');
          },
          "should respond with an error": function (err, ign) {
            assert.isTrue(!!err);
          }
        }
      }
    }
  }
}).addBatch({
  "When using prompt": {
    "the get() method": {
      "with a simple string prompt": {
        "that is not a property in prompt.properties": {
          topic: function () {
            var that = this;
            helpers.stdout.once('data', function (msg) {
              that.msg = msg;
            })

            prompt.get('test input', this.callback);
            helpers.stdin.writeNextTick('test value\n');
          },
          "should prompt to stdout and respond with the value": function (err, result) {
            assert.isNull(err);
            assert.include(result, 'test input');
            assert.equal(result['test input'], 'test value');
            assert.isTrue(this.msg.indexOf('test input') !== -1);
          }
        },
      }
    }
  }
}).addBatch({
  "When using prompt": {
    "the get() method": {
      "with a simple string prompt": {
        "that is a property name in prompt.properties": {
          "with a default value": {
            topic: function () {
              var that = this;

              helpers.stdout.once('data', function (msg) {
                that.msg = msg;
              });

              prompt.properties.riffwabbles = schema.properties.riffwabbles;
              prompt.get('riffwabbles', this.callback);
              helpers.stdin.writeNextTick('\n');
            },
            "should prompt to stdout and respond with the default value": function (err, result) {
              assert.isNull(err);
              assert.isTrue(this.msg.indexOf('riffwabbles') !== -1);
              assert.isTrue(this.msg.indexOf('(foobizzles)') !== -1);
              assert.include(result, 'riffwabbles');
              assert.equal(result['riffwabbles'], schema.properties['riffwabbles'].default);
            }
          },
        }
      }
    }
  }
}).addBatch({
  "When using prompt": {
    "the get() method": {
      "with a simple string prompt": {
        "that is a property name in prompt.properties": {
          "that expects a numeric value": {
            "and gets valid input": {
              topic: function () {
                var that = this;

                helpers.stdout.once('data', function (msg) {
                  that.msg = msg;
                });

                prompt.properties.number = schema.properties.number;
                prompt.get('number', this.callback);
                helpers.stdin.writeNextTick('15\n');
              },
              "should prompt to stdout and respond with a numeric value": function (err, result) {
                assert.isNull(err);
                assert.include(result, 'number');
                assert.equal(result['number'], 15);
              }
            }
          }
        }
      }
    }
  }
}).addBatch({
  "When using prompt": {
    "the get() method": {
      "with a simple string prompt": {
        "that is a property name in prompt.properties": {
          "with a sync function validator (.validator)": {
            topic: function () {
              var that = this;

              helpers.stdout.once('data', function (msg) {
                that.msg = msg;
              });

              prompt.get(helpers.schema.properties.fnvalidator, this.callback);
              helpers.stdin.writeNextTick('fn123\n');
            },
            "should accept a value that is checked": function (err, result) {
              assert.isNull(err);
              assert.equal(result['fnvalidator'],'fn123');
            }
          }
        }
      }
    }
  }
}).addBatch({
  "When using prompt": {
    "the get() method": {
      "with a simple string prompt": {
        "that is a property name in prompt.properties": {
          "with a sync function validator (.conform)": {
            topic: function () {
              var that = this;

              helpers.stdout.once('data', function (msg) {
                that.msg = msg;
              });

              prompt.get(grab('fnconform'), this.callback);
              helpers.stdin.writeNextTick('fn123\n');
            },
            "should accept a value that is checked": function (err, result) {
              assert.isNull(err);
              assert.equal(result['fnconform'],'fn123');
            }
          }
          //
          // Remark Does not work with revalidator
          //
          // "with a callback validator": {
          //   topic: function () {
          //     var that = this;
          //
          //     helpers.stdout.once('data', function (msg) {
          //       that.msg = msg;
          //     });
          //
          //     prompt.get(grab('cbvalidator'), this.callback);
          //     helpers.stdin.writeNextTick('cb123\n');
          //   },
          //   "should not accept a value that is correct": function (err, result) {
          //     assert.isNull(err);
          //     assert.equal(result['cbvalidator'],'cb123');
          //   }
          // }
        }
      },
    }
  }
}).addBatch({
  "When using prompt": {
    "the get() method": {
      "with a simple string prompt": {
        "that is a property name in prompt.properties": {
          "with a sync function before (.before)": {
            topic: function() {
              var that = this;

              helpers.stdout.once('data', function(msg) {
                that.msg = msg;
              });

              prompt.get({
                properties: {
                  fnbefore: {
                    before: function(v) {
                      return 'v' + v;
                    }
                  }
                }
              }, this.callback);
              helpers.stdin.writeNextTick('fn456\n');
            },
            "should modify user's input": function(e, result) {
              assert.equal(result.fnbefore, 'vfn456');
            }
          }
        }
      }
    }
  }
}).addBatch({
  "When using prompt": {
    "the get() method": {
      "skip prompt with prompt.overide": {
        topic: function () {
          prompt.override = { coconihet: 'whatever' }
          prompt.get('coconihet', this.callback);
        },
        "skips prompt and uses overide": function (err, results) {
          assert.equal(results.coconihet, 'whatever')
        }
      }
    }
  }
}).addBatch({
  "When using prompt": {
    "the addProperties() method": {
      topic: function () {
        prompt.addProperties({}, ['foo', 'bar'], this.callback);
        helpers.stdin.writeSequence(['foo\n', 'bar\n']);
      },
      "should add the properties to the object": function (err, obj) {
        assert.isNull(err);
        assert.isObject(obj);
        assert.equal(obj.foo, 'foo');
        assert.equal(obj.bar, 'bar');
      }
    }
  }
}).addBatch({
  "When using prompt": {
    "the get() method": {
      "with old schema": {
        topic: function () {
          var that = this;

          helpers.stdout.once('data', function (msg) {
            that.msg = msg;
          });

          prompt.properties.username = schema.properties.oldschema;
          prompt.get('username', this.callback);

          helpers.stdin.writeSequence(['\n', 'hell$\n', 'hello\n']);
        },
        "should prompt to stdout and respond with the default value": function (err, result) {
          assert.isNull(err);
          assert.isTrue(this.msg.indexOf('username') !== -1);
          assert.include(result, 'username');
          assert.equal(result.username, 'hello');
        }
      }
    }
  }
}).addBatch({
  "When using prompt": {
    "the history() method": {
      "when used inside of a complex property": {
        "with correct value(s)": {
          topic: function () {
            prompt.get([grab('animal'), grab('sound')], this.callback);
            helpers.stdin.writeSequence(['dog\n', 'woof\n']);
          },
          "should respond with the values entered": function (err, result) {
            assert.isTrue(!err);
            assert.equal(result.animal, 'dog');
            assert.equal(result.sound, 'woof');
            assert.equal(prompt.history('nothing'), null);
            assert.deepEqual(prompt.history('animal'), { property: 'animal', value: 'dog' });
          }
        },
      }
    }
  }
}).addBatch({
  "When using prompt": {
    "the history() method": {
      "when used inside of a complex property": {
        "with an incorrect value": {
          topic: function () {
            prompt.get([grab('animal'), grab('sound')], function () {});
            prompt.once('invalid', this.callback.bind(null, null));
            helpers.stdin.writeSequence(['dog\n', 'meow\n']);
          },
          "should prompt for the error": function (ign, property, line) {
            assert.equal(property.path.join(''), 'sound');
            assert.equal(line, 'meow');
          }
        }
      }
    }
  }
}).addBatch({
  "when using prompt": {
    "the get() method": {
      topic: function () {
        prompt.override = { xyz: 468, abc: 123 }
        prompt.get(['xyz', 'abc'], this.callback);
      },
      "should respond with overrides": function (err, results) {
        assert.isNull(err);
        assert.deepEqual(results, { xyz: 468, abc: 123 });
      }
    }
  }
}).addBatch({
  "When using prompt": {
    "with fancy properties": {
      "the get() method": {
        topic: function () {
          prompt.override = { UVW: 5423, DEF: 64235 }
          prompt.get({
            properties: {
              'UVW': {
                description: 'a custom message',
                default: 6
              },
              'DEF': {
                description: 'a custom message',
                default: 6
              }
            }
          }, this.callback);
        },
        "should respond with overrides": function (err, results) {
          assert.isNull(err);
          assert.deepEqual(results, { UVW: 5423, DEF: 64235 });
        }
      }
    }
  }
}).addBatch({
  "When using prompt": {
    "with a type and a description property": {
        "the get() method": {
          topic: function () {
            var that = this;
            helpers.stdout.once('data', function (msg) {
              that.msg = msg;
            });

            prompt.get({
              name: 'test',
              type: 'number',
              description: 'Please input a number'
            }, this.callback);
            helpers.stdin.writeNextTick('42\n');
          },
          "should prompt to stdout and respond with the value": function (err, result) {
            assert.isNull(err);
            assert.include(result, 'test');
            assert.equal(result['test'], '42');
            assert.isTrue(this.msg.indexOf('Please input a number') !== -1);
          }
        },
      }
    }
  })
.addBatch(
  macros.shouldConfirm({
    messages: ['with a string message'],
    prop: 'test',
    response: 'Y'
  })
).addBatch(
  macros.shouldConfirm({
    messages: ['with an empty string and default yes'],
    prop: 'test',
    response: ''
  }, {
    default: 'yes'
  })
).addBatch(
  macros.shouldNotConfirm({
    messages: ['with a string message'],
    prop: 'test',
    response: 'N'
  })
).addBatch(
  macros.shouldConfirm({
    messages: ['with a string message'],
    prop: 'test',
    response: 'YES'
  })
).addBatch(
  macros.shouldNotConfirm({
    messages: ['with a string message'],
    prop: 'test',
    response: 'NO'
  })
).addBatch(
  macros.shouldConfirm({
    messages: ['with a string message'],
    prop: 'test',
    response: 'T'
  })
).addBatch(
  macros.shouldNotConfirm({
    messages: ['with a string message'],
    prop: 'test',
    response: 'F'
  })
).addBatch(
  macros.shouldConfirm({
    messages: ['with a string message'],
    prop: 'test',
    response: 'TRUE'
  })
).addBatch(
  macros.shouldNotConfirm({
    messages: ['with a string message'],
    prop: 'test',
    response: 'FALSE'
  })
).addBatch(
  macros.shouldConfirm({
    messages: ['with an object', 'and description set'],
    prop: { description: 'a custom message' },
    response: 'Y'
  })
).addBatch(
  macros.shouldConfirm({
    messages: ['with an object', 'and they forgot the description'],
    prop: { description: 'a custom message' },
    response: 'Y'
  })
).addBatch(
  macros.shouldConfirm({
    messages: ['with an object', 'and custom validators'],
    prop: {
      description: 'node or jitsu?',
      pattern: /^(node|jitsu)/i,
      yes: /^node/i
    },
    response: 'node'
  })
).addBatch(
  macros.shouldNotConfirm({
    messages: ['with an object', 'and custom validators'],
    prop: {
      description: 'node or jitsu?',
      pattern: /^(node|jitsu)/i,
      yes: /^node/i
    },
    response: 'jitsu'
  })
).addBatch(
  macros.shouldConfirm({
    messages: ['with multiple strings'],
    prop: ["test", "test2", "test3"],
    response: ['Y\n', 'y\n', 'YES\n']
  })
).addBatch(
  macros.shouldNotConfirm({
    messages: ['with multiple strings'],
    prop: ["test", "test2", "test3"],
    response: ['Y\n', 'N\n', 'YES\n']
  })
).addBatch(
  macros.shouldNotConfirm({
    messages: ['with multiple strings'],
    prop: ["test", "test2", "test3"],
    response: ['n\n', 'NO\n', 'N\n']
  })
).addBatch(
  macros.shouldConfirm({
    messages: ['with multiple objects'],
    prop: [
      { message: "test" },
      { message: "test2" }
    ],
    response: ['y\n', 'y\n']
  })
).addBatch(
  macros.shouldNotConfirm({
    messages: ['with multiple objects'],
    prop: [
      { message: "test" },
      { message: "test2" }
    ],
    response: ['n\n', 'n\n']
  })
).addBatch(
  macros.shouldNotConfirm({
    messages: ['with multiple objects'],
    prop: [
      { message: "test" },
      { message: "test2" }
    ],
    response: ['n\n', 'y\n']
  })
).export(module);
