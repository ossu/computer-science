/*

 Write a program that does the following in order:

 1. Asks the user to enter his/her date of birth.
 2. Asks the user to enter his/her last name.
 3. Prints out the user’s last name and date of birth, in that order.

*/

var answers   = 0;
var birthDate = '';
var lastName  = '';

process.stdin.resume();

console.log( 'What is your birth date?' );
process.stdin.setEncoding( 'utf8' );

process.stdin.on( 'data', function( input ) {

  if ( answers === 0 ) {

    birthDate = input;
    answers += 1;

    console.log( 'What is your last name?' );

  } else if ( answers === 1 ) {

    lastName = input;

    console.log(
      '================\n<< Informations >>\n' +
      'Birth date: ' + birthDate + '\n' +
      'Last Name: '  + lastName + '\n' +
      'Bye!'
    );

    process.exit();

  }

});