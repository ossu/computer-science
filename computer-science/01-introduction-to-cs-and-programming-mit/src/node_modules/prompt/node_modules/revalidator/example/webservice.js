//
// (C) 2011, Nodejitsu Inc.
// MIT License
//
// A simple web service for storing JSON data via REST
//
// GET - View Object
// POST - Create Object
// PUT - Update Object
// DELETE - Delete Object
//

var revalidator = require('../'),
  http = require('http'),
//
// Keep our objects in a simple memory store
//
  memoryStore = {},
//
// Set up our request schema
//
  schema = {
    properties: {
      url: {
        description: 'the url the object should be stored at',
        type: 'string',
        pattern: '^/[^#%&*{}\\:<>?\/+]+$',
        required: true
      },
      challenge: {
        description: 'a means of protecting data (insufficient for production, used as example)',
        type: 'string',
        minLength: 5
      },
      body: {
        description: 'what to store at the url',
        type: 'any',
        default: null
      }
    }
  }

var server = http.createServer(function validateRestRequest (req, res) {
  req.method = req.method.toUpperCase();

  //
  // Log the requests
  //
  console.log(req.method, req.url);

  //
  // Buffer the request so it can be parsed as JSON
  //
  var requestBody = [];
  req.on('data', function addDataToBody (data) {
    requestBody.push(data);
  });

  //
  // Once the request has ended work with the body
  //
  req.on('end', function dealWithRest () {

    //
    // Parse the JSON
    //
    requestBody = requestBody.join('');
    if ({POST: 1, PUT: 1}[req.method]) {
      try {
        requestBody = JSON.parse(requestBody);
      }
      catch (e) {
        res.writeHead(400);
        res.end(e);
        return;
      }
    }
    else {
      requestBody = {};
    }

    //
    // If this was sent to a url but the body url was not declared
    // Make sure the body get the requested url so that our schema
    //  validates before we work on it
    //
    if (!requestBody.url) {
      requestBody.url = req.url;
    }

    //
    // Don't let users override the main API endpoint
    //
    if (requestBody.url === '/') {
      res.writeHead(400);
      res.end('Cannot override the API endpoint "/"');
      return;
    }

    //
    // See if our request and target are out of sync
    // This lets us double check the url we are about to take up
    //  if we choose to send the request to the url directly
    //
    if (req.url !== '/' && requestBody.url !== req.url) {
      res.writeHead(400);
      res.end('Requested url and actual url do not match');
      return;
    }

    //
    // Validate the schema
    //
    var validation = revalidator.validate(requestBody, schema);
    if (!validation.valid) {
      res.writeHead(400);
      res.end(validation.errors.join('\n'));
      return;
    }

    //
    // Grab the current value from storage and
    //  check if it is a valid state for REST
    //
    var storedValue = memoryStore[requestBody.url];
    if (req.method === 'POST') {
      if (storedValue) {
        res.writeHead(400);
        res.end('ALREADY EXISTS');
        return;
      }
    }
    else if (!storedValue) {
      res.writeHead(404);
      res.end('DOES NOT EXIST');
      return;
    }

    //
    // Check our challenge
    //
    if (storedValue && requestBody.challenge != storedValue.challenge) {
      res.writeHead(403);
      res.end('NOT AUTHORIZED');
      return;
    }

    //
    // Since revalidator only checks and does not manipulate
    //  our object we need to set up the defaults our selves
    // For an easier solution to this please look at Flatiron's
    //  `Resourceful` project
    //
    if (requestBody.body === undefined) {
      requestBody.body = schema.properties.body.default;
    }

    //
    // Use REST to determine how to manipulate the stored
    //  values
    //
    switch (req.method) {

      case "GET":
        res.writeHead(200);
        var result = storedValue.body;
        res.end(JSON.stringify(result));
        return;

      case "POST":
        res.writeHead(201);
        res.end();
        memoryStore[requestBody.url] = requestBody;
        return;

      case "DELETE":
        delete memoryStore[requestBody.url];
        res.writeHead(200);
        res.end();
        return;

      case "PUT":
        memoryStore[requestBody.url] = requestBody;
        res.writeHead(200);
        res.end();
        return;

      default:
        res.writeHead(400);
        res.end('Invalid Http Verb');
        return;
    }
  });
})
//
// Listen to various ports depending on environment we are being run on
//
server.listen(process.env.PORT || process.env.C9_PORT || 1337, function reportListening () {

  console.log('JSON REST Service listening on port', this.address().port);
  console.log('Requests can be sent via REST to "/" if they conform to the following schema:');
  console.log(JSON.stringify(schema, null, ' '));

});