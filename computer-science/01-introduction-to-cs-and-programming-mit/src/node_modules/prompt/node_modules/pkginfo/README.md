# node-pkginfo

An easy way to expose properties on a module from a package.json

## Installation

### Installing npm (node package manager)
```
  curl http://npmjs.org/install.sh | sh
```

### Installing pkginfo
```
  [sudo] npm install pkginfo
```

## Motivation
How often when writing node.js modules have you written the following line(s) of code? 

* Hard code your version string into your code

``` js
  exports.version = '0.1.0';
```

* Programmatically expose the version from the package.json

``` js
  exports.version = JSON.parse(fs.readFileSync('/path/to/package.json', 'utf8')).version;
```

In other words, how often have you wanted to expose basic information from your package.json onto your module programmatically? **WELL NOW YOU CAN!**

## Usage

Using `pkginfo` is idiot-proof, just require and invoke it. 

``` js
  var pkginfo = require('pkginfo')(module);
  
  console.dir(module.exports);
```

By invoking the `pkginfo` module all of the properties in your `package.json` file will be automatically exposed on the callee module (i.e. the parent module of `pkginfo`). 

Here's a sample of the output:

```
  { name: 'simple-app',
    description: 'A test fixture for pkginfo',
    version: '0.1.0',
    author: 'Charlie Robbins <charlie.robbins@gmail.com>',
    keywords: [ 'test', 'fixture' ],
    main: './index.js',
    scripts: { test: 'vows test/*-test.js --spec' },
    engines: { node: '>= 0.4.0' } }
```

### Expose specific properties
If you don't want to expose **all** properties on from your `package.json` on your module then simple pass those properties to the `pkginfo` function:

``` js
  var pkginfo = require('pkginfo')(module, 'version', 'author');
  
  console.dir(module.exports);
```

```
  { version: '0.1.0',
    author: 'Charlie Robbins <charlie.robbins@gmail.com>' }
```

If you're looking for further usage see the [examples][0] included in this repository. 

## Run Tests
Tests are written in [vows][1] and give complete coverage of all APIs.

```
  vows test/*-test.js --spec
```

[0]: https://github.com/indexzero/node-pkginfo/tree/master/examples
[1]: http://vowsjs.org

#### Author: [Charlie Robbins](http://nodejitsu.com)
#### License: MIT