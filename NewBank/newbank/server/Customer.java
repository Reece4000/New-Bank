package newbank.server;

import java.util.ArrayList;
import java.util.Objects;

public class Customer {
	
	private ArrayList<Account> accounts;
	
	public Customer() {
		accounts = new ArrayList<>();
	}

	/* Loop through accounts names converting to string
	* format, if more than 1 account add delimiter in
	* to print in a nicer format.
	 */
	public String accountsToString() {
		String s = "";
		String delimiter = "";

		if(accounts.size()>1){
			delimiter = Character.toString((char)124);
		}

		for(Account a : accounts) {
			s += a.toString()+" "+delimiter+" ";
		}
		return s;
	}

	/* loop through customer accounts,
	* if name matches return account else null
	 */
	public Account getAccount(String accountName){
		for(Account a : accounts){
			if(Objects.equals(accountName, a.getName())){
				return a;
			}
		}
		return null;
	}


	public void addAccount(Account account) {
		accounts.add(account);		
	}

	public void removeAccount(Account account) {
		accounts.remove(account);
	}
}
