package newbank.server;

public class Account {

	private String accountName;
	private double openingBalance;
	private double currentBalance;

	public Account(String accountName, double openingBalance) {
		this.accountName = accountName;
		this.openingBalance = openingBalance;
		this.currentBalance = openingBalance;
	}

	public String toString() {
		return (accountName + ": " + currentBalance);
	}

	public String getName() {
		return this.accountName;
	}

	public double getBalance() {
		return this.currentBalance;
	}

	public void changeBalance(double amount){
		this.currentBalance += amount;
	}
}
