package newbank.server;

public class Account {

	private String accountName;
	private double openingBalance;

	public Account(String accountName, double openingBalance) {
		this.accountName = accountName;
		this.openingBalance = openingBalance;
	}

	public String toString() {
		ArrayList listofAccounts = new Arraylist<>;

		listofAccounts.add(accountName);
		listofAccounts.add(openingBalance);

		return (listofAccounts);
	}

	public String getName() {
		return this.accountName;
	}

	public double getBalance() {
		return this.openingBalance;
	}
}
