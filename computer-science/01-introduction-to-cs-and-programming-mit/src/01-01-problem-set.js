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
    description : 'Outstanding balance'
  },
  {
    name : 'interest',
    description : 'Annual interest rate'
  },
  {
    name : 'minimum',
    description : 'Minimum monthly payment rate'
  }
], function( err, results ) {

  var initialBalance = results.balance;
  var interest = results.interest;
  var minPayment = results.minimum;


});
