package newbank.server;

public class Account {

	private String accountName;
	private double accountBalance;

	public Account(String accountName, double openingBalance) {
		this.accountName = accountName;
		this.accountBalance = openingBalance;
	}

	public String toString() {
		return (accountName + ": " + accountBalance);
	}

	public String getName() {
		return this.accountName;
	}

	public void renameAccount(String newName) {
		this.accountName = newName;
	}

	public double getBalance() {
		return this.accountBalance;
	}

	public void changeBalance(double amount){
		this.accountBalance += amount;
	}
}
