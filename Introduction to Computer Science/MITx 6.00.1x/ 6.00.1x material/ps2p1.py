def main():
    balance = 484
    annualInterestRate = 0.2
    monthlyPaymentRate = 0.04
    for i in range(12):
        balance = round(balance - round(balance*monthlyPaymentRate,2),2)
        balance = round(balance + round(balance*annualInterestRate/12,2),2)
    print("Remaining balance: ", balance)



main()
