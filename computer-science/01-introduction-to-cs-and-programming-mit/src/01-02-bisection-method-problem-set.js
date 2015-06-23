/*
*
* Author: Eric Douglas
* Finished:
*
* Paying Debt Off In a Year - BISECTION METHOD
*
* - interest is compounded monthly according to the balance at
*     the start of the month 
* - monthly payment must be a multiple of $10 and is the same for all months
* -  it is possible for the balance to become negative using this scheme
*/

var prompt = require( 'prompt' );

prompt.start();
prompt.get([
  {
    name : 'balance',
    description : 'Enter the outstanding balance on your credit card'
  },
  {
    name : 'interest',
    description : 'Enter the annual credit card interest rate as a decimal'
  }
], function( err, results ) {

  // input variables
  var balance  = parseFloat( results.balance );
  var interest = parseFloat( results.interest );
  
  // output variables
  var parcel = 0;
  var months = 0;
  var paid   = 0;

  // helper variables
  var step    = 0.01;
  var min     = 0;
  var max     = 0;
  var guesses = 0;

  // Results
  console.log( '======= RESULT =======');
  console.log( 'Monthly payment to pay off debt in 1 year:', parcel );
  console.log( 'Number of months needed:', months );
  console.log( 'Balance', paid );

});
