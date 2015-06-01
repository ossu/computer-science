var read = require("../lib/read.js")

read({prompt: "Username: ", default: "test-user" }, function (er, user) {
  read({prompt: "Password: ", default: "test-pass", silent: true }, function (er, pass) {
    read({prompt: "Password again: ", default: "test-pass", silent: true }, function (er, pass2) {
      console.error({user: user,
                     pass: pass,
                     verify: pass2,
                     passMatch: (pass === pass2)})
      console.error("the program should exit now")
    })
  })
})
