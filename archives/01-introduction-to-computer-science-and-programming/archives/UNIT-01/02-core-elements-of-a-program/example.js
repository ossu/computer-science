x = 3; // Create variable and assign value 3 to it

x = x * x; // Bind x to value 9

console.log( x );  // print the x's value

process.stdout.write( 'Enter a number: ' ); // better way to print in the terminal
process.stdin.resume();
// convert the entered value from stream into a string
// process.stdin.setEncoding( 'utf8' );
process.stdin.on('data', function( inputText ) {
  process.stdout.write( inputText.toString() );
	process.exit();
});
