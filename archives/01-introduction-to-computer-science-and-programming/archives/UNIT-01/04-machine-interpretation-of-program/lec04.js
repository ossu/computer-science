var x = 0.5;
var epsilon = 0.01;
var low = 0;
var high = x;
var ans = ( high + low ) / 2;

while ( Math.abs( Math.pow( ans, 2 ) ) >= epsilon && ans <= x ) {
  console.log( 'ans = ' + ans + ' low = ' + low + ' high = ' + high );

  if ( Math.pow( ans, 2 ) < x )
    low = ans;
  else
    high = ans;
}

console.log( ans + ' is close to square root of ' + x );
