var tap = require('tap')
var read = require('../lib/read.js')

var CLOSE = 'close'
if (process.version.match(/^v0\.6/)) {
  CLOSE = 'exit'
}

if (process.argv[2] === 'child') {
  return child()
}

var spawn = require('child_process').spawn
function child () {
  read({prompt:'1'}, function (er, r1) {if (er) throw er
  read({prompt:'2'}, function (er, r2) {if (er) throw er
  read({prompt:'3'}, function (er, r3) {if (er) throw er
  read({prompt:'4'}, function (er, r4) {if (er) throw er
  read({prompt:'5'}, function (er, r5) {if (er) throw er
  read({prompt:'6'}, function (er, r6) {if (er) throw er
  read({prompt:'7'}, function (er, r7) {if (er) throw er
  read({prompt:'8'}, function (er, r8) {if (er) throw er
  read({prompt:'9'}, function (er, r9) {if (er) throw er
  read({prompt:'10'}, function (er, r10) {if (er) throw er
  read({prompt:'11'}, function (er, r11) {if (er) throw er
  read({prompt:'12'}, function (er, r12) {if (er) throw er
  read({prompt:'13'}, function (er, r13) {if (er) throw er
  read({prompt:'14'}, function (er, r14) {if (er) throw er
  read({prompt:'15'}, function (er, r15) {if (er) throw er
  read({prompt:'16'}, function (er, r16) {if (er) throw er
  read({prompt:'17'}, function (er, r17) {if (er) throw er
  read({prompt:'18'}, function (er, r18) {if (er) throw er
    console.log(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10,
                r11, r12, r13, r14, r15, r16, r17, r18)
    if (process.stdin.unref)
      process.stdin.unref()
  })})})})})})})})})})})})})})})})})})
}

tap.test('many reads', function (t) {
  var child = spawn(process.execPath, [__filename, 'child'])
  var n = 0
  var output = ''
  var expect = '1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 ' +
               '16 17 18 1 2 3 4 5 6 7 8 9 10 11 12 ' +
               '13 14 15 16 17 18\n'
  var write = child.stdin.write.bind(child.stdin)
  var answers =
      [ '1\n',
        '2\n',
        '3\n',
        '4\n',
        '5\n',
        '6\n',
        '7\n',
        '8\n',
        '9\n',
        '10\n',
        '11\n',
        '12\n',
        '13\n',
        '14\n',
        '15\n',
        '16\n',
        '17\n',
        '18\n' ]
  child.stdout.on('data', function (c) {
    n++;
    output += c
    if (answers.length) {
      write(answers.shift())
    }
  })
  child.stderr.on('data', function (c) {
    output += c
    console.error('' + c)
  })
  child.on(CLOSE, function (c) {
    t.equal(output, expect)
    t.equal(n, 19)
    t.end()
  })
})
