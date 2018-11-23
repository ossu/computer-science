balance = 3329; annualInterestRate = 0.2
minimumPayment = 0.01
temp = balance
while balance > 0:
    balance = temp
    for i in range(12):
        balance = round(balance - minimumPayment,2)
        balance = round(balance + round(balance*annualInterestRate/12,2),2)
    if balance > 0:
        minimumPayment += 0.01
print("Lowestest Payment: ", minimumPayment)
    
