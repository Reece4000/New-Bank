package newbank.server;

import java.util.ArrayList;

public class Customer {
	
	private ArrayList<Account> accounts;

	public String storedPassword;
	public Customer(String password) {
		accounts = new ArrayList<>();
		storedPassword = password;
	}
	
	public String accountsToString() {
		String s = "";
		for(Account a : accounts) {
			s += a.toString();
		}
		return s;
	}

	public void addAccount(Account account) {
		accounts.add(account);		
	}
	public String getStoredPassword() {
	    return storedPassword;
	}
}
