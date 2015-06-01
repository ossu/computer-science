var tap = require('tap')
var read = require('../lib/read.js')

if (process.argv[2] === 'child') {
  return child()
}

var CLOSE = 'close'
if (process.version.match(/^v0\.6/)) {
  CLOSE = 'exit'
}

var spawn = require('child_process').spawn

tap.test('basic', function (t) {
  var child = spawn(process.execPath, [__filename, 'child'])
  var output = ''
  var write = child.stdin.write.bind(child.stdin)
  child.stdout.on('data', function (c) {
    console.error('data %s', c)
    output += c
    if (output.match(/Username: \(test-user\) $/)) {
      process.nextTick(write.bind(null, 'a user\n'))
    } else if (output.match(/Password: \(<default hidden>\) $/)) {
      process.nextTick(write.bind(null, 'a password\n'))
    } else if (output.match(/Password again: \(<default hidden>\) $/)) {
      process.nextTick(write.bind(null, 'a password\n'))
    } else {
      console.error('prompts done, output=%j', output)
    }
  })

  var result = ''
  child.stderr.on('data', function (c) {
    result += c
    console.error('result %j', c.toString())
  })

  child.on(CLOSE, function () {
    result = JSON.parse(result)
    t.same(result, {"user":"a user","pass":"a password","verify":"a password","passMatch":true})
    t.equal(output, 'Username: (test-user) Password: (<default hidden>) Password again: (<default hidden>) ')
    t.end()
  })
})

function child () {
  read({prompt: "Username: ", default: "test-user" }, function (er, user) {
    read({prompt: "Password: ", default: "test-pass", silent: true }, function (er, pass) {
      read({prompt: "Password again: ", default: "test-pass", silent: true }, function (er, pass2) {
        console.error(JSON.stringify({user: user,
                       pass: pass,
                       verify: pass2,
                       passMatch: (pass === pass2)}))
        if (process.stdin.unref)
          process.stdin.unref()
      })
    })
  })
}
