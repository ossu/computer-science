
// Represents a savings account
public class Savings extends Account {

	double interest; // The interest rate

	public Savings(int accountNum, int balance, String name, double interest) {
		super(accountNum, balance, name);
		this.interest = interest;
	}
	public int withdraw(int amt) {
		if (this.balance < amt) {
			throw new RuntimeException(amt + " is not available");
		} else {
			this.balance = this.balance - amt;
			return this.balance;
		}
	}
	public int deposit(int funds) {
		this.balance = this.balance + funds;
		return this.balance;
	}
}
