
// Represents a checking account
public class Checking extends Account {

	int minimum; // The minimum account balance allowed

	public Checking(int accountNum, int balance, String name, int minimum) {
		super(accountNum, balance, name);
		this.minimum = minimum;
	}
	@Override
	public int withdraw(int amt) {
		if (this.balance - this.minimum < amt) {
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
