Fork of https://github.com/douglascrockford/JSON-js, maintained in npm as `cycle`.

# Contributors

* Douglas Crockford
* Nuno Job
* Justin Warkentin

# JSON in JavaScript

Douglas Crockford
douglas@crockford.com

2010-11-18


JSON is a light-weight, language independent, data interchange format.
See http://www.JSON.org/

The files in this collection implement JSON encoders/decoders in JavaScript.

JSON became a built-in feature of JavaScript when the ECMAScript Programming
Language Standard - Fifth Edition was adopted by the ECMA General Assembly
in December 2009. Most of the files in this collection are for applications
that are expected to run in obsolete web browsers. For most purposes, json2.js
is the best choice.


json2.js: This file creates a JSON property in the global object, if there
isn't already one, setting its value to an object containing a stringify
method and a parse method. The parse method uses the eval method to do the
parsing, guarding it with several regular expressions to defend against
accidental code execution hazards. On current browsers, this file does nothing,
prefering the built-in JSON object.

json.js: This file does everything that json2.js does. It also adds a
toJSONString method and a parseJSON method to Object.prototype. Use of this
file is not recommended.

json_parse.js: This file contains an alternative JSON parse function that
uses recursive descent instead of eval.

json_parse_state.js: This files contains an alternative JSON parse function that
uses a state machine instead of eval.

cycle.js: This file contains two functions, JSON.decycle and JSON.retrocycle,
which make it possible to encode cyclical structures and dags in JSON, and to
then recover them. JSONPath is used to represent the links.
http://GOESSNER.net/articles/JsonPath/
