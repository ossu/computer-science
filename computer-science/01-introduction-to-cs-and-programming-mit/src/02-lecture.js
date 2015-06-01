// Modules
var prompt = require( 'prompt' );

// // Create a variable x and assign value 3 to it
// var x = 3;

// // Bind x to value 9
// x *= x; // or x = x * x;
// console.log( x );

// // read input data from terminal
// prompt.start();
// prompt.get({
//   name        : 'number',
//   description : 'Enter a number'
// }, function( err, results ) {

//   console.log( results.number );

// });

// // Verify if a integer number is even or odd.
// // If odd, verify if the number is divisible by 3
// // read input data from terminal
// prompt.start();

// prompt.get([
//   {
//     name        : 'number',
//     description : 'Enter a integer'
//   }
// ], function( err, results ) {

//   var int = parseInt( results.number, 10 );

//     if ( int % 2 === 0 ) {

//       console.log( int + ' is EVEN' );

//     } else {

//       var msg = int.toString() + ' is ODD';

//       if ( int % 3 !== 0 ) {

//         msg += ' and NOT divisible by 3';

//       }

//       console.log( msg );

//     }

// });

// Find the lowest among three numbers
prompt.start();
prompt.get([
  {
    name : 'x',
    description : 'Enter x'
  },
  {
    name : 'y',
    description : 'Enter y'
  },
  {
    name : 'z',
    description : 'Enter z'
  }
], function( err, results ) {

  if ( results.x < results.y ) {

    if ( results.x < results.z ) {
      console.log( 'x is least' );
    } else {
      console.log( 'z is least' );
    }

  } else if ( results.y < results.z ) {

    console.log( 'y is least' );

  } else {

    console.log( 'z is least' );

  }

});
