package newbank.server;

import java.util.HashMap;

public class NewBank {
	
	private static final NewBank bank = new NewBank();
	private HashMap<String,Customer> customers;
	
	private NewBank() {
		customers = new HashMap<>();
		addTestData();
	}
	
	private void addTestData() {
		Customer bhagy = new Customer("bhag");
		bhagy.addAccount(new Account("Main", 1000.0));
		customers.put("Bhagy", bhagy);
		
		Customer christina = new Customer("christi2000");
		christina.addAccount(new Account("Savings", 1500.0));
		customers.put("Christina", christina);
		
		Customer john = new Customer("John2022");
		john.addAccount(new Account("Checking", 250.0));
		customers.put("John", john);
	}
	
	public static NewBank getBank() {
		return bank;
	}
	
	public synchronized CustomerID checkLogInDetails(String userName, String password) {
		if (customers.containsKey(userName)) {
			Customer currentCustomer = customers.get(userName);
			if (password.equals(currentCustomer.getStoredPassword())) {
				return new CustomerID(userName);
			} else {
				return new CustomerID("Invalid password");
			}
		}else{
			return new CustomerID("Invalid username");
		}
	}

	/*
	* commands from the NewBank customer are processed in this method, the string requests
	* is split up into args which are used if necessary by the function selected by the
	* user
	 */
	public synchronized String processRequest(CustomerID customer, String request) {
		String[] commands = request.split(" ");

		if(customers.containsKey(customer.getKey())) {
			switch(commands[0]) {
			case "SHOWMYACCOUNTS" : return showMyAccounts(customer);
			default : return "FAIL";
			}
		}
		return "FAIL";
	}
	
	private String showMyAccounts(CustomerID customer) {
		return (customers.get(customer.getKey())).accountsToString();
	}

}
