import tester.*;

// Bank Account Examples and Tests
public class Examples {

	public Examples() {
		reset();
	}

	Bank bank1;
	Bank bank2;

	Account check1;
	Account savings1;
	Account credit1;
	Account check2;
	Account savings2;
	Account credit2;

	// Initializes accounts to use for testing with effects.
	// We place inside reset() so we can "reuse" the accounts
	public void reset() {

		bank1 = new Bank("First Bank");
		bank2 = new Bank("Second Bank");

		// Initialize the account examples
		check1 = new Checking(1, 100, "First Checking Account", 20);
		savings1 = new Savings(4, 200, "First Savings Account", 2.5);
		credit1 = new Credit(8, 20, "First Line of Credit", 999, 5.0);
		check2 = new Checking(2, 20, "Second Checking Account", 20);
		savings2 = new Savings(24, 1000, "Second Savings Account", 2.5);
		credit2 = new Credit(28, 0, "Second Line of Credit", 999, 5.0);

		bank1.accounts = new ConsLoA(check1, new ConsLoA(savings1, new ConsLoA(credit1, new MtLoA())));
	}

	// Tests the exceptions we expect to be thrown when
	//   performing an "illegal" action.
	public void testExceptions(Tester t) {
		reset();
		t.checkException("Test for invalid Checking withdraw",
				new RuntimeException("85 is not available"),
				this.check1,
				"withdraw",
				85);
		t.checkException("Test for invalid Savings withdraw",
				new RuntimeException("1000 is not available"),
				this.savings1,
				"withdraw",
				1000);
		t.checkException("Test for invalid Credit withdraw",
				new RuntimeException("1000 is not available"),
				this.credit1,
				"withdraw",
				1000);
		t.checkException("Test for invalid Credit withdraw",
				new RuntimeException("1000 is greater than line of credit balance: 20"),
				this.credit1,
				"deposit",
				1000);
		t.checkException("Test for invalid Account number",
				new RuntimeException("100 cound not be found"),
				this.bank1,
				"withdraw",
				100,
				100);
		t.checkException("Test for invalid Account add",
				new RuntimeException("100 cound not be found"),
				this.bank1,
				"removeAccount",
				100);
	}

	// Test the deposit method(s)
	public void testWithdraw(Tester t) {
		reset();
		t.checkExpect(check1.withdraw(25), 75);
		t.checkExpect(check1, new Checking(1, 75, "First Checking Account", 20));
		reset();
		t.checkExpect(savings1.withdraw(25), 175);
		t.checkExpect(savings1, new Savings(4, 175, "First Savings Account", 2.5));
		reset();
		t.checkExpect(credit1.withdraw(900), 920);
		t.checkExpect(credit1, new Credit(8, 920, "First Line of Credit", 999, 5.0));
		reset();
		t.checkExpect(bank1.withdraw(1, 25), 75);
		t.checkExpect(check1, new Checking(1, 75, "First Checking Account", 20));
		reset();
		t.checkExpect(bank1.withdraw(4, 25), 175);
		t.checkExpect(savings1, new Savings(4, 175, "First Savings Account", 2.5));
		reset();
		t.checkExpect(bank1.withdraw(8, 900), 920);
		t.checkExpect(credit1, new Credit(8, 920, "First Line of Credit", 999, 5.0));
	}
	public void testDeposit(Tester t) {
		reset();
		t.checkExpect(check1.deposit(25), 125);
		t.checkExpect(check1, new Checking(1, 125, "First Checking Account", 20));
		reset();
		t.checkExpect(savings1.deposit(25), 225);
		t.checkExpect(savings1, new Savings(4, 225, "First Savings Account", 2.5));
		reset();
		t.checkExpect(credit1.deposit(20), 0);
		t.checkExpect(credit1, new Credit(8, 0, "First Line of Credit", 999, 5.0));
		reset();
		t.checkExpect(bank1.deposit(1, 25), 125);
		t.checkExpect(check1, new Checking(1, 125, "First Checking Account", 20));
		reset();
		t.checkExpect(bank1.deposit(4, 25), 225);
		t.checkExpect(savings1, new Savings(4, 225, "First Savings Account", 2.5));
		reset();
		t.checkExpect(bank1.deposit(8, 20), 0);
		t.checkExpect(credit1, new Credit(8, 0, "First Line of Credit", 999, 5.0));
		reset();
	}
	public void testAddAccount(Tester t) {
		reset();
		t.checkExpect(this.bank2.accounts, new MtLoA());
		this.bank2.add(check1);
		t.checkExpect(this.bank2.accounts, new ConsLoA(check1, new MtLoA()));
		this.bank2.add(savings1);
		t.checkExpect(this.bank2.accounts, new ConsLoA(savings1, new ConsLoA(check1, new MtLoA())));
	}
	public void testRemoveAccount(Tester t) {
		reset();
		t.checkExpect(bank1.accounts,
				new ConsLoA(check1, new ConsLoA(savings1, new ConsLoA(credit1, new MtLoA()))));
		bank1.removeAccount(1);
		t.checkExpect(bank1.accounts,
				new ConsLoA(savings1, new ConsLoA(credit1, new MtLoA())));
		reset();
		bank1.removeAccount(4);
		t.checkExpect(bank1.accounts,
				new ConsLoA(check1, new ConsLoA(credit1, new MtLoA())));
		reset();
		bank1.removeAccount(8);
		t.checkExpect(bank1.accounts,
				new ConsLoA(check1, new ConsLoA(savings1, new MtLoA())));
	}
}
