
// Represents the empty List of Accounts
public class MtLoA implements ILoA {

	MtLoA() {}
	public Account find(int accountNum) {
		throw new RuntimeException(accountNum + " cound not be found");
	}
	public ILoA removeAccount(int accountNum) {
		throw new RuntimeException(accountNum + " cound not be found");
	}
}
