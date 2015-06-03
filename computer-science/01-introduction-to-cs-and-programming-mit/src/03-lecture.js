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

// // Find the cube root of a perfect cube
// prompt.start();
// prompt.get([
//   {
//     name        : 'x',
//     description : 'Enter a interger'
//   }
// ], function( err, results ) {

//   var x = parseInt( results.x, 10 );
//   var ans;

//   for ( ans in range( 0, Math.abs( x ) + 1 )) {

//     if ( Math.pow( ans, 3 ) === Math.abs( x )) {
//       break;
//     }

//   }

//   if ( Math.pow( ans, 3 ) !== Math.abs( x )) {

//     console.log( x + ' is not a perfect cube' );

//   } else {

//     console.log( 'Cube root of ' + x.toString() + ' is ' + ans.toString());

//   }

// });

// // Find closest number to be a square root of another number - Brute Force
// var x          = 25;
// var epsilon    = 0.01;
// var numGuesses = 0;
// var ans        = 0;

// while ( Math.abs( Math.pow( ans, 2 ) - x ) >= epsilon && ans <= x ) {

//   ans += 0.00001;
//   numGuesses += 1;

// }

// console.log( 'numGuesses: ' + numGuesses );

// if ( Math.abs( Math.pow( ans, 2 ) - x >= epsilon )) {
//   console.log( 'Failed on square root of ' + x.toString());
// } else {
//   console.log( ans.toString() + ' is close to square root of ' + x.toString());
// }

// Find closest number to be a square root of another number - bisection method
var x          = 12345;
var epsilon    = 0.01;
var numGuesses = 0;
var low        = 0;
var high       = x;
var ans        = ( high + low ) / 2;

while ( Math.abs( Math.pow( ans, 2 ) - x ) >= epsilon && ans <= x ) {

  numGuesses += 1;

  if ( Math.pow( ans, 2 ) < x ) {

    low = ans;

  } else {

    high = ans;

  }

  ans = ( high + low ) / 2;

}

console.log( 'numGuesses:', numGuesses );
console.log( ans, 'is close to square root of', x );