
// Represents a Bank with list of accounts
public class Bank {

	String name;
	ILoA accounts;

	public Bank(String name) {
		this.name = name;

		// Each bank starts with no accounts
		this.accounts = new MtLoA();
	}

	// EFFECT: Add a new account to this Bank
	void add(Account acct) {
		this.accounts = new ConsLoA(acct, this.accounts);
	}
	//EFFECT: Withdraw the given amount
	//Return the new balance
	public int withdraw(int accountNum, int amt) {
		return this.accounts.find(accountNum).withdraw(amt);
	}
	//EFFECT: Deposit the given funds into this account
	//Return the new balance
	public int deposit(int accountNum, int funds) {
		return this.accounts.find(accountNum).deposit(funds);
	}
	// EFFECT: Remove the given account from this Bank
	public void removeAccount(int accountNum) {
		this.accounts = this.accounts.removeAccount(accountNum);
	}
}
