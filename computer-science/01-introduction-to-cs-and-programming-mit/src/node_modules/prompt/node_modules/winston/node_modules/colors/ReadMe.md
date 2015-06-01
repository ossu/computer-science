# colors.js - get color and style in your node.js console ( and browser ) like what

<img src="http://i.imgur.com/goJdO.png" border = "0"/>


## Installation

    npm install colors

## colors and styles!

- bold
- italic
- underline
- inverse
- yellow
- cyan
- white
- magenta
- green
- red
- grey
- blue
- rainbow
- zebra
- random

## Usage

``` js
var colors = require('./colors');

console.log('hello'.green); // outputs green text
console.log('i like cake and pies'.underline.red) // outputs red underlined text
console.log('inverse the color'.inverse); // inverses the color
console.log('OMG Rainbows!'.rainbow); // rainbow (ignores spaces)
```

# Creating Custom themes

```js

var colors = require('colors');

colors.setTheme({
  silly: 'rainbow',
  input: 'grey',
  verbose: 'cyan',
  prompt: 'grey',
  info: 'green',
  data: 'grey',
  help: 'cyan',
  warn: 'yellow',
  debug: 'blue',
  error: 'red'
});

// outputs red text
console.log("this is an error".error);

// outputs yellow text
console.log("this is a warning".warn);
```


### Contributors 

Marak (Marak Squires)
Alexis Sellier (cloudhead)
mmalecki (Maciej Małecki)
nicoreed (Nico Reed)
morganrallen (Morgan Allen)
JustinCampbell (Justin Campbell)
ded (Dustin Diaz)


####  , Marak Squires , Justin Campbell, Dustin Diaz (@ded)
