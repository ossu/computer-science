//// Create a variable x and assign value 3 to it
//var x = 3;
//
//// Bind x to value 9
//x *= x; // or x = x * x;
//console.log( x );

//// read input data from terminal
//process.stdin.resume();
//
//console.log( 'Enter a number:' );
//process.stdin.setEncoding( 'utf8' );
//
//process.stdin.on( 'data', function( input ) {
//
//  console.log( typeof( input ));
//  console.log( input );
//
//  process.exit();
//
//});

// Verify if a integer number is even or odd.
// If odd, verify if the number is divisible by 3
// read input data from terminal
process.stdin.resume();

console.log( 'Enter a integer:' );
process.stdin.setEncoding( 'utf8' );

process.stdin.on( 'data', function( input ) {

  var int = parseInt( input, 10 );

  if ( int % 2 === 0 ) {

    console.log( 'Even' );

  } else {

    console.log( 'Odd' );

    if ( int % 3 !== 0 ) {

      console.log( 'And not divisible by 3' );

    }

  }

  process.exit();

});