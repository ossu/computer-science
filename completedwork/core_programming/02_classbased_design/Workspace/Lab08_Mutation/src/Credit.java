
// Represents a credit line account
public class Credit extends Account {

	int creditLine;  // Maximum amount accessible
	double interest; // The interest rate charged

	public Credit(int accountNum, int balance, String name, int creditLine, double interest) {
		super(accountNum, balance, name);
		this.creditLine = creditLine;
		this.interest = interest;
	}
	public int withdraw(int amt) {
		if (this.creditLine < this.balance + amt) {
			throw new RuntimeException(amt + " is not available");
		} else {
			this.balance = this.balance + amt;
			return this.balance;
		}
	}
	public int deposit(int funds) {
		if (this.balance < funds) {
			throw new RuntimeException(
					funds + " is greater than line of credit balance: " + this.balance);
		}
		this.balance = this.balance - funds;
		return this.balance;
	}
}
