/*
*
* Write a program to calculate the credit card balance after one year 
* if a person only pays the minimum monthly payment required by the credit
* card company each month.
*
* Use raw_input() to ask for the following three floating point numbers:
*
*   1. the outstanding balance on the credit card
*   2. annual interest rate
*   3. minimum monthly payment rate
*
* For each month, print the minimum monthly payment, remaining balance, 
*   principle paid in the format shown in the test cases below. 
* All numbers should be rounded to the nearest penny. Finally, print the result,
*   which should include the total amount paid that year and the remaining balance. 
*
*   - Minimum monthly payment = Minimum monthly payment rate x Balance
*       (Minimum monthly payment gets split into interest paid and principal paid)
*   - Interest Paid = Annual interest rate / 12 months x Balance
*   - Principal paid = Minimum monthly payment – Interest paid
*   - Remaining balance = Balance – Principal paid 
*
* http://bit.ly/1S6Tdys
*
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
  },
  {
    name : 'minimum',
    description : 'Enter the minimum monthly payment rate as a decimal'
  }
], function( err, results ) {

  console.log( results );

  // constants
  var PERIOD            = 12;

  // input variables
  var initialBalance    = parseFloat( results.balance, 10 );
  var interest          = parseFloat( results.interest, 10 );
  var minPayment        = parseFloat( results.minimum, 10 );

  console.log( initialBalance );
  console.log( interest );
  console.log( minPayment );
  
  // output variables
  var month             = 1;
  var minMonthlyPayment = 0;
  var principalPaid     = 0;
  var actualBalance     = initialBalance;

  // helper variables
  var actualMinPayment  = 0; 
  var actualInterest    = 0;
  var totalPaid         = 0;

  while( month <= PERIOD ) {

    actualMinPayment = minPayment * actualBalance;
    actualInterest   = ( interest / 12 ) * actualBalance;
    principalPaid    = actualMinPayment - actualInterest;
    actualBalance    -= principalPaid;
    totalPaid        += actualMinPayment;
  
    console.log( 'Month:', month );
    console.log( 'Minimum monthly payment:', actualMinPayment.toFixed( 2 ));
    console.log( 'Principal paid:', principalPaid.toFixed( 2 ));
    console.log( 'Remaining balance', actualBalance.toFixed( 2 ), '\n' );

    month += 1;
  
  }

  console.log( '======= RESULT =======');
  console.log( 'Total amount paid:', totalPaid.toFixed( 2 ));
  console.log( 'Remaining balance', actualBalance.toFixed( 2 ), '\n' );

});
