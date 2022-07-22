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
	final int PAY = 5;
	
	private NewBank() {
		customers = new HashMap<>();
//		addTestData();
	}
	
/*	private void addTestData() {
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
*/
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
			} else {
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
				case "PAY" : return moveFunds(customer, commands);
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

	/* Command to move funds from one accounts to
	* another, first check if the amount given can be converted to a
	* double, then get the account objects of the user. if
	* the accounts exists and the amount being moved is
	*  greater than 0, proceed. If the moveFunds function is used with
	* PAY, it will get the payee's account details, if used with MOVE
	* it will get the customers account details.
	 */
	private String moveFunds(CustomerID customer, String[] command){
		String result = "FAIL";
		double amountToTransfer;
		Account accountTo = null;
		Account accountFrom = customers.get(customer.getKey()).getAccount(command[2]);

		try {
			amountToTransfer = Double.parseDouble(command[1]);
		} catch (NumberFormatException e){
			return result;
		}

		if(command[0].equals("MOVE")) {
			if (checkCommandSize(command, MOVE)) {
				accountTo = customers.get(customer.getKey()).getAccount(command[3]);
			}
		} else if(command[0].equals("PAY")) {
			if (checkCommandSize(command, PAY)) {
				Customer payee = customers.get(command[3]);
				accountTo = payee.getAccount(command[4]);
			}
		}

		// actually move the funds here
		if(doTransaction(accountFrom, accountTo, amountToTransfer))
			result = "SUCCESS";

		return result;
	}

	// private function to actually move funds between two accounts. Returns Boolean
	private boolean doTransaction(Account accountFromName, Account accountToName, double amount){
		boolean bool = false;

		if(!(accountFromName==null || accountToName==null || amount <= 0)) {
			if (accountFromName.getBalance() >= amount) {

				accountFromName.changeBalance(-amount);
				accountToName.changeBalance(amount);
				bool = true;
			}
		}
		return bool;
	}

	private boolean checkCommandSize(String[] command, int sizeof){
		return command.length == sizeof;
	}

	private String showMyAccounts(CustomerID customer) {
		return (customers.get(customer.getKey())).accountsToString();
	}

	public HashMap<String,Customer> getAccounts(){
		return this.customers;
	}
}
