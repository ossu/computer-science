
// Represents a non-empty List of Accounts...
public class ConsLoA implements ILoA {

	Account first;
	ILoA rest;

	public ConsLoA(Account first, ILoA rest) {
		this.first = first;
		this.rest = rest;
	}
	public Account find(int accountNum) {
		if (this.first.accountNum == accountNum) {
			return this.first;
		} else {
			return this.rest.find(accountNum);
		}
	}
	public ILoA removeAccount(int accountNum) {
		if (this.first.accountNum == accountNum) {
			return this.rest;
		} else {
			return new ConsLoA(this.first, this.rest.removeAccount(accountNum));
		}
	}

	/* Template
	 *  Fields:
	 *    ... this.first ...         --- Account
	 *    ... this.rest ...          --- ILoA
	 *
	 *  Methods:
	 *
	 *  Methods for Fields:
	 *
	 */
}
