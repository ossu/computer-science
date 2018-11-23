balance = 999999; annualInterestRate = 0.18
monthlyInterestRate = (annualInterestRate) / 12.0
monthlyPaymentLowerBound = round(balance / 12,2)
monthlyPaymentUpperBound = round((balance * (1 + monthlyInterestRate)**12) / 12.0,2)
minimumPayment = 0
temp = balance
while abs(balance) > 0.04:
    balance = temp
    minimumPayment = round((monthlyPaymentLowerBound + monthlyPaymentUpperBound)/2,2)
    for i in range(12):
        balance = round(balance - minimumPayment,2)
        balance = round(balance + round(balance*annualInterestRate/12,2),2)
    if balance > 0:
        monthlyPaymentLowerBound = round(minimumPayment,2)
    elif balance < 0:
        monthlyPaymentUpperBound = round(minimumPayment,2)
        
print("Lowestest Payment: ", minimumPayment)        
