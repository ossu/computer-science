
// Represents a List of Accounts
public interface ILoA {
	public Account find(int accountNum);
	public ILoA removeAccount(int accountNum);
}
