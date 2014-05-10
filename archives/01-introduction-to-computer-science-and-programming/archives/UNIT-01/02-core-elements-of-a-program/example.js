x = 3; // Create variable and assign value 3 to it
x = x * x; // Bind x to value 9
console.log( x );  // print the x's value
process.stdout.write( 'Enter a number: ' ); // better way to print in the terminal
process.stdin.resume();
// convert the entered value from stream to a string
// process.stdin.setEncoding( 'utf8' );
var y = '';
process.stdin.once('data', function( inputText ) {
  y = inputText.toString();
	process.stdout.write( typeof y + '\n');
  process.stdout.write( y + '\n' );

  process.stdout.write( 'Enter another number: ' ); 

  process.stdin.once('data', function( inputText ) {
	  y = parseFloat( inputText.toString() );
	  process.stdout.write( typeof y + '\n');
	  process.stdout.write( y + '\n' );
  });
});
