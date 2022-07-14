package newbank.server;

import java.util.HashMap;

public class NewBank {

	private static final NewBank bank = new NewBank();
	private HashMap<String,Customer> customers;

	/* CONSTANTS which define the number of arguments to be passed
	* to a given command
	*/
	final int NEWACCOUNT = 2;
	final int REMOVEACCOUNT = 2;
	final int MOVE = 4;
	
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
	* commands from the NewBank customer are processed in this method. the string request
	* is converted to an Array of arguments. The first item in the array is the request.
	* The entire commands array is passed to the given function
	 */
	public synchronized String processRequest(CustomerID customer, String request) {
		String[] commands = request.split(" ");

		if(customers.containsKey(customer.getKey())) {
			switch(commands[0]) {
				case "SHOWMYACCOUNTS" : return showMyAccounts(customer);
				case "NEWACCOUNT" : return createNewAccount(customer, commands);
				case "REMOVEACCOUNT" : return deleteAccount(customer, commands);
				case "MOVE" : return moveFunds(customer, commands);
				default : return "FAIL";
			}
		}
		return "FAIL";
	}

	private String createNewAccount(CustomerID customer, String[] command) {
		String result = "FAIL";

		if(checkCommandSize(command, NEWACCOUNT)){
			String accountName = command[1];
			if(customers.get(customer.getKey()).getAccount(accountName) == null) {
				Account a = new Account(accountName, 0.00);
				customers.get(customer.getKey()).addAccount(a);
				result = "SUCCESS";
			}
		}
		return result;
	}


	private String deleteAccount(CustomerID customer, String[] command){
		String result = "FAIL";

		if(checkCommandSize(command, REMOVEACCOUNT)) {
			String accountName = command[1];
			Account toDelete = customers.get(customer.getKey()).getAccount(accountName);

			if (toDelete != null && toDelete.getBalance() == 0.00) {
				customers.get(customer.getKey()).removeAccount(toDelete);
				result = "SUCCESS";
			}
		}
		return result;
	}

	private String moveFunds(CustomerID customer, String[] command){
		String result = "FAIL";

		if(checkCommandSize(command, MOVE)){
			result="SUCCESS";
		}
		return result;
	}

	private boolean checkCommandSize(String[] command, int sizeof){
		if(command.length == sizeof){
			return true;
		} else {
			return false;
		}
	}

	private String showMyAccounts(CustomerID customer) {
		return (customers.get(customer.getKey())).accountsToString();
	}

}
