// Modules
var range  = require( 'range' ).range;
var prompt = require( 'prompt' );

// // Find the cube root of a perfect cube
// prompt.start();
// prompt.get([
//   {
//     name        : 'x',
//     description : 'Enter a interger'
//   }
// ], function( err, results ) {

//   var x   = parseInt( results.x, 10 );
//   var ans = 0;

//   while ( Math.pow( ans, 3 ) < Math.abs( x )) {

//     ans += 1;
//     console.log( 'Current guess:', ans );

//   }

//   if ( Math.pow( ans, 3 ) !== Math.abs( x )) {

//     console.log( x, 'is not a perfect cube' );
  
//   } else {

//     if ( x < 0 ) {
//       ans = -ans;
//     }

//     console.log( 'Cube root of ' + x.toString() + ' is ' + ans.toString());

//   }

// });

// Find the cube root of a perfect cube
prompt.start();
prompt.get([
  {
    name        : 'x',
    description : 'Enter a interger'
  }
], function( err, results ) {

  var x = parseInt( results.x, 10 );
  var ans;

  for ( ans in range( 0, Math.abs( x ) + 1 )) {

    if ( Math.pow( ans, 3 ) === Math.abs( x )) {
      break;
    }

  }

  if ( Math.pow( ans, 3 ) !== Math.abs( x )) {

    console.log( x + ' is not a perfect cube' );

  } else {

    console.log( 'Cube root of ' + x.toString() + ' is ' + ans.toString());

  }

});